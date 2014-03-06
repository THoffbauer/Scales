package de.thoffbauer.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Polynom {

	private ArrayList<PolynomFactor> factors;
	
	public Polynom() {
		factors = new ArrayList<PolynomFactor>();
	}
	
	public void addFactor(PolynomFactor factor) {
		factors.add(factor);
	}
	
	public void removeFactor(PolynomFactor factor) {
		factors.remove(factor);
	}
	
	public Iterator<PolynomFactor> getIterator() {
		return factors.listIterator();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Iterator<PolynomFactor> it = getIterator(); it.hasNext();) {
			sb.append(it.next());
			if(it.hasNext()) {
				sb.append("*");
			}
		}
		return sb.toString();
	}
	
	public static Polynom mul(Polynom poly1, Polynom poly2, Polynom result) {
		if(result == null) {
			result = new Polynom();
		}
		for(Iterator<PolynomFactor> it1 = poly1.getIterator(); it1.hasNext();) {
			PolynomFactor factor1 = it1.next();
			for(Iterator<PolynomFactor> it2 = poly2.getIterator(); it2.hasNext();) {
				PolynomFactor factor2 = it2.next();
				PolynomFactor mulResult = PolynomFactor.mul(factor1, factor2, null);
				result.addFactor(mulResult);
			}
		}
		return result;
	}

	public void simplify() {
		ArrayList<PolynomFactor> newFactors = new ArrayList<PolynomFactor>();
		for(PolynomFactor factor : factors) {
			boolean match = false;
			for(PolynomFactor newFactor : newFactors) {
				if(factor.getExponent() == newFactor.getExponent()) {
					newFactor.setCoefficent(newFactor.getCoefficent() + factor.getCoefficent());
					match = true;
				}
			}
			if(!match) {
				newFactors.add(factor);
			}
		}
		Collections.sort(newFactors);
		factors = newFactors;
	}
	
}
