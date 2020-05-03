public interface Habit
{
	void updateHabits(Schedule thisSchedule, PercentageUpdate baseUpdate);
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
	public void updateHabits(Schedule thisSchedule, PercentageUpdate baseUpdate)
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
	public void updateHabits(Schedule thisSchedule, PercentageUpdate baseUpdate)
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
	public void updateHabits(Schedule thisSchedule, PercentageUpdate baseUpdate)
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
class EstimationAccuracy implements Habit
{
	private double estimateRatio;
	public EstimationAccuracy()
	{
		estimateRatio = 1; // Default assumes that the estimations are accurate and can accurately represent how much a user needs to actually spend
	}
	@Override
	public void updateHabits(Schedule thisSchedule, PercentageUpdate baseUpdate)
	{

	}

	public double getEstimateRatio()
	{
		return estimateRatio;
	}

	public void setEstimateRatio(double estimateRatio)
	{
		this.estimateRatio = estimateRatio;
	}
}