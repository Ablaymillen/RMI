package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    private static final int length = 10;
    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        try {

            System.setProperty(RMI_HOSTNAME, hostName);
            // Create service for RMI
            Service service = new ServiceImpl();
            // Init service
            for (int i =1; i<length; i++){
                service.add(String.valueOf(i));
            }

            String serviceName = "CountPrimes";

            System.out.println("Initializing " + serviceName);

            Registry registry = LocateRegistry.createRegistry(port);
            // Make service available for RMI
            registry.rebind(serviceName, service);

            System.out.println("Start " + serviceName);
            // wait for some time while queue of services size is not zero for server not to close
            int queuelength = service.size();
            while(queuelength==12){
                queuelength = service.size();
                Thread.sleep(100);
            }
            long begin = System.nanoTime();

            while(queuelength > 0){
                queuelength = service.size();
                Thread.sleep(10000);
            }
            long end = System.nanoTime();
            long spent = end - begin;
            // service.getResult();
            System.out.println("Time spent: "+spent/1000000000);



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}