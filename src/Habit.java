public interface Habit
{
	void updateEventCompletion(ScheduleEvent event, double completionPercentage);
}

class TimeFrameProductivity implements Habit
{
	// Hour in UTC: (System.currentTimeMillis() / 3600000.0) % (24)
	private double timeFrameBegin, timeFrameEnd;
	public TimeFrameProductivity()
	{
		timeFrameBegin = 9.0; // 9 AM
		timeFrameEnd = 17.0; // 5 PM;
	}
	@Override
	public void updateEventCompletion(ScheduleEvent event, double completionPercentage)
	{
		// To Implement
	}

	public double getTimeFrameBegin()
	{
		return timeFrameBegin;
	}

	public void setTimeFrameBegin(double timeFrameBegin)
	{
		this.timeFrameBegin = timeFrameBegin;
	}

	public double getTimeFrameEnd()
	{
		return timeFrameEnd;
	}

	public void setTimeFrameEnd(double timeFrameEnd)
	{
		this.timeFrameEnd = timeFrameEnd;
	}
}
class ExtendedWorkDuration implements Habit
{
	// Should be approximations.
	private double maxHoursWork, minHoursBreak;
	public ExtendedWorkDuration()
	{
		// Default Values
		maxHoursWork = 1;
		minHoursBreak = 0.5;
	}
	@Override
	public void updateEventCompletion(ScheduleEvent event, double completionPercentage)
	{
		// To Implement
	}

	public double getMaxHoursWork()
	{
		return maxHoursWork;
	}

	public void setMaxHoursWork(double maxHoursWork)
	{
		this.maxHoursWork = maxHoursWork;
	}

	public double getMinHoursBreak()
	{
		return minHoursBreak;
	}

	public void setMinHoursBreak(double minHoursBreak)
	{
		this.minHoursBreak = minHoursBreak;
	}
}
class Procrastination implements Habit
{
	private int procrastinationModel;
	public Procrastination()
	{
		procrastinationModel = 1;
		// huh
	}
	@Override
	public void updateEventCompletion(ScheduleEvent event, double completionPercentage)
	{

	}

	public int getProcrastinationModel()
	{
		return procrastinationModel;
	}

	public void setProcrastinationModel(int procrastinationModel)
	{
		this.procrastinationModel = procrastinationModel;
	}
}
