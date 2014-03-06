package de.thoffbauer.ui;

import de.thoffbauer.algorithm.ScalesCalculater;
import de.thoffbauer.utils.Polynom;
import de.thoffbauer.utils.Weight;

public class ConsoleInterface {
	
	public void process(String[] args) {
		Polynom[] polynoms = findPolynoms(args);
		System.out.println("Computed Polynoms:");
		printArray(polynoms);
		ScalesCalculater sc = new ScalesCalculater(polynoms);
		sc.calculate();
		System.out.println("Computed Result:");
		System.out.println(sc.getPolynom());
	}

	private Polynom[] findPolynoms(String args[]) {
		int length = args.length / 4;
		Polynom[] polynoms = new Polynom[length];
		for(int i = 0; i < polynoms.length; i++) {
			Weight w = new Weight(Integer.valueOf(args[i * 4]));
			boolean[] allowed = new boolean[3];
			for(int j = 0; j < 3; j++) {
				allowed[j] = Boolean.valueOf(args[i * 4 + j + 1]);
			}
			w.setAllowed(allowed);
			polynoms[i] = w.computePolynom();
		}
		return polynoms;
	}
	
	private void printArray(Object[] objs) {
		for(int i = 0; i < objs.length; i++) {
			System.out.println(i + ": " + objs[i]);
		}
	}

}
