/*
    Handles all mappings regarding the login and session functionality
 */

package dev.hotdeals.login.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/submitLogin") // url mappings that this method handles, POST method only
    public String submitLogin()
    {
        // validate login credentials
        // if true, start new session
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
