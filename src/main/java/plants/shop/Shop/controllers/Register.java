package plants.shop.Shop.controllers;
import org.springframework.web.bind.annotation.RequestParam;
import plants.shop.Shop.models.Role;
import plants.shop.Shop.repo.userRepository;
import plants.shop.Shop.models.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class Register {
    @Autowired
    private userRepository userRepo;
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String addUser(user user, Map<String, Object> model){
        user userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null){
            model.put("message", "Пользователь с такими данными уже существует!");
            return "register";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return "redirect:/login";
    }
}
