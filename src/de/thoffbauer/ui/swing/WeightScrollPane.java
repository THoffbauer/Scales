package de.thoffbauer.ui.swing;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;

import de.thoffbauer.utils.Weight;

@SuppressWarnings("serial")
public class WeightScrollPane extends JScrollPane implements ListDataListener {
	
	protected Session session;
	protected JList<Weight> list;
	
	public WeightScrollPane(Session session) {
		this.session = session;
		addComponents();
	}

	private void addComponents() {
		session.addListDataListener(this);
		list = new JList<Weight>(session);
		session.setList(list);
		list.setLayoutOrientation(JList.VERTICAL);
		setViewportView(list);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	public void addSelectionListener(ListSelectionListener listener) {
		list.addListSelectionListener(listener);
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		list.setSelectedIndex(e.getIndex0());
		session.updateOrder();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		
	}

	public Weight getSelected() {
		return list.getSelectedValue();
	}

}
