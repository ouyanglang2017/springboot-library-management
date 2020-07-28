package com.ouyang.springbootlibrarymanagement.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdleConnectionEvictor extends Thread {
    @Autowired
    private HttpClientConnectionManager connectionManager;

    private volatile boolean shutdown;

    public IdleConnectionEvictor() {
        super();
        super.start();
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    //关闭失效连接
                    connectionManager.closeExpiredConnections();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //关闭清理无效连接的线程
    public void shutdown(){
        shutdown = true;
        synchronized (this){
            notifyAll();
        }
    }
}

