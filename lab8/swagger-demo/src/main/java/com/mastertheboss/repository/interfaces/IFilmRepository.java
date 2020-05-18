package com.mastertheboss.repository.interfaces;


import com.mastertheboss.model.Film;
import com.mastertheboss.repository.exceptions.ElementNotFoundException;

import javax.ejb.Remote;

@Remote
public interface IFilmRepository extends Repository<Long, Film>{
    Film findByTitle(String title) throws ElementNotFoundException;
}
