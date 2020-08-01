package com.example.mesagemanager;

public class Sms{
    private String _id;
    private String _address;
    private String _msg;
    private String _readState; //"0" for have not read sms and "1" for have read sms
    private String _time;
    private String _folderName;

    public Sms() {
    }

    public Sms(String _id, String _address, String _msg, String _readState, String _time, String _folderName) {
        this._id = _id;
        this._address = _address;
        this._msg = _msg;
        this._readState = _readState;
        this._time = _time;
        this._folderName = _folderName;
    }

    public String getId(){
        return _id;
    }
    public String getAddress(){
        return _address;
    }
    public String getMsg(){
        return _msg;
    }
    public String getReadState(){
        return _readState;
    }
    public String getTime(){
        return _time;
    }
    public String getFolderName(){
        return _folderName;
    }


    public void setId(String id){
        _id = id;
    }
    public void setAddress(String address){
        _address = address;
    }
    public void setMsg(String msg){
        _msg = msg;
    }
    public void setReadState(String readState){
        _readState = readState;
    }
    public void setTime(String time){
        _time = time;
    }
    public void setFolderName(String folderName){
        _folderName = folderName;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "_id='" + _id + '\'' +
                ", _address='" + _address + '\'' +
                ", _msg='" + _msg + '\'' +
                ", _readState='" + _readState + '\'' +
                ", _time='" + _time + '\'' +
                ", _folderName='" + _folderName + '\'' +
                '}';
    }
}