package kevat25.example.signup;

import static org.assertj.core.api.Assertions.assertThat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import kevat25.example.signup.model.Attend;
import kevat25.example.signup.model.AttendRepository;
import kevat25.example.signup.model.BreedRepository;
import kevat25.example.signup.model.DogRepository;
import kevat25.example.signup.model.ExerciseRepository;
import kevat25.example.signup.model.GenreRepository;
import kevat25.example.signup.model.Trainer;
import kevat25.example.signup.model.TrainerRepository;

import org.junit.jupiter.api.Test;

@DataJpaTest // if you are using in-memory database, like H2
// @SpringBootTest(classes = SignupApplication.class)
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// //if you are using real db
@Transactional
public class SignupRepositoryTests {

    @Autowired
    private AttendRepository aRepository;

    @Autowired
    private BreedRepository bRepository;

    @Autowired
    private DogRepository dRepository;

    @Autowired
    private ExerciseRepository eRepository;

    @Autowired
    private GenreRepository gRepository;

    @Autowired
    private TrainerRepository tRepository;

    @Test
    public void createNewTrainer() {
        Trainer trainer = new Trainer("masa", "masa", "USER", "masa", "masa");
        tRepository.save(trainer);
        assertThat(trainer.getId()).isNotNull();
    }

    @Test
    public void findTrainerByUsername() {
        Trainer trainer = new Trainer("kaisa", "testi123", "USER", "Kaisa", "Kaisanen");
        tRepository.save(trainer);

        Trainer foundTrainer = tRepository.findByUserName("kaisa");

        assertThat(foundTrainer).isNotNull();
        assertThat(foundTrainer.getUserName()).isEqualTo("kaisa");
    }

    @Test
    public void updateTrainerPassword() {
        Trainer trainer = new Trainer("muokattava", "salasana", "USER", "Matti", "Meikäläinen");
        trainer = tRepository.save(trainer);

        trainer.setPassword("uusiSalasana");
        tRepository.save(trainer);

        Trainer updatedTrainer = tRepository.findByUserName("muokattava");

        assertThat(updatedTrainer).isNotNull();
        assertThat(updatedTrainer.getPassword()).isEqualTo("uusiSalasana");
    }

    @Test
    public void deleteTrainer() {
        Trainer trainer = new Trainer("poistettava", "salasana", "USER", "Pekka", "Poistettava");
        trainer = tRepository.save(trainer);

        tRepository.delete(trainer);

        Trainer deletedTrainer = tRepository.findByUserName("poistettava");

        assertThat(deletedTrainer).isNull();
    }

}
