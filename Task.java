import java.util.Date;

public class Task
{
    private Date complete;
    private double percentDone;
    private long time;
    private byte priority;
    private boolean active;
    private String name;

    public Task(Date end, long estimate, byte pri, boolean active, String name)
    {
        this.complete = end;
        this.time = estimate;
        this.priority = pri;
        this.active = active;
        this.name = name;
    }
    public Task(Date end, long estimate, byte pri, boolean active, String name, int percentDone)
    {
        this.complete = end;
        this.time = estimate;
        this.priority = pri;
        this.active = active;
        this.name = name;
        this.percentDone = percentDone;
    }

    public boolean getActive() { return this.active; }

    public void setActive(boolean state) { this.active = state; }

    public byte getPriority() { return this.priority; }

    public void setPriority(int pri) { this.priority = (byte) pri; }

    public void setPercentDone(double done) { this.percentDone += done; }

    public void update(TaskUpdate updater) { updater.updateTask(this); }

    public void getName{return this.name};
    public void getDueDate{return this.complete};
    public void getPercentDone{return this.percentDone};
    public void getEstimatedTime{return this.time};
    public String toString() {
        String output = "";
        output+="Due Date: " + complete + "\n";
        output+="Estimated Time Needed: " + time + "\n";
        output+="Priority (1-100): " + priority + "\n";
        output+="Active: " + active + "\n";
        output+="Name: " + name + "\n";
        output+="Percent Done: " + percentDone + "\n";
    }
}