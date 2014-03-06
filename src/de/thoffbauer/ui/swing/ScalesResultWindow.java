package de.thoffbauer.ui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import de.thoffbauer.algorithm.Possibility;
import de.thoffbauer.algorithm.ScalesCalculater;
import de.thoffbauer.utils.Polynom;

@SuppressWarnings("serial")
public class ScalesResultWindow extends JDialog {
	
	private ScalesCalculater sc;
	
	private FormulaLabel formula;
	private int FORMULA_SIZE = 25;
	
	private JList<Possibility> list;

	public ScalesResultWindow(JFrame parent, Polynom[] polynoms) {
		super(parent, "Scales - Result");
		sc = new ScalesCalculater(polynoms);
		setSize(500, 400);
		setLocation(parent.getLocation().x + 100, parent.getLocation().y + 100);
		setLayout(new BorderLayout(5, 20));
		addComponents();
		calculateResults();
	}

	private void addComponents() {
		addFormula();
		addResultsList();
	}

	private void addFormula() {
		formula = new FormulaLabel() {
			public Dimension getMaximumSize() {
				Dimension max = super.getMaximumSize();
				max.height = 100;
				return max;
			}
			public Dimension getMinimumSize() {
				Dimension min = super.getMinimumSize();
				min.height = 100;
				return min;
			}
		};
		formula.updateFormula("\\text {Computing...}", FORMULA_SIZE);
		JScrollPane scroll = new JScrollPane(formula, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.NORTH);
	}

	private void addResultsList() {
		list = new JList<Possibility>(sc);
		JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll, BorderLayout.CENTER);
	}

	private void calculateResults() {
		sc.calculate();
		formula.updateFormula(sc.getPolynom().toString().replace('*', '+'), FORMULA_SIZE);
	}

}
