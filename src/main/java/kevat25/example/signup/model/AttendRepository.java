package kevat25.example.signup.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AttendRepository extends CrudRepository<Attend, Long> {

    List<Attend> findByExercise(Exercise exercise);
    long countByExercise(Exercise exercise);
    boolean existsByDog(Dog dog);

}
