import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Damanjit on 09/03/2017.
 */
public class IDGenerator {

		private Set<Integer> meetingID = new TreeSet<>();
		private Set<Integer> contactID = new TreeSet<>();

		public int getMeetingID(){
				int id = (int) (Math.random() * 1000000000);
				if (meetingID.contains(id)){
						getMeetingID();
				} else{
						meetingID.add(id);
				}
				return id;
		}

		public int getContactID(){
				int id = (int) (Math.random() * 1000000000);
				if (contactID.contains(id)){
						getContactID();
				} else{
						contactID.add(id);
				}
				return id;
		}
}
