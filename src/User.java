import java.util.ArrayList;
import java.util.Date;

public class User
{
	private TimeFrameProductivity timeFrameProductivity;
	private ExtendedWorkDuration extendedWorkDuration;
	private Procrastination procrastination;
	private ArrayList<Task> taskList;
	private Schedule schedule;
	public User()
	{
		timeFrameProductivity = new TimeFrameProductivity();
		extendedWorkDuration = new ExtendedWorkDuration();
		procrastination = new Procrastination();
		taskList = new ArrayList<>();
		schedule = new Schedule(this);
	}
	public ArrayList<Task> getActiveTasks()
	{
		Date current = new Date(System.currentTimeMillis());
		ArrayList<Task> active = new ArrayList<>();
		for(Task e: taskList)
			if(e.getComplete().compareTo(current) > 0 && Math.abs(e.getPercentDone() - 100) < 0.00001)
				active.add(e);
		return active;
	}
	public void updateEventCompletion(ScheduleEvent event, double completionPercentage)
	{
		timeFrameProductivity.updateEventCompletion(event, completionPercentage);
		extendedWorkDuration.updateEventCompletion(event, completionPercentage);
		procrastination.updateEventCompletion(event, completionPercentage);
		schedule.updateEventCompletion(event, completionPercentage);
	}

	public TimeFrameProductivity getTimeFrameProductivity()
	{
		return timeFrameProductivity;
	}

	public void setTimeFrameProductivity(TimeFrameProductivity timeFrameProductivity)
	{
		this.timeFrameProductivity = timeFrameProductivity;
	}

	public ExtendedWorkDuration getExtendedWorkDuration()
	{
		return extendedWorkDuration;
	}

	public void setExtendedWorkDuration(ExtendedWorkDuration extendedWorkDuration)
	{
		this.extendedWorkDuration = extendedWorkDuration;
	}

	public Procrastination getProcrastination()
	{
		return procrastination;
	}

	public void setProcrastination(Procrastination procrastination)
	{
		this.procrastination = procrastination;
	}


	public ArrayList<Task> getTaskList()
	{
		return taskList;
	}

	public void setTaskList(ArrayList<Task> taskList)
	{
		this.taskList = taskList;
	}
}
