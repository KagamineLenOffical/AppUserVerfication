import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client {
    File saveFile;
    Long vCode;
    public Client(String path) throws IOException {
        saveFile=new File(path);
        if(saveFile.exists()){
            FileInputStream fis=new FileInputStream(saveFile);
            byte buff[]=new byte[1024];
            int len=fis.read(buff);
            String s= new String(buff,0,len);
            vCode=Long.valueOf(s);
        }
        else{
            inputVCode();
        }
    }
    boolean getLongValue(String s){
        System.out.println(s);
        try{
            vCode= Long.valueOf(s);
            System.out.println("ret:"+vCode);

        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    void inputVCode() throws IOException {
        System.out.println("请输入认证码");
        Scanner in=new Scanner(System.in);
        //String s=in.nextLine();
        String s="1000000000";
        while(!getLongValue(s)){
            s=in.nextLine();
        }
        FileOutputStream fos=new FileOutputStream(saveFile);
        byte buff[]=String.valueOf(vCode).getBytes();
        fos.write(buff);
        return;
    }
    String sendVer() throws Exception {
        return "VER "+String.valueOf(vCode)+" "+getMac();
    }
    String getMac() throws Exception {
        List<String> macList=MacTools.getMacList();
        return String.valueOf(macList.get(0));
    }
}
class MacTools {
    /***因为一台机器不一定只有一个网卡呀，所以返回的是数组是很合理的***/
    public static List<String> getMacList() throws Exception {
        java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> tmpMacList = new ArrayList<>();
        while (en.hasMoreElements()) {
            NetworkInterface iface = en.nextElement();
            List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
            for (InterfaceAddress addr : addrs) {
                InetAddress ip = addr.getAddress();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if (network == null) {
                    continue;
                }
                byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                sb.delete(0, sb.length());
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                tmpMacList.add(sb.toString());
            }
        }
        if (tmpMacList.size() <= 0) {
            return tmpMacList;
        }
        List<String> unique = tmpMacList.stream().distinct().collect(Collectors.toList());
        return unique;
    }
}