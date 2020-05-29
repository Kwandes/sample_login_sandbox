/*
    Handles communication between the backend and the database
    Uses the User Model class to represent the 'user' table from the 'donjoe' schema
 */

package dev.hotdeals.login.Repository;

import dev.hotdeals.login.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepo
{
    @Autowired
    JdbcTemplate template; // handles actual query calls to the DB

    public UserRepo()
    {
    }

    // retrieve all rows from the 'user' table
    public List<User> fetchAll()
    {
        String query = "SELECT * FROM user";
        RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class); // a collection type that holds the results of the query
        List<User> userList; // list of users that will be returned
        try
        {
            userList = template.query(query, userRowMapper); // call the database and assign the results to the userList
        } catch (EmptyResultDataAccessException e)
        {
            userList = new ArrayList<>(); // return an empty arrayList
        }
        return userList;
    }

    // retrieve a specific User from the 'user' table
    public User fetchById(int id)
    {
        String query = "SELECT * FROM user WHERE id = ?";
        RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);
        User user; // a specific user that will be returned
        try
        {
            user = template.queryForObject(query, userRowMapper, id); // call the database
        } catch (EmptyResultDataAccessException e)
        {
            user = null; // return a null user
        }
        return user;
    }

    // inserts a new User into the 'user' table
    public boolean addUser(User user)
    {
        String query = "INSERT INTO user (username, password, email, access_level) VALUES (?, ?, ?, ?)";
        RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);
        int rowsAffected; // how many rows were affected by the query. Used for validating success
        try
        {
            rowsAffected = template.update(query, user.getUsername(), user.getPassword(), user.getEmail(), user.getAccessLevel()); // call the database
        } catch (EmptyResultDataAccessException e)
        {
            rowsAffected = 0;
        }
        return rowsAffected > 0; // returns false if no rows were affected, aka the insert has failed
    }

    // deletes a User from the 'user' table based on the id
    public boolean deleteUser(int id)
    {
        String query = "DELETE FROM user WHERE id = ?";
        RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);
        int rowsAffected; // how many rows were affected by the query. Used for validating success
        try
        {
            rowsAffected = template.update(query, id); // call the database
        } catch (EmptyResultDataAccessException e)
        {
            rowsAffected = 0;
        }
        return rowsAffected > 0; // returns false if no rows were affected, aka the insert has failed
    }

    // retrieve all rows from the 'user' table based on the username
    public User searchByUsername(String username)
    {
        String query = "SELECT * FROM user WHERE username = ?";
        RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class); // a collection type that holds the results of the query
        User user; // list of users that will be returned
        try
        {
            user = template.queryForObject(query, userRowMapper, username); // call the database and assign the results to the userList
        } catch (EmptyResultDataAccessException e)
        {
            user = null; // return null
        }
        return user;
    }

}
