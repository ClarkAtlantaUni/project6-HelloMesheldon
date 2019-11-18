package edu.cau.cps.cis301.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/hi")
public class ResteasyApplication extends Application {

    public ResteasyApplication(){

        System.err.println("\n\n\nHello from Resteasy................\n\n\n");
    }
}
