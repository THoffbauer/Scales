package de.thoffbauer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import de.thoffbauer.save.Saver;
import de.thoffbauer.utils.Weight;

// TODO: New -> reset viewPanel

public class GUIMain extends JFrame implements ActionListener, ListSelectionListener, ItemListener, DocumentListener {

	/**
	 * serialVersion UID, generated by eclipse
	 */
	private static final long serialVersionUID = -4334331084275710349L;
	private Session session;
	private WeightScrollPane scrollPane;
	private WeightViewPanel viewPanel;
	private JFileChooser chooser;
	
	public GUIMain() {
		super("Scales");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		setLocation(100, 100);
		session = new Session();
		createChooser();
		addComponents();
	}
	
	private void addComponents() {
		setLayout(new BorderLayout());
		JMenuBar menu = createMenu();
		add(menu, BorderLayout.NORTH);
		scrollPane = new WeightScrollPane(session);
		scrollPane.setPreferredSize(new Dimension(100, getHeight()));
		scrollPane.addSelectionListener(this);
		add(scrollPane, BorderLayout.WEST);
		viewPanel = new WeightViewPanel(this, this);
		add(viewPanel, BorderLayout.CENTER);
	}
	private void createChooser() {
		chooser = new JFileChooser(new File("."));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.addChoosableFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(".sca") || f.isDirectory();
			}

			@Override
			public String getDescription() {
				return "Scales Files (.sca)";
			}
		});
	}
	private JMenuBar createMenu() {
		JMenuBar bar = new JMenuBar();
		
		JMenu menuFile = new JMenu("File");
		JMenuItem iReset = new JMenuItem("New");
		iReset.addActionListener(this);
		menuFile.add(iReset);
		JMenuItem iLoad = new JMenuItem("Load");
		iLoad.addActionListener(this);
		menuFile.add(iLoad);
		JMenuItem iSave = new JMenuItem("Save");
		iSave.addActionListener(this);
		menuFile.add(iSave);
		JMenuItem iCompute = new JMenuItem("Compute");
		iCompute.addActionListener(this);
		menuFile.add(iCompute);
		bar.add(menuFile);
		
		JMenu menuWeight = new JMenu("Weight");
		JMenuItem iAddWeight = new JMenuItem("Add Weight");
		iAddWeight.addActionListener(this);
		menuWeight.add(iAddWeight);
		JMenuItem iRemoveWeight = new JMenuItem("Remove Weight");
		iRemoveWeight.addActionListener(this);
		menuWeight.add(iRemoveWeight);
		bar.add(menuWeight);
		
		return bar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("New")) {
			onNewClicked();
		} else if(cmd.equals("Save")) {
			onSaveClicked();
		} else if(cmd.equals("Load")) {
			onLoadClicked();
		} else if(cmd.equals("Add Weight")) {
			onAddWeightClicked();
		} else if(cmd.equals("Remove Weight")) {
			onRemoveWeightClicked();
		} else if(cmd.equals("Compute")) {
			onComputeClicked();
		}
	}
	private void onNewClicked() {
		if(JOptionPane.showConfirmDialog(this, "Do you really want to start a new session?", "New Session", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.CANCEL_OPTION) {
			return;
		}
		session.newSession();
		scrollPane.repaint();
	}
	private void onSaveClicked() {
		if(session.getSize() == 0) {
			JOptionPane.showMessageDialog(this, "Can`t save session if no weights are specified!", "Error!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int returnVal = chooser.showSaveDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File save = chooser.getSelectedFile();
			try {
				Saver.save(session, save);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this, "Error while saving to file!", "Error!", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
	}
	private void onLoadClicked() {
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File load = chooser.getSelectedFile();
			if(!load.getName().endsWith(".sca")) {
				load = new File(load.getAbsolutePath() + ".sca");
			}
			try {
				String args = Saver.load(load);
				if(args == null) {
					JOptionPane.showMessageDialog(this, "Error while loading file!", "Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				session.load(args.split(" "));
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(this, "File not found!", "Error!", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
	}
	private void onAddWeightClicked() {
		String sWeight = JOptionPane.showInputDialog(this, "Type in the weight of the new element:", "New Weight", JOptionPane.QUESTION_MESSAGE);
		if(sWeight.isEmpty()) {
			return;
		}
		try {
			int weight = Integer.valueOf(sWeight);
			session.add(new Weight(weight));
		} catch(NumberFormatException exeption) {
			JOptionPane.showMessageDialog(this, "Please enter a number!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void onRemoveWeightClicked() {
		if(scrollPane.getSelected() == null) {
			JOptionPane.showMessageDialog(this, "Weight doesn`t exist!", "Error!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(JOptionPane.showConfirmDialog(this, "Do you really want remove this weight?", "Remove Weight", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.CANCEL_OPTION) {
			return;
		}
		session.remove(scrollPane.getSelected());
	}
	private void onComputeClicked() {
		if(session.getSize() == 0) {
			JOptionPane.showMessageDialog(this, "Can`t calculate results if no weights are specified!", "Error!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ScalesResultWindow srw = new ScalesResultWindow(this, session.getPolynoms());
		srw.setVisible(true);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		viewPanel.updateWeight(scrollPane.getSelected());
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Weight w = scrollPane.getSelected();
		boolean[] allowed = w.getAllowed();
		if(e.getItem() == viewPanel.checkLeft) {
			allowed[0] = viewPanel.checkLeft.isSelected();
		}
		if(e.getItem() == viewPanel.checkMiddle) {
			allowed[1] = viewPanel.checkMiddle.isSelected();
		}
		if(e.getItem() == viewPanel.checkRight) {
			allowed[2] = viewPanel.checkRight.isSelected();
		}
		w.setAllowed(allowed);
		viewPanel.updateStatus(true);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		onDocumentUpdate(e);
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		onDocumentUpdate(e);
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
		onDocumentUpdate(e);
	}
	public void onDocumentUpdate(DocumentEvent e) {
		try {
			int value = Integer.valueOf(((JTextField) e.getDocument().getProperty("owner")).getText());
			scrollPane.getSelected().setWeight(value);
			session.updateOrder();
		} catch(NumberFormatException exc) {
			
		}
	}

}