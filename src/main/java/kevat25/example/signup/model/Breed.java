package kevat25.example.signup.model;

import jakarta.persistence.*;

@Entity
public class Breed {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rotu", nullable = false, unique = true)
    private String breed;

    public Breed() {
    }

    public Breed(String breed) {
        this.breed = breed;
    }

    public Long getBreedId() {
        return id;
    }

    public void setBreedId(Long id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public String toString() {
        return "Breed [id=" + id + ", breed=" + breed + "]";
    }

    


}
