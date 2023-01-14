package com.game.service;

import com.game.entity.Player;
import com.game.entity.PlayerInDto;
import com.game.entity.PlayerOutDto;

import java.util.Date;

public class PlayerMapper {

    private PlayerMapper() {
    }

    public static PlayerOutDto mapToPlayerOut(Player player) {
        return PlayerOutDto.builder()
                .id(player.getId())
                .name(player.getName())
                .title(player.getTitle())
                .race(player.getRace())
                .profession(player.getProfession())
                .experience(player.getExperience())
                .level(player.getLevel())
                .birthday(player.getBirthday().getTime())
                .banned(player.getBanned())
                .untilNextLevel(player.getUntilNextLevel())
                .build();
    }

    public static Player mapToPlayer(PlayerInDto playerInDto, Integer level, Integer untilNextLevel) {
        return Player.builder()
                .name(playerInDto.getName())
                .title(playerInDto.getTitle())
                .race(playerInDto.getRace())
                .profession(playerInDto.getProfession())
                .level(level)
                .untilNextLevel(untilNextLevel)
                .experience(playerInDto.getExperience())
                .banned(playerInDto.getBanned() != null && playerInDto.getBanned())
                .birthday(new Date(playerInDto.getBirthday()))
                .build();
    }
}
