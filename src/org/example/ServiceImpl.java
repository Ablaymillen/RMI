package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of the remote service.
 */
public class ServiceImpl extends UnicastRemoteObject implements Service {
    private final BlockingQueue<String> queue;
    private final BlockingQueue<String> result;

    public ServiceImpl() throws RemoteException {
        super();
        this.queue = new LinkedBlockingQueue<>();
        this.result = new LinkedBlockingQueue<>();

    }

    @Override
    public String poll() throws RemoteException {
        return this.queue.poll();
    }

    @Override
    public void add(String str) throws RemoteException {
        this.queue.add(str);
        System.out.println("Added Service: " + str);
    }

    @Override
    public int size()throws RemoteException{
        return this.queue.size();
    }

    @Override
    public void result(String str) throws RemoteException {
        this.result.add(str);
    }

    @Override
    public String getRes()throws RemoteException{
        System.out.println("Result is: " + this.result);
        return null;
    }
}