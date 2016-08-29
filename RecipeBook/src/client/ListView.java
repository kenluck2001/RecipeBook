package client;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.util.Date;

@SuppressWarnings("serial")
public class ListView extends VerticalLayout {

    private RecipebookApplication application;
    private final Table table = new Table();
    private String[][] foodList;
    private VerticalLayout vl;
    private boolean searchButton = false;
    private Button mainListButton;
    private Button searchListButton;
    
    public ListView(RecipebookApplication ra) {
        application = ra;
        searchButton = false;
	foodList = Query.query("SELECT food.id, food.name, food.date, users.name AS username FROM food INNER JOIN users ON food.user_id = users.id ORDER BY food.id");
        init();
    }

    public ListView(RecipebookApplication ra, Boolean addButton) {
        application = ra;
        this.searchButton = addButton;
        init();
    }

    private String EpochToDateShort(String epoch) {
        return new java.text.SimpleDateFormat("dd.MM.yyyy").format(new Date(Long.parseLong(epoch) * 1000));
    }

    public void updateList(String [][] s) {
        foodList = s;
        refresh();
    }

    public void updateList() {
        foodList = Query.query("SELECT food.id, food.name, food.date, users.name AS username FROM food INNER JOIN users ON food.user_id = users.id ORDER BY food.id");
        refresh();
    }

    public void init() {
        vl = new VerticalLayout();
        
	// Define the names and data types of columns.
	 // The "default value" parameter is meaningless here. 
	table.addContainerProperty("Food name", String.class, null);
	table.addContainerProperty("Date added", String.class, null);
	table.addContainerProperty("User", String.class, null);
        
	table.setSizeFull();
        table.setRequired(true);
	table.setSelectable(true);
	table.setImmediate(true);
//	table.setNullSelectionAllowed(false);
	table.addListener(new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
//	    	System.out.println(table.getValue());
                Food food = null;
	    	if (table.isValid()) {
                    food = Query.GetFood((Integer)table.getValue());
                }
	    	if (food != null) {
                    Status.setCurrentFood(food);
                    Status.setCurrentView(Current.RECIPE);
                    application.recipeView();
                }
	    }
	});
        mainListButton = new Button("Back to main list", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                application.removeListSelection();
                application.listView();
            }
        });
        searchListButton = new Button("Back to search list", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                application.removeListSelection();
                application.searchListView();
            }
        });
        refresh();
    }
    
    public void refresh() {
        table.removeAllItems();
        vl.removeAllComponents();
        removeAllComponents();
        
	// Add a few items in the table.
        if (foodList == null) {
	    foodList = new String[0][0];
	}
	for (int i = 0; i < foodList.length; i++) {
            table.addItem(new Object[] {
                foodList[i][1],
                EpochToDateShort(foodList[i][2]),
                foodList[i][3]},
                new Integer(foodList[i][0]));
	}
				
	vl.addComponent(table);
        if (searchButton) {
            vl.addComponent(mainListButton);
        } else {
            if (Status.getSearchStatus()) {
                vl.addComponent(searchListButton);
            }
        }
	
	addComponent(vl);
		
	}

    public void removeSelection() {
        table.select(null);
    }
}
