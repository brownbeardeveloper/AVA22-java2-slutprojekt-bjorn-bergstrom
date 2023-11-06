package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import Controller.RegulatorController;

public class SwingGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RegulatorController controller;
	private static SwingGUI instance;
	private JPanel contentPane;
    public JButton addWorkerBtn;
    public JButton deleteWorkerBtn;
    public JButton saveBtn;
    public JButton createGetBtn;
    public JLabel producersLabel;
    public JProgressBar progressBar;
    public JTextArea textArea; 
    
    public static SwingGUI getInstance() 
    {
    	if(instance == null)
    	{
    		instance = new SwingGUI();
    	}
    	return instance;
    }

	/**
	 * Launch the application.
	 */
	public void makeJFrameVisible() {
		
		EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}

	/**
	 * Create the frame.
	 */
	public SwingGUI() {
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 385);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(createAddWorkerBtn());
		contentPane.add(createDeleteWorkerBtn());
		contentPane.add(createSaveBtn());
		contentPane.add(createGetBtn());
		contentPane.add(createProducersLabel());
		contentPane.add(createJProgressBar());
		contentPane.add(createScrollPane());
		controller = RegulatorController.getInstance();
		setContentPane(contentPane);
	}
			
	private JButton createAddWorkerBtn() {
		addWorkerBtn = new JButton("Add Worker");
		addWorkerBtn.setActionCommand("ADD_WORKER");
		addWorkerBtn.addActionListener(this);
		return addWorkerBtn;
	}
	
	private JButton createDeleteWorkerBtn() {
		deleteWorkerBtn = new JButton("Delete Worker");
		deleteWorkerBtn.setActionCommand("DELETE_WORKER");
		deleteWorkerBtn.addActionListener(this);
		return deleteWorkerBtn;
	}
	
	private JButton createSaveBtn() {
		saveBtn = new JButton("Save");
		saveBtn.setActionCommand("SAVE_COLLECTION");
		saveBtn.addActionListener(this);
		return saveBtn;
	}

	private JButton createGetBtn() {
		createGetBtn = new JButton("Get");
		createGetBtn.setActionCommand("GET_COLLECTION");
		createGetBtn.addActionListener(this);
		return createGetBtn;
	}
	
	private JLabel createProducersLabel() {
		producersLabel = new JLabel("producers: 0");
		return producersLabel;
	}
	
	private JProgressBar createJProgressBar() {
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setPreferredSize(new Dimension(460,12));		
		return progressBar;
	}
	
	private void initializeTextArea() {
	    textArea = new JTextArea(20, 90);
	    textArea.setBackground(Color.black);
	    textArea.setForeground(Color.green);
	    textArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
	    textArea.setBorder(BorderFactory.createLineBorder(Color.black, 10));
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	public boolean resetTextArea() {
        textArea.setText("");
		return true;
	}
	
	private JScrollPane createScrollPane() {
		initializeTextArea();
	    JScrollPane scrollPane = new JScrollPane(textArea);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	    return scrollPane;
	}
	
	public void insertNewLineInTextArea(String data) {
		try {
			textArea.getDocument().insertString(0, data + "\n", null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void SetProgressBar(int value, Color color)
	{
		progressBar.setValue(value);
		progressBar.setForeground(color);
	}
	
	public void setProducersLabel(int value) {
		producersLabel.setText("producers: " + value);
	}
	
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String clickedButton = e.getActionCommand();
        
        switch (clickedButton) {
        case "ADD_WORKER" -> controller.addProducer();
        case "DELETE_WORKER" -> controller.deleteProducer();
        case "SAVE_COLLECTION" -> controller.saveThisConfigToFile();
        case "GET_COLLECTION" -> controller.getNewConfigfromFile();
        }
    }
}
