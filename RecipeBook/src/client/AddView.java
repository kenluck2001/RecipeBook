/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

/**
 *
 * @author anmkosk
 */
public class AddView extends VerticalLayout {
    private RecipebookApplication application;

    private Button saveButton;
    private Button clearButton;
    private Button cancelButton;

    private final RichTextArea recipeEditor = new RichTextArea();
    private final TextField nameEditor = new TextField("Name of the dish:");
    private final TextField pictureEditor = new TextField("URL of the picture:");


    public AddView(RecipebookApplication ra) {
        application = ra;

        nameEditor.setWidth("100%");
        nameEditor.setRequired(true);
        recipeEditor.setWidth("100%");
        recipeEditor.setRequired(true);
        recipeEditor.setCaption("Recipe:");
        
        pictureEditor.setWidth("100%");
        saveButton = new Button("Save", new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                save();
            }
        });
        
        clearButton = new Button("Clear", new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                nameEditor.setValue("");
                recipeEditor.setValue("");
                pictureEditor.setValue("");
            }
        });

        cancelButton = new Button("Cancel", new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                nameEditor.setValue("");
                recipeEditor.setValue("");
                pictureEditor.setValue("");
                application.login();
                application.previousView();
            }
        });

        setSpacing(true);
        setMargin(true);

        addComponent(nameEditor);
        addComponent(recipeEditor);
        addComponent(pictureEditor);
        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(saveButton);
        hl.addComponent(clearButton);
        hl.addComponent(cancelButton);
        addComponent(hl);

    }
    
    private void save() {
        try {
            Query query = Status.getQuery();
            if (query == null) {
                query = new Query();
            }
            User u = Status.getUser();
            String s1 = null;
            String s2 = null;
            String s3 = null;
            String s4 = null;
            if (u != null) {
                if (nameEditor.isValid()) {
                    s1 = String.valueOf(nameEditor.getValue());
                }
                s2 = String.valueOf(u.getUserId());
                if (recipeEditor.isValid()) {
                    s3 = String.valueOf(recipeEditor.getValue());
                }
                s4 = String.valueOf(pictureEditor.getValue());
                if (s1 != null && s3 != null) {
                        query.InsertFood(s1, s2, s3, s4);
                        getWindow().showNotification("Recipe saved!");
                        application.updateMainList();
                        application.login();
                        application.previousView();
                } else {
                    getWindow().showNotification("Name and recipe needed!");
                }
            } else {
                getWindow().showNotification("Save failed!");
            }
            nameEditor.setValue("");
            recipeEditor.setValue("");
            pictureEditor.setValue("");
        } catch (MyException ex) {
            getWindow().showNotification("Save failed!",
                    Notification.TYPE_HUMANIZED_MESSAGE);
        }
    }

}
