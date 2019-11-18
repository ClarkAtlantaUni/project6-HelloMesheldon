package edu.cau.cps.cis301.rest;

import edu.cau.cps.cis301.AppointmentBookManager;
import edu.cau.cps.cis301.Message;
import edu.cau.cps.cis301.TemplateTools;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;

@Path("/")
public class AppointmentRestServices {

    @Context private ServletContext context;

    @GET @Path("/getinfo")
    @Produces({ MediaType.TEXT_HTML})
    public Response getAppointmentBooks(@QueryParam("owner") String owner){
        AppointmentBookManager appointmentBookManager = new AppointmentBookManager();
        appointmentBookManager.loadDefaultDataStore();
        Message msg = new Message(200,
                "Found the following "+appointmentBookManager.getAppointmentBookOwners().values().toString(),
                "");
        return Response.status(200).entity(TemplateTools.populatePage(context, msg)).build();
    }

    @GET
    public Response generalHandeler(){
        URI gotoHome = URI.create("/apptBook/");
        return Response.seeOther(gotoHome).build();
    }
}
