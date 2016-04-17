import java.net.Socket;
import java.net.SocketException;
import java.net.InetAddress;
import java.util.Scanner;
import java.net.ServerSocket;
import java.io.*;

public class Server {
    private Socket[] socket = new Socket[2];
    private Socket[] socket1 = new Socket[2];
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private ServerSocket[] serverSocket = new ServerSocket[2];
    private ObjectInputStream inStream = null;
    private InetAddress[] address = new InetAddress[2];

    public Server() {
    }

    public void communicate(String[] args) {

        try{
            serverSocket[0] = new ServerSocket(4445);
            serverSocket[1] = new ServerSocket(4445+1);
        }catch (SocketException se) {
            se.printStackTrace();
                // System.exit(0);
        }catch (IOException e) {
            e.printStackTrace();
        }

        int count = 0;
        while(true){
            inputStream = null;
            outputStream = null;
            inStream = null;

            try{
                //address[count] = InetAddress.getByName(args[count]);
                socket[count] = new Socket(args[count], 5445+count);
                outputStream = new ObjectOutputStream(socket[count].getOutputStream());
                Test test = new Test(count);
                System.out.println("Object sent " + test);
                outputStream.writeObject(test); 
            }catch (IOException e){
                e.printStackTrace();
            }

            System.out.println("Hello");
            try {
                while(true){
                    socket1[count] = serverSocket[count].accept();
                    System.out.println("Connected");
                    inStream = new ObjectInputStream(socket1[count].getInputStream());

                    Test test1 = (Test) inStream.readObject();
                    System.out.println("Object received " + count + " Test " + test1);
                    //socket1[count].close();
                    if(test1.getId() == -1)
                        break;
                }

            } catch (SocketException se) {
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException cn) {
                cn.printStackTrace();
            }
            
            //count = (count+1)%2;
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.communicate(args);
    }
}
