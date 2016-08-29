package client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Query {
	// Retrieve multiple lines.
	public static String[][] query(String query) {
		String[][] result = null;
		
		try {
			DB.connect();
			
	        ResultSet rs = DB.getStmt().executeQuery(query);
	        ResultSetMetaData rsMetaData = rs.getMetaData();
        	rs.last();
        	int columnCount = rsMetaData.getColumnCount();
        	result = new String[rs.getRow()][columnCount];
        	rs.beforeFirst();
	        while (rs.next()) {
	        	for (int i = 0; i < columnCount; i++) {
	        		result[rs.getRow()-1][i] = rs.getString(i+1);
				}
            }
	        rs.close();
	        DB.disconnect();
		} catch(Exception e) {
			System.out.println("Query error: " + e);
		}
		return result;
	}
	
	// Insert stuff to db.
	public static void Insert(String query) throws MyException {
		try {
			DB.connect();
			
	        DB.getStmt().executeUpdate(query);
	        
	        DB.disconnect();
		} catch(Exception e) {
			System.out.println("SQL Insert error: " + e);
                        throw new MyException("SQL Insert error: " + e);
		}
	}
	
	// Retrieve a single line
	public static String ScalarQuery(String query) {
		String result = "";
		
		try {
			DB.connect();
			
	        ResultSet rs = DB.getStmt().executeQuery(query);
	    	
            result = rs.getString("name");
	        
	        rs.close();
	        DB.disconnect();
		} catch(Exception e) {
			System.out.println("Scalar Query error: " + e);
		}
		return result;
	}



    public static String[][] getFoods(String searchTerm, boolean searchTitles, boolean searchDescriptions) {
        String s = "";
        if (searchTitles) {
            s = "food.name";
            if (searchDescriptions) {
                s = "food.name, food.recipe";
            }
        } else if (searchDescriptions) {
            s = "food.recipe";
        } else {
            return null;
        }
        String result[][] = Query.query("SELECT food.id, food.name, food.date, food.user_id, food.recipe, food.url, users.name AS username "
                + "FROM food INNER JOIN users ON food.user_id = users.id "
                + "WHERE MATCH(" + s + ") " +
                "AGAINST ('" + searchTerm + "')");
        if (result == null) {
            result = new String[0][0];
        }
        return result;
    }

    public static String[][] getFoodsExpanded(String searchTerm, boolean searchTitles, boolean searchDescriptions) {
        String s = "";
        if (searchTitles) {
            s = "food.name";
            if (searchDescriptions) {
                s = "food.name, food.recipe";
            }
        } else if (searchDescriptions) {
            s = "food.recipe";
        } else {
            return null;
        }
        String result[][] = Query.query("SELECT food.id, food.name, food.date, food.user_id, food.recipe, food.url, users.name AS username "
                + "FROM food INNER JOIN users ON food.user_id = users.id "
                + "WHERE MATCH(" + s + ") " +
                "AGAINST ('" + searchTerm + "')");
        if (result == null) {
            result = new String[0][0];
        }
        return result;
    }
    
    public static String[][] getFoodsNotExact(String searchTerm, boolean searchTitles, boolean searchDescriptions) {
        String s = "";
        if (searchTitles) {
            s = "food.name LIKE '%" + searchTerm + "%'";
            if (searchDescriptions) {
                s = "(food.name LIKE '%" + searchTerm + "%' OR food.recipe LIKE '%" + searchTerm + "%')";
            }
        } else if (searchDescriptions) {
            s = "food.recipe LIKE '%" + searchTerm + "%'";
        } else {
            return null;
        }
        String result[][] = Query.query("SELECT food.id, food.name, food.date, food.user_id, food.recipe, food.url, users.name AS username "
                + "FROM food INNER JOIN users ON food.user_id = users.id "
                + "WHERE " + s);
        if (result == null) {
            result = new String[0][0];
        }
        return result;
    }

	// Insert a new comment into db.
	public void InsertComment(String food_id, String user_id, String text) throws MyException {
		Query.Insert("INSERT INTO comment (food_id, user_id, date, comment) VALUES " +
					"('"+food_id+"','"+user_id+"','"+GetDate()+"','"+text+"')");
	}
	
	// Insert a new  food item into db.
	public void InsertFood(String name, String user_id, String recipe, String url) throws MyException {
            Query.Insert("INSERT INTO food (name, date, user_id, recipe, url) VALUES " +
                    "('"+name+"','"+GetDate()+"','"+user_id+"','"+recipe+"','"+url+"')");
	}
	
	// Store new user info to db.
    public void InsertUser(String username, String password) {
        try {
            Query.Insert("INSERT INTO users (name, pass, date) " + "VALUES ('" + username + "', '" + md5(password) + "', '" + GetDate() + "')");
        } catch (Exception e) {
            System.out.println("SQL Insert error: " + e);
        }
    }
	
	// Verify username and password validity
	public static  boolean CheckLogin(String username, String password) {
		boolean valid = false;
		try {
			String result[][] = Query.query("SELECT * FROM users WHERE name='"+username+"' " +
						"AND pass='"+md5(password)+"'");
			if (result.length == 1) {
				valid = true;
                        }
		} catch (Exception e) {
			System.out.println("User verification error: " + e);
		}
		return valid;
	}

        public static boolean checkUser(String name) {
            boolean valid = false;
            String result[][] = Query.query("SELECT name FROM users WHERE name='"+name+"'");
            if (result.length == 1) {
                valid = true;
            }
            return valid;
        }

        public static User login(String username, String password) {
            User user = null;
			try {
				String result[][] = Query.query("SELECT * FROM users WHERE name='"+username+"' " +
							"AND pass='"+md5(password)+"'");
				if (result.length == 1) {
	                                user = new User(Integer.valueOf(result[0][0]),
	                                        result[0][1], result[0][2], result[0][3]);
	                        }
			} catch (Exception e) {
				System.out.println("User verification error: " + e);
			}
			return user;
        }
        
        public static String[][] GetComments(int food_id) {
        	String result[][] = Query.query("SELECT users.name AS username, comment.comment, comment.date  FROM comment INNER JOIN users ON comment.user_id = users.id WHERE comment.food_id='"+food_id+"'");
			return result;
        }
        
        public static Food GetFood(int food_id) {
        	Food food = null;
        	if (food_id == 0)
        		food_id = 1;
        	
        	String[][] result = Query.query("SELECT food.name, food.url, food.user_id, food.recipe, food.date FROM food WHERE food.id='"+food_id+"'");
        	
        	food = new Food(food_id, result[0][0], result[0][3], result[0][2], result[0][4], result[0][1]);
			
        	return food;
        }
	
	public static String md5(String s) throws Exception{
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(s.getBytes(),0,s.length());
		return new BigInteger(1,m.digest()).toString(16);
	}
	
	public long GetDate() {
		long epoch = System.currentTimeMillis()/1000;
		return epoch;
	}
}
