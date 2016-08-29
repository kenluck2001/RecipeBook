package client;

import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import java.util.Iterator;

@SuppressWarnings("serial")
public class RecipebookApplication extends Application {
    private Window mainWindow;
    private RegisterView registerView;
    private ListView listView;
    private ListView searchListView;
    private RecipeView recipeView;
    private AddView addView;

    private SearchWindow searchWindow;
    private LoginWindow loginWindow;
    private Button registerButton;
    private Button logoutButton;
    private Button addButton;
    private Button refreshButton;
	
    @Override
    public void init() {
        registerView = new RegisterView(this);
        listView = new ListView(this);
        recipeView = new RecipeView(this);
        addView = new AddView(this);
        searchListView = new ListView(this, true);
        
        HorizontalLayout hl = new HorizontalLayout();

        mainWindow = null;
        mainWindow = new Window("Recipebook Application");
        mainWindow.setSizeFull();
        setMainWindow(mainWindow);


        searchWindow = new SearchWindow(this);
        loginWindow = new LoginWindow(this);
        registerButton = new Button("Register", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                hideButtons();
                registerView();
            }
        });
        logoutButton = new Button("Logout", new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                Status.logout();
                logout();
            }
        });
        addButton = new Button("Add recipe", new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                hideButtons();
                addView();
            }
        });
        refreshButton = new Button("Refresh", new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                recipeView.refresh();
                Status.setSearchStatus(false);
                if (Current.SEARCHLIST.equals(Status.getCurrentView())) {
                    listView();
                }
                listView.updateList();
            }
        });

        logoutButton.setVisible(false);
        addButton.setVisible(false);
        hl.addComponent(searchWindow);
        hl.addComponent(loginWindow);
        hl.addComponent(registerButton);
        hl.addComponent(logoutButton);
        hl.addComponent(addButton);
        hl.addComponent(refreshButton);
        hl.setSpacing(true);

        mainWindow.addComponent(hl);
        mainWindow.addComponent(listView);
//        mainWindow.addComponent(recipeView);
//        Status.setCurrentView(Current.RECIPE);
    }

    public void registerView() {
        Iterator<Component> i = mainWindow.getContent().getComponentIterator();
        Component c = i.next();
        while (i.hasNext()){
            c = i.next();
        }
        mainWindow.removeComponent(c);
        mainWindow.addComponent(registerView);
    }

    public void addView() {
        Iterator<Component> i = mainWindow.getContent().getComponentIterator();
        Component c = i.next();
        while (i.hasNext()){
            c = i.next();
        }
        mainWindow.removeComponent(c);
        mainWindow.addComponent(addView);
    }

    public void listView() {
        Iterator<Component> i = mainWindow.getContent().getComponentIterator();
        Component c = i.next();
        while (i.hasNext()){
            c = i.next();
        }
        mainWindow.removeComponent(c);
        mainWindow.addComponent(listView);
        Status.setCurrentView(Current.LIST);
    }

    public void recipeView() {
        Iterator<Component> i = mainWindow.getContent().getComponentIterator();
        Component c = i.next();
        while (i.hasNext()){
            c = i.next();
        }
        
//        recipeView.removeAllComponents();
        recipeView.refresh();

        mainWindow.removeComponent(c);
        mainWindow.addComponent(recipeView);
        Status.setCurrentView(Current.RECIPE);
    }

    public void searchListView(String [][] foodList) {
        searchListView.updateList(foodList);
        searchListView();
    }

    public void searchListView() {
        Iterator<Component> i = mainWindow.getContent().getComponentIterator();
        Component c = i.next();
        while (i.hasNext()){
            c = i.next();
        }
        mainWindow.removeComponent(c);
        mainWindow.addComponent(searchListView);
        Status.setCurrentView(Current.SEARCHLIST);
        Status.setSearchStatus(true);
    }

    public void previousView() {
        switch (Status.getCurrentView()) {
            case LIST: listView(); break;
            case RECIPE: recipeView(); break;
            case SEARCHLIST: searchListView(); break;
            default: listView();
        }
    }

    public void logout() {
        registerButton.setVisible(true);
        loginWindow.setVisible(true);
        logoutButton.setVisible(false);
        searchWindow.setVisible(true);
        addButton.setVisible(false);
        previousView();
    }

    public void login() {
        registerButton.setVisible(false);
        loginWindow.setVisible(false);
        logoutButton.setVisible(true);
        searchWindow.setVisible(true);
        addButton.setVisible(true);
        previousView();
    }

    public void hideButtons() {
        loginWindow.setVisible(false);
        registerButton.setVisible(false);
        logoutButton.setVisible(false);
        searchWindow.setVisible(false);
        addButton.setVisible(false);
    }

    public void removeListSelection() {
        searchListView.removeSelection();
        listView.removeSelection();
    }

    public void updateMainList() {
        listView.updateList();
    }
}