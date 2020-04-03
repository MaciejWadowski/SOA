package pl.agh.kis.edu;


import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CounterServlet", urlPatterns = {"/CounterServlet"})
public class CounterServlet extends HttpServlet {

    @EJB(lookup = "java:global/ejb3-server-war/TestBeanCounter!pl.agh.kis.edu.IRemoteTestBeanCounter")
    ITestBeanCounter beanCounter;

    @EJB(lookup = "java:module/TestAddBean")
    ILocalTestBean addBean;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        beanCounter.increment();
        PrintWriter out = response.getWriter();
        System.out.println(request.getParameter("a"));
        System.out.println(request.getParameter("b"));
        out.println("Cześć!");
        out.println("Wynik dodawania");
        int a = Integer.parseInt(request.getParameter("a"));
        int b = Integer.parseInt(request.getParameter("b"));
        out.println(" a + b =" + addBean.add(a,b));
        out.println("I losc klikniec = " + beanCounter.getNumber());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
