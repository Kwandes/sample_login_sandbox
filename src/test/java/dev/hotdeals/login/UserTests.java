/*
    Validate various User methods
 */

package dev.hotdeals.login;

import dev.hotdeals.login.Model.User;
import dev.hotdeals.login.Repository.UserRepo;
import dev.hotdeals.login.Service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // make it so tests are run in the numerical order
public class UserTests
{
    @Autowired
    UserRepo userRepo;

    public static boolean testVerification; // holds status of the previous test in the series

    //region User Repository tests
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class) // make it so tests are run in the numerical order
    public class UserRepotests
    {
        @Test
        public void userRepoLoadsTest()
        {
            // validate if the User Repository layer has loaded
            assertThat(userRepo).isNotNull();
        }

        @Test
        @Order(1)
        @DisplayName("searchByUsername()")
        public void userRepoSearchByModelTest() throws Exception
        {
            List<User> userList = userRepo.searchByUsername("");

            testVerification = false; // if the list is empty, testVerification will be false ( test has failed ) else, it will be true

            assertThat(userList).isNotEmpty();
            testVerification = true; // this will only be reached if the assert is successful
        }

        @Test
        @Order(2)
        @DisplayName("addUser()")
        public void userRepoAddUserTest() throws Exception
        {
            // only run if the previous test was successful
            if (!testVerification)
            {
                // throw a more descriptive exception message
                assertThat("User Repo - Search User By Username Test to be ").isEqualTo("Successful");
            }

            // If the Search has passed, we can test the Add
            // given
            testVerification = false; // reset the variable to false
            boolean queryResult;
            User foundUser;

            User user = new User();
            // only the required parameters are set
            user.setUsername("testUsername");
            user.setPassword("testPassword");
            user.setEmail("testEmail");
            user.setAccessLevel(1);

            // when
            queryResult = userRepo.addUser(user);
            foundUser = userRepo.searchByUsername(user.getUsername()).get(0); // retrieve first result from the database

            // then
            assertThat(queryResult).isTrue(); // check if the query was successful

            // check if the added User is the same as the original
            // IDs needs to be removed from the toString() as the original doesn't have an ID
            assertThat(user.toString().replaceFirst("(\\d+)", "")).
                    isEqualTo(foundUser.toString().replaceFirst("(\\d+)", ""));

            testVerification = true; // this will only be reached if the assert is successful
        }

        @Test
        @Order(3)
        @DisplayName("deleteUser()")
        public void userRepoDeleteUserTest() throws Exception
        {
            // only run if the previous test was successful
            if (!testVerification)
            {
                // throw a more descriptive exception message
                assertThat("User Repo - Add User By Username Test to be ").isEqualTo("Successful");
            }

            // If the Add has passed, we can delete the result
            User user = userRepo.searchByUsername("testUsername").get(0); // get the added user
            List<User> userList;
            boolean queryResult;

            // when
           queryResult = userRepo.deleteUser(user.getId());
            userList = userRepo.searchByUsername("testUsername"); // search for the now-deleted user still exists in the database

            // then
            assertThat(queryResult).isTrue(); // check if the query was successful
            assertThat(userList).isEmpty(); // validate if the user wa not found (because of being deleted)
        }

        @Test
        @DisplayName("fetchAll()")
        public void userRepoFetchAllTest() throws Exception
        {
            List<User> userList = userRepo.fetchAll();
            assertThat(userList).isNotEmpty();
        }

        @Test
        @DisplayName("fetchById()")
        public void userRepoFetchByIdTest() throws Exception
        {
            // this test assumes that there is an entry with id = 1
            int id = 1;
            User user = userRepo.fetchById(id);
            assertThat(user).isNotNull();
        }
    }
    //endregion

    //region User Service Tests
    @Autowired
    UserService userService;

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class UserServiceTest
    {
        // In the User's case, the Service logic only contains Repository calls
        // Therefore, not all Service layer methods are tested

        @Test
        public void userServiceLoadsTest()
        {
            assertThat(userService).isNotNull();
        }
    }

}
