package com.game.controller;

import com.game.entity.*;
import com.game.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(@Autowired PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping()
    public List<PlayerOutDto> getAll(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String title,
                                     @RequestParam(required = false) Race race,
                                     @RequestParam(required = false) Profession profession,
                                     @RequestParam(required = false) Long after,
                                     @RequestParam(required = false) Long before,
                                     @RequestParam(required = false) Boolean banned,
                                     @RequestParam(required = false) Integer minExperience,
                                     @RequestParam(required = false) Integer maxExperience,
                                     @RequestParam(required = false) Integer minLevel,
                                     @RequestParam(required = false) Integer maxLevel,
                                     @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
                                     @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                     @RequestParam(required = false, defaultValue = "3") Integer pageSize) {
        return playerService.getAll(name, title, race, profession, after, before, banned, minExperience, maxExperience,
                minLevel, maxLevel, order, pageNumber, pageSize);
    }

    @GetMapping("/count")
    public Integer getAllCount(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String title,
                               @RequestParam(required = false) Race race,
                               @RequestParam(required = false) Profession profession,
                               @RequestParam(required = false) Long after,
                               @RequestParam(required = false) Long before,
                               @RequestParam(required = false) Boolean banned,
                               @RequestParam(required = false) Integer minExperience,
                               @RequestParam(required = false) Integer maxExperience,
                               @RequestParam(required = false) Integer minLevel,
                               @RequestParam(required = false) Integer maxLevel) {
        return playerService.getAllCount(name, title, race, profession, after, before, banned, minExperience, maxExperience,
                minLevel, maxLevel);
    }

    @PostMapping
    public PlayerOutDto createPlayer(@RequestBody PlayerInDto player) {
        return playerService.createPlayer(player);
    }

    @GetMapping("/{id}")
    public PlayerOutDto findById(@PathVariable long id) {
        return playerService.findById(id);
    }

    @PostMapping("/{id}")
    public PlayerOutDto updatePlayer(@PathVariable long id, @RequestBody PlayerInDto player) {
        return playerService.updatePlayer(id, player);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        playerService.delete(id);
    }
}
