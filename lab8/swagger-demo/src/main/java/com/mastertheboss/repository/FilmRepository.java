package com.mastertheboss.repository;

import com.mastertheboss.model.Film;
import com.mastertheboss.model.User;
import com.mastertheboss.repository.exceptions.ElementNotFoundException;
import com.mastertheboss.repository.interfaces.IFilmRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class FilmRepository implements IFilmRepository {
    @PersistenceContext(name ="JPA")
    private EntityManager em;

    @Override
    public List<Film> findAll() {
        return em.createQuery("FROM Film", Film.class).getResultList();
    }

    @Override
    public void delete(Long id) throws ElementNotFoundException {
        Film film = findById(id);
        if (film == null) throw new ElementNotFoundException();
        List<User> users = em.createQuery("FROM User", User.class).getResultList();
        for (User u:
             users) {
            u.getFilms().remove(film);
        }
        em.remove(findById(id));
    }

    @Override
    public void save(Film object) {
        object.setId(null);
        em.persist(object);
    }

    @Override
    public Film findById(Long id) {
        return em.find(Film.class, id);
    }

    @Override
    public void update(Long id, Film object) throws ElementNotFoundException {
        Film film = findById(id);
        if (film == null) throw new ElementNotFoundException();
        if (object.getTitle() != null) film.setTitle(object.getTitle());
        if (object.getUri() != null) film.setUri(object.getUri());
    }

    @Override
    public Film findByTitle(String title) throws ElementNotFoundException {
        Query q =  em.createQuery("SELECT f FROM Film f WHERE f.title = :title");
        q.setParameter("title", title);
        q.setMaxResults(1);
        List<Film> films = q.getResultList();
        if (films.size() > 0) {
            return films.get(0);
        } else {
            throw new ElementNotFoundException();
        }
    }
}
