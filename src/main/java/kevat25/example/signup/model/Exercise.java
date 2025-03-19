package kevat25.example.signup.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private String place;
    private String description;

    private LocalDate exerciseDay;
    private LocalTime exerciseTime;

    public Exercise() {
    }

    public Exercise(Genre genre, String place, String description, LocalDate exerciseDay, LocalTime exerciseTime) {
        this.genre = genre;
        this.place = place;
        this.description = description;
        this.exerciseDay = exerciseDay;
        this.exerciseTime = exerciseTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExerciseDay() {
        return exerciseDay;
    }

    public void setExerciseDay(LocalDate exerciseDay) {
        this.exerciseDay = exerciseDay;
    }

    public LocalTime getExerciseTime() {
        return exerciseTime;
    }

    public void setExerciseTime(LocalTime exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    @Override
    public String toString() {
        return "Exercise [id=" + id + ", genreId=" + (genre != null ? genre.getId() : "null") +
                ", place=" + place + ", description=" + description +
                ", day=" + exerciseDay + ", time=" + exerciseTime + "]";
    }

}