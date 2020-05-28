/*
    Validate if different controllers load properly
 */

package dev.hotdeals.login;

import dev.hotdeals.login.Controller.LoginController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LoginApplicationTests
{
    @Autowired
    LoginController loginController;

    @Test
    @DisplayName("Login Controller load validation")
    void loginControllerLoadsTest()
    {
        // validate that the controller has loaded
        assertThat(loginController).isNotNull();
    }

}
