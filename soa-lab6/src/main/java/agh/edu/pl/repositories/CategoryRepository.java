package agh.edu.pl.repositories;

import agh.edu.pl.model.Catalog;
import agh.edu.pl.model.Category;
import agh.edu.pl.repositories.interfaces.ICategoryRemoteRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategoryRepository implements ICategoryRemoteRepository {
    @PersistenceContext(name ="JPA")
    private EntityManager em;

    @Override
    public List<Category> findAll() {
        try {
            return em.createQuery("FROM Category", Category.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category findById(Long id) {
        return em.find(Category.class, id);
    }

    @Override
    public void save(Category object) {
        em.persist(object);
    }

    @Override
    public void deleteById(Long id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
        }
    }

    @Override
    public void update(Category object, Long id) {
        Category category = em.find(Category.class, id);
        if (object.getType() != null)
            category.setType(object.getType());
    }

    @Override
    public Category getCategoryByType(String type) {
        return findAll().stream()
                .filter(e -> e.getType().equals(type))
                .findFirst()
                .orElse(null);
    }
}
