package client;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class RegisterWindow extends VerticalLayout {

    Window registerWindow;
    private final TextField username;
    private final TextField password;


    public RegisterWindow() {

        // Create the window
        registerWindow = new Window("Register window");
        registerWindow.setWidth("180px");

        // Configure the windws layout; by default a VerticalLayout
        VerticalLayout layout = (VerticalLayout) registerWindow.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);

        // Add some content; a label and a close-button
//        Label message = new Label("This is a registeration window");
//        registerWindow.addComponent(message);

//        Button close = new Button("Close", new Button.ClickListener() {
//            // inline click-listener
//            public void buttonClick(ClickEvent event) {
//                // close the window by removing it from the parent window
//                ((Window) registerWindow.getParent()).removeWindow(registerWindow);
//            }
//        });
        // The components added to the window are actually added to the window's
        // layout; you can use either. Alignments are set using the layout
//        layout.addComponent(close);
//        layout.setComponentAlignment(close, "right");

        
        // Username
        username = new TextField("Username");
        layout.addComponent(username);
        
        // Password
        password = new TextField("Password");
        password.setSecret(true);
        layout.addComponent(password);
        
        // Login button
        
        Button loginButton = new Button("Register", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				getWindow().showNotification(
						"User: " + username.getValue() + "Password: "
								 + password.getValue());
			}
		});
        
        layout.addComponent(loginButton);
        layout.setComponentAlignment(loginButton, "right");
        
        
        // Add a button for opening the registerWindow
        Button open = new Button("Register", new Button.ClickListener() {
            // inline click-listener
            public void buttonClick(ClickEvent event) {
                if (registerWindow.getParent() != null) {
                    // window is already showing
                    getWindow().showNotification("Window is already open");
                } else {
                    // Open the registerWindow by adding it to the parent window
                    getWindow().addWindow(registerWindow);
                }
            }
        });
        addComponent(open);

    }

}