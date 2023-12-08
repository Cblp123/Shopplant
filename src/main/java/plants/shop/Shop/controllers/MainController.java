package plants.shop.Shop.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import plants.shop.Shop.models.plant;
import org.springframework.security.core.Authentication;
import plants.shop.Shop.models.user;
import plants.shop.Shop.repo.plantRepository;
import plants.shop.Shop.repo.userRepository;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Optional;


@Controller
public class MainController {

    @Autowired
    private plantRepository plantRep;

    @Autowired
    private userRepository userRep;

    @GetMapping("")
    public String mainpage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Пользователь аутентифицирован
            model.addAttribute("title", "Plant store");
            Iterable<plant> plants = plantRep.findAll();
            model.addAttribute("plants", plants);
            String username = authentication.getName();
            user thisuser = userRep.findByUsername(username);
            model.addAttribute("user", thisuser);
            return "mainpage1";
        } else {
            // Пользователь не аутентифицирован
            model.addAttribute("title", "Plant store");
            Iterable<plant> plants = plantRep.findAll();
            model.addAttribute("plants", plants);
            return "mainpage2";
        }
    }

    @GetMapping("/plant/{id}")
    public String plantpage(@PathVariable(value = "id") Long id, Model model, Authentication authentication) {
        if(!plantRep.existsById(id)){
            return "redirect:/";
        }
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<plant> plant = plantRep.findById(id);
            ArrayList<plant> res = new ArrayList<>();
            plant.ifPresent(res::add);
            model.addAttribute("plant", res);
            String username = authentication.getName();
            user thisuser = userRep.findByUsername(username);
            model.addAttribute("user", thisuser);
            return "plant-infoAUTH";
        } else {
            Optional<plant> plant = plantRep.findById(id);
            ArrayList<plant> res = new ArrayList<>();
            plant.ifPresent(res::add);
            model.addAttribute("plant", res);
            return "plant-infoNO";
        }
    }

}




