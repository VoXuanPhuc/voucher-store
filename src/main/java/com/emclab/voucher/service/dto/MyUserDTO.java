package com.emclab.voucher.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.emclab.voucher.domain.MyUser} entity.
 */
public class MyUserDTO implements Serializable {

    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String gender;

    @NotNull
    private String phone;

    @NotNull
    private String email;

    @NotNull
    private String avatar;

    @NotNull
    private Date dob;

    private AddressDTO address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDob() {
        return dob;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyUserDTO)) {
            return false;
        }

        MyUserDTO myUserDTO = (MyUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, myUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyUserDTO{" + "id=" + getId() + ", username='" + getUsername() + "'" + ", password='" + getPassword()
                + "'" + ", firstName='" + getFirstName() + "'" + ", lastName='" + getLastName() + "'" + ", gender='"
                + getGender() + "'" + ", phone='" + getPhone() + "'" + ", email='" + getEmail() + "'" + ", address="
                + getAddress() + "}";
    }
}
