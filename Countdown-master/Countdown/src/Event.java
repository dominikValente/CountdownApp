import javax.swing.JLabel;
import javax.swing.JPanel;

public class Event extends JPanel {

	String event;
	String date;
	long time;
	JLabel textLabel;

	public Event(String event, String date, long time) {
		this.event = event;
		this.date = date;
		this.time = time;

		textLabel = new JLabel();
		add(textLabel);
	}
}