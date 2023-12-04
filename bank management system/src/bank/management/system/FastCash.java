
package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {
    
    JButton withdraw, withdrawl, withdraw2, withdraw3, withdraw4, withdraw5, exit;
    String pinnumber;
    
    FastCash(String pinnumber) {
        this.pinnumber = pinnumber;
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);
        
        JLabel text = new JLabel("SELECT WITHDRAWL AMOUNT");
        text.setBounds(215, 300, 700, 35);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        image.add(text);
        
        withdraw = new JButton("Rs 100");
        withdraw.setBounds(170, 390, 150, 30);
        withdraw.addActionListener(this);
        image.add(withdraw);
        
        withdrawl = new JButton("Rs 200");
        withdrawl.setBounds(355, 390, 150, 30);
        withdrawl.addActionListener(this);
        image.add(withdrawl);
        
        withdraw2 = new JButton("Rs 500");
        withdraw2.setBounds(170, 425, 150, 30);
        withdraw2.addActionListener(this);
        image.add(withdraw2);
        
        withdraw3 = new JButton("Rs 2000");
        withdraw3.setBounds(355, 425, 150, 30);
        withdraw3.addActionListener(this);
        image.add(withdraw3);
        
        withdraw4 = new JButton("Rs 5000");
        withdraw4.setBounds(170, 460, 150, 30);
        withdraw4.addActionListener(this);
        image.add(withdraw4);
        
        withdraw5 = new JButton("Rs 10000");
        withdraw5.setBounds(355, 460, 150, 30);
        withdraw5.addActionListener(this);
        image.add(withdraw5);
        
        exit = new JButton("BACK");
        exit.setBounds(355, 495, 150, 30);
        exit.addActionListener(this);
        image.add(exit);
        
        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == exit){
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        } else {
            String amount = ((JButton)ae.getSource()).getText().substring(3); // Rs 500
            Conn c = new Conn();
            try{
                ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pinnumber+"'");
                int balance = 0;
                while(rs.next()){
                    if (rs.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(rs.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }
                
                if(ae.getSource() != exit && balance < Integer.parseInt(amount)){
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }
                
                Date date = new Date();
                String query = "insert into bank values('"+pinnumber+"', '"+date+"', 'Withdrawl', '"+amount+"' )";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Rs "+ amount +"Debited Successfully");
                
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);
                
            } catch (Exception e){
                System.out.println(e);
            }
            
        }
        
    }
    
    public static void main(String args[]){
        new FastCash("");
        
    }
    
}
