package de.thoffbauer.algorithm;

public class Possibility implements Comparable<Possibility> {

	private int weight;
	private int options;
	
	public Possibility(int weight, int options) {
		this.weight = weight;
		this.options = options;
	}
	
	public String toString() {
		return weight + "g: " + options;
	}
	
	public void increaseOptions(int increase) {
		options += increase;
	}

	public int getWeight() {
		return weight;
	}

	public int getOptions() {
		return options;
	}

	@Override
	public int compareTo(Possibility o) {
		if(weight < o.weight) {
			return -1;
		} else if(weight > o.weight) {
			return 1;
		}
		return 0;
	}
	
}
