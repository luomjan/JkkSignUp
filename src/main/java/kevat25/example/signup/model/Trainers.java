package kevat25.example.signup.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Trainers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "etunimi", nullable = false, unique = true)
    private String firstName;

    @Column(name = "sukunimi", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dog> dogs;

    public Trainers() {
    }

    public Trainers(String firstName, String lastName, List<Dog> dogs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dogs = dogs;
    }

    public Long getTrainerId() {
        return id;
    }

    public void setTrainerId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }

    


    
}
