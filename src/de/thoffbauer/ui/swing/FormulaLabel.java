package de.thoffbauer.ui.swing;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

@SuppressWarnings("serial")
public class FormulaLabel extends JLabel {

	public void updateFormula(String sFormula, int size) {
		TeXFormula formula = new TeXFormula(sFormula);
		TeXIcon icon = formula.new TeXIconBuilder().setStyle(TeXConstants.STYLE_DISPLAY)
				.setSize(size)
				.build();
		this.setIcon(icon);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

}
