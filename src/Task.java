import java.util.Date;

public class Task
{
	private String name;
	private Date complete;
	private double percentDone;
	private double time; // Hours Left
	private byte priority;

	public Task(Date end, double estimate, byte pri, String name)
	{
		this.complete = end;
		this.time = estimate;
		this.priority = pri;
		this.name = name;
	}

	public byte getPriority() { return this.priority; }

	public void setPriority(int pri) { this.priority = (byte) pri; }

	public void setPercentDone(double done) { this.percentDone += done; }

	public double getTime()
	{
		return time;
	}

	public double getTimeLeft()
	{
		return (time * (1 - percentDone));
	}

	public void setTime(long time)
	{
		this.time = time;
	}

	public double getPercentDone()
	{
		return percentDone;
	}

	public Date getComplete()
	{
		return complete;
	}

	public void setComplete(Date complete)
	{
		this.complete = complete;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
