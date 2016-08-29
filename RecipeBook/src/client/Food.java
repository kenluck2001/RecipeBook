package client;

public class Food
{
    private int foodId = 0;
    private String foodName;
    private String foodCreatedDate;
    private String foodDescription;
    private User myUser;
    private int foodUserId;
    private String foodUrl;

	public Food(int id, String name, String description, String user_id, String date, String url)
	{
		// SELECT food.food_id, food.name
		foodId = id;
		foodName = name;
		foodDescription = description;
		foodCreatedDate = date;
		foodUrl = url;
		foodUserId = Integer.valueOf(user_id);
	}

	public void setFoodId(int foodId)
    {
        this.foodId = foodId;        
    }

    public void setFoodName(String name)
    {
        this.foodName = name;        
    }

    public String getFoodDate()
    {
        return foodCreatedDate;
    }

    public void setFoodDescription(String description)
    {
        this.foodDescription =  description;        
    }    

    public int getFoodId()
    {
        return foodId;        
    }

    public String getFoodName()
    {
        return foodName;        
    }

    public String getFoodDescription()
    {
        return foodDescription;        
    } 
 
    public String getFoodUserName()
    {
        return myUser.getUserName();
    }
    
    public String getFoodUrl()
    {
        return foodUrl;        
    } 
 
    public void setFoodUrl(String url)
    {
    	foodUrl = url;
    }

	public int getFoodUserId() {
		return foodUserId;
	}

	public void setFoodUserId(int foodUserId) {
		this.foodUserId = foodUserId;
	}
   
}
