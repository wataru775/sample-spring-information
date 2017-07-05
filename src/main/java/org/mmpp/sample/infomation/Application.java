package org.mmpp.sample.infomation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.net.*;
import java.util.Enumeration;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(Application.class)
                .headless(false)
                .web(false)
                .run(args);
    }

    public void run(String... args){
        System.out.println(showInformation());
    }
    public String showInformation(){
        StringBuffer bufBody = new StringBuffer();
        bufBody.append("--------------------------------"   + "\n");
        bufBody.append("Application Version : "   + getAppicationVersion()     + "\n");
        bufBody.append("HOST NAME : "   + getHostName()     + "\n");
        bufBody.append("IP Address : "  + getIpAddress()    + "\n");

        bufBody.append("MAC Address : " + getMACAddress()   + "\n");
        bufBody.append("Java Version : "+ getJavaVersion()  + "\n");
        bufBody.append("--------------------------------"   + "\n");

        return bufBody.toString();
    }
    private String getAppicationVersion(){
        return applicationContext.getVersion();
    }
    private String getIpAddress(){
        try {
            return( getInetAddress4().getHostAddress() );
        }catch(SocketException e){

        }catch(UnknownHostException e){

        }
        return "UNKNOWN IP ADDRESS";
    }
    private String getHostName(){
        try {
            return InetAddress.getLocalHost().getHostName() ;
        }catch(UnknownHostException e){

        }
        return "UNKNOW HOST NAME";
    }
    private String getMACAddress(){

        Enumeration<NetworkInterface> nics;
        try {
            nics = NetworkInterface.getNetworkInterfaces();
            while(nics.hasMoreElements()){
                NetworkInterface nic = nics.nextElement();
                if(!(nic.getDisplayName().equals("eth0") || nic.getDisplayName().equals("en0")))continue;
                String macAddress = "";
                byte[] hardwareAddress = nic.getHardwareAddress();
                if(hardwareAddress != null){
                    for(byte b : hardwareAddress){
                        macAddress += String.format("%02X ", b);
                    }
                }
                return  macAddress;
            }
        } catch (SocketException e) {
        }

        return "UNKNOWN MAC ADDRESS";

    }

    private String getJavaVersion(){
        return System.getProperty("java.version");
    }


    private InetAddress getInetAddress4() throws UnknownHostException,SocketException {
        InetAddress rtnInet = null;
        Enumeration <NetworkInterface> netSet;//集合内の列挙操作用
        netSet = NetworkInterface.getNetworkInterfaces();
        while(netSet.hasMoreElements()){//すべてのインターフェイスを走査
            NetworkInterface nInterface = (NetworkInterface) netSet.nextElement();
            List<InterfaceAddress> list = nInterface.getInterfaceAddresses();
            if( list.size() == 0 ) continue;
            for (InterfaceAddress interfaceAdr : list){
                InetAddress inet = interfaceAdr.getAddress();
                if(inet.isLoopbackAddress() ) continue;
                if(inet.getClass() == Inet4Address.class) {
                    rtnInet = inet;
                }
            }
        }
        return rtnInet;
    }

}
