package edu.cau.cps.cis301;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentBookManagerTest {

    private AppointmentBookManager appointmentBookManager;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        appointmentBookManager = new AppointmentBookManager();
        appointmentBookManager.loadDefaultDataStore();
    }

    @Test
    void testEmptyDSAppointmentBookManagerLoad(){
        appointmentBookManager.resetDataStore();
        appointmentBookManager.loadDefaultDataStore();
        assertNotNull(appointmentBookManager.getAppointmentBookHashMap());
        assertNotNull(appointmentBookManager.getAppointmentBookOwners());
    }

    @Test
    void testAddnewAppointment(){
        Appointment appointment = new Appointment();
        Calendar cal = Calendar.getInstance();
        cal.set(2019,0,20,13,53);
        appointment.setBeginTime(cal.getTime());
        cal.add(Calendar.HOUR_OF_DAY,3);
        appointment.setEndTime(cal.getTime());
        appointment.setDescription("This is a test!");
        appointmentBookManager.addNewAppointment("Ali", appointment);
        AppointmentBookManager appointmentBookManagerTest = new AppointmentBookManager();
        appointmentBookManagerTest.loadDefaultDataStore();
        assertNotNull(appointmentBookManagerTest.getAppointmentBookOwners());
        assertNotNull(appointmentBookManagerTest.getAppointmentBookHashMap());
    }

    @Test
    void testAddMultipleOwners(){
        Appointment appointment = new Appointment();
        Calendar cal = Calendar.getInstance();
        cal.set(2019,0,20,13,53);
        appointment.setBeginTime(cal.getTime());
        cal.add(Calendar.HOUR_OF_DAY,3);
        appointment.setEndTime(cal.getTime());
        appointment.setDescription("This is a test!");
        appointmentBookManager.addNewAppointment("Ali", appointment);
        appointmentBookManager.addNewAppointment("John", appointment);
        AppointmentBookManager appointmentBookManagerTest = new AppointmentBookManager();
        appointmentBookManagerTest.loadDefaultDataStore();
        assertNotNull(appointmentBookManagerTest.getAppointmentBookOwners());
        assertNotNull(appointmentBookManagerTest.getAppointmentBookHashMap());
    }

    @Test
    void testSimpleParser(){
        String datetime = "2019-12-09";
        try {
            Date date = TemplateTools.parseDate("13:00", "2019-12-09");
            assertNotNull(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}