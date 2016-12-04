package wechat;

import javax.swing.*;

/**
 * Created by ek2zqun on 11/30/2016.
 */
public class ContactItem {
    private ImageIcon icon;
    private int type;
    private int id;
    private String name;
    private String lastMessage;

    public ContactItem(int id, String name, int type){
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return this.type;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setImage(ImageIcon icon){
        this.icon = icon;
    }

    public ImageIcon getImage(){
       return this.icon;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setLastMessage(String message){
        this.lastMessage = message;
    }

    public String getLastMessage(){
        return this.lastMessage;
    }

    public String toString(){
        return this.name;
    }
}
