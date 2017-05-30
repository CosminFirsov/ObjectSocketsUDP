/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psp_ud3_e8_ObjetoSocketUDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author DAM203
 */
public class E8_ServidorObjetoUDP {
    public static void main(String[] args) throws SocketException, IOException, ClassNotFoundException 
    {
        byte[] recibidos = new byte[1024];
        byte[] enviando = new byte[1024];
        int numeroPuerto = 6000;
        DatagramSocket serverSocket = new DatagramSocket(numeroPuerto);
        recibidos = new byte[1024];
        DatagramPacket paqRecibido = new DatagramPacket(recibidos, recibidos.length);
        System.out.println("Esperando al cliente....");
        
        
        serverSocket.receive(paqRecibido);
        ByteArrayInputStream bais = new ByteArrayInputStream(recibidos);
        ObjectInputStream in = new ObjectInputStream(bais);
        E8_Persona per = (E8_Persona) in.readObject();
        System.out.println("Recibo: " + per.getNombre() + "*" + per.getEdad());
        
        InetAddress IPOrigen = paqRecibido.getAddress();
        int puerto = paqRecibido.getPort();
        
        per.setEdad(22);
        per.setNombre("Maria");
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bs);
        out.writeObject(per);
        enviando = bs.toByteArray();
        
        DatagramPacket paqEnviando = new DatagramPacket(enviando, enviando.length, IPOrigen, puerto);
        serverSocket.send(paqEnviando); 
        System.out.println("Envio: " + per.getNombre() + "*" + per.getEdad());

        
        out.close();
        in.close();
        serverSocket.close();        
    }
}
