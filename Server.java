/**
 * 
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    /**
     *
     * @param args
     */
    public static void main(String[] args){

        ExecutorService pool = Executors.newCachedThreadPool();
        int counter = 0;
        try (ServerSocket serverSocket = new ServerSocket(5001)){
            System.out.println("Waiting for a client...");
            while (true){
                counter++;
                Socket socket = serverSocket.accept();
                System.out.println("client accepted");
                pool.execute(new ClientHandler(socket, counter));


            }




        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

/**
 * class for handle the client
 */
class ClientHandler implements Runnable{
    private Socket socket;
    private int clientNum;

    public ClientHandler(Socket connectionSocket, int clientNum) {
        this.socket = connectionSocket;
        this.clientNum=clientNum;
    }

    @Override
    public void run() {

        try {
            String str="";
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            while(true){
                byte[] buffer = new byte[2048];

                int read = inputStream.read(buffer);
                String line = new String(buffer, 0, read);
                System.out.println("client number" + clientNum + ": " + line);

                if (line.equals("over")){
                    break;
                }

                str+=line;
                outputStream.write(str.getBytes());

            }
            System.out.println("Closing client"+clientNum+"...");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
