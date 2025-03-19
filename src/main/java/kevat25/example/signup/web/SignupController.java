package kevat25.example.signup.web;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kevat25.example.signup.model.Attend;
import kevat25.example.signup.model.AttendRepository;
import kevat25.example.signup.model.Breed;
import kevat25.example.signup.model.BreedRepository;
import kevat25.example.signup.model.Dog;
import kevat25.example.signup.model.DogRepository;
import kevat25.example.signup.model.Exercise;
import kevat25.example.signup.model.ExerciseRepository;
import kevat25.example.signup.model.Genre;
import kevat25.example.signup.model.GenreRepository;
import kevat25.example.signup.model.Trainer;
import kevat25.example.signup.model.TrainerRepository;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignupController {

    @Autowired
    private TrainerRepository tRepository;

    @Autowired
    private ExerciseRepository eRepository;

    @Autowired
    private DogRepository dRepository;

    @Autowired
    private AttendRepository aRepository;

    @Autowired
    private GenreRepository gRepository;

    @Autowired
    private BreedRepository bRepository;

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = { "/", "/main" })
    public String showExercises(Model model) {
        model.addAttribute("exercises", eRepository.findAll());
        return "main";
    }

    @RequestMapping(value = "/trainers")
    public String showAllTrainers(Model model) {
        model.addAttribute("trainers", tRepository.findAll());
        return "trainers";
    }

    @RequestMapping(value = "/addexercise")
    public String addExercise(Model model) {
        model.addAttribute("exercise", new Exercise());
        model.addAttribute("genres", gRepository.findAll());
        return "addexercise";
    }

    @PostMapping("/save")
    public String saveExercise(@ModelAttribute("exercise") Exercise exercise, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("exercise", exercise);
            model.addAttribute("genres", gRepository.findAll());
            return "addexercise";
        }

        eRepository.save(exercise);
        return "redirect:/main";
    }

    @RequestMapping(value = "/addtrainer")
    public String addTrainerWithDog(Model model) {
        model.addAttribute("trainer", new Trainer());
        model.addAttribute("dog", new Dog());
        model.addAttribute("breeds", bRepository.findAll());
        return "addtrainer";
    }

    @PostMapping("/savetrainer")
    public String saveTrainerAndDog(@ModelAttribute Trainer trainer,
            @ModelAttribute Dog dog,
            @RequestParam Long dogBreedId) {

        trainer.setRole("USER");
        tRepository.save(trainer);

        Breed breed = bRepository.findById(dogBreedId).orElse(null);
        dog.setBreed(breed);
        dog.setTrainer(trainer);

        dRepository.save(dog);

        return "redirect:/main";
    }

    @RequestMapping(value = "/signin/{id}")
    public String signinForm(@PathVariable("id") Long exerciseId, Model model) {
        Exercise exercise = eRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        model.addAttribute("exercise", exercise);

        List<Dog> dogs = new ArrayList<>();
        dRepository.findAll().forEach(dogs::add);
        dogs.sort(Comparator.comparing(d -> d.getName().toLowerCase()));
        model.addAttribute("dogs", dogs);

        return "signin";
    }

    @PostMapping("/saveSignup")
    public String saveSignup(@RequestParam Long exerciseId,
            @RequestParam Long dogId,
            RedirectAttributes redirectAttributes) {

        Exercise exercise = eRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        Dog dog = dRepository.findById(dogId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));

        Attend attend = new Attend(dog, exercise);
        aRepository.save(attend);

        redirectAttributes.addFlashAttribute("message", "Ilmoittautuminen lisätty!");
        return "redirect:/main";
    }

    @RequestMapping(value = "/participants/{id}")
    public String showParticipants(@PathVariable("id") Long exerciseId, Model model) {
        Exercise exercise = eRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        model.addAttribute("exercise", exercise);

        List<Attend> attendees = aRepository.findByExercise(exercise);
        model.addAttribute("attendees", attendees);

        return "participants";
    }

    @RequestMapping(value = "/edit/{id}")
    public String editExercise(@PathVariable("id") Long exerciseId, Model model) {
        Exercise exercise = eRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        model.addAttribute("exercise", exercise);
        model.addAttribute("genres", gRepository.findAll()); // Oletus: GenreRepository on olemassa
        return "editexercise";
    }

    @RequestMapping(value = "/saveExercise")
    public String saveExercise(@ModelAttribute Exercise exercise, RedirectAttributes redirectAttributes) {
        eRepository.save(exercise);
        redirectAttributes.addFlashAttribute("message", "Exercise updated successfully!");
        return "redirect:/main";
    }

}
