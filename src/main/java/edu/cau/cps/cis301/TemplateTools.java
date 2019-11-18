package edu.cau.cps.cis301;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TemplateTools {


    public static String populatePage( ServletContext context, Message msg) {
        String page="";
        try {
            URL url = context.getResource("/WEB-INF/templates/main.html");
            StringBuilder sb = new StringBuilder();
            String content = prepareHTMLMessage(msg);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(url.getFile())))) {

                while (br.ready()) {
                    sb.append(br.readLine());
                }
                page = sb.toString();
                page=page.replace("##content", content);
                page = page.replace("${appRoot}", context.getContextPath());

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return page;

    }

    public static String prepareHTMLMessage(Message msg) {
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
    protected static Date parseDate(String timeValue, String dateValue) throws ParseException {
        //valid dates are m/d/yyyy or mm/dd/yyyy or mm/d/yyyy or m/dd/yyyy
        //DateFormat datetermFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        List<String> patterns = Arrays.asList("m/d/yyyy h:mm",
                "mm/dd/yyyy h:mm", "mm/d/yyyy h:mm", "m/dd/yyyy h:m");
        String datetime = dateValue +' ' +timeValue;
        SimpleDateFormat simpleDateFormat ;
        Date date =null;
        for (String pattern : patterns){
            try {
                simpleDateFormat = new SimpleDateFormat(pattern);
                date = simpleDateFormat.parse(datetime);
            }catch (Exception ex){}
        }

        return date;
    }
}
