package pl.arkadiusz.SpringBootCompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.arkadiusz.SpringBootCompany.model.Address;
import pl.arkadiusz.SpringBootCompany.model.Role;
import pl.arkadiusz.SpringBootCompany.model.User;
import pl.arkadiusz.SpringBootCompany.model.Users_roles;
import pl.arkadiusz.SpringBootCompany.repository.AddressRepository;
import pl.arkadiusz.SpringBootCompany.repository.UserRepository;
import pl.arkadiusz.SpringBootCompany.repository.Users_RolesRepository;
import pl.arkadiusz.SpringBootCompany.security.MyUserDetails;
import pl.arkadiusz.SpringBootCompany.service.AddressService;
import pl.arkadiusz.SpringBootCompany.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    long adr_id;
    long u_id;

    @Autowired
    private final AddressRepository addressRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final Users_RolesRepository users_rolesRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;


    @RequestMapping("/check_functions")
    public void checkFunctions(){
    }

    // strony
    @RequestMapping("/")
    public String getHomePage() {
        return "homepage";
    }

    @RequestMapping("/HomePage")
    public String getHomepage() {
        return "homepage";
    }


    @RequestMapping("/myProfilePage")
    public String myProfile(Model model_user, Model model_address) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userService.findbyLogin(login);
        model_user.addAttribute("user", user);

        Address address = addressService.findAddressById(user.getAddress_id());
        model_address.addAttribute("address", address);
        return "user_profile";
    }

    @RequestMapping("/changePasswordPage")
    public String changePassword(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "changepassword";
    }

    @RequestMapping("/showUsers")
    public String users_list(ModelMap modelMap) {
        modelMap.put("users", userService.getUsers());
        return "users_list";
    }

    @PostMapping("/changePassword")
    public String savePassword(@ModelAttribute(value="user") User user, Model hint1, Model hint2, Model hint3) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String login = authentication.getName();
        String cur_pass = user.getPassword();
        String new_pass = user.getNew_password();
        String new_pass2 = user.getConfirm_password();

        if(encoder.matches(cur_pass, userRepository.findByLogin(login).getPassword())) {
            if (new_pass.equals(new_pass2)) {
                userRepository.changePassword(login, new BCryptPasswordEncoder().encode(new_pass));
                return "redirect:/changePasswordPage";
            } else {
                hint2.addAttribute("hint2", "Different passwords");
                hint3.addAttribute("hint3", "Different passwords");
                return "changepassword";
            }
        }
        else {
            hint1.addAttribute("hint1", "Wrong current password");
            return "changepassword";
        }
    }


    @RequestMapping("/admin/registeruserPage")
    public String registerUser1(Model model, Model model2, Model model3) {
        List<String> options = new ArrayList<>();
        options.add("USER");
        options.add("ADMIN");
        model.addAttribute("options", options);

        Address address = new Address();
        User user = new User();
        model2.addAttribute("address", address);
        model3.addAttribute("user", user);
        return "registeruser";
    }

    @RequestMapping("/admin/deleteuserPage")
    public String deleteUserPage(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "deleteuser";
    }

    @PostMapping("/admin/registerUser")
    public String registerUser(@ModelAttribute(value="address") Address address,
                               @ModelAttribute(value="user") User user,
                               @ModelAttribute("options") String option,
                               Model hint_fields, Model hint_login, Model success) {
        boolean right_login = true;
        boolean right_fields = true;
        List<User> users = userService.getUsers();
        for(User x:users) {
            if(x.getLogin().equals(user.getLogin())){
                hint_login.addAttribute("hint_login", "User with this login already exist!");
                right_login = false;
                break;
            }
            else{}
        }
        if(user.getName().equals("") || user.getSurname().equals("") || user.getLogin().equals("") || user.getPassword().equals("") ||
           address.getCity().equals("") || address.getState().equals("") || address.getCity().equals("") || address.getHouse_no().equals("")) {
            hint_fields.addAttribute("hint_fields", "Fill all required fields!");
            right_fields = false;
        }

        if(right_login && right_fields) {

            adr_id = addressRepository.save(address).getId();

            User user1 = User.builder()
                    .name(user.getName())
                    .surname(user.getSurname())
                    .address_id(adr_id)
                    .login(user.getLogin())
                    .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                    .enabled(true)
                    .build();
            userRepository.save(user1);
            u_id = userRepository.save(user1).getUser_id();

            Users_roles users_roles = new Users_roles();
            users_roles.setUser_id(u_id);
            if (option.equals("USER")) {
                users_roles.setRole_id(2);
            } else {
                users_roles.setRole_id(1);
            }
            users_rolesRepository.save(users_roles);
            success.addAttribute("success", "User successfully registered");
            return "registeruser";
        }
        else {
            return "registeruser";
        }

    }


    @RequestMapping("/deleteUser")
    public String deleteUser(Model model, @ModelAttribute("options") String option) {
        String[] parts = option.split(",");
        String str1 = parts[0];
        String[] parts2 = str1.split(": ");
        String login = parts2[1];
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(login.equals(authentication.getName())){
            model.addAttribute("users", userService.getUsers());
            model.addAttribute("hint", "You can not delete your account!");
            return "deleteuser";
        }
        else {
            User user = userRepository.findByLogin(login);
            addressRepository.deleteById(user.getAddress_id());
            userRepository.deleteUser(login);
            return "redirect:/admin/deleteuserPage";
        }

    }

}
