package nl.friendshipbench.api.models;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * The Client model extends user
 *
 * @author Nick Oosterhuis
 */
@Entity
public class Client extends User {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private OffsetDateTime birthDay;
    @Column(name = "street_name", nullable = false)
    private String streetName;
    @Column(name = "house_number", nullable = false)
    private String houseNumber;
    @Column(nullable = false)
    private String province;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="bound_health_worker")
    private HealthWorker healthWorker;

    public Client() {

    }

    public Client(String firstName, String lastName, String gender, OffsetDateTime birthDay, String streetName,
                  String houseNumber, String province, String district, String email, String phoneNumber, HealthWorker healthWorker) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDay = birthDay;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.province = province;
        this.district = district;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.healthWorker = healthWorker;
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

    public OffsetDateTime getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(OffsetDateTime birthDay) {
        this.birthDay = birthDay;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHousenumber() {
        return houseNumber;
    }

    public void setHousenumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public HealthWorker getHealthWorker() {
        return healthWorker;
    }

    public void setHealthWorker(HealthWorker healthWorker) {
        this.healthWorker = healthWorker;
    }
}