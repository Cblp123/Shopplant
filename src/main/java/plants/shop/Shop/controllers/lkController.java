package plants.shop.Shop.controllers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import plants.shop.Shop.models.plant;
import plants.shop.Shop.models.user;
import plants.shop.Shop.repo.plantRepository;
import plants.shop.Shop.repo.userRepository;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Controller
public class lkController {

    @Autowired
    private userRepository userRep;
    @Autowired
    private plantRepository plantRep;

    @GetMapping("/lk")
    public String getInfo(Model model) {
        model.addAttribute("title", "Plant store");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        user thisuser = userRep.findByUsername(username);
        Long id = thisuser.getId();
        Iterable<plant> plants = plantRep.findAll();

        List<plant> userPlants = new ArrayList<>();
        for (plant p : plants)
            if (p.getIdS() == id)
                userPlants.add(p);
        model.addAttribute("userPlants", userPlants);

        model.addAttribute("plants", plants);
        model.addAttribute("user", thisuser);
        model.addAttribute("id", id);
        return "lk";
    }
    @PostMapping("/lk")
    public String postInfo(@RequestParam String Title, @RequestParam String price, @RequestParam String photoURL, @RequestParam String description, @RequestParam String contact, Model model) {

        if (Title.isEmpty() || price.isEmpty() || contact.isEmpty()) {
            model.addAttribute("message", "Заполните все обязательные поля");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            user thisuser = userRep.findByUsername(username);
            model.addAttribute("user", thisuser);
            return "lk";
        } else {
            if (description.isEmpty()) {
                description = "Описание отсутствует";
            }
            if (photoURL.isEmpty()) {
                photoURL = "https://i.imgur.com/giueGKY.png";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            user thisuser = userRep.findByUsername(username);
            Long id = thisuser.getId();
            plant plant = new plant(id, Title, description, photoURL, username, price, contact);
            plantRep.save(plant);
            Iterable<plant> plants = plantRep.findAll();
            List<plant> userPlants = new ArrayList<>();
            for (plant p : plants)
                if (p.getIdS() == id)
                    userPlants.add(p);
            model.addAttribute("userPlants", userPlants);
            model.addAttribute("plant", plants);
            model.addAttribute("id", id);
            model.addAttribute("message", "Товар успешно добавлен");
            model.addAttribute("user", thisuser);
            return "lk2";
        }
    }

    @GetMapping("/lk/{id}/edit")
    public String editInfo(@PathVariable(value = "id") Long id, Model model) {
        if(!plantRep.existsById(id)){
            return "redirect:/";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserUsername = authentication.getName();
        plant plant2 = plantRep.findById(id).orElseThrow();

        if (currentUserUsername.equals(plant2.getSellerName())) {
            Optional<plant> plant = plantRep.findById(id);
            ArrayList<plant> res = new ArrayList<>();
            plant.ifPresent(res::add);
            model.addAttribute("plant", res);
            return "plant-edit";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/lk/{id}/edit")
    public String editInfoP(@PathVariable(value = "id") Long id, @RequestParam String Title, @RequestParam String price, @RequestParam String photoURL, @RequestParam String description, @RequestParam String contact, Model model) {
        if (Title.isEmpty() || price.isEmpty() || contact.isEmpty()) {
            return "redirect:/lk/{id}/edit";
        }
        if (description.isEmpty()) {
            description = "Описание отсутствует";
        }
        if (photoURL.isEmpty()) {
            photoURL = "https://i.imgur.com/giueGKY.png";
        }
        plant plant = plantRep.findById(id).orElseThrow();
        plant.setTitle(Title);
        plant.setPrice(price);
        plant.setPhotoUrl(photoURL);
        plant.setDescription(description);
        plant.setContact(contact);
        plantRep.save(plant);
        return "redirect:/lk";
    }

    @PostMapping("/lk/{id}/remove")
    public String delInfo(@PathVariable(value = "id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserUsername = authentication.getName();
        plant plant = plantRep.findById(id).orElseThrow();

        if (currentUserUsername.equals(plant.getSellerName())) {
            plantRep.delete(plant);
            return "redirect:/lk";
        } else {
            return "redirect:/";
        }
    }
}
