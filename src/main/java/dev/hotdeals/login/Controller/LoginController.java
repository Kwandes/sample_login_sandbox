/*
    Handles all mappings regarding the login and session functionality
 */

package dev.hotdeals.login.Controller;

import dev.hotdeals.login.Model.User;
import dev.hotdeals.login.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class LoginController
{
    // default mappings
    @GetMapping({"", "/", "/index", "index/"}) // url mappings that this method handles,  GET method only
    public String index()
    {
        return "redirect:/login";   // mapping to be called instead. Works as if the client sent a web request
    }

    @GetMapping("/login")
    public String login()
    {
        return "login/login";   // page to be opened when this mapping is called
    }

    @Autowired
    UserService userService;

    @PostMapping("/submitLogin") // url mappings that this method handles, POST method only
    public String submitLogin(WebRequest wr, Model model)
    {
        User user = userService.searchByUsername(wr.getParameter("username"));
        String password = wr.getParameter("password");
        if (user == null)
        {
            System.out.println("Username not found");
            return "redirect:/login";
        }
        else if (!userService.checkPasswordMatch(password, user.getPassword()) )
        {
            System.out.println("User password is invalid");
            return "redirect:/login";
        }
        System.out.println("Credentials are ok");
        // start new session
        return "redirect:/successPage";
    }

    @GetMapping("/createNewAccount")
    public String createNewAccount()
    {
        return "login/createAccount";
    }

    @PostMapping("/submitNewAccount")
    public String submitNewAccount()
    {
        // add a new account
        return "login/login";
    }

    @GetMapping("/successPage")
    public String successPage()
    {
        return "login/successPage";
    }

    @GetMapping("/logout")
    public String logout()
    {
        // end the session
        return "redirect:/login";
    }
}
