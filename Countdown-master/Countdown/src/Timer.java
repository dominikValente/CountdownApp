import java.awt.Color;
import java.awt.Frame;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

public class Timer extends Thread {

	Event obj;
	
	public Timer(Event event) {
		this.obj = event;
	}

	public void run() {
		while(obj.time > 0) {
			try {
				
			long seconds = Math.abs(TimeUnit.MILLISECONDS.toSeconds(obj.time) % 60);
			long minutes = Math.abs(TimeUnit.MILLISECONDS.toMinutes(obj.time) % 60);
			long hours = Math.abs(TimeUnit.MILLISECONDS.toHours(obj.time) % 24);
			long days = Math.abs(TimeUnit.MILLISECONDS.toDays(obj.time));
			
			obj.textLabel.setText(obj.event + " --- " + obj.date + " --- " + 
			String.format("%02d d %02d h %02d m %02d s", days, hours, minutes, seconds));
						
			obj.time-=1000;
			Thread.sleep(1000);
			
			if(obj.time <= 0) {
				Frame f = new Frame();
				obj.setBackground(Color.RED);
				JOptionPane.showMessageDialog(f, obj.event + " has ended"); 
				
			}
			
		}catch(Exception e) {
			
		}

	}
}
}