package client;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
public class LoginWindow extends VerticalLayout {

    Window loginWindow;
    private final TextField username;
    private final TextField password;
    private final RecipebookApplication application;

    public LoginWindow(RecipebookApplication ra) {
        application = ra;

        // Create the window
        loginWindow = new Window("Login window");
        loginWindow.setWidth("180px");

        // Configure the windws layout; by default a VerticalLayout
        VerticalLayout layout = (VerticalLayout) loginWindow.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);

        // Add some content; a label and a close-button
//        Label message = new Label("This is a login window");
//        loginWindow.addComponent(message);

//        Button close = new Button("Close", new Button.ClickListener() {
//            // inline click-listener
//            public void buttonClick(ClickEvent event) {
//                // close the window by removing it from the parent window
//                ((Window) loginWindow.getParent()).removeWindow(loginWindow);
//            }
//        });
        // The components added to the window are actually added to the window's
        // layout; you can use either. Alignments are set using the layout
//        layout.addComponent(close);
//        layout.setComponentAlignment(close, "right");
        
        
        // Username
        username = new TextField("Username");
        username.setRequired(true);
        layout.addComponent(username);
        
        // Password
        password = new TextField("Password");
        password.setSecret(true);
        password.setRequired(true);
        layout.addComponent(password);
        
        // Login button
        
        Button loginButton = new Button("Login", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (username.isValid() && password.isValid()) {
                    User user = Query.login(username.getValue().toString(),
                            password.getValue().toString());
                    if (user != null) {
                        Status.login(user);
                        application.login();
                        close();
                    } else {
                        getWindow().showNotification("Login failed!",
                                Notification.TYPE_HUMANIZED_MESSAGE);
                    }
                } else {
                    getWindow().showNotification("Give username and password");
                }
            }
        });
        
        layout.addComponent(loginButton);
        layout.setComponentAlignment(loginButton, "right");
        
        
        // Add a button for opening the loginWindow
        Button open = new Button("Login", new Button.ClickListener() {
            // inline click-listener
            public void buttonClick(ClickEvent event) {
                if (loginWindow.getParent() != null) {
                    // window is already showing
                    getWindow().showNotification("Window is already open");
                } else {
                    // Open the loginWindow by adding it to the parent window
                    getWindow().addWindow(loginWindow);
                }
            }
        });
        addComponent(open);
    }

    private void close(){
        getWindow().removeWindow(loginWindow);
        username.setValue("");
        password.setValue("");
    }
}