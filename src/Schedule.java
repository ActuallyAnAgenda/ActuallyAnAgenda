import java.util.ArrayList;
import java.util.Date;

public class Schedule
{
	private ArrayList<ScheduleEvent> currentEventlist;
	public Schedule()
	{
		currentEventlist = new ArrayList<>();
	}
}

class ScheduleEvent
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
}