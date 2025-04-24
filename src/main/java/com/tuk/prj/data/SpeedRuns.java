package com.tuk.prj.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
public class SpeedRuns extends AbstractEntity {

    private String name;
    private String lastname;

    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private SamplePerson person;

    public SamplePerson getPerson() {
        return person;
    }
    public void setPerson(SamplePerson person) {
        this.person = person;
    }

    private Integer time;

    // Getters and setters
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

    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getTime() {
        return time;
    }
    public void setTime(Integer time) {
        this.time = time;
    }
}
