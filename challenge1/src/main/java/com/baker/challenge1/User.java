package com.baker.challenge1;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity  // This is a Java Persistence API annotation
public class User {

    // @Id marks this primary, and @GeneratedValue marks it to be auto-generated
    private @Id @GeneratedValue Long id;
    private String name;
    private Integer age;
    private String address1;
    private String address2;

    // Prevent this object from being default-constructed
    private User() {}

    // This constructor is for creating with specific IDs
    public User(Long id, String name, Integer age, String address1, String address2) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address1 = address1;
        this.address2 = address2;
    }

    // This constructor will auto-generate an ID
    public User(String name, Integer age, String address1, String address2) {
        this.name = name;
        this.age = age;
        this.address1 = address1;
        this.address2 = address2;
    }

    @Override
    public boolean equals(Object o) {
        // This object is me!
        if (this == o) {
            return true;
        }

        // This isn't even a User
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        // Make sure everything matches
        User u = (User) o;
        return Objects.equals(id, u.id) &&
                Objects.equals(name, u.name) &&
                Objects.equals(age, u.age) &&
                Objects.equals(address1, u.address1) &&
                Objects.equals(address2, u.address2);
    }

    @Override
    public int hashCode() {
        // Total cop out. Just let the library make the hash
        return Objects.hash(id, name, age, address1, address2);
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, name=%s, age=%d, address1=%s, address2=%s}",
                id, name, age, address1, address2);
    }
}
