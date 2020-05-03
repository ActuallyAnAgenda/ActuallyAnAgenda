import java.util.Date;

public abstract class TaskUpdate
{
	public final Task baseTask;
	public final Date dateInitialized;
	public TaskUpdate(Task baseTask, Date dateInitialized)
	{
		this.baseTask = baseTask;
		this.dateInitialized = dateInitialized;
	}
	public TaskUpdate(Task baseTask)
	{
		this(baseTask, new Date(System.currentTimeMillis()));
	}
	abstract String getType();
	abstract void updateTask(Task task);
}

class PercentageUpdate extends TaskUpdate
{
	private double percentage;
	public PercentageUpdate(Task baseTask, double percentage)
	{
		super(baseTask);
	}

	@Override
	public String getType()
	{
		return "PercentageUpdate";
	}

	@Override
	public void updateTask(Task task)
	{
		task.setPercentDone(percentage);
	}

	public double getPercentage()
	{
		return percentage;
	}

	public void setPercentage(double percentage)
	{
		this.percentage = percentage;
	}
}