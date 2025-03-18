package kevat25.example.signup.web;

import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kevat25.example.signup.model.Attend;
import kevat25.example.signup.model.AttendRepository;
import kevat25.example.signup.model.Dog;
import kevat25.example.signup.model.DogRepository;
import kevat25.example.signup.model.Exercise;
import kevat25.example.signup.model.ExerciseRepository;
import kevat25.example.signup.model.Genre;
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

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = { "/", "/main" })
    public String showExercises(Model model) {
        model.addAttribute("exercises", eRepository.findAll());
        return "main";
    }

    @RequestMapping(value = "/signin/{id}")
    public String signinForm(@PathVariable("id") Long exerciseId, Model model) {
        Exercise exercise = eRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        model.addAttribute("exercise", exercise);

        List<Trainer> trainers = new ArrayList<>();
        tRepository.findAll().forEach(trainers::add);
        model.addAttribute("trainers", trainers);

        List<Dog> dogs = new ArrayList<>();
        dRepository.findAll().forEach(dogs::add);
        model.addAttribute("dogs", dogs);

        return "signin";
    }

    @PostMapping("/saveSignup")
    public String saveSignup(@RequestParam Long exerciseId,
            @RequestParam Long trainerId,
            @RequestParam Long dogId,
            RedirectAttributes redirectAttributes) {

        Exercise exercise = eRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        Trainer trainer = tRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
        Dog dog = dRepository.findById(dogId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));

        Attend attend = new Attend(trainer, dog, exercise);
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

        // Lisää genre-lista dropdownia varten
        model.addAttribute("genres", gRepository.findAll());

        return "editexercise";
    }

    @PostMapping("/updateExercise")
    public String updateExercise(@RequestParam Long id,
            @RequestParam Long genreId,
            @RequestParam String place,
            @RequestParam String description,
            @RequestParam LocalDate exerciseDay,
            @RequestParam LocalTime exerciseTime,
            RedirectAttributes redirectAttributes) {

        Exercise exercise = eRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        Genre genre = gRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        exercise.setGenre(genre);
        exercise.setPlace(place);
        exercise.setDescription(description);
        exercise.setExerciseDay(exerciseDay);
        exercise.setExerciseTime(exerciseTime);

        eRepository.save(exercise);

        redirectAttributes.addFlashAttribute("message", "Harjoitus päivitetty!");
        return "redirect:/main";
    }

}
