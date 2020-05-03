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

			long day = 0;
			allocateEvent(event, eachWorkSessionTime, day);
		}
	}

	public boolean allocateEvent(ScheduleEvent event, double timeNeeded, long epochDay)
	{
		TimeFrameProductivity productivity = user.getTimeFrameProductivity();
		ArrayList<ScheduleEvent> active = getActiveEvents();
		Collections.sort(active);
		int idx = 0;
		long dayMillisStart = epochDay * 24 * 60 * 60 * 1000;
		long dayMillisEnd = (epochDay + 1)  * 24 * 60 * 60 * 1000;

		long minCost = Integer.MAX_VALUE;
		Date bestStart = null, bestEnd = null;
		for(long st = dayMillisStart; st <= dayMillisEnd && st <= event.getAssociatedTask().getComplete().getTime(); st += 60000) // Check each minute of the day brute force
		{
			for(long en = dayMillisStart; en <= dayMillisEnd && en <= event.getAssociatedTask().getComplete().getTime(); en += 60000) // Check each minute of the day brute force
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
				if(cannotAllocate)
					continue;
				// TODO find cost
			}
		}
		if(minCost == Integer.MAX_VALUE) return false;

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
		for(ScheduleEvent se: conflict) allocateEvent(se, TimeFrameProductivity.findHour(se.getEnd().getTime() - se.getBegin().getTime()), se.getBegin().getTime() / (3600000 * 24));
		return true;
	}

	public void updateEventCompletion(ScheduleEvent event, double completionPercentage)
	{

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