package nl.friendshipbench.api.models;

import javax.persistence.*;

/**
 * The Healthworker model extends user
 *
 * @author Nick Oosterhuis
 */

@Entity
public class HealthWorker extends User {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String gender;
    private int age;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

    public HealthWorker() { }

    public HealthWorker(String firstName, String lastname, String gender,
                  int age, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phoneNumber;
    }

    public void setPhonenumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
