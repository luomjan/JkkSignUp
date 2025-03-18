package kevat25.example.signup.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Optional<Genre> findByGenre(String genre);

}
