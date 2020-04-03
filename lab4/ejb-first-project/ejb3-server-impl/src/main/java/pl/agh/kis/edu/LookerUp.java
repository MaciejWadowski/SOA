package pl.agh.kis.edu;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class LookerUp {

    private Properties prop = new Properties();
    private String jndfiPrefix;

    public LookerUp() {
        prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
    }

    public Object findLocalSessionBean(String moduleName, String beanName, String interfaceFullQualifiedName) throws NamingException {
        final Context context = new InitialContext(prop);
        Object object = context.lookup("java:global/" + moduleName + "/" + beanName + "!" + interfaceFullQualifiedName);
        context.close();
        return object;
    }
}
