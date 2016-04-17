import java.net.Socket;
import java.net.SocketException;
import java.net.InetAddress;
import java.util.Scanner;
import java.net.ServerSocket;
import java.io.*;
import java.net.UnknownHostException;

public class Client {
    private Socket socket = null;
    private Socket socket1 = null; 
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private ServerSocket serverSocket = null;
    private ObjectInputStream inStream = null;

    public Client() {
    }

    public void communicate(String host, int id) {

        try{
            serverSocket = new ServerSocket(5445+id);
        } catch (SocketException se) {
            se.printStackTrace();
            // System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                while(true){
                    socket = serverSocket.accept();
                    System.out.println("Connected");
                    inStream = new ObjectInputStream(socket.getInputStream());

                    Test test = (Test) inStream.readObject();
                    System.out.println("Object received " + test);
                    socket.close();
                    if(test.getId() == id)
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException cn) {
                cn.printStackTrace();
            }

            while(true){
                try {
                    //InetAddress address = InetAddress.getByName(host);
                    socket1 = new Socket(host, 4445+id);
                    outputStream = new ObjectOutputStream(socket1.getOutputStream());
                    Scanner scanner = new Scanner(System.in);
                    int input = scanner.nextInt();
                    Test test = new Test(input);
                    System.out.println("Object sent " + test);
                    outputStream.writeObject(test);
                    if(input == -1)
                        break;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }   
        }
    }

    public static void main(String[] args) {
            Client client = new Client();
            client.communicate(args[0], Integer.valueOf(args[1]));
    }
}
