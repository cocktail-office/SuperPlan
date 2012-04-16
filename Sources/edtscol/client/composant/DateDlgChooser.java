package edtscol.client.composant;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSTimestamp;

import fr.univlr.utilities.CalendarPanel;

public class DateDlgChooser extends JDialog implements ChangeListener {

	private CalendarPanel calendarPanel;
	private JTextField calendarpanelTextField;
	private String strDate;
	private Object owner;
	private String callback;
	private Object sender;
	private NSTimestamp initDate;

	public DateDlgChooser(Object owner, JFrame frame, Object sender, String callback, NSTimestamp initDate) {
		super(frame, true);
		strDate = "";
		this.owner = owner;
		this.callback = callback;
		this.sender = sender;
		this.initDate = initDate;
		init((Component) sender);
	}

	public DateDlgChooser(Object owner, JDialog dlg, Object sender, String callback, NSTimestamp initDate) {
		super(dlg, true);
		strDate = "";
		this.owner = owner;
		this.callback = callback;
		this.sender = sender;
		this.initDate = initDate;
		init((Component) sender);
	}

	public DateDlgChooser(Object owner, JFrame frame, Object sender, String callback) {
		super(frame, true);
		strDate = "";
		this.owner = owner;
		this.callback = callback;
		this.sender = sender;
		init((Component) sender);
	}

	public DateDlgChooser(Object owner, JDialog dlg, Object sender, String callback) {
		super(dlg, true);
		strDate = "";
		this.owner = owner;
		this.callback = callback;
		this.sender = sender;
		init((Component) sender);
	}

	private void init(Component sender) {
		setTitle("Date");
		createGUI();
		setResizable(false);
		pack();
		setLocationRelativeTo(sender);
		setVisible(true);
	}

	private void createGUI() {
		Container container = getContentPane();
		container.setLayout(new BorderLayout(5, 5));
		calendarPanel = new CalendarPanel();
		calendarPanel.addChangeListener(this);
		container.add(calendarPanel, "Center");
		if (initDate != null) {
			setSelectedDate(initDate);
		}
	}

	public void setSelectedDate(NSTimestamp date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.setTime(cal.getTime());
		calendarPanel.setCalendar(cal);
	}

	public void stateChanged(ChangeEvent e) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		strDate = df.format(calendarPanel.getCalendar().getTime());
		try {
			if (!e.getSource().getClass().getName().equals("javax.swing.JComboBox")) {
				NSSelector.invoke(callback, new Class[] { java.lang.String.class }, owner, new Object[] { strDate });
			}
		}
		catch (Exception exe) {
			NSLog.out.appendln("DateDlgChooser : " + exe.getMessage());
			exe.printStackTrace();
		}
		if (!e.getSource().getClass().getName().equals("javax.swing.JComboBox")) {
			hide();
		}
	}
}
