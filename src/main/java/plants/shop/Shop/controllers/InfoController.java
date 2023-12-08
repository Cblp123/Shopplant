package plants.shop.Shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import plants.shop.Shop.models.plant;
import plants.shop.Shop.repo.plantRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/info")
public class InfoController {
    @GetMapping
    public String getInfo(Model model) {
        model.addAttribute("title", "Plant store");
        return "inf";
    }
}
