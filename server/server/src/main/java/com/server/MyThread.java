package com.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MyThread extends Thread{
    
    Socket s;

    public MyThread(Socket s){
        this.s = s;
    }

    @Override
    public void run(){
        try{
            BufferedReader inputDalClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream outputVersoClient = new DataOutputStream(s.getOutputStream());
            ArrayList<String> listaParole = new ArrayList<>();
            listaParole.add("eva");
            listaParole.add("mela");
            listaParole.add("mouse");
            listaParole.add("lanini");
            listaParole.add("lasagna");
            
            String parola = listaParole.get(new Random().nextInt(listaParole.size()));
            
            System.out.println("Si e' connesso un client");

            String parolaNascosta = "";
            for(int i = 0; i < parola.length(); i++){
                parolaNascosta += "*";
            }

            char scelta;
            
            System.out.println("Parola scelta: " + parola);
            do{
                outputVersoClient.writeBytes(parolaNascosta + "\n");
                outputVersoClient.writeBytes("Indovina una parola. Puoi: scegliere una lettera ('L'), indovinare la parola ('P')" + "\n");
                scelta = inputDalClient.readLine().charAt(0);
                switch (scelta) {
                    case 'L':
                    case 'l':
                        outputVersoClient.writeBytes("Scrivi la lettera che vuoi indovinare:" + "\n");
                        char lettera;
                        boolean change = false;
                        lettera = inputDalClient.readLine().charAt(0);
                        for(int i = 0; i < parola.length(); i++){
                            if(parola.charAt(i) == lettera){
                                parolaNascosta = parolaNascosta.substring(0, i) + lettera + parolaNascosta.substring(i + 1);
                                change = true;
                            }
                        }
                        if(change){
                            outputVersoClient.writeBytes(parolaNascosta + "\n");
                        }
                        else{
                            outputVersoClient.writeBytes("La lettera " + lettera + " non e' presente nella parola" + "\n");
                        }
                        break;

                    case 'P':
                    case 'p':
                        outputVersoClient.writeBytes("Scrivi la parola che vuoi indovinare:" + "\n");
                        String parolaDI = ""; // DI == da indovinare
                        parolaDI = inputDalClient.readLine();
                        if(parola.equals(parolaDI)){
                            outputVersoClient.writeBytes("1" + "\n");
                        }
                        else{
                            outputVersoClient.writeBytes("0" + "\n");
                        }
                        break;
            
                    case 'X':
                    case 'x':
                        outputVersoClient.writeBytes("Hai abbandonato il gioco. La parola era: " + parola + "\n");
                        break;

                    default:
                        outputVersoClient.writeBytes("Inserisci una delle tre opzioni" + "\n");
                        break;
                }
            } while (!parolaNascosta.equals(parola) && scelta != 'X' && scelta != 'x');
            

            s.close();
        }
        catch(Exception e){
            System.out.println("Errore di connessione");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
