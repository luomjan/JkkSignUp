package kevat25.example.signup.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface BreedRepository extends CrudRepository<Breed, Long> {

    Optional<Breed> findByBreed(String breed);

}
