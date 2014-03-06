package de.thoffbauer.utils;

public class PolynomFactor implements Comparable<PolynomFactor> {
	
	private int exp;
	private int coeff;
	
	public PolynomFactor(int exp, int coeff) {
		this.exp = exp;
		this.coeff = coeff;
	}
	
	public static PolynomFactor mul(PolynomFactor factor1, PolynomFactor factor2, PolynomFactor result) {
		if(result == null) {
			result = new PolynomFactor(0, 0);
		}
		result.coeff = factor1.coeff * factor2.coeff;
		result.exp = factor1.exp + factor2.exp;
		return result;
	}
	
	public String toString() {
		return (coeff == 1 ? "" : coeff) + "x^{" + exp + "}";
	}

	public int getExponent() {
		return exp;
	}

	public void setExponent(int exp) {
		this.exp = exp;
	}

	public int getCoefficent() {
		return coeff;
	}

	public void setCoefficent(int coeff) {
		this.coeff = coeff;
	}

	@Override
	public int compareTo(PolynomFactor o) {
		if(exp > o.exp) {
			return 1;
		} else if(exp < o.exp) {
			return -1;
		}
		return 0;
	}

}
