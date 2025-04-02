package kevat25.example.signup.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Trainer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "etunimi", nullable = false)
    private String firstName;

    @Column(name = "sukunimi", nullable = false)
    private String lastName;
    

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dog> dogs;

    @Column
    private boolean activity;

    public Trainer() {
    }

    
    

    public Trainer(String userName, String password, String role, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activity = true;
    }




    public String getUserName() {
        return userName;
    }




    public void setUserName(String userName) {
        this.userName = userName;
    }




    public String getPassword() {
        return password;
    }




    public void setPassword(String password) {
        this.password = password;
    }




    public String getRole() {
        return role;
    }




    public void setRole(String role) {
        this.role = role;
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




    public Long getId() {
        return id;
    }




    public void setId(Long id) {
        this.id = id;
    }




    public boolean isActivity() {
        return activity;
    }




    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    


    
}
