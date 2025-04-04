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
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "breed_id", nullable = false)
    private Breed breed;

    @Column
    private boolean activity;

    public Dog() {
    }

    public Dog(String name, String kennelName, Trainer trainer, Breed breed) {
        this.name = name;
        this.kennelName = kennelName;
        this.trainer = trainer;
        this.breed = breed;
        this.activity = true;
    }

    public Dog(String name, String kennelName) {
        this.name = name;
        this.kennelName = kennelName;
    }

    public Dog(String name, Trainer trainer, Breed breed) {
        this.name = name;
        this.trainer = trainer;
        this.breed = breed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
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

    public Dog(Long id, String name, String kennelName, Trainer trainer, Breed breed) {
        this.id = id;
        this.name = name;
        this.kennelName = kennelName;
        this.trainer = trainer;
        this.breed = breed;
    }

    public boolean getActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Dog [id=" + id + ", name=" + name + ", kennelName=" + kennelName + ", trainer=" + trainer + ", breed="
                + breed + ", activity=" + activity + "]";
    }
}
