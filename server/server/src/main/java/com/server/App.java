package com.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ServerSocket server = new ServerSocket(3001);
        try{
            Socket s = server.accept();
            MyThread t = new MyThread(s);
            t.start();
        }
        catch(){

        }
    }
}
