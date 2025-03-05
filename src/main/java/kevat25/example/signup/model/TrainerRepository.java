package kevat25.example.signup.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface TrainerRepository extends CrudRepository<Trainer, Long> {

    Optional<Trainer> findByFirstNameAndLastName(String firstName, String lastName);

    Trainer findByUserName(String userName);

}
