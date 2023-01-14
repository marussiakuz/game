package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.*;
import com.game.exception.InvalidPlayerDataException;

import com.game.exception.PlayerNotFoundException;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(@Autowired PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<PlayerOutDto> getAll(String name, String title, Race race, Profession profession, Long after, Long before,
                                     Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                                     Integer maxLevel, PlayerOrder order, int pageNumber, int pageSize) {
        List<Player> all = playerRepository.findPlayersByCriteria(name, title, race, profession, after, before, banned,
                minExperience, maxExperience, minLevel, maxLevel, order, pageNumber, pageSize);

        return all.stream()
                .map(PlayerMapper::mapToPlayerOut)
                .collect(Collectors.toList());
    }

    public Integer getAllCount(String name, String title, Race race, Profession profession, Long after, Long before,
                               Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                               Integer maxLevel) {
        long allCount = playerRepository.countPlayersByCriteria(name, title, race, profession, after, before, banned,
                minExperience, maxExperience, minLevel, maxLevel);

        return Math.toIntExact(allCount);
    }

    public PlayerOutDto createPlayer(PlayerInDto playerInDto) {
        Player player = validateAndGetPlayer(playerInDto);

        return PlayerMapper
                .mapToPlayerOut(playerRepository.save(player));
    }

    public PlayerOutDto findById(long id) {
        PlayersDataValidator.validateId(id);

        return PlayerMapper.mapToPlayerOut(playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id)));
    }

    public PlayerOutDto updatePlayer(long id, PlayerInDto playerInDto) {
        PlayersDataValidator.validateId(id);

        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));

        if (updateAndGetPlayer(player, playerInDto))
            return PlayerMapper.mapToPlayerOut(playerRepository.save(player));

        return PlayerMapper.mapToPlayerOut(player);
    }

    public void delete(long id) {
        PlayersDataValidator.validateId(id);

        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));

        playerRepository.delete(player);
    }

    private int getCurrentLevel(int experience) {
        return ((int) Math.sqrt(2500 + 200 * experience) - 50) / 100;
    }

    private int getExperienceUntilNextLevel(int level, int experience) {
        return 50 * ++level * ++level - experience;
    }

    private boolean mainFieldsNotNull(PlayerInDto playerInDto) {
        return !isNull(playerInDto.getName()) && !isNull(playerInDto.getTitle()) && !isNull(playerInDto.getRace())
                && !isNull(playerInDto.getProfession()) && !isNull(playerInDto.getExperience())
                && !isNull(playerInDto.getBirthday());
    }

    private Player validateAndGetPlayer(PlayerInDto playerInDto) {
        if (!mainFieldsNotNull(playerInDto))
            throw new InvalidPlayerDataException("Some players data is null");

        PlayersDataValidator.validateName(playerInDto.getName());
        PlayersDataValidator.validateTitle(playerInDto.getTitle());
        PlayersDataValidator.validateExperience(playerInDto.getExperience());
        PlayersDataValidator.validateBirthday(playerInDto.getBirthday());

        int level = getCurrentLevel(playerInDto.getExperience());
        int untilNextLevel = getExperienceUntilNextLevel(level, playerInDto.getExperience());

        return PlayerMapper.mapToPlayer(playerInDto, level, untilNextLevel);
    }

    private boolean updateAndGetPlayer(Player player, PlayerInDto playerInDto) {
        boolean updated = false;

        if (!isNull(playerInDto.getName()) && !playerInDto.getName().equals(player.getName())) {
            PlayersDataValidator.validateName(playerInDto.getName());
            player.setName(playerInDto.getName());
            updated = true;
        }

        if (!isNull(playerInDto.getTitle()) && !playerInDto.getTitle().equals(player.getTitle())) {
            PlayersDataValidator.validateTitle(playerInDto.getTitle());
            player.setTitle(playerInDto.getTitle());
            updated = true;
        }

        if (!isNull(playerInDto.getExperience()) && !Objects.equals(playerInDto.getExperience(), player.getExperience())) {
            PlayersDataValidator.validateExperience(playerInDto.getExperience());
            player.setExperience(playerInDto.getExperience());
            int level = getCurrentLevel(playerInDto.getExperience());
            player.setLevel(level);
            player.setUntilNextLevel(getExperienceUntilNextLevel(level, playerInDto.getExperience()));
            updated = true;
        }

        if (!isNull(playerInDto.getBirthday()) && playerInDto.getBirthday() != player.getBirthday().getTime()) {
            PlayersDataValidator.validateBirthday(playerInDto.getBirthday());
            player.setBirthday(new Date(playerInDto.getBirthday()));
            updated = true;
        }

        if (!isNull(playerInDto.getRace()) && playerInDto.getRace() != player.getRace()) {
            player.setRace(playerInDto.getRace());
            updated = true;
        }

        if (!isNull(playerInDto.getProfession()) && playerInDto.getProfession() != player.getProfession()) {
            player.setProfession(playerInDto.getProfession());
            updated = true;
        }

        if (!isNull(playerInDto.getBanned()) && playerInDto.getBanned() != player.getBanned()) {
            player.setBanned(playerInDto.getBanned());
            updated = true;
        }

        return updated;
    }
}
