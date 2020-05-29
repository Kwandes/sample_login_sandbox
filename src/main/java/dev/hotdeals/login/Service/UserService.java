/*
    Handles logic for all User operations
 */

package dev.hotdeals.login.Service;

import dev.hotdeals.login.Model.User;
import dev.hotdeals.login.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class UserService
{
    //region Security - hashing of the passwords

    private int hashStrength = 12; // work factor of bcrypt

    // hash a provided string using Springboots' Bcrypt encoder
    // it produces a hash that is further salted.
    // in order to see if a given password matches the hashed one, a encoder.matches(string1, string2) method has to be used
    public String hashPassword(String password)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(hashStrength);
        String encodedPassword = encoder.encode(password);
        return encodedPassword;
    }

    // compares 2 hashed passwords (one from user and one from database)
    public boolean checkPasswordMatch(String password, String password2)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(hashStrength); // hashing
        return encoder.matches(password, password2);
    }

    //endregion

    //region UserRepo methods
    @Autowired
    UserRepo userRepo;

    // retrieve all rows from the 'user' table
    public List<User> fetchAll()
    {
        return userRepo.fetchAll();
    }

    // retrieve a specific User from the 'user' table
    public User fetchById(int id)
    {
        return userRepo.fetchById(id);
    }

    // inserts a new User into the 'user' table
    public boolean addUser(User user)
    {
        return userRepo.addUser(user);
    }

    // deletes a User from the 'user' table based on the id
    public boolean deleteUser(int id)
    {
        return userRepo.deleteUser(id);
    }

    // retrieve all rows from the 'user' table based on the username
    public User searchByUsername(String username)
    {
        return userRepo.searchByUsername(username);
    }
    //endregion
}
