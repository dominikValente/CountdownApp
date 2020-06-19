import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Form extends JFrame {

	public DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a");
	private JPanel formPanel;
	public JTextField taskField;
	public boolean validDate = false;
	public boolean validText = false;
	Countdown c;

	public Form(Countdown c) {
		this.c = c;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 252, 226);
		formPanel = new JPanel();
		setContentPane(formPanel);
		formPanel.setLayout(null);

		// get instance of date time
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// date minimum and maximum
		cal.add(Calendar.YEAR, -1);
		Date earliestDate = cal.getTime();
		cal.add(Calendar.YEAR, 2);
		Date latestDate = cal.getTime();

		// spinner date and time
		Date today = new Date();
		JSpinner dateSpinner = new JSpinner(new SpinnerDateModel(today, earliestDate, latestDate, Calendar.YEAR));
		JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "M/d/yyyy h:mm a");
		dateSpinner.setEditor(editor);
		dateSpinner.setBounds(10, 88, 137, 25);
		formPanel.add(dateSpinner);

		// task label display
		JLabel taskLabel = new JLabel("Event");
		taskLabel.setBounds(10, 11, 223, 19);
		formPanel.add(taskLabel);

		// date label display
		JLabel dateLabel = new JLabel("Date & Time");
		dateLabel.setBounds(10, 65, 153, 25);
		formPanel.add(dateLabel);

		// task field component
		taskField = new JTextField();
		taskField.setBounds(10, 29, 183, 25);
		formPanel.add(taskField);
		taskField.setColumns(10);

		// date and time error display
		JLabel dateTimeError = new JLabel("");
		dateTimeError.setBounds(10, 151, 137, 25);
		formPanel.add(dateTimeError);

		// set character limiter in task field
		taskField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (taskField.getText().length() >= 50) // limit text field to 50 characters
					e.consume();
			}
		});

		dateSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				String userDate = sdf.format(dateSpinner.getValue());

				try {
					if (new SimpleDateFormat("M/dd/yyyy HH:mm").parse(userDate).before(new Date())) {
						dateTimeError.setText("INVALID TIME");
					} else {
						dateTimeError.setText("");
						validDate = true;
					}
				} catch (ParseException f) {
					f.printStackTrace();
				}
			}

		});

		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(158, 151, 68, 25);
		formPanel.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (taskField.getText().equals("")) {
					dateTimeError.setText("NO TASK ENTERED");
				} else {
					validText = true;
				}

				if (validDate && validText) {

					String event = taskField.getText();
					String date = sdf.format(dateSpinner.getValue());
					try {
						c.SendEvent(event, date);
					} catch (ParseException e1) {
					}
					taskField.setText("");
					validText = false;
					validDate = false;
					dispose();

				}
			}
		});
	}
}
