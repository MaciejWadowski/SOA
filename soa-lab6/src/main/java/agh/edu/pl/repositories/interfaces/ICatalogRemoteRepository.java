package agh.edu.pl.repositories.interfaces;

import agh.edu.pl.model.Catalog;

import javax.ejb.Remote;

@Remote
public interface ICatalogRemoteRepository extends CustomRepository<Long, Catalog>{
    Catalog findAvailableCatalog(Long id);
}
