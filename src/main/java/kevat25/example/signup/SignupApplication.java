package kevat25.example.signup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import kevat25.example.signup.model.Breed;
import kevat25.example.signup.model.BreedRepository;
import kevat25.example.signup.model.Dog;
import kevat25.example.signup.model.DogRepository;
import kevat25.example.signup.model.ExerciseRepository;
import kevat25.example.signup.model.GenreRepository;
import kevat25.example.signup.model.Trainer;
import kevat25.example.signup.model.TrainerRepository;
import kevat25.example.signup.web.SignupController;

@SpringBootApplication
public class SignupApplication {

	private static final Logger log = LoggerFactory.getLogger(SignupController.class);

	public static void main(String[] args) {
		SpringApplication.run(SignupApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(TrainerRepository tRepository, ExerciseRepository eRepository,
			GenreRepository gRepository, DogRepository dRepository, BreedRepository bRepository) {
		return (args) -> {

			log.info("save traines");
			tRepository.save(new Trainer("admin", "$2a$05$s6rx2hOmx9N2bN6yqqxAO.nyDdOQDKHSKAR6wMjBNY2HGAtXiLx5q", "ADMIN", "Admin", "Admin"));
			tRepository.save(new Trainer("user", "$2a$05$EyPQcXjdxcpuv11fIRBRku0qhCdRpm1wjKIw0dkftNA8tT2OHZGQO", "USER", "User", "User"));
			tRepository.save(new Trainer("matti", "$2a$05$2yJeT4zjec0UXqLPaKInEuznKtoFgDKn8r4hYy7hgVpioyAPc7H6q", "USER", "Matti", "Meikäläinen"));
			tRepository.save(new Trainer("maija", "$2a$05$KdR6qs51ApT2cKIiFEEaEuj5boYy3O/nAHRJsFUgtLIQ/DRUwZj2y", "ADMIN", "Maija", "Meikäläinen"));
			tRepository.save(new Trainer("testi", "$2a$05$3RXpqTwujYbGQrOBiQJ1JeoTtPYME/EimhnVZMF00z.DVDaYHdLWi", "USER", "Testi", "Testinen"));

			log.info("save dog breeds");
			bRepository.save(new Breed("Collie"));
			bRepository.save(new Breed("Corgi"));
			bRepository.save(new Breed("Suomenlapinkoira"));
			bRepository.save(new Breed("Labradori"));

			Trainer admin = tRepository.findByFirstNameAndLastName("Admin", "Admin")
					.orElseThrow(() -> new RuntimeException("Trainer not found!"));
			Trainer user = tRepository.findByFirstNameAndLastName("User", "User")
					.orElseThrow(() -> new RuntimeException("Trainer not found!"));		
			Trainer matti = tRepository.findByFirstNameAndLastName("Matti", "Meikäläinen")
					.orElseThrow(() -> new RuntimeException("Trainer not found!"));
			Trainer maija = tRepository.findByFirstNameAndLastName("Maija", "Meikäläinen")
					.orElseThrow(() -> new RuntimeException("Trainer not found!"));
			Trainer testi = tRepository.findByFirstNameAndLastName("Testi", "Testinen")
					.orElseThrow(() -> new RuntimeException("Trainer not found!"));

			Breed corgi = bRepository.findByBreed("Corgi")
					.orElseThrow(() -> new RuntimeException("Breed not found!"));
			Breed labradori = bRepository.findByBreed("Labradori")
					.orElseThrow(() -> new RuntimeException("Breed not found!"));
			Breed suomenlapinkoira = bRepository.findByBreed("Suomenlapinkoira")
					.orElseThrow(() -> new RuntimeException("Breed not found!"));

			log.info("add dogs");
			dRepository.save(new Dog("Rekku", "Rekkuliittus", admin, labradori));
			dRepository.save(new Dog("Musti", "Hänen mustiutensa", user, suomenlapinkoira));
			dRepository.save(new Dog("Fili", "Cloudmoore Go-Grow-Glow", matti, corgi));
			dRepository.save(new Dog("Freia", "Cloudmoore Hottest Fire Fox", matti, corgi));
			dRepository.save(new Dog("Pomo", "", maija, labradori));
			dRepository.save(new Dog("Riitu", "Kultasilmä Riitu", testi, suomenlapinkoira));

		};
	}

}
