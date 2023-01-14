package com.game.entity;

public class PlayerOutDto {
    private final long id;
    private final String name;
    private final String title;
    private final Race race;
    private final Profession profession;
    private final long birthday;
    private final boolean banned;
    private final int level;
    private final int experience;
    private final int untilNextLevel;

    private PlayerOutDto (Builder builder) {
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String name;
        private String title;
        private Race race;
        private Profession profession;
        private long birthday;
        private boolean banned;
        private int level;
        private int experience;
        private int untilNextLevel;

        private Builder() {

        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder race(Race race) {
            this.race = race;
            return this;
        }

        public Builder profession(Profession profession) {
            this.profession = profession;
            return this;
        }

        public Builder birthday(long birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder banned(boolean banned) {
            this.banned = banned;
            return this;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public Builder experience(int experience) {
            this.experience = experience;
            return this;
        }

        public Builder untilNextLevel(int untilNextLevel) {
            this.untilNextLevel = untilNextLevel;
            return this;
        }

        public PlayerOutDto build() {
            return new PlayerOutDto(this);
        }
    }
}
