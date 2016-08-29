package client;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.ObjectProperty;

public class ViewController {
	
	private ObjectProperty view;
	private IndexedContainer container;
	
	public ViewController() {
		container = new IndexedContainer();
		refreshContents();
	}

	public void refreshContents() {
		container.removeAllItems();
	}
	
	public IndexedContainer getContainer() {
		return container;
	}
}
