

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    /**
     *
     * @param args
     */
    public static void main(String[] args){

        Scanner in = new Scanner(System.in);


        try(Socket client = new Socket("127.0.0.1", 5001)) {

            OutputStream outputStream = client.getOutputStream();
            InputStream inputStream = client.getInputStream();
            System.out.println("Connected to server");
            while (true){
                byte[] buffer = new byte[2048];
                String string = in.nextLine();
                //write
                outputStream.write(string.getBytes());

                if (string.equals("over")){
                    break;
                }

                //read
                int read = inputStream.read(buffer);

                String l = new String(buffer, 0, read);
                System.out.println(l);

            }
            System.out.println("closing client...");
            client.close();

        }catch (IOException ex){
            System.err.println(ex);
        }

    }

}
