import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class Countdown extends JFrame {

	public static JFrame frame;
	public static JPanel panel;
	public static JScrollPane scrollPane;
	
	public static JButton btnAdd;
	public static JButton btnRemove;
	
	Form form = new Form(this);
	
	public static DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a");
	public static ArrayList<Event> list = new ArrayList<Event>();
	public static ArrayList<Timer> threadList = new ArrayList<Timer>();
	public static int index = 0;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			
			GUI();
			
			for(int i = 0; i < list.size(); i++) {
				StartTimer();
			}
		});
	}

	public static void GUI() {
		frame = new Countdown();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		((Countdown) frame).UI();
		frame.setSize(500, 300);
		frame.setTitle("Countdown");
		frame.setVisible(true);
	}

	public void UI() {
		panel = new JPanel();
		scrollPane = new JScrollPane(panel);
		btnAdd = new JButton("Add Event");
		btnRemove = new JButton("Clear");

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				form.setVisible(true);
			}
		});
		
		btnRemove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				for(Timer o : threadList) {
					o.interrupt();
				}
				//Reset
				list.clear();
				threadList.clear();
				index = 0;
				panel.removeAll();
				panel.revalidate();
				repaint();
				
			}
		});
		
		getContentPane().add(scrollPane);
		getContentPane().add(btnAdd, BorderLayout.PAGE_START);
		getContentPane().add(btnRemove, BorderLayout.PAGE_END);
	}

	public void SendEvent(String event, String date) throws ParseException {

		// Subtract user date and current date
		long time = Difference(date);
		
		list.add(new Event(event, date, time));
		StartTimer();
	}
	public static void StartTimer() {
		Timer timer = new Timer(list.get(index));
		threadList.add(timer);
		panel.add(list.get(index));
		threadList.get(index).start();
		panel.revalidate();
		index++;

	}

	public long Difference(String date) throws ParseException {
		
		/**
		 * Computes the difference between current time and event time
		 *
		 * @param event date as a string
		 * @return difference in milliseconds
		 */
		
		Date userDate = sdf.parse(date);
		
		long userMillis = userDate.getTime();
		
		Date current_time = new Date();
		String current_time_string = sdf.format(current_time);
		current_time = sdf.parse(current_time_string);
		
		long currentMillis = current_time.getTime();
		long millis = userMillis - currentMillis;
		return millis;
	}

}
