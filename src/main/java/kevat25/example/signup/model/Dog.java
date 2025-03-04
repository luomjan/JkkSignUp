package kevat25.example.signup.model;

import jakarta.persistence.*;

@Entity
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "kutsumanimi", nullable = false)
    private String name;

    @Column(name = "virallinennimi", nullable = false)
    private String kennelName;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainers trainer;

    @ManyToOne
    @JoinColumn(name = "breed_id", nullable = false)
    private Breed breed;

    public Dog() {
    }

    

    public Dog(String name, String kennelName, Trainers trainer, Breed breed) {
        this.name = name;
        this.kennelName = kennelName;
        this.trainer = trainer;
        this.breed = breed;
    }



    public Dog(String name, Trainers trainer, Breed breed) {
        this.name = name;
        this.trainer = trainer;
        this.breed = breed;
    }

    public Long getDogId() {
        return id;
    }

    public void setDogId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trainers getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainers trainer) {
        this.trainer = trainer;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }



    public String getKennelName() {
        return kennelName;
    }



    public void setKennelName(String kennelName) {
        this.kennelName = kennelName;
    }



    public Dog(Long id, String name, String kennelName, Trainers trainer, Breed breed) {
        this.id = id;
        this.name = name;
        this.kennelName = kennelName;
        this.trainer = trainer;
        this.breed = breed;
    }

    
}
