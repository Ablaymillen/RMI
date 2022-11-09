package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for a service which will be accessible remotely
 */
public interface Service extends Remote {
    String poll() throws RemoteException;
    void add(String str) throws RemoteException;
    int size() throws RemoteException;
    void result(String str) throws RemoteException;
    String getRes()throws RemoteException, InterruptedException;
}
