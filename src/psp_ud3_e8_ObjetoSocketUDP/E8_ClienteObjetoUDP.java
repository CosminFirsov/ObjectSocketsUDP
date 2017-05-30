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
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author DAM203
 */
public class E8_ClienteObjetoUDP {
    public static void main(String[] args) throws SocketException, IOException, ClassNotFoundException {
        DatagramSocket clienteSocket = new DatagramSocket();
        byte[] enviados = new byte[1024];
        byte[] recibidos = new byte[1024];
        
        InetAddress IPServidor = InetAddress.getLocalHost();
        int puerto = 6000;
        E8_Persona per = new E8_Persona("Pepe", 84);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bs);
        out.writeObject(per);
        enviados = bs.toByteArray();
        System.out.println("Envio: " + per.getNombre() + "*" + per.getEdad());
        
        DatagramPacket envio = new DatagramPacket(enviados, enviados.length,IPServidor, puerto);
        clienteSocket.send(envio);
        
        DatagramPacket recibo = new DatagramPacket(recibidos, recibidos.length);
        clienteSocket.receive(recibo);
        ByteArrayInputStream bais = new ByteArrayInputStream(recibidos);
        ObjectInputStream in = new ObjectInputStream(bais);
        per = (E8_Persona) in.readObject();
        System.out.println("Recibido: " + per.getNombre() + "*" + per.getEdad());
        
        clienteSocket.close();
        in.close();
        out.close();

    }
}
