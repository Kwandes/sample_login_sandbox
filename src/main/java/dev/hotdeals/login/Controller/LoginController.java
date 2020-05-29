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

import javax.servlet.http.HttpServletRequest;

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
    public String login(HttpServletRequest request)
    {
        // reset the session
        request.getSession().invalidate();

        return "login/login";   // page to be opened when this mapping is called
    }

    @Autowired
    UserService userService;

    @PostMapping("/submitLogin") // url mappings that this method handles, POST method only
    public String submitLogin(WebRequest wr, Model model, HttpServletRequest request)
    {
        User user = userService.searchByUsername(wr.getParameter("username"));
        String password = wr.getParameter("password");
        if (user == null)
        {
            System.out.println("Failed login - Username '" + wr.getParameter("username") + "' not found");
            return "redirect:/login";
        }
        else if (!userService.checkPasswordMatch(password, user.getPassword()) )
        {
            System.out.println("Failed login - User '" + wr.getParameter("username") + "' has inputted wrong password");
            return "redirect:/login";
        }

        // start the session
        request.getSession().setAttribute("username", user.getUsername());
        request.getSession().setAttribute("email", user.getEmail());
        request.getSession().setAttribute("accessLevel", user.getAccessLevel());
        return "redirect:/successPage";
    }

    @GetMapping("/createNewAccount")
    public String createNewAccount()
    {
        return "login/createAccount";
    }

    @PostMapping("/submitNewAccount")
    public String submitNewAccount(WebRequest wr)
    {
        User user = userService.searchByUsername(wr.getParameter("username"));
        String password = wr.getParameter("password");
        if (user != null)
        {
            System.out.println("User " + user.getUsername() + " already exists");
            return "redirect:/login";
        }
        user = new User();
        user.setUsername(wr.getParameter("username"));
        user.setPassword(userService.hashPassword(wr.getParameter("password")));
        user.setEmail(wr.getParameter("email"));
        System.out.println("Adding new user (" + user + ")");
        userService.addUser(user);

        return "login/login";
    }

    @GetMapping("/successPage")
    public String successPage(HttpServletRequest request, Model model)
    {
        // check if given user has access to this page
        if (request.getSession().getAttribute("accessLevel") == null)
        {
            System.out.println("Access Denied to user [" + request.getSession().getAttribute("username") + "] due to null access Level");
            return "redirect:/login";
        }

        // log successful login
        System.out.println("New login as " + request.getSession().getAttribute("username"));
        model.addAttribute("username", request.getSession().getAttribute("username"));
        model.addAttribute("email", request.getSession().getAttribute("email"));
        model.addAttribute("accessLevel", request.getSession().getAttribute("accessLevel"));

        return "login/successPage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request)
    {
        // end the session
        System.out.println(request.getSession().getAttribute("username") + " has logged out");
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
