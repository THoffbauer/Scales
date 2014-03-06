package de.thoffbauer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import de.thoffbauer.utils.Weight;

@SuppressWarnings("serial")
public class WeightViewPanel extends JPanel {

	private static final int FORMULA_SIZE = 25;
	protected JTextField weightInputField;
	protected JCheckBox checkLeft;
	protected JCheckBox checkMiddle;
	protected JCheckBox checkRight;
	protected FormulaLabel formula;
	private Weight weight;

	public WeightViewPanel(DocumentListener dl, ItemListener il) {
		addComponents(dl, il);	
	}

	private void addComponents(DocumentListener dl, ItemListener il) {
		setLayout(new BorderLayout());
		
		formula = new FormulaLabel();
		formula.setHorizontalAlignment(JLabel.CENTER);
		formula.updateFormula("\\text {Nothing selected!}", FORMULA_SIZE );
		add(formula, BorderLayout.NORTH);
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
		
		JPanel weightInputPanel = new JPanel() {
			public Dimension getMaximumSize() {
				return new Dimension(super.getMaximumSize().width, 20);
			}
		};
		weightInputPanel.setLayout(new BoxLayout(weightInputPanel, BoxLayout.LINE_AXIS));
		
		JLabel weightLabel = new JLabel("Weight:");
		weightInputPanel.add(weightLabel);
		weightInputPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		weightInputField = new JTextField(0);
		weightInputField.setColumns(10);
		weightInputField.getDocument().putProperty("owner", weightInputField);
		weightInputField.getDocument().addDocumentListener(dl);
		weightInputPanel.add(weightInputField);
		
		optionsPanel.add(weightInputPanel);
		optionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JPanel allowedInputPanel = new JPanel();
		allowedInputPanel.setLayout(new BoxLayout(allowedInputPanel, BoxLayout.LINE_AXIS));
		JLabel allowedLabel = new JLabel("Allowed:");
		allowedInputPanel.add(allowedLabel);
		allowedInputPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		checkLeft = new JCheckBox("left");
		checkLeft.addItemListener(il);
		allowedInputPanel.add(checkLeft);
		allowedInputPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		checkMiddle = new JCheckBox("middle");
		checkMiddle.addItemListener(il);
		allowedInputPanel.add(checkMiddle);
		allowedInputPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		checkRight = new JCheckBox("right");
		checkRight.addItemListener(il);
		allowedInputPanel.add(checkRight);
		
		optionsPanel.add(allowedInputPanel);
		
		add(Box.createRigidArea(new Dimension(0, 30)));
		add(optionsPanel, BorderLayout.CENTER);
	}

	public void updateWeight(Weight selected) {
		boolean updateText = selected != weight;
		this.weight = selected;
		updateStatus(updateText);
	}

	public void updateStatus(boolean updateText) {
		if(updateText) {
			weightInputField.setText(String.valueOf(weight.getWeight()));
		}
		boolean[] allowed = weight.getAllowed();
		checkLeft.setSelected(allowed[0]);
		checkMiddle.setSelected(allowed[1]);
		checkRight.setSelected(allowed[2]);
		weight.computePolynom();
		String polynom = weight.getPolynom().toString();
		formula.updateFormula(polynom.isEmpty() ? "1" : polynom, FORMULA_SIZE);
	}
	
}
