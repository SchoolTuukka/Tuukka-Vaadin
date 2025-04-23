package com.tuk.prj.data;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;

@Entity
public class SpeedRuns extends AbstractEntity {

    private String name;
    private String lastname;
    @Email
    private String email;
    private String game;
    private Integer time;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGame() {
        return game;
    }
    public void setGame(String game) {
        this.game = game;
    }
    public Integer getTime() {
        return time;
    }
    public void setTime(Integer time) {
        this.time = time;
    }

}
