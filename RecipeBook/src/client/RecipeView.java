package client;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.util.Date;

@SuppressWarnings("serial")
public class RecipeView  extends VerticalLayout {
    private Label richText;
    private Button buttonBack1;
    private Button buttonBack2;
    private Food food;
    private Label headerLabel;
    private Embedded image;
    private Label commentLabel;
    private Label commentTitle;
    private CommentWindow commentWindow;
    private HorizontalLayout hl;

    private RecipebookApplication application;
	
    public RecipeView(RecipebookApplication ra) {
		application = ra;
                init();
		refresh();
    }
    
    private void init() {
        headerLabel = new Label("");
        headerLabel.setContentMode(Label.CONTENT_XHTML);
        richText = new Label("");
        richText.setContentMode(Label.CONTENT_XHTML);
        
    	buttonBack1 = new Button("Back to main list", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                application.removeListSelection();
                application.listView();
            }
        });

        buttonBack2 = new Button("Back to search results", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                application.removeListSelection();
                application.searchListView();
            }
        });
        
    	commentTitle = new Label("<h1>Comments:</h1>");
    	commentTitle.setContentMode(Label.CONTENT_XHTML);
        commentLabel = new Label("");
    	commentLabel.setContentMode(Label.CONTENT_XHTML);
    	commentWindow = new CommentWindow(this);
        hl = new HorizontalLayout();
    	hl.addComponent(buttonBack1);
    	hl.addComponent(buttonBack2);
    }
    
    public void refresh() {
        removeAllComponents();

    	food = Status.getCurrentFood();
        
    	headerLabel.setValue("");
        image = new Embedded();
        richText.setValue("");
        
        if (food != null) {
            headerLabel.setValue("<h1>"+food.getFoodName()+"<h1>");
            image.setSource(new ExternalResource(food.getFoodUrl()));
            richText.setValue(food.getFoodDescription());
        }
    		
    	setSpacing(true);
    		
    	addComponent(headerLabel);
    	addComponent(image);
    		
    	addComponent(richText);

        if (Status.getSearchStatus()) {
            buttonBack2.setVisible(true);
        } else {
            buttonBack2.setVisible(false);
        }
        addComponent(hl);
    	
    	String food_comments[][] = null;
    	
    	try {
    		food_comments = Query.GetComments(food.getFoodId());
    	} catch(Exception NullPointerException) {
    		food_comments = null;
    		System.out.println("Food id is null");
    	}

    	addComponent(commentTitle);
    	
        if (food_comments != null) {
        	for (int i = 0; i < food_comments.length; i++) {
        		commentLabel = new Label(food_comments[i][1] + "<br><br>Comment posted by: <b>" + food_comments[i][0] + "</b> at " + EpochToDate(food_comments[i][2])+"<HR>");
        		commentLabel.setContentMode(Label.CONTENT_XHTML);
        		addComponent(commentLabel);
        	}
        }
    		
    	if (Status.getLoginStatus()) {
            addComponent(commentWindow);
        }
    }

    private String EpochToDate(String epoch) {
        return new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(Long.parseLong(epoch) * 1000));
    }
}
