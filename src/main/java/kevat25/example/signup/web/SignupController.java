package kevat25.example.signup.web;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kevat25.example.signup.model.TrainerRepository;


@Controller
public class SignupController {

	@Autowired
	private TrainerRepository tRepository;

	@RequestMapping(value = "/login")
    public String login() {
        return "login";
    }
	
	@RequestMapping(value = { "/", "/trainers" })
    public String showTrainers(Model model) {
        model.addAttribute("trainers", tRepository.findAll());
        return "trainers";
    }

   
   
    
}
