package edu.cau.cps.cis301;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

public class AppointmentServlet extends HttpServlet {

    private AppointmentBookManager appointmentBookManager;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        appointmentBookManager = new AppointmentBookManager(context);
        appointmentBookManager.loadDefaultDataStore();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*
            code 100: general search, only display count of appointment books and their owners
         */
        String query = req.getParameter("q");
        String code = req.getParameter("code");
        Message msg=null;
        if (code==null || code.length() < 1) {
             msg = new Message(500, "Missing search query ", "");
        }else if (code.equals("100")){
            int count =appointmentBookManager.getAppointmentBookOwners().size();
            StringBuilder sb = new StringBuilder();
            sb.append("There are ").append(count).append(" belonging to: ").append("<br>");
            Collection<String> owners = appointmentBookManager.getAppointmentBookOwners().values();
            sb.append("<ol>");
            for (String owner: owners){
                sb.append("<li><a href=\"#\">").append(owner).append("</a></li>");
            }
            sb.append("</ol>");
            msg = new Message(200, sb.toString(), "");
        }
        resp.getWriter().write(TemplateTools.populatePage(getServletContext(),msg));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //{owner=[Jada],newappt=[Create],bdate=[2020-01-01],btime=[00:00],etime=[01:00],edate=[2020-01-01]}
        String owner = req.getParameter("owner");
        String bdate = req.getParameter("bdate");
        String btime = req.getParameter("btime");
        String edate = req.getParameter("edate");
        String etime = req.getParameter("etime");
        String desc = req.getParameter("description");
        Message msg=null;

        try {
            Date startDate = TemplateTools.parseDate(btime,bdate);
            Date endDate = TemplateTools.parseDate(etime,edate);
            Appointment appointment = new Appointment();
            appointment.setBeginTime(startDate);
            appointment.setEndTime(endDate);
            appointment.setDescription(desc);
            appointmentBookManager.addNewAppointment(owner, appointment);
            msg = new Message(200,
                    "Appointment was created successfully<br>" + req.getParameterMap().toString()
                    ,"");
        } catch (ParseException e) {
            e.printStackTrace();
            msg = new Message(500,
                    "Failed creating a new appt."
                    ,"");
        }

         msg = new Message(200,
                "Appointment was created successfully<br>" + req.getParameterMap().toString()
                    ,"");

        resp.getWriter().write(TemplateTools.populatePage(getServletContext(), msg));
    }

    private String populatePage(Message msg) {
        String page="";
        try {
            URL url = getServletContext().getResource("/WEB-INF/templates/main.html");
            StringBuilder sb = new StringBuilder();
            String content = prepareHTMLMessage(msg);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(url.getFile())))) {

                while (br.ready()) {
                    sb.append(br.readLine());
                }
                page = sb.toString();
                page=page.replace("##content", content);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return page;

    }

    private String prepareHTMLMessage(Message msg) {
        StringBuilder sb = new StringBuilder();
        if (msg.getCode()==500) {
            sb.append("<div class=\"alert alert-danger\" role=\"alert\">\n")
                    .append(msg.getDescription())
                    .append("</div>");
        }else if(msg.getCode()==200){
            sb.append("<div class=\"alert alert-info\" role=\"alert\">\n")
                    .append(msg.getDescription())
                    .append("</div>");
        }
        return sb.toString();
    }
}