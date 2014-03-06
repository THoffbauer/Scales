package de.thoffbauer.utils;

public class Weight implements Comparable<Weight> {

	private int weight;
	private Polynom polynom;
	private boolean[] allowed;
	
	public Weight(int weight) {
		this.weight = weight;
		this.allowed = new boolean[]{true, true, true};
	}
	
	public Polynom computePolynom() {
		if(allowed.length != 3) {
			throw new IllegalArgumentException("Allowed-Array must have a length of 3!");
		}
		polynom = new Polynom();
		if(allowed[0]) {
			polynom.addFactor(new PolynomFactor(-weight, 1));
		}
		if(allowed[1]) {
			polynom.addFactor(new PolynomFactor(0, 1));
		}
		if(allowed[2]) {
			polynom.addFactor(new PolynomFactor(weight, 1));
		}
		return polynom;
	}
	
	public Polynom getPolynom() {
		return polynom;
	}

	@Override
	public int compareTo(Weight w) {
		if(weight < w.weight) {
			return -1;
		}
		if(weight > w.weight) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return weight + "g";
	}
	
	public boolean[] getAllowed() {
		return allowed;
	}
	
	public void setAllowed(boolean[] allowed) {
		this.allowed = allowed;
	}

	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}

}
