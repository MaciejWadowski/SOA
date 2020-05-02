package agh.edu.pl.repositories.interfaces;

import agh.edu.pl.model.Category;

import javax.ejb.Remote;

@Remote
public interface ICategoryRemoteRepository extends CustomRepository<Long, Category> {
    Category getCategoryByType(String type);
}
