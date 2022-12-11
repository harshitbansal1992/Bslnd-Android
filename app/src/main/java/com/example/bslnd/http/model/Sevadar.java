package com.example.bslnd.http.model;

import java.io.Serializable;

public class Sevadar implements Serializable {

    private final String full_name;

    private final String fatherName;

    private final String gender;

    private final String email;

    private final String phone;

    private final String address;

    private final String state;

    private final String city;

    private final String pin;

    private final String profession;

    private final String qualification;

    private final String pathYear;

    private final String knownLanguage;

    private final String gotra;

    private final String dob;

    private final String tob;

    private final String pob;

    private final String nationality;

    public Sevadar(final String fullName, final String fatherName, final String gender,
                   final String email, final String phone, final String address,
                   final String state, final String city, final String pin,
                   final String profession, final String qualification, final String pathYear,
                   final String knownLanguage, final String gotra, final String dob,
                   final String tob, final String pob, final String nationality) {
        this.full_name = fullName;
        this.fatherName = fatherName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.state = state;
        this.city = city;
        this.pin = pin;
        this.profession = profession;
        this.qualification = qualification;
        this.pathYear = pathYear;
        this.knownLanguage = knownLanguage;
        this.gotra = gotra;
        this.dob = dob;
        this.tob = tob;
        this.pob = pob;
        this.nationality = nationality;
    }

    public String getFullName() {
        return full_name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getPin() {
        return pin;
    }

    public String getProfession() {
        return profession;
    }

    public String getQualification() {
        return qualification;
    }

    public String getPathYear() {
        return pathYear;
    }

    public String getKnownLanguage() {
        return knownLanguage;
    }

    public String getGotra() {
        return gotra;
    }

    public String getDob() {
        return dob;
    }

    public String getTob() {
        return tob;
    }

    public String getPob() {
        return pob;
    }

    public String getNationality() {
        return nationality;
    }
}
