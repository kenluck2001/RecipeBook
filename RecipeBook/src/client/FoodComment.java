package client;

public class FoodComment
{
    private String description;
    private int commentId;
    private String commentDate;
    private Food myFood;
    private User myUser;

	public FoodComment(int id, String description, Food food, User user, String date)
	{
		commentId = id;
		this.description = description;
		myFood = food;
		myUser = user;
		commentDate = date;
	}

    public void setDescription(String description)
    {
        this.description = description;
    }

//comment id comes from the auto increment feature of the database
    public void setCommentId(int commentId)
    {
        this.commentId = commentId;
    }

    public String getCommentDate()
    {
        return commentDate;
    }

    public String getDescription()
    {
        return description;
    }


    public int getCommentId()
    {
        return commentId;
    }

    public int getCommentUserId()
    {
        return myUser.getUserId();
    }

	public int getCommenFoodId()
	{
		return myFood.getFoodId();
	}
    
}
