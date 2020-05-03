import java.util.ArrayList;

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
	}

	public void updateEventCompletion(ScheduleEvent event, double completionPercentage)
	{
		timeFrameProductivity.updateEventCompletion(event, completionPercentage);
		extendedWorkDuration.updateEventCompletion(event, completionPercentage);
		procrastination.updateEventCompletion(event, completionPercentage);
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
