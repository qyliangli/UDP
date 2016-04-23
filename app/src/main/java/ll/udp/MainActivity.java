package ll.udp;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int port=80;
        final int times=1000;
        final Button bt1=(Button)findViewById(R.id.button);
       int aa=192,bb=168,cc=1,dd=1;


        DatagramSocket sk = null;
        InetAddress serverAddress = null;
        try {
            sk = new DatagramSocket();
            serverAddress = InetAddress.getByName(aa+"."+bb+"."+cc+"."+dd);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        final InetAddress finalServerAddress = serverAddress;
        final DatagramSocket finalSk = sk;
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MThread[] t=new MThread[20];
                for(int i=0;i<20;i++){
                    t[i]=new MThread(finalSk, finalServerAddress,port);
                   new Thread(t[i]).start();
                }
                /*MThread m1 = new MThread("For the peace of the universe,I represent the moon to punish you.Don't be afraid,I won't hurt you.", finalSk, finalServerAddress,times,port);
                final Thread t1 = new Thread(m1);
                t1.start();*/

            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            int result=msg.arg1;
            double costtime=result/1000.0;
            System.out.println(costtime+"秒");
        }
    };
    public  class MThread implements  Runnable{
        private String name;
        private  DatagramSocket sk;
        private InetAddress ip;
        private  int port;
        MThread(DatagramSocket sk,InetAddress ServerAddress,int port){

            this.sk=sk;
            this.ip =ServerAddress;

            this.port=port;
        }
        public void run(){
            name="For the peace of the universe,I represent the moon to punish you.Don't be afraid,I won't hurt you.";
            while (true) {
                try {
                    byte data2[] = name.getBytes();
                    //long a = System.currentTimeMillis();
                    DatagramPacket pk2 = new DatagramPacket(data2, data2.length, ip, port);
                    sk.send(pk2);

                /*long b=System.currentTimeMillis();
                int result=(int)(b-a);
                Message message=new Message();
                message.arg1=result;
                handler.sendMessage(message);*/
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("send error");
                }
            }
        }

    }
}
