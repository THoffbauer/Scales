package de.thoffbauer.ui.swing;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.thoffbauer.utils.Polynom;
import de.thoffbauer.utils.Weight;

@SuppressWarnings("serial")
public class Session extends AbstractListModel<Weight> {
	
	private ArrayList<Weight> weights;
	private ArrayList<ListDataListener> listeners;
	private JList<Weight> list;
	
	public Session() {
		weights = new ArrayList<Weight>();
		listeners = new ArrayList<ListDataListener>();
	}

	private void findPolynoms(String args[]) {
		int length = args.length / 4;
		for(int i = 0; i < length; i++) {
			Weight w = new Weight(Integer.valueOf(args[i * 4]));
			boolean[] allowed = new boolean[3];
			for(int j = 0; j < 3; j++) {
				allowed[j] = Boolean.valueOf(args[i * 4 + j + 1]);
			}
			w.setAllowed(allowed);
			w.computePolynom();
			add(w);
		}
	}
	
	public void setList(JList<Weight> list) {
		this.list = list;
	}
	
	public ArrayList<Weight> getWeights() {
		return weights;
	}
	
	public void updateOrder() {
		Weight w = list.getSelectedValue();
		Collections.sort(weights);
		list.setSelectedValue(w, true);
		for(ListDataListener l : listeners) {
			l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, weights.size() - 1));
		}
	}
	
	public void add(Weight weight) {
		weights.add(weight);
		for(ListDataListener l : listeners) {
			l.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, weights.size() - 1, weights.size() - 1));
		}
	}
	
	public void remove(Weight weight) {
		int index = weights.indexOf(weight);
		if(index == -1) {
			throw new IllegalArgumentException("Weight is not in this list!");
		}
		weights.remove(index);
		for(ListDataListener l : listeners) {
			l.intervalRemoved(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index));
		}
	}

	@Override
	public int getSize() {
		return weights.size();
	}

	@Override
	public Weight getElementAt(int index) {
		return weights.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	public void newSession() {
		weights.clear();
	}

	public void load(String[] split) {
		newSession();
		findPolynoms(split);
	}

	public Polynom[] getPolynoms() {
		Polynom[] polynoms = new Polynom[weights.size()];
		for(int i = 0; i < polynoms.length; i++) {
			polynoms[i] = weights.get(i).computePolynom();
		}
		return polynoms;
	}

}
