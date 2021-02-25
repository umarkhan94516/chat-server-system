package Person;
import java.sql.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
// download mysql and mysql connecter driver
//add driver in eclips library
// create table customer in mysql and database name is db1 
// create table customer(cid int,name varchar(20),mno varchar(20));
class Serverobj
{
	private Socket address;
	private String mynumber;
	private String clientnumber;
	
	public void setdetail(Socket s)
	{
	 address=s;
	}
	public void getdetail(String msg,String names)
	{String msg1="Client "+names+" say "+msg;
		try{
		DataOutputStream dos=new DataOutputStream(address.getOutputStream());
		dos.writeUTF(msg1);
		//System.out.println("umar");
		}catch(Exception e){}
	}
	
}
public class Server extends JFrame
 {	 static Socket s;
	 static JTextArea ta;
	 public static int sv=0;
	 static int count;
	 JButton b1,b2;
	 public Server(){}
	 public Server(String s)
	 {
		 super(s);
	 }
	 public void setComponents()
	 {
		b1=new JButton("Start Server");
		b2=new JButton("Stop Server");
		ta=new JTextArea();
		setLayout(null);
		b1.setBounds(5,5,370,50);
		b1.addActionListener(new Handler());
		b2.setBounds(5,60,370,50);
		b2.addActionListener(new Handler2());
		ta.setBounds(5,120,370,420);
		add(ta);
		add(b1);
		add(b2);
	 }
	 class Handler implements ActionListener
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			 if(sv>0)
		ta.setText("Server is Started\n");
		
	 }
	 }
	  class Handler2 implements ActionListener
	 {
		 public void actionPerformed(ActionEvent e)
		 {  
			ta.append("Server is Stop /n");
		 }
	 }
	public static void main(String args[]){
		Server jf=new Server("clients");
		jf.setComponents();
		jf.setVisible(true);
		jf.setSize(400,600);
		//jdbc conncetion 
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Loaded");
			//change username and password of your mysql below line
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db1","root","umar.up");
			Statement st=con.createStatement();
			System.out.println("Connected");
		
		
			
		ServerSocket server = new ServerSocket(6666);
		//sv++;
			System.out.println("Server is started");
		Serverobj temp[]=new Serverobj[10];
		while(true){
			s=server.accept();
			ta.append("Client is Connected \n"+s+"\n");
		count++;
		temp[count]=new Serverobj();
		temp[count].setdetail(s);
		new Thread(new Runnable()
		{
			public void run()
			{
			
			try{
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			String myname=dis.readUTF();
			String myno=dis.readUTF();
			
			
			st.executeUpdate("insert into customer values('"+count+"','"+myname+"','"+myno+"')");
			
			//temp[count].mobileno(myno,frino);
			while(true)
			{
				String frino=dis.readUTF();
			    int frinos=Integer.parseInt(frino);
			    String msg=dis.readUTF();
			
				temp[frinos].getdetail(msg,myname);
			}}catch(Exception u){}
			}
		}).start();
		}
		 }catch(Exception sss){}
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
	}
	
}  
