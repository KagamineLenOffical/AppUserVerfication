import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        Server s=new Server("./a.dat");
        String code=s.getNextVCode(2);
        System.out.println(code);

        /*System.out.println(code);
        char mac[]={48,48,48,48,48,48};
        mac[5]+=1;
        String smac= String.valueOf(mac);
        System.out.println(smac);
        System.out.println(s.verified(code,smac));
        mac[5]+=1;
        smac= String.valueOf(mac);
        System.out.println(smac);
        System.out.println(s.verified(code,smac));
        mac[5]+=1;
        smac= String.valueOf(mac);
        System.out.println(smac);
        System.out.println(s.verified(code,smac));
        mac[5]+=1;
        smac= String.valueOf(mac);
        System.out.println(s.verified(code,smac));
        Thread.sleep(500);
        System.out.println(s.verified(code,smac));*/
        Client c=new Client("./b.dat");
        c.vCode=Long.valueOf(code);
        //.inputVCode();
        System.out.println(s.readCmd("VER "+code+" FF-FF-FF-FF-FF-C9"));
        System.out.println(s.readCmd("VER "+code+" FF-FF-FF-FF-FF-CA"));
        System.out.println(s.readCmd("VER "+code+" FF-FF-FF-FF-FF-CB"));
    }
}
