package edu.cau.cps.cis301;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Appointment extends AbstractAppointment implements Comparable<Appointment> {

    private String description = null;
    private Date beginTime = null;
    private Date endTime = null;
    private UUID uuid;

    public Appointment(){
        uuid = UUID.randomUUID();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    @Override
    public String getBeginTimeString() {
        return  this.beginTime.toString();
    }

    @Override
    public String getEndTimeString() {
        return this.endTime.toString();
    }

    @Override
    public String getDescription() {

        return  this.description;
    }

    @Override
    public int compareTo(Appointment o) {
        if(this.beginTime.compareTo(o.beginTime)==0) {
                return this.endTime.compareTo((o.endTime));
        }else{
            return this.beginTime.compareTo(o.beginTime);
        }

    }
}
