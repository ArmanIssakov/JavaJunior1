import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client_4 {

    public static void main(String[] args) throws IOException {
        final Socket client = new Socket("localhost", 8181);
        // чтение
        new Thread(() -> {
            try (Scanner input = new Scanner(client.getInputStream())) {
                while (true) {
                    System.out.println(input.nextLine());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        // запись
        new Thread(() -> {
            try (PrintWriter output = new PrintWriter(client.getOutputStream(), true)) {
                Scanner consoleScanner = new Scanner(System.in);
                while (true) {
                    String consoleInput = consoleScanner.nextLine();
                    output.println(consoleInput);
                    if (Objects.equals("q", consoleInput)) {
                        client.close();
                        break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
}

