import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.util.*;

public class Main
{
	Firestore db;
	public Main()
	{
		setup();
	}

	public void setup()
	{
		// Use the application default credentials
		GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
		FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials)
				.setProjectId("ActuallyAnAgenda") //not sure if the input here is string
				.build();
		FirebaseApp.initializeApp(options);
		db = FirestoreClient.getFirestore();
	}

	public static ArrayList<Task> queryAllTasks(String userID)
	{
		//output ArrayList
		ArrayList<Task> taskList = new ArrayList<>();
		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection(userID).get();
		// ...
		// query.get() blocks on response
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> allTasks = querySnapshot.getDocuments(); //documents
		for(QueryDocumentSnapshot curTask: allTasks)
		{
			Date dueDate = new java.util.Date((long) curTask.getDouble("due") * 1000);
			double percentage = curTask.getDouble("per") / 100;
			long estimatedTimeRequiredInSeconds = (long) curTask.getDouble("timeSecs");
			byte priority = (byte) curTask.getDouble("timeSecs");
			boolean active = curTask.getDouble("active");
			String name = curTask.getString("name");
			taskList.add(new Task(dueDate, estimatedTimeRequiredInSeconds, pri, active, name, percentage));
		}
		return taskList;
	}

	public static ArrayList<Task> getActiveTasks(String userID)
	{
		ArrayList<Task> taskList = new ArrayList<>();

		ApiFuture<QuerySnapshot> query = db.collection(userID).get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> allTasks = querySnapshot.getDocuments(); //documents

		for(QueryDocumentSnapshot curTask: allTasks)
		{
			Date dueDate = new java.util.Date((long) curTask.getDouble("due") * 1000);
			double percentage = curTask.getDouble("per") / 100;
			long estimatedTimeRequiredInSeconds = (long) curTask.getDouble("timeSecs");
			byte priority = (byte) curTask.getDouble("timeSecs");
			boolean active = curTask.getDouble("active");
			String name = curTask.getString("name");
			if(active)
				taskList.add(new Task(dueDate, estimatedTimeRequiredInSeconds, pri, active, name, percentage));
		}
		return taskList;
	}

	public static void update(ArrayList<Tasks> taskList, String userID)
	{
		for(Task curTask: allTasks)
		{
			// Create a Map to store the data we want to set
			Map<String, Object> taskData = new HashMap<>();
			taskData.put("due", curTask.getDueDate().getTime());
			taskData.put("per", curTask.getPercentDone());
			taskData.put("timeSecs", curTask.getEstimatedTime());
			taskData.put("priority", curTask.getPriority());
			taskData.put("active", curTask.getActive());
			taskData.put("name", curTask.getName());
			ApiFuture<WriteResult> future = db.collection(userID).document(curTask.getName()).set(taskData);
			System.out.println("Update time for " + curTask.getName() + ": " + future.get().getUpdateTime());
		}
	}

	public static void main(String[] args)
	{
		ArrayList<Task> test = queryAllTasks("FMWjYg2vAaVPSGnJgyIENGFbcUj2");
		for(Task t: test)
		{
			System.out.println(t);
		}
	}
}