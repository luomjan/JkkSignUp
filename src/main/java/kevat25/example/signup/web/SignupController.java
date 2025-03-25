package kevat25.example.signup.web;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import kevat25.example.signup.model.Attend;
import kevat25.example.signup.model.AttendRepository;
import kevat25.example.signup.model.Breed;
import kevat25.example.signup.model.BreedRepository;
import kevat25.example.signup.model.Dog;
import kevat25.example.signup.model.DogRepository;
import kevat25.example.signup.model.Exercise;
import kevat25.example.signup.model.ExerciseRepository;
import kevat25.example.signup.model.GenreRepository;
import kevat25.example.signup.model.Trainer;
import kevat25.example.signup.model.TrainerRepository;

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

    @RequestMapping(value = "/addexercise")
    public String addExercise(Model model) {
        model.addAttribute("exercise", new Exercise());
        model.addAttribute("genres", gRepository.findAll());
        return "addexercise";
    }

    @PostMapping("/save")
    public String saveExercise(@Valid @ModelAttribute("exercise") Exercise exercise, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("exercise", exercise);
            model.addAttribute("genres", gRepository.findAll());
            return "addexercise";
        }

        eRepository.save(exercise);
        return "redirect:/main";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/edit/{id}")
    public String editExercise(@PathVariable("id") Long exerciseId, Model model) {
        Exercise exercise = eRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        model.addAttribute("exercise", exercise);
        model.addAttribute("genres", gRepository.findAll()); // Oletus: GenreRepository on olemassa
        return "editexercise";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteExercise(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Exercise exercise = eRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        long attendCount = aRepository.countByExercise(exercise);

        if (attendCount > 0) {
            redirectAttributes.addFlashAttribute("error", "Harjoituksessa on osallistujia, joten sitä ei voi poistaa.");
            return "redirect:/main";
        }

        eRepository.delete(exercise);
        return "redirect:/main";
    }

    @RequestMapping(value = "/trainers")
    public String showAllTrainers(Model model) {
        model.addAttribute("trainers", tRepository.findAll());
        return "trainers";
    }

    @RequestMapping(value = "/addtrainer")
    public String addTrainerWithDog(Model model) {
        model.addAttribute("trainer", new Trainer());
        model.addAttribute("dog", new Dog());
        model.addAttribute("breeds", bRepository.findAll());
        return "addtrainer";
    }

    @PostMapping("/savetrainer")
    public String saveTrainerAndDogs(@ModelAttribute Trainer trainer) {
        trainer.setRole("USER");

        if (trainer.getDogs() != null) {

            trainer.getDogs().removeIf(dog -> dog.getName() == null || dog.getName().isEmpty());

            for (Dog dog : trainer.getDogs()) {
                if (dog.getBreed() != null && dog.getBreed().getBreedId() != null) {

                    Breed breed = bRepository.findById(dog.getBreed().getBreedId()).orElse(null);
                    if (breed != null) {
                        dog.setBreed(breed);
                    }
                }
                dog.setTrainer(trainer);
            }
        }

        tRepository.save(trainer);

        return "redirect:/main";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/deletetrainer/{id}")
    public String deleteTrainer(@PathVariable Long id) {

        Trainer trainer = tRepository.findById(id).orElse(null);

        if (trainer != null) {

            for (Dog dog : trainer.getDogs()) {
                dRepository.delete(dog);
            }

            tRepository.delete(trainer);
        }

        return "redirect:/trainers";
    }

    @GetMapping("/edittrainer/{id}")
    public String editTrainer(@PathVariable Long id, Model model) {
        Trainer trainer = tRepository.findById(id).orElse(null);
        if (trainer == null) {
            return "redirect:/trainers";
        }
        model.addAttribute("trainer", trainer);
        return "edittrainer";
    }

    @PostMapping("/saveonlytrainer")
    public String editedTrainerOnly(@ModelAttribute("trainer") Trainer trainer, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("trainer", trainer);
            return "addtrainer";
        }

        Trainer existingTrainer = tRepository.findById(trainer.getId())
                .orElseThrow(() -> new RuntimeException("Treenaaja ei löytynyt"));

        existingTrainer.setFirstName(trainer.getFirstName());
        existingTrainer.setLastName(trainer.getLastName());
        existingTrainer.setRole(trainer.getRole());
        existingTrainer.setPassword(trainer.getPassword());
        existingTrainer.setUserName(trainer.getUserName());

        tRepository.save(existingTrainer);

        return "redirect:/trainers";
    }

    @RequestMapping("/adddog/{trainerId}")
    public String addDog(@PathVariable Long trainerId, Model model) {
        model.addAttribute("dog", new Dog());
        model.addAttribute("breeds", bRepository.findAll());
        return "adddog";
    }

    @PostMapping("/adddog/{trainerId}")
    public String saveDog(@PathVariable Long trainerId, @ModelAttribute Dog dog) {

        Trainer trainer = tRepository.findById(trainerId).orElse(null);
        if (trainer == null) {
            return "redirect:/trainers";
        }

        dog.setTrainer(trainer);

        dRepository.save(dog);

        return "redirect:/trainers";
    }

    @GetMapping("/editdog/{id}")
    public String showEditDogForm(@PathVariable Long id, Model model) {
        Dog dog = dRepository.findById(id).orElse(null);

        model.addAttribute("dog", dog);
        model.addAttribute("breeds", bRepository.findAll());

        return "editdog";

    }

    @PostMapping("/savedog")
    public String saveEditedDog(@ModelAttribute Dog dog) {

        Dog existingDog = dRepository.findById(dog.getId()).orElse(null);

        if (existingDog != null) {
            existingDog.setName(dog.getName());
            existingDog.setKennelName(dog.getKennelName());
            existingDog.setBreed(dog.getBreed());

            dRepository.save(existingDog);
        }

        return "redirect:/trainers";
    }

    @PostMapping("/deletedog/{id}")
    public String deleteDog(@PathVariable Long id) {

        Dog dog = dRepository.findById(id).orElse(null);

        if (dog != null) {
            dRepository.delete(dog);
        }

        return "redirect:/trainers";
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

}
