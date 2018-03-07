package fi.abo.date.datepiikkiapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jimmy on 12/3/2017.
 */

public class Account implements Parcelable{

    //Variables used in account
    private String username;
    private String password;
    private int type = 1; //type 1 == normal, type 0 == admin.
    private String token;
    private String balance;
    private String ID;
    private String name;

    //Constructors
    Account(String username,String password){
        this.username = username;
        this.password = password;
    }
    // -- getters --
    String getUsername(){
        return username;
    }
    String getPassword(){
        return password;
    }
    int getTyp(){ return type; }
    String getToken(){ return token; }
    String getBalance(){ return balance; }
    String getID(){ return ID; }
    String getName(){ return name;}
    // -- Setters --
    void setToken(String token){ this.token = token; }
    void setBalance(String balance){ this.balance = balance; }
    void setID(String id) { this.ID = id; }
    void setName(String name) { this.name = name; }
    void setType(int type) { this.type = type; }
    /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(username);
        out.writeString(password);
        out.writeInt(type);
        out.writeString(token);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Account(Parcel in) {
        username = in.readString();
        password = in.readString();
        type = in.readInt();
        token = in.readString();
    }}
