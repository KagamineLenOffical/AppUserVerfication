import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Server s=new Server("./a.dat");
        String code=String.valueOf(6842570856l);
        System.out.println(code);
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
        System.out.println(s.verified(code,smac));
    }
}
