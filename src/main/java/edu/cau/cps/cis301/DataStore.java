package edu.cau.cps.cis301;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletContext;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
/**
 * <P>This class stores data in the appt book </P>
 *
 * @author Trinity Dean
 * @version 1.0
 */
public class DataStore {

    private final String datafile = "apptbook";
    private File dataStoreFile ;

    public DataStore(){
        dataStoreFile = new File("src/main/resources/apptbook.js");
    }

    public DataStore(String path){
        dataStoreFile = new File(path);
    }
    public DataStore(ServletContext context) {
        try {
            URL url = context.getResource("/WEB-INF/classes/apptbook.js");
            dataStoreFile = new File(url.getFile());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    protected synchronized void resetDataStore() {
        try {
            if (dataStoreFile.exists()) {
                dataStoreFile.delete();
            }
            dataStoreFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected synchronized void store(AppointmentBookManager appointmentBookManager) {
        if (!dataStoreFile.exists()) {
            try {
                dataStoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fw = new FileWriter(dataStoreFile)) {
            String jsonABM = gson.toJson(appointmentBookManager);
            fw.write(jsonABM);
            fw.flush();
            gson = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected synchronized AppointmentBookManager load() {

        String data = null;
        StringBuilder sb = new StringBuilder();
        AppointmentBookManager appointmentBookManager = null;
        if (!dataStoreFile.exists()) {
            try {
                dataStoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(dataStoreFile))) {
            while (br.ready()) {
                sb.append(br.readLine());
            }
            Gson gson = new Gson();
            appointmentBookManager = gson.fromJson(sb.toString(), AppointmentBookManager.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appointmentBookManager;
    }

}
