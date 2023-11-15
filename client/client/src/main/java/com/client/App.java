package com.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try{
            System.out.println("CLIENT ESEGUITO");

            Scanner scanner = new Scanner(System.in);
            Socket s = new Socket("localhost", 3001);
            BufferedReader inputDalServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream outputVersoServer = new DataOutputStream(s.getOutputStream());

            String scelta = "";
            String letteraoparola = "";

            do {
                System.out.println(inputDalServer.readLine());
                System.out.println(inputDalServer.readLine());
                // il client vuole indovinare la lettera o la parola?
                scelta = scanner.nextLine();
                outputVersoServer.writeBytes(scelta + "\n");
                //stampa messaggio 'scrivi la lettera' ecc
                System.out.println(inputDalServer.readLine());
                // il client digita la lettera/parola
                letteraoparola = scanner.nextLine();
                outputVersoServer.writeBytes(letteraoparola + "\n");
                //stampa la stringa nascosta mostrando il carattere indovinato
                System.out.println(inputDalServer.readLine());
                if(scelta.equals("X") || scelta.equals("x")) {
                    break;
                }
            }while(true);

            s.close();
        }
        catch(Exception e){
            System.out.println("Errore di connessione");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
