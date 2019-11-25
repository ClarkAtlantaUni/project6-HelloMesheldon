package edu.cau.cps.cis301;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationServlet extends HttpServlet {

    private AppointmentBookManager appointmentBookManager;
    private String user="cis301user";
    private String password="H@ppyC0ding";

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        appointmentBookManager = new AppointmentBookManager(context);
        appointmentBookManager.loadDefaultDataStore();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //your code
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(username.equals(user) && this.password.equals(password)){
            Message msg = new Message(200,"Authentication Success!", "");
            resp.getWriter().write(TemplateTools.populatePage(getServletContext(),msg));
        }else{
            Message msg = new Message(500,"Authentication Failed!", "");
            resp.getWriter().write(TemplateTools.populatePage(getServletContext(),msg));
        }


    }
}