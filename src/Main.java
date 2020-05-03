//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.Firestore;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
import java.util.*;

public class Main
{

	public static void main(String[] args)
	{

		User user = new User();

		// Whenever there is a new task:
		user.getSchedule().addTask(
				new Task(
						new Date(System.currentTimeMillis() + 60 * 1000 * 60 * 24 * 1), // should be the unix time
						2.0,
						(byte) 5,
						"ISP"
				)
		);
		for(ScheduleEvent se: user.getSchedule().getCurrentEventlist())
		{
			System.out.println(se.getBegin());
			System.out.println(se.getEnd());
		}
	}
}