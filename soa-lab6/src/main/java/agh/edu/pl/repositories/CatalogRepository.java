package agh.edu.pl.repositories;

import agh.edu.pl.model.Borrow;
import agh.edu.pl.model.Catalog;
import agh.edu.pl.repositories.interfaces.ICatalogRemoteRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CatalogRepository implements ICatalogRemoteRepository {
    @PersistenceContext(name ="JPA")
    private EntityManager em;

    @Override
    public List<Catalog> findAll() {
        try {
            return em.createQuery("FROM Catalog ", Catalog.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Catalog findById(Long id) {
        return em.find(Catalog.class, id);
    }

    @Override
    public void save(Catalog object) {
        em.persist(object);
    }

    @Override
    public void deleteById(Long id) {
        Catalog catalog = em.find(Catalog.class, id);
        if (catalog != null) {
            em.remove(catalog);
        }
    }

    @Override
    public void update(Catalog object, Long id) {
        Catalog catalog = em.find(Catalog.class, id);
        catalog.setBooked(object.isBooked());
        if (object.getBook() != null)
            catalog.setBook(object.getBook());
    }

    @Override
    public Catalog findAvailableCatalog(Long id) {
        return findAll().stream()
                .filter(e -> e.getBook().getId().equals(id))
                .filter(e -> !e.isBooked())
                .findFirst()
                .orElse(null);
    }
}
