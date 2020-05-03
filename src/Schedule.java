import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Schedule
{
	private ArrayList<ScheduleEvent> currentEventlist;
	private User user;
	public Schedule(User user1)
	{
		currentEventlist = new ArrayList<>();
		user = user1;
		// Initialized as empty
	}
	public ArrayList<ScheduleEvent> getActiveEvents()
	{
		Date current = new Date(System.currentTimeMillis());
		ArrayList<ScheduleEvent> active = new ArrayList<>();
		for(ScheduleEvent e: currentEventlist)
			if(e.getBegin().compareTo(current) > 0)
				active.add(e);
		return active;
	}

	public void addTask(Task toAdd)
	{
		final double totalTime = toAdd.getTimeLeft();
		ExtendedWorkDuration bestWorkingShifts = user.getExtendedWorkDuration();
		final int workSessionsApproximate = (int) (totalTime / bestWorkingShifts.getMaxHoursWork());
		final double eachWorkSessionTime = totalTime / workSessionsApproximate;

		for(int i = 0; i < workSessionsApproximate; i++)
		{
			ScheduleEvent event = new ScheduleEvent(null, null, toAdd, this);
			Date currentDate = new Date(System.currentTimeMillis()), endDate = toAdd.getComplete();
			allocateEvent(event, eachWorkSessionTime);
		}
	}

	public boolean allocateEvent(ScheduleEvent event, double timeNeeded)
	{
		TimeFrameProductivity productivity = user.getTimeFrameProductivity();
		ExtendedWorkDuration workDuration = user.getExtendedWorkDuration();
		double idealWorkingHours = workDuration.getMaxHoursWork(), idealBreak = workDuration.getMinHoursBreak();
		ArrayList<ScheduleEvent> active = getActiveEvents();
		Collections.sort(active);
		int idx = 0;
		long dayMillisStart = System.currentTimeMillis();
		long dayMillisEnd = event.getAssociatedTask().getComplete().getTime();

		double minCost = Long.MAX_VALUE;
		Date bestStart = null, bestEnd = null;
		for(long st = dayMillisStart; st <= dayMillisEnd; st += 60000) // Check each minute of the day brute force
		{
			for(long en = dayMillisStart; en <= dayMillisEnd; en += 60000) // Check each minute of the day brute force
			{
				if(st == en) continue;
				event.setBegin(new Date(st));
				event.setEnd(new Date(en));
				boolean cannotAllocate = false;
				for(ScheduleEvent e: active)
				{
					if(e.overlaps(event))
						cannotAllocate = true;
					if(cannotAllocate)
						break;
				}
				ScheduleEvent lastBefore = null, firstAfter = null;
				for(ScheduleEvent e: active)
				{
					if(e.getEnd().getTime() <= st) lastBefore = e;
					else if(e.getBegin().getTime() >= en)
					{
						firstAfter = e;
						break;
					}
				}
				if(cannotAllocate)
					continue;
				double timeFrameCost = findCost(productivity, st, en);
				double currTime = en - st;
				double timeCost = Math.abs(timeNeeded - (currTime / (60 * 60 * 1000))) * 4;

				double prevBreak = lastBefore == null? Long.MAX_VALUE: (st - lastBefore.getEnd().getTime()) / (60 * 60 * 1000);
				double breakAfter = firstAfter == null? Long.MAX_VALUE: (firstAfter.getBegin().getTime() - en) / (60 * 60 * 1000);

				double breakCost = Math.max(0, idealBreak - prevBreak) / 3.0 + Math.max(0, idealBreak - breakAfter) / 3.0;
				double workingLongDurationCost = Math.max((en - st) / (60 * 60 * 1000) - idealWorkingHours, 0) * 1.7;

				double totalCost = workingLongDurationCost + breakCost + timeCost + timeFrameCost;
				if(totalCost <= minCost)
				{
					minCost = totalCost;
					bestStart = new Date(st);
					bestEnd = new Date(en);
				}
			}
		}
		if(minCost == Long.MAX_VALUE) return false;
		event.setBegin(bestStart);
		event.setEnd(bestEnd);
		currentEventlist.add(event);
		return true;
	}
	private static double findCost(TimeFrameProductivity bestTimeFrame, long start, long end)
	{
		double startHour = TimeFrameProductivity.findHour(start);
		double endHour = TimeFrameProductivity.findHour(end);
		if(startHour >= bestTimeFrame.getTimeFrameBegin() && endHour <= bestTimeFrame.getTimeFrameEnd()) return 0;
		return Math.max(0, bestTimeFrame.getTimeFrameBegin() - startHour) + Math.max(0, endHour - bestTimeFrame.getTimeFrameEnd());
	}

	public boolean addUnchangeableEvent(ScheduleEvent event)
	{
		ArrayList<ScheduleEvent> conflict = new ArrayList<>();
		int idx = 0;
		while(idx < currentEventlist.size())
		{
			if(event.overlaps(currentEventlist.get(idx)))
			{
				conflict.add(currentEventlist.get(idx));
				if(currentEventlist.get(idx).getAssociatedTask() == null) return false;
				currentEventlist.remove(idx);
			}
			else idx++;
		}
		for(ScheduleEvent se: conflict) allocateEvent(se, TimeFrameProductivity.findHour(se.getEnd().getTime() - se.getBegin().getTime()));
		return true;
	}

	public ArrayList<ScheduleEvent> getCurrentEventlist()
	{
		return currentEventlist;
	}

	public void setCurrentEventlist(ArrayList<ScheduleEvent> currentEventlist)
	{
		this.currentEventlist = currentEventlist;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
}

class ScheduleEvent implements Comparable<ScheduleEvent>
{
	private Schedule source;
	private Date begin, end;
	private Task associatedTask;
	public ScheduleEvent(Date begin, Date end, Task associatedTask, Schedule source)
	{
		this.begin = begin;
		this.end = end;
		this.associatedTask = associatedTask;
		this.source = source;
	}

	public Date getBegin()
	{
		return begin;
	}

	public void setBegin(Date begin)
	{
		this.begin = begin;
	}

	public Date getEnd()
	{
		return end;
	}

	public void setEnd(Date end)
	{
		this.end = end;
	}

	public Task getAssociatedTask()
	{
		return associatedTask;
	}

	public boolean overlaps(ScheduleEvent other)
	{
		long begin = getBegin().getTime();
		long end = getEnd().getTime();
		long obegin = other.getBegin().getTime();
		long oend = other.getEnd().getTime();
		return obegin >= begin && obegin <= end || oend >= begin && oend <= end;
	}

	public void setAssociatedTask(Task associatedTask)
	{
		this.associatedTask = associatedTask;
	}

	public Schedule getSource()
	{
		return source;
	}

	public void setSource(Schedule source)
	{
		this.source = source;
	}

	@Override
	public int compareTo(ScheduleEvent o)
	{
		return getBegin().compareTo(o.getBegin());
	}
}