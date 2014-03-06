package de.thoffbauer.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.AbstractListModel;

import de.thoffbauer.utils.Polynom;
import de.thoffbauer.utils.PolynomFactor;

@SuppressWarnings("serial")
public class ScalesCalculater extends AbstractListModel<Possibility> {
	
	private Polynom[] polynoms;
	private Polynom result;
	private Possibility[] possibilities;
	
	public ScalesCalculater(Polynom[] polynoms) {
		this.polynoms = polynoms;
		this.possibilities = new Possibility[0];
	}
	
	public void calculate() {
		result = polynoms[0];
		for(int i = 1; i < polynoms.length; i++) {
			Polynom temp = Polynom.mul(result, polynoms[i], null);
			result = temp;
		}
		result.simplify();
		ArrayList<Possibility> possibilities = new ArrayList<Possibility>();
		for(Iterator<PolynomFactor> it = result.getIterator(); it.hasNext();) {
			PolynomFactor factor = it.next();
			if(factor.getExponent() >= 0) {
				possibilities.add(new Possibility(factor.getExponent(), factor.getCoefficent()));
			}
		}
		Collections.sort(possibilities);
		this.possibilities = possibilities.toArray(new Possibility[possibilities.size()]);
	}
	
	public Polynom getPolynom() {
		return result;
	}
	
	public Possibility[] getPossibilities() {
		return possibilities;
	}

	@Override
	public int getSize() {
		return possibilities.length;
	}

	@Override
	public Possibility getElementAt(int index) {
		return possibilities[index];
	}

}
