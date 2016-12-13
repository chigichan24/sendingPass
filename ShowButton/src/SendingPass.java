import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by prolab on 12/14/16.
 */
public class SendingPass implements Runnable{
    private Callback callback;
    String str;

    public interface Callback{
       void setOK();
       void setNG();
       void reset();
    }

    public SendingPass(Callback callback){
        this.callback = callback;
    }

    public void run() {
        String cmds[] = {"./Go/sendPass"};
        ProcessBuilder pb = new ProcessBuilder(cmds);
        try {
            Process proc = pb.start();
            BufferedReader brstd = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            str = brstd.readLine();
            brstd.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        if(str.equals("Success!")){
            this.callback.setOK();
        }
        else{
            this.callback.setNG();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        this.callback.reset();
    }
}
