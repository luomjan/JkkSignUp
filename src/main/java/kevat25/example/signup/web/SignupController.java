package kevat25.example.signup.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import kevat25.example.signup.model.Genre;
import kevat25.example.signup.model.Trainers;



@SpringBootApplication
public class SignupController {

    private static final Logger log = LoggerFactory.getLogger(SignupController.class);

    @Bean
	public CommandLineRunner demo(TrainersRepository trepository, ExerciseRepository erepository, GenreRepository grepository) {
		return (args) -> {
			log.info("save a couple of students");
			

		};
	}
    
}
