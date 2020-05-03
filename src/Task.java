import java.util.Date;

public class Task
{
	private Date complete;
	private double percentDone;
	private long time;
	private byte priority;
	private boolean active;

	public Task(Date end, long estimate, byte pri, boolean active)
	{
		this.complete = end;
		this.time = estimate;
		this.priority = pri;
		this.active = active;
	}

	public boolean getActive() { return this.active; }

	public void setActive(boolean state) { this.active = state; }

	public byte getPriority() { return this.priority; }

	public void setPriority(int pri) { this.priority = (byte) pri; }

	public void setPercentDone(double done) { this.percentDone += done; }

	public void update(TaskUpdate updater) { updater.updateTask(this); }
}
