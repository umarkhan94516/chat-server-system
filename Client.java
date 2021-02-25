package Person;
import java.sql.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;


// download mysql and mysql connecter driver
//add driver in eclips library
// create table customer in mysql and database name is db1 
// create table customer(cid int,name varchar(20),mno varchar(20));
class Swing2 extends JFrame
 {	 JLabel l1,l2,l3,l4;
	 JTextField t0,t1,t2,t3;
	 JTextArea ta;
	 JButton b1,b2;
	 public String a0,a,b,sum;
	 public static Socket s;
	 DataOutputStream dos;
	 DataInputStream dis;
	 
	 public int send(String frino)
	 {int roll;
		//mysql connection
			try{
				Class.forName("com.mysql.jdbc.Driver");
				//change username and password of your mysql below line
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db1","root","umar.up");
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery("select cid from customer where mno='"+frino+"' ");
				rs.next();
				roll=rs.getInt("cid");
				System.out.println(roll);
				st.close();
				con.close();
				return roll;
				}
				catch(Exception e)
				{
					System.out.println("Exception "+e.getMessage());
				}
				return 0;
			
	 }

	 public Swing2(){}
	 public Swing2(String m)
	 {
		 super(m);
	 }
	 public void connect(Socket sss)
	 {
		 s=sss;
	 }
	 public void setComponents()
	 {
		l1=new JLabel("Chat server System");
		l2=new JLabel("Your name");		
		l3=new JLabel("Friend Mobile number");
		l4=new JLabel();
		ta=new JTextArea();
		t0=new JTextField();
		t1=new JTextField();
		t2=new JTextField();
		b1=new JButton("Connect");
		b2=new JButton("Send");
		setLayout(null);
		t0.setBounds(150,10,200,20);
		l1.setBounds(3,10,200,20);
		l2.setBounds(5,40,200,20);
		l3.setBounds(5,70,200,20);
		l4.setBounds(5,100,300,50);
		ta.setBounds(5,120,370,380);
		t1.setBounds(140,40,70,23);
		t2.setBounds(140,70,70,23);
		b1.setBounds(230,45,140,40);
		b1.addActionListener(new Handler());
		t3=new JTextField();
		t3.setBounds(2,515,300,40);
		b2.setBounds(310,515,70,40);
		b2.addActionListener(new Handler2());
	    add(t0);
		add(l1);
		add(l2);
		add(l3);
		add(l4);
		add(t1);
		add(t2);
		add(b1);
		add(t3);
		add(b2);
		add(ta);
	 }
	 class Handler implements ActionListener
	 {
		 public void actionPerformed(ActionEvent e)
		 {   a0=t0.getText();
			 a=t1.getText();
			
		try{
		 dos=new DataOutputStream(s.getOutputStream());
		 dis=new DataInputStream(s.getInputStream());// from server
		 dos.writeUTF(a0);
		 dos.writeUTF(a);
		 
			new Thread(new Runnable(){
				public void run()
				{try{
					ta.setText("Connected to the Client \n");
					while(true){
					//System.out.println(dis.readUTF());
					sum=dis.readUTF();
					ta.append(sum+"\n");
					}}catch(Exception ee){}
				}
			}).start();
			
	 }catch(Exception ce){}	
	 }
	 }
	  class Handler2 implements ActionListener
	 {
		 public void actionPerformed(ActionEvent e)
		 {try{
				b=t2.getText();
				int no=send(b);
				String frino=Integer.toString(no);
				dos.writeUTF(frino);
				String msg=t3.getText();
				dos.writeUTF(msg);
				ta.append("Me "+msg+"\n");
		 }catch(Exception ss){} 
		 }
	 }
 }
public class Client
{public static Socket server;
	public static void main(String args[])
	{
		Swing2 jf=new Swing2("clients");
		jf.getContentPane().setBackground(Color.lightGray);
		try{ server=new Socket("localhost",6666);}catch(Exception eee){}
		jf.connect(server);
		jf.setComponents();
		
		jf.setVisible(true);
		jf.setSize(400,600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jf.getContentPane().setBackground(yellow);
		
	
		
		 	 
	}
	
}
