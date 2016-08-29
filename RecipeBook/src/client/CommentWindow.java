package client;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class CommentWindow extends VerticalLayout  {
    private RecipeView view;
    private Window commentWindow;

    public CommentWindow(RecipeView rv) {
        view = rv;
	commentWindow = new Window();
	
	commentWindow = new Window("Submit a comment");
	commentWindow.setWidth("527px");
	
	VerticalLayout layout = (VerticalLayout) commentWindow.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);
        
        final TextField editor;
        editor = new TextField(null, "");
        editor.setRows(10);
        editor.setColumns(40);
        editor.setImmediate(true);
        editor.setRequired(true);
        
        // Add a button for opening the searchWindow
        Button submit = new Button("Submit", new Button.ClickListener() {
            // inline click-listener
            public void buttonClick(ClickEvent event) {
            	// Submit stuff
                if (editor.isValid()) {
                    User u = Status.getUser();
                    Query q = Status.getQuery();
                    Food f = Status.getCurrentFood();
                    if (u != null) {
                        if (q == null) {
                            q = new Query();
                        }
                        if (f != null) {
                            try {
                                q.InsertComment(String.valueOf(f.getFoodId()),
                                        String.valueOf(u.getUserId()),
                                        editor.getValue().toString());
                                view.refresh();
                                getWindow().showNotification("Comment added");
                            } catch (MyException ex) {
                                getWindow().showNotification(
                                        "Submitting comment failed!");
                            }
                        }
                    } else {
                        getWindow().showNotification("You have to be logged in to comment!");
                    }
                    close();
                } else {
                    getWindow().showNotification("You haven't written the comment yet!");
                }
            }
        });
        
        layout.addComponent(editor);
        layout.addComponent(submit);
        
        
        // Add a button for opening the searchWindow
        Button addComment = new Button("Add a comment", new Button.ClickListener() {
            // inline click-listener
            public void buttonClick(ClickEvent event) {
                if (commentWindow.getParent() != null) {
                    // window is already showing
                    getWindow().showNotification("Window is already open");
                } else {
                    // Open the searchWindow by adding it to the parent window
                    getWindow().addWindow(commentWindow);
                }
            }
        });
        
	addComponent(addComment);
    }

    private void close(){
        getWindow().removeWindow(commentWindow);
    }
}
