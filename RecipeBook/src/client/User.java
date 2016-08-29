package client;

import java.util.Date;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User
{
    private int userId;
    private String userPassword;
    private String userName;
    private String userCreatedDate;

	public User(int id, String name, String password, String date)
	{
		userId = id;
		userName = name;
		userPassword = password;
		userCreatedDate = date;
	}

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getUserDate()
    {
        return userCreatedDate;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = hashPassword(userPassword);
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public int getUserId()
    {
        return userId;
    }


    public String getUserName()
    {
        return userName;
    }

	public String getUserPassword()
	{
		return userPassword;
	}

	private String hashPassword(String s)
	{
		String newPassword;
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		m.update(s.getBytes(),0,s.length());
		newPassword = new BigInteger(1,m.digest()).toString(16);
		return newPassword;
	}

}
