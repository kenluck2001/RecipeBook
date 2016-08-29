/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

/**
 *
 * @author anmkosk
 */
public class Status {
    private static User user;
    private static Current currentView = Current.LIST;
    private static boolean loginStatus = false;
    private static Food currentFood;
    private static Boolean searchStatus = false;

    public static void logout() {
        currentView = Current.LIST;
        loginStatus = false;
        currentFood = null;
        user = null;
    }

    public static void login(User u) {
        user = u;
        loginStatus = true;
    }

    public static User getUser() {
        return user;
    }

    public static void setCurrentView(Current c) {
        currentView = c;
    }

    public static Current getCurrentView() {
        return currentView;
    }

    public static boolean getLoginStatus() {
        return loginStatus;
    }

    public static void setCurrentFood(Food f) {
        currentFood = f;
    }

    public static Food getCurrentFood() {
        return currentFood;
    }

    public static Query getQuery() {
    	return null;
    }

    public static void setSearchStatus(boolean b) {
        searchStatus = b;
    }

    public static boolean getSearchStatus() {
        return searchStatus;
    }

}
