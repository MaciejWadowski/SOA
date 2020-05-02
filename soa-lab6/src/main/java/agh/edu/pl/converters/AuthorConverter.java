package agh.edu.pl.converters;

import agh.edu.pl.model.Author;
import agh.edu.pl.repositories.interfaces.IAuthorRemoteRepository;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@FacesConverter(value = "authorConverter")
public class AuthorConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String s) {
        Author author = new Author();
        author.setName(s);
        return s;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Author) o).getId().toString();
    }
}
