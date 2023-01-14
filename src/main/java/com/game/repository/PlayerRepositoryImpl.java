package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerRepositoryImpl implements PlayerRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Player> findPlayersByCriteria(String name, String title, Race race, Profession profession, Long after,
                                              Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                              Integer minLevel, Integer maxLevel, PlayerOrder order, int pageNumber,
                                              int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Player> cq = cb.createQuery(Player.class);
        Root<Player> player = cq.from(Player.class);

        List<Predicate> predicates = getPredicates(player, cb, name, title, race, profession, after, before, banned,
                minExperience, maxExperience, minLevel, maxLevel);

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(player.get(order.getFieldName())));

        TypedQuery<Player> query = em.createQuery(cq);

        return query.setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).getResultList();
    }

    @Override
    public Long countPlayersByCriteria(String name, String title, Race race, Profession profession, Long after,
                                       Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                       Integer minLevel, Integer maxLevel) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Player> player = cq.from(Player.class);
        cq.select(cb.count(player));

        List<Predicate> predicates = getPredicates(player, cb, name, title, race, profession, after, before, banned,
                minExperience, maxExperience, minLevel, maxLevel);

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getSingleResult();
    }

    private List<Predicate> getPredicates(Root<Player> player, CriteriaBuilder cb, String name, String title, Race race,
                                          Profession profession, Long after, Long before, Boolean banned,
                                          Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        List<Predicate> predicates = new ArrayList<>();

        if (name != null) predicates.add(cb.like(cb.upper(player.get("name")), "%" + name.toUpperCase() + "%"));
        if (title != null) predicates.add(cb.like(cb.upper(player.get("title")), "%" + title.toUpperCase() + "%"));
        if (race != null) predicates.add(cb.equal(player.get("race"), race));
        if (profession != null) predicates.add(cb.equal(player.get("profession"), profession));
        if (after != null) predicates.add(cb.greaterThan(player.get("birthday"), new Date(after)));
        if (before != null) predicates.add(cb.lessThan(player.get("birthday"), new Date(before)));
        if (minExperience != null) predicates.add(cb.greaterThanOrEqualTo(player.get("experience"), minExperience));
        if (maxExperience != null) predicates.add(cb.lessThanOrEqualTo(player.get("experience"), maxExperience));
        if (minLevel != null) predicates.add(cb.greaterThanOrEqualTo(player.get("level"), minLevel));
        if (maxLevel != null) predicates.add(cb.lessThanOrEqualTo(player.get("level"), maxLevel));
        if (banned != null) predicates.add(banned ? cb.isTrue(player.get("banned")) : cb.isFalse(player.get("banned")));

        return predicates;
    }
}
