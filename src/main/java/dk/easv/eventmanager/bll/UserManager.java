package dk.easv.eventmanager.bll;

import dk.easv.eventmanager.utils.PasswordUtils;
import dk.easv.eventmanager.be.User;
import dk.easv.eventmanager.dal.web.UserDAO;
import javafx.collections.ObservableList;

import static dk.easv.eventmanager.utils.PasswordUtils.hashPassword;

public class UserManager {
    private final UserDAO userDAO;

    public UserManager() {
        userDAO = new UserDAO();
    }

    // Method to validate Admin login
    public boolean validateAdmin(String username, String password) {
        User user = userDAO.validateUser(username, password);
        return user != null && user.getRankName().equals("Admin");
    }

    // Method to validate Coordinator login
    public boolean validateCoordinator(String username, String password) {
        User user = userDAO.validateUser(username, password);
        return user != null && user.getRankName().equals("Coordinator");
    }

    // Get all users
    public ObservableList<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    // Add User
    public boolean addUser(User user, String password) {
        // Hash password using PasswordUtils
        user.setPasswordHash(hashPassword(password));
        return userDAO.addUser(user);
    }

    // Check if the username already exists
    public boolean doesUsernameExist(String username) {
        return userDAO.doesUsernameExist(username);
    }

    // Edit User
    public boolean editUser(User user, String password) {
        if (password != null && !password.isEmpty()) {
            // Hash password using PasswordUtils if provided
            user.setPasswordHash(hashPassword(password));
        }
        return userDAO.editUser(user);
    }

    // Get user by ID
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    // Validate user login
    public boolean validateUserLogin(String username, String password) {
        User user = userDAO.validateUser(username, password);
        if (user == null) {
            return false; // No user found with that username
        }

        // Use PasswordUtils to check the password
        return PasswordUtils.checkPassword(password, user.getPasswordHash());
    }

    // Get filtered users (delegate to UsersDAO)
    public ObservableList<User> getFilteredUsers(boolean showAdmin, boolean showCoordinator, String usernameFilter) {
        return userDAO.getFilteredUsers(showAdmin, showCoordinator, usernameFilter);
    }

    // Method to delete a user
    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }
}
