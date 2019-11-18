package edu.cau.cps.cis301;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class AppointmentBookManager {

    private HashMap<UUID, AppointmentBook>  appointmentBookHashMap;
    private HashMap<UUID, String> appointmentBookOwners;
    private DataStore dataStore ;

    public AppointmentBookManager(){
        appointmentBookHashMap = new HashMap<>();
        appointmentBookOwners = new HashMap<>();
        dataStore = new DataStore();
    }
    public AppointmentBookManager(ServletContext context){
        appointmentBookHashMap = new HashMap<>();
        appointmentBookOwners = new HashMap<>();
        dataStore = new DataStore(context);
    }

    protected void resetDataStore(){
        dataStore.resetDataStore();
    }

    public HashMap<UUID, AppointmentBook> getAppointmentBookHashMap() {
        return appointmentBookHashMap;
    }

    public void setAppointmentBookHashMap(HashMap<UUID, AppointmentBook> appointmentBookHashMap) {
        this.appointmentBookHashMap = appointmentBookHashMap;
    }

    public HashMap<UUID, String> getAppointmentBookOwners() {
        return appointmentBookOwners;
    }

    public void setAppointmentBookOwners(HashMap<UUID, String> appointmentBookOwners) {
        this.appointmentBookOwners = appointmentBookOwners;
    }
    public void loadDefaultDataStore(){
            AppointmentBookManager appointmentBookManager = dataStore.load();
            if(appointmentBookManager==null){
                return;
            }
            this.appointmentBookHashMap = appointmentBookManager.getAppointmentBookHashMap();
            this.appointmentBookOwners = appointmentBookManager.getAppointmentBookOwners();
            if(this.appointmentBookOwners==null){
                this.appointmentBookOwners = new HashMap<>();
            }

            if(this.appointmentBookHashMap==null){
                this.appointmentBookHashMap = new HashMap<>();
           }
    }
    private UUID searchUuidByOwner(String owner){
        UUID uuid= null;
        Set<Map.Entry<UUID,String>> entries=  appointmentBookOwners.entrySet();
        for (Map.Entry<UUID,String> e: entries) {
            if(e.getValue().equals(owner)){
                uuid = e.getKey();
                break;
            }
        }
        return uuid;
    }
    public void addNewAppointment (String owner, Appointment appointment)  throws RuntimeException{
        if(!appointmentBookOwners.containsValue(owner)){
            UUID uuid= UUID.randomUUID();
            appointmentBookOwners.put(uuid, owner);
            AppointmentBook appointmentBook = new AppointmentBook(owner);
            appointmentBook.addAppointment(appointment);
            appointmentBookHashMap.put(uuid, appointmentBook);
            dataStore.store(this);
        }else{

            UUID uuid = searchUuidByOwner(owner);
            if(uuid==null) {
                throw new RuntimeException("Could not locate the Owner!");
            }

            AppointmentBook appointmentBook = appointmentBookHashMap.get(uuid);
            if(appointmentBook==null){
                appointmentBook = new AppointmentBook(owner);
            }
            appointmentBook.addAppointment(appointment);
            appointmentBookHashMap.put(uuid, appointmentBook);
            dataStore.store(this);
        }
    }

    public AppointmentBook getAppointmentBooksByOwner(String owner) throws RuntimeException{
        UUID uuid = searchUuidByOwner(owner);
        if(uuid==null){
            throw new RuntimeException("Could not locate the Owner!");
        }
        return appointmentBookHashMap.get(uuid);
    }




}
