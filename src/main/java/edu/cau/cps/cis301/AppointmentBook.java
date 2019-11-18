package edu.cau.cps.cis301;
import java.util.Collection;
import java.util.LinkedList;

public class AppointmentBook extends AbstractAppointmentBook<Appointment> {

    private String ownerName;
    private LinkedList<Appointment> appointments;

    public AppointmentBook(String owner){
        this.appointments = new LinkedList<Appointment>();
        this.ownerName = owner;
    }

    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    @Override
    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }
}