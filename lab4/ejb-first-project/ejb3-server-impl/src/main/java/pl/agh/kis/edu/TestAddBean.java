package pl.agh.kis.edu;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class TestAddBean implements ILocalTestBean {

    public TestAddBean() {}

    public int add(int a, int b) {
        return a + b;
    }
}
