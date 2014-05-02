package org.imbox.client.network.boardcast;

import java.net.ServerSocket;
import java.net.Socket;



public class SocketServer extends java.lang.Thread{
  
  private boolean OutServer = false;
  private ServerSocket server ;
  private final int ServerPort = 8090;//using port

  
  public SocketServer()
  { try
    {
      server = new ServerSocket(ServerPort);
      
    }
    catch(java.io.IOException e)
    {
      System.out.println("Socket connection error!" );
      System.out.println("IOException :" + e.toString());
    }
  }
  
  

  
  
  public void run()
  {
    Socket socket ;
    java.io.BufferedInputStream in ;

    System.out.println("Socket function normally !"  );
    while(!OutServer)
    {
      socket = null;
      try
      {
        synchronized(server) 
        {
          socket = server.accept();
        }
        System.out.println("listening at : InetAddress = " + 
socket.getInetAddress()  );
        //TimeOut interval
        socket.setSoTimeout(15000);
        

        in = new 
java.io.BufferedInputStream(socket.getInputStream());
        byte[] b = new byte[1024];
        String data ="";
        int length;
        while((length = in.read(b))>0)//<=0 then it end
        {
        	data += new String(b, 0, length);
        }
        
        System.out.println("dataread:"+data);
        in.close();
        in = null ;
        socket.close();
        
        
        
      }
      catch(java.io.IOException e)
      {
        System.out.println("Socket connection error !" );
        System.out.println("IOException :" + e.toString());
      }
      
    }
  }
  
  public static void main(String args[])
  {
    (new SocketServer()).start();
  }
  
}