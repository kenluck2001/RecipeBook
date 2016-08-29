package client;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class SearchWindow extends VerticalLayout {

    RecipebookApplication application;
    Window searchWindow;
    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    HorizontalLayout hl;
    TextField searchField;
    Button searchButton;

    public SearchWindow(RecipebookApplication ra) {
        application = ra;

        // Create the window
        searchWindow = new Window("Search window");
        searchWindow.setWidth("600px");
        

        // Configure the windws layout; by default a VerticalLayout
        VerticalLayout layout = (VerticalLayout) searchWindow.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);

        // Add some content; a label and a close-button
//        Label message = new Label("This is a search window");
//        searchWindow.addComponent(message);

//        Button close = new Button("Close", new Button.ClickListener() {
//            // inline click-listener
//            public void buttonClick(ClickEvent event) {
//                // close the window by removing it from the parent window
//                ((Window) searchWindow.getParent()).removeWindow(searchWindow);
//            }
//        });
        // The components added to the window are actually added to the window's
        // layout; you can use either. Alignments are set using the layout
//        layout.addComponent(close);
//        layout.setComponentAlignment(close, "right");
        Label searchLabel = new Label("Search for:");
        layout.addComponent(searchLabel);
        
        hl = new HorizontalLayout();
        
        searchField = new TextField();
        searchField.setColumns(40);
        searchField.setRequired(true);
        searchButton = new Button("Search", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                search();
            }
        });
        hl.addComponent(searchField);
        hl.addComponent(searchButton);
        
        layout.addComponent(hl);
        
        hl = new HorizontalLayout();
        
        Label searchIn = new Label("Search in:");
        hl.addComponent(searchIn);
        
        cb1 = new CheckBox("Titles", true);
        cb1.setImmediate(true);
        hl.addComponent(cb1);
        
        cb2 = new CheckBox("Descriptions");
        cb2.setImmediate(true);
        hl.addComponent(cb2);

        HorizontalLayout hl2 = new HorizontalLayout();
        hl2.setMargin(false, false, false, true);

        Label exactOnly = new Label("Exact results only:");
        hl2.addComponent(exactOnly);
        
        cb3 = new CheckBox("", true);
        cb3.setImmediate(true);
        hl2.addComponent(cb3);
        hl.addComponent(hl2);

        hl.setComponentAlignment(hl2, Alignment.MIDDLE_RIGHT);
        layout.addComponent(hl);
        
        // Add a button for opening the searchWindow
        Button open = new Button("Search", new Button.ClickListener() {
            // inline click-listener
            public void buttonClick(ClickEvent event) {
                if (searchWindow.getParent() != null) {
                    // window is already showing
                    getWindow().showNotification("Window is already open");
                } else {
                    // Open the searchWindow by adding it to the parent window
                    getWindow().addWindow(searchWindow);
                }
            }
        });
        addComponent(open);

    }

    private void search() {
        if (searchField.isValid()) {
            if (cb1.booleanValue() | cb2.booleanValue()) {
                String[][] result;
                if (cb3.booleanValue()) {
                    result = Query.getFoods(searchField.getValue().toString(), cb1.booleanValue(), cb2.booleanValue());
                } else {
//                    result = Query.getFoodsExpanded(searchField.getValue().toString(), cb1.booleanValue(), cb2.booleanValue());
                    result = Query.getFoodsNotExact(searchField.getValue().toString(), cb1.booleanValue(), cb2.booleanValue());
                }
                if (result != null) {
                    if (result.length < 1) {
                        getWindow().showNotification("No matches found!");
                    } else {
                        proceed(result);
                    }
                } else {
                    getWindow().showNotification("Error with search procedure, try again later.");
                    close();
                }
            } else {
                getWindow().showNotification("One or both boxes need to be checked!");
            }
        } else {
            getWindow().showNotification("You haven't given search terms yet!");
        }
    }

    private void proceed(String[][] s) {
        String[][] result = s;
        if (result.length == 1) {
            Food food = new Food(Integer.valueOf(result[0][0]), result[0][1], result[0][4], result[0][3], result[0][2], result[0][5]);
            Status.setCurrentFood(food);
            application.recipeView();
        } //food.id, food.name, food.date, users.name
        else if (result.length >= 1) {
            int i = 0;
            String[][] foodList = new String[result.length][4];
            while (i < result.length) {
                foodList[i][0] = result[i][0];
                foodList[i][1] = result[i][1];
                foodList[i][2] = result[i][2];
                foodList[i][3] = result[i][6];
                i++;
            }
            application.searchListView(foodList);
            application.updateMainList();
        } else {
            getWindow().showNotification("Something weird is going on with the search!");
            System.out.println("Something weird is going on with the search!");
        }
        close();
    }

    private void close(){
        getWindow().removeWindow(searchWindow);
    }

}