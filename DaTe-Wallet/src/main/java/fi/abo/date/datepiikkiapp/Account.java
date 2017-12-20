package fi.abo.date.datepiikkiapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jimmy on 12/3/2017.
 */

public class Account implements Parcelable{

    private String username;
    private String password;
    private String type = "NORMAL";

    Account(String username, String password,String type){
        this.username = username;
        this.password = password;
        this.type = type.toUpperCase();
    }
    void changeUsername(String username){
        this.username = username;
    }

    void changePassword(String newPassword){
        this.password = newPassword;
    }
    String getUsername(){
        return username;
    }
    String getPassword(){
        return password;
    }
    String getTyp(){ return type; }
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
        out.writeString(type);
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
        type = in.readString();
    }
}
