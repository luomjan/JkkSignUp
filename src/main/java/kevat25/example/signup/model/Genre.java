package kevat25.example.signup.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;

    private String genre;

    @JsonIgnoreProperties("genre")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "genre")
    private List<Exercise> exercise;

    public Genre() {
    }

    public Genre(String genre) {
        this.genre = genre;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Exercise> getExercise() {
        return exercise;
    }

    public void setExercise(List<Exercise> exercise) {
        this.exercise = exercise;
    }

    @Override
    public String toString() {
        return "Genre [genreId=" + genreId + ", genre=" + genre + ", exercise=" + exercise + "]";
    }

    
    
}
