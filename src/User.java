public class User
{
	private TimeFrameProductivity timeFrameProductivity;
	private ExtendedWorkDuration extendedWorkDuration;
	private Procrastination procrastination;
	private EstimationAccuracy estimationAccuracy;
	public User()
	{
		timeFrameProductivity = new TimeFrameProductivity();
		extendedWorkDuration = new ExtendedWorkDuration();
		procrastination = new Procrastination();
		estimationAccuracy = new EstimationAccuracy();
	}

	public void updateAllHabits(Schedule thisSchedule, TaskUpdate baseUpdate)
	{
		if(!baseUpdate.getType().equals("PercentageUpdate")) return;
		PercentageUpdate update = (PercentageUpdate) baseUpdate;
		timeFrameProductivity.updateHabits(thisSchedule, update);
		extendedWorkDuration.updateHabits(thisSchedule, update);
		procrastination.updateHabits(thisSchedule, update);
		estimationAccuracy.updateHabits(thisSchedule, update);
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

	public EstimationAccuracy getEstimationAccuracy()
	{
		return estimationAccuracy;
	}

	public void setEstimationAccuracy(EstimationAccuracy estimationAccuracy)
	{
		this.estimationAccuracy = estimationAccuracy;
	}
}
