package edu.cau.cps.cis301;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {


    private  Integer code;
    private String description;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Message(Integer _code, String _description, Object _data){
        this.code = _code;
        this.data = _data;
        this.description = _description;
    }

    public String toJSON(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this, Message.class);
    }

}
