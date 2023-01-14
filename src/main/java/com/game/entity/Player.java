package com.game.entity;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 12)
    private String name;

    @Column(length = 30)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column
    private Race race;

    @Enumerated(EnumType.STRING)
    @Column
    private Profession profession;

    @Column
    private Integer experience;

    @Column
    private Integer level;

    @Column
    private Integer untilNextLevel;

    @Column
    private Date birthday;

    @Column
    private Boolean banned;

    public Player() {
    }

    public Player(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.title = builder.title;
        this.race = builder.race;
        this.profession = builder.profession;
        this.birthday = builder.birthday;
        this.banned = builder.banned;
        this.level = builder.level;
        this.experience = builder.experience;
        this.untilNextLevel = builder.untilNextLevel;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public static Player.Builder builder() {
        return new Player.Builder();
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", experience=" + experience +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                ", birthday=" + birthday +
                ", banned=" + banned +
                '}';
    }

    public static class Builder {
        private Long id;
        private String name;
        private String title;
        private Race race;
        private Profession profession;
        private Integer experience;
        private Integer level;
        private Integer untilNextLevel;
        private Date birthday;
        private Boolean banned;

        private Builder() {

        }

        public Player.Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Player.Builder name(String name) {
            this.name = name;
            return this;
        }

        public Player.Builder title(String title) {
            this.title = title;
            return this;
        }

        public Player.Builder race(Race race) {
            this.race = race;
            return this;
        }

        public Player.Builder profession(Profession profession) {
            this.profession = profession;
            return this;
        }

        public Player.Builder level(Integer level) {
            this.level = level;
            return this;
        }

        public Player.Builder experience(Integer experience) {
            this.experience = experience;
            return this;
        }

        public Player.Builder untilNextLevel(Integer untilNextLevel) {
            this.untilNextLevel = untilNextLevel;
            return this;
        }

        public Player.Builder birthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        public Player.Builder banned(boolean banned) {
            this.banned = banned;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
