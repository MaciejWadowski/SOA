package com.mastertheboss.repository;


import com.mastertheboss.model.Film;
import com.mastertheboss.model.User;
import com.mastertheboss.repository.exceptions.ElementNotFoundException;
import com.mastertheboss.repository.interfaces.IUserRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class UserRepository implements IUserRepository, Serializable {
    @PersistenceContext(name = "JPA")
    private EntityManager em;

    public UserRepository() {
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void delete(Long id) throws ElementNotFoundException {
        User user =  findById(id);
        if (user == null) throw new ElementNotFoundException();
        user.setFilms(null);
        em.remove(user);
    }

    @Override
    public void save(User object) {
        object.setId(null);
        Set<Film> films = object.getFilms();
        Set<Film> filmsDb = new HashSet<>();
        for (var f: films) {
            Film film = em.find(Film.class, f.getId());
            if (film != null)
                filmsDb.add(film);
        }
        object.setFilms(filmsDb);
        em.persist(object);
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void update(Long id, User object) throws ElementNotFoundException {
        User user = findById(id);
        if (user == null) {
            throw new ElementNotFoundException();
        }
        if (object.getAge() != null) user.setAge(object.getAge());
        if (object.getAvatar() != null) user.setAvatar(object.getAvatar());
        if (object.getFilms() != null) user.setFilms(object.getFilms());
        if (object.getName() != null) user.setName(object.getName());
    }
}
