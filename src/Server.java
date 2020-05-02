import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


//REQ 50 VER code mac
//10分钟 发一次更新时间 30分钟则认定超时
//超时可以继续使用 但是退出后需要重新认证
//VCO verificationCode
public class Server {
    ArrayList<VCode> vCodes;
    FileUtil fileUtil;
    VCodeGenerator gen;
    public Server(String path){
        vCodes=new ArrayList<VCode>();
        File saveFile=new File(path);
        fileUtil=new SequentialFileUtil(saveFile);
        fileUtil.LoadFile(vCodes);
        gen=new VCodeGenerator(1);
    }
    void save() throws IOException {
        fileUtil.SaveFile(vCodes);
    }
    String getNextVCode(int userNum) throws IOException {
        long ret;
        VCode tmp;
        while(true){
            boolean notRepeat=true;
            ret=gen.getNextVCode();
            tmp=new VCode(ret,userNum);
            for(VCode v:vCodes){
                if(v.equals(tmp)){
                    notRepeat=false;
                    break;
                }
            }
            if(notRepeat)break;
        }
        vCodes.add(tmp);
        save();
        return String.valueOf(ret);
    }
    boolean verified(String code,String mac) throws IOException {
//        System.out.println(code+" "+mac);
        long c=Long.valueOf(code);
        char macChar[]=mac.toCharArray();
        for(VCode vCode:vCodes){
            if(c==vCode.getvCode()){
                for(User u:vCode.getUsers()){
                    if(u.macEquals(macChar)){
                        u.updateTime();
                        save();
                        return true;
                    }
                }
//                System.out.println(Arrays.toString(macChar));
                boolean ret=vCode.addNewUser(macChar);
                save();
                return ret;
            }
        }
        return false;
    }
    void endVer(String code,String mac) throws IOException {
        long c=Long.valueOf(code);
        char macChar[]=mac.toCharArray();

        for(VCode vCode:vCodes){
            if(c==vCode.getvCode()){
                User delUser = null;
                for(User u:vCode.getUsers()){
                    if(u.macEquals(macChar)){
                        delUser=u;
                        break;
                    }
                }
                if(delUser!=null)vCode.getUsers().remove(delUser);
                save();
                return;
            }
        }
    }
}
class VCodeGenerator{
    Random r;
    final long mod= (long) 1e11;
    public VCodeGenerator(int seed){
        r=new Random(seed);
    }
    public long getNextVCode(){
        long ret=r.nextLong();
        ret%=mod;
        if(ret<0)ret+=mod;
        return ret;
    }

}