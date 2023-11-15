package com.server;

import java.net.Socket;

public class MyThread extends Thread{
    
    Socket s;

    public MyThread(Socket s){
        this.s = s;
    }

    @Override
    public void run(){
        
    }
}
