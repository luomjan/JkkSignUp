package kevat25.example.signup.model;

import jakarta.persistence.*;

@Entity
public class Attend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "dog_id", nullable = false)
    private Dog dog;
    
    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false )
    private Exercise exercise;


    
    public Attend() {
    }


    public Attend(Trainer trainer, Dog dog, Exercise exercise) {
        this.trainer = trainer;
        this.dog = dog;
        this.exercise = exercise;
    }


    public Attend(Dog dog, Exercise exercise) {
        this.dog = dog;
        this.exercise = exercise;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Dog getDog() {
        return dog;
    }


    public void setDog(Dog dog) {
        this.dog = dog;
    }


    public Exercise getExercise() {
        return exercise;
    }


    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }


    public Trainer getTrainer() {
        return trainer;
    }


    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }


    @Override
    public String toString() {
        return "Attend [id=" + id + ", trainer=" + trainer + ", dog=" + dog + ", exercise=" + exercise + "]";
    }

    

}
