/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.data.validator.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Window.Notification;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author modified from FormAdvancedLayoutExample by anmkosk
 */
@SuppressWarnings("serial")
public class RegisterForm extends VerticalLayout {
    
    Person person;
    Query query;
    
    private static final String COMMON_FIELD_WIDTH = "12em";
    
    public RegisterForm() {
        query = new Query();
        person = new Person(); // a person POJO
        BeanItem personItem = new BeanItem(person); // item from POJO

        // Create the Form
        final FormWithComplexLayout personForm = new FormWithComplexLayout(personItem);

        // Add form to layout
        addComponent(personForm);

        // The cancel / apply buttons
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
/*        Button discardChanges = new Button("Discard changes",
                new Button.ClickListener() {
                    public void buttonClick(Button.ClickEvent event) {
                        personForm.discard();
                    }
                });
        discardChanges.setStyleName(Button.STYLE_LINK);
        buttons.addComponent(discardChanges);
        buttons.setComponentAlignment(discardChanges, "middle"); */

        Button register = new Button("Register", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                try {
                    personForm.commit();
                    if (person.getPassword().equals(person.getPasswordAgain())) {
                        query.InsertUser(person.getUsername(),
                                person.getPassword());
                        getWindow().showNotification("User saved",
                                Notification.TYPE_HUMANIZED_MESSAGE);
                    } else {
                        getWindow().showNotification(
                                "Error:",
                                "re-enter the password",
                                Notification.TYPE_ERROR_MESSAGE);
                        personForm.clearPassword();
                    }
                } catch (Exception e) {
                    // Ingnored, we'll let the Form handle the errors
                }
            }
        });
        buttons.addComponent(register);

        Button clear = new Button("Clear", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                personForm.clear();
                personForm.discard();
            }
        });
        buttons.addComponent(clear);

        personForm.getFooter().setMargin(true);
        personForm.getFooter().addComponent(buttons);

        // button for showing the internal state of the POJO
        Button showPojoState = new Button("Show POJO internal state",
                new Button.ClickListener() {
                    public void buttonClick(Button.ClickEvent event) {
                        showPojoState();
                    }
                });
        addComponent(showPojoState);
    }
    
    public class FormWithComplexLayout extends Form {

        private VerticalLayout ourLayout;

        public FormWithComplexLayout(BeanItem personItem) {
            setCaption("Register");

            // Create our layout
            ourLayout = new VerticalLayout();

            // Use top-left margin and spacing
            ourLayout.setMargin(true, false, false, true);
            ourLayout.setSpacing(true);

            setLayout(ourLayout);

            // Set up buffering
            setWriteThrough(false); // we want explicit 'apply'
            setInvalidCommitted(false); // no invalid values in datamodel

            // FieldFactory for customizing the fields and adding validators
            setFormFieldFactory(new PersonFieldFactory());
            setItemDataSource(personItem); // bind to POJO via BeanItem

            // Determines which properties are shown, and in which order:
            setVisibleItemProperties(Arrays.asList(new String[] { "username",
                    "password", "passwordAgain" }));
        }

        /*
         * Override to get control over where fields are placed.
         */
//        @Override
        protected void attachField(Object propertyId, Field field) {
            if (propertyId.equals("username")) {
                ourLayout.addComponent(field);
            } else if (propertyId.equals("password")) {
                ourLayout.addComponent(field);
            } else if (propertyId.equals("passwordAgain")) {
                field.setCaption("Password again");
                ourLayout.addComponent(field);
            }
        }

        public void clear() {
            person.setUsername("");
            person.setPassword("");
            person.setPasswordAgain("");
        }

        public void clearPassword() {
            person.setPassword("");
            person.setPasswordAgain("");
        }

    }
    
    //TODO: create comparing functionality
    private void showPojoState() {
        Window.Notification n = new Window.Notification("POJO state",
                Window.Notification.TYPE_TRAY_NOTIFICATION);
        n.setPosition(Window.Notification.POSITION_CENTERED);
        n.setDescription("Username: " + person.getUsername()
                + "<br/>Password: " + person.getPassword()
                + "<br/>Password2: " + person.getPasswordAgain());
        getWindow().showNotification(n);
    }
    
    private class PersonFieldFactory extends DefaultFieldFactory {

        public PersonFieldFactory() {
        }

        @Override
        public Field createField(Item item, Object propertyId,
                Component uiContext) {
            System.out.println(propertyId);
            Field f = super.createField(item, propertyId, uiContext);
            if ("username".equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setRequired(true);
                tf.setRequiredError("Please enter a username");
                tf.setWidth(COMMON_FIELD_WIDTH);
                tf.addValidator(new StringLengthValidator(
                        "Username must be 3-25 characters", 3, 25, false));
            } else if ("password".equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setSecret(true);
                tf.setRequired(true);
                tf.setRequiredError("Please enter a password");
                tf.setWidth("10em");
                tf.addValidator(new StringLengthValidator(
                        "Password must be 6-20 characters", 6, 20, false));
            } else if ("passwordAgain".equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setSecret(true);
                tf.setRequired(true);
                tf.setRequiredError("Please re-enter the password");
                tf.setWidth("10em");
                tf.addValidator(new StringLengthValidator(
                        "Password must be 6-20 characters", 6, 20, false));
            }

            return f;
        }
    }

    public class Person implements Serializable {

        private String username = "";
        private String password = "";
        private String passwordAgain = "";

        public Person() {
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getPasswordAgain() {
            return passwordAgain;
        }

        public void setUsername(String name) {
            username = name;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setPasswordAgain(String password) {
            passwordAgain = password;
        }

    }

}
