package kevat25.example.signup.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    List<Exercise> findByGenreGenre(String genre);
}
