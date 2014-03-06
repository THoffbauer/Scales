package de.thoffbauer;

import de.thoffbauer.ui.swing.GUIMain;


public class Scales {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Polynom p1 = new Polynom();
//		p1.addFactor(new PolynomFactor(3, 1));
//		p1.addFactor(new PolynomFactor(1, 1));
//		Polynom p2 = new Polynom();
//		p2.addFactor(new PolynomFactor(2, 1));
//		p2.addFactor(new PolynomFactor(3, 1));
//		Polynom pResult = Polynom.mul(p1, p2, null);
//		System.out.println("(" + p1.toString() + ")(" + p2.toString() + ") = " + pResult.toString());
//		Weight w = new Weight(9);
//		w.computePolynom(new boolean[]{true, true, true});
//		System.out.println(w.getPolynom());
//		Polynom[] polynoms = new Polynom[2];
//		polynoms[0] = new Weight(1).computePolynom(new boolean[]{true, true, true});
//		polynoms[1] = new Weight(3).computePolynom(new boolean[]{true, true, true});
//		ScalesCalculater sc = new ScalesCalculater(polynoms);
//		sc.calculate();
//		System.out.println(sc.getPolynom().toString());
//		ConsoleInterface ci = new ConsoleInterface();
//		ci.process(new String[]{"1", "true", "true", "true", "3", "true", "true", "true", " "});
////		ci.process(args);
//		SwingInterface si = new SwingInterface();
//		si.show();
		GUIMain gui = new GUIMain();
		gui.setVisible(true);
	}

}
