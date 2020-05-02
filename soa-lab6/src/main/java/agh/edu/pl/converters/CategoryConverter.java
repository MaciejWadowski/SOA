package agh.edu.pl.converters;

import agh.edu.pl.model.Category;
import agh.edu.pl.repositories.interfaces.ICategoryRemoteRepository;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@FacesConverter(value = "categoryConverter")
public class CategoryConverter implements Converter {
    @EJB(lookup = "java:module/CategoryRepository")
    private ICategoryRemoteRepository categoryRepository;
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Category category = new Category();
        category.setType(s);
        return s;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Category) o).getId().toString();
    }
}
