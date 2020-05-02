import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable{
    char mac[];
    long t;
    static final long gapTime=30*60*1000l;
    public User(char mac[]){
        this.mac=mac.clone();
        t=new Date().getTime();
    }

    public long getT() {
        return t;
    }

    public boolean macEquals(char mac[]){
        return Arrays.equals(mac,this.mac);
    }
    public void updateTime(){
        t=new Date().getTime();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Arrays.equals(mac, user.mac);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mac);
    }
}
class VCode implements Serializable {
    long vCode;
    int userNum;
    ArrayList<User> users;
    public VCode(long vCode,int userNum){
        this.vCode=vCode;
        this.userNum=userNum;
        users=new ArrayList<User>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public long getvCode() {
        return vCode;
    }
    public boolean addNewUser(char mac[]){
        clearOverTime();
        if(users.size()<userNum){
            //System.out.println(Arrays.toString(mac));
            User u=new User(mac);
            users.add(u);
            return true;
        }
        return false;
    }
    public void clearOverTime(){
        long t=new Date().getTime();
        ArrayList<User> newUsers=new ArrayList<User>();
        for(User u:users){
            if(t-u.getT()<User.gapTime){
                newUsers.add(u);
            }
        }
        users=newUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VCode vCode1 = (VCode) o;
        return vCode == vCode1.vCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vCode);
    }
}