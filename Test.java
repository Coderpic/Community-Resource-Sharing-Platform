import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.Date;

class Login
{   
    public JFrame f = new JFrame("Login page");
    Login(Connection connect)
    {
        final JPasswordField value = new JPasswordField();
        value.setBounds(175,120,100,30);

        JLabel J1 = new JLabel("Username:");
        J1.setBounds(50,75,80,30);
        J1.setFont(new Font("Serif", Font.BOLD, 14));

        JLabel j2 = new JLabel("Password:");
        j2.setBounds(50,120,80,30);
        j2.setFont(new Font("Serif", Font.BOLD, 14));

        JButton click = new JButton("Login");
        click.setBounds(150,180,80,30);
        click.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
        click.setForeground(Color.BLACK); 
        click.setBackground(Color.GRAY); 
        click.setFocusPainted(false);

        final JTextField text = new JTextField();  
        text.setBounds(175,75, 100,30);
        text.setFont(new Font("Arial",Font.PLAIN,14));
        
        JLabel acc = new JLabel("Don't have an account?");
        acc.setBounds(75, 225, 150, 75);
        acc.setFont(new Font("Verdana", Font.ITALIC, 12)); 
        acc.setForeground(Color.DARK_GRAY);

        JButton reg = new JButton("Register");
        reg.setBounds(225, 245, 100, 30);
        reg.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
        reg.setForeground(Color.WHITE); 
        reg.setBackground(Color.black); 
        reg.setFocusPainted(false); 

        f.add(value); f.add(J1); f.add(j2); f.add(click); f.add(text);f.add(acc);f.add(reg);

        f.setSize(400,400);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        click.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                 Connection con = connect;
                 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 PreparedStatement pst = null;
                 int uid = -1;
                 try
                 {
                    pst=con.prepareStatement("select * from users where username=? and password=?");
                    String name = text.getText();
                    String secret = new String(value.getPassword());
                    pst.setString(1,name);
                    pst.setString(2,secret);
                    ResultSet  rs=pst.executeQuery();
                    if(rs.next())
                    {
                        pst = con.prepareStatement("select id from users where username=? and password=?");
                        pst.setString(1, name);
                        pst.setString(2, secret);
                        rs = pst.executeQuery();
                        if(rs.next())
                        {
                            uid = rs.getInt("id");
                        }
                        new DashBoard(connect,uid); 
                        f.dispose();
                    }
                    else
                    {
                        JLabel dis = new JLabel("User not found");
                        dis.setBounds(140, 20, 150, 50);
                        dis.setForeground(Color.DARK_GRAY);
                        f.add(dis);
                        f.repaint();
                    }
                    rs.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
            finally {
                try {
                    if (pst != null) pst.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        }
        ); 
    
        reg.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e)
           {
               Registration res = new Registration(f,connect);
               res.create();
           }
        });
    }
}
  
class Registration
{
    public JFrame f1 = new JFrame("User Registration");
    JFrame f;
    Connection con;
    Registration(JFrame f,Connection con)
    {
        this.f = f;
        this.con = con;
    }
    void create()
    {
        f1.setSize(800, 500); 
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel nlabel = new JLabel("Name:");
        nlabel.setBounds(50, 30, 60, 20);
        nlabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField name = new JTextField();
        name.setBounds(190, 30, 200, 20);
        
        JLabel ulabel = new JLabel("Username:");
        ulabel.setBounds(50, 70, 100, 20);
        ulabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField username = new JTextField();
        username.setBounds(190, 70, 200, 20);
        
        JLabel alabel = new JLabel("Age:");
        alabel.setBounds(50, 110, 100, 20);

        alabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField age = new JTextField();
        age.setBounds(190, 110, 50, 20);
        
        JLabel plabel = new JLabel("Password:");
        plabel.setBounds(50, 150, 100, 20);
        plabel.setFont(new Font("Arial", Font.BOLD, 14));
        JPasswordField password = new JPasswordField();
        password.setBounds(190, 150, 200, 20);
                
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(400, 143, 150, 30);
        showPassword.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
             {
                if (showPassword.isSelected()) {
                      password.setEchoChar((char) 0);
                 } else {
                            password.setEchoChar('*'); 
                        }
            }
        });
        
          JLabel cplabel = new JLabel("Confirm Password:");
          cplabel.setBounds(50, 190, 150, 20);
          cplabel.setFont(new Font("Arial", Font.BOLD, 14));

          JPasswordField confirmPassword = new JPasswordField();
          confirmPassword.setBounds(190, 190, 200, 20);
        
          JLabel adlabel = new JLabel("Address:");
          adlabel.setBounds(50, 230, 100, 20);
          adlabel.setFont(new Font("Arial", Font.BOLD, 14));

          JTextArea address = new JTextArea();
          address.setBorder(BorderFactory.createLineBorder(Color.GRAY));
          address.setBounds(190, 230, 200, 90);
          address.setLineWrap(true);
          address.setWrapStyleWord(true);
        
       JLabel mlabel = new JLabel("Mobile Number:");
       mlabel.setBounds(50, 340, 150, 20);
       mlabel.setFont(new Font("Arial", Font.BOLD, 14));
       JTextField mobile = new JTextField();
       mobile.setBounds(190, 340, 200, 20);
    
       JButton submit = new JButton("Submit");
       submit.setBounds(230,390,90,30);
          f1.add(nlabel); f1.add(name);
          f1.add(alabel); f1.add(age);
          f1.add(ulabel); f1.add(username);
          f1.add(plabel); f1.add(password);
          f1.add(showPassword);f1.add(submit);
          f1.add(cplabel);f1.add(confirmPassword);
          f1.add(adlabel);f1.add(address);
          f1.add(mlabel); f1.add(mobile);
          f1.setLocationRelativeTo(null);
          f1.setLayout(null);
          f.setVisible(false);
          f1.setVisible(true);

         submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                PreparedStatement pst;
                try
                {
                    String pw1 = new String(password.getPassword());
                    String pw2 = new String(confirmPassword.getPassword());
                    if (pw1.equals(pw2))
                    {
                        pst = con.prepareStatement("insert into users (name, age, username, password, confirm_password, address, mobile_number) values(?,?,?,?,?,?,?)");
                        pst.setString(1,name.getText());
                        String s1=age.getText(); 
                        pst.setInt(2,Integer.parseInt(s1));
                        pst.setString(3,username.getText());
                        pst.setString(4,new String(password.getPassword()));
                        pst.setString(5,new String(confirmPassword.getPassword()));
                        pst.setString(6,address.getText());
                        String s2 = mobile.getText();
                        pst.setDouble(7,Double.parseDouble(s2));
                        int i=pst.executeUpdate();
                        if (i == 1) {
                            JOptionPane.showMessageDialog(f, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                            f1.dispose();  
                            f.setVisible(true);  
                        } else {
                            JOptionPane.showMessageDialog(f, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        pst.close();
                    } else {
                        JOptionPane.showMessageDialog(f, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
                }
            }
         );
    }
}

class DashBoard
{
     JFrame f;
     Connection con;
     int user;
     String username;
     JScrollPane scrollPane;  
     JPanel resultpane;  
     JPanel noResultPanel;
     DashBoard(Connection con,int uid)
     {
        this.con = con;
        user = uid;
        view();
     }
     void view()
     {
        f = new JFrame("Dashboard");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PreparedStatement pst;
        ResultSet rs;
        try
        {
            pst = con.prepareStatement("select username from users where id=?");
            pst.setInt(1,user);
            rs = pst.executeQuery();
            if(rs.next())
               username = rs.getString("username");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        f.setLayout(null);
        f.setSize(800, 600);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        new TotalResourcesAvailable(con,f);

        JFrame searchPanel = new JFrame("Results");
        searchPanel.setLayout(null);  
        searchPanel.setBounds(0, 0, f.getWidth(), 150); 

        JTextField searchField = new JTextField(50);
        searchField.setBounds(200,70,250,30);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(460, 70, 100, 30);

        JLabel welcome = new JLabel("Welcome "+username);
        welcome.setBounds(20,20,400,40);
        welcome.setFont(new Font("Arial", Font.BOLD, 30));
        welcome.setForeground(new Color(128,0,128));

        f.add(welcome);
        f.add(searchField);
        f.add(searchButton);

        f.setVisible(true);

                
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                searchPanel.setSize(600,600);
                searchPanel.setLocationRelativeTo(null);
                searchPanel.setVisible(true);
                resultpane = new JPanel();
                resultpane.setLayout(new BoxLayout(resultpane, BoxLayout.Y_AXIS));
                scrollPane = new JScrollPane(resultpane);  
                scrollPane.setBounds(20,30,350, 200);  
                scrollPane.setVisible(false);
                searchPanel.add(scrollPane); 
                if (!query.isEmpty()) {
                    searchResources(query);  
                } else {
                    resultpane.removeAll();  
                    resultpane.revalidate();  
                    resultpane.repaint();  
                    scrollPane.setVisible(false);  
                }
            }
        });

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchButton.doClick();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton borrowedButton = new JButton("Borrowed");
        borrowedButton.setBounds(20, f.getHeight() - 100, 120, 30); 
        f.add(borrowedButton);

        borrowedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BorrowedResources borrowedResources = new BorrowedResources(f, user, con);  
                borrowedResources.view(); 
            }
        });

        JButton posted = new JButton("Posted Resources");
        posted.setBounds(350, f.getHeight() - 100, 180, 30); 
        f.add(posted);

        posted.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                Posted pos = new Posted(con,f,user);
                pos.view();
            }
        });
    
        JButton postResourceButton = new JButton("Post Resource");
        postResourceButton.setBounds(f.getWidth() / 2 + 130, f.getHeight() - 100, 120, 30); 
        f.add(postResourceButton);
  
 
        JButton chatButton = new JButton("Chat");
		chatButton.setBounds(f.getWidth() - 170, f.getHeight() - 100, 120, 30);
        f.add(chatButton);
        
        f.add(buttonPanel, BorderLayout.SOUTH);
       
        postResourceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
               PostResource pos = new PostResource(con, f,user);
                pos.display();
            }
         });
        
         chatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Chat(con, user, f); 
                f.revalidate(); 
                f.repaint();
            }
        });
        

        f.setVisible(true);
    }
        
    void searchResources(String query) {
        resultpane.removeAll();  
        try {
            PreparedStatement pst = con.prepareStatement(
                "SELECT * FROM resources WHERE resource_name LIKE ?");
            pst.setString(1, "%" + query + "%");  
            ResultSet rs = pst.executeQuery();

            boolean hasResults = false;
            while (rs.next()) {
                String resourceName = rs.getString("resource_name");
                int resourceId = rs.getInt("resource_id");
                String desc = rs.getString("description");
                int owner_id = rs.getInt("owner_id");
                String rstatus = rs.getString("status");
                JPanel resourcePanel = createResourcePanel(resourceName,resourceId,desc,owner_id,rstatus);  
                resultpane.add(resourcePanel); 
                hasResults = true;
            }

            if (!hasResults) {
                JPanel noResultsPanel = createResourcePanel("No results found.",0,null,0,null);
                resultpane.add(noResultsPanel);  
            }

            resultpane.revalidate();  
            resultpane.repaint();  
            scrollPane.setVisible(true);  
        } catch (SQLException ex) {
            ex.printStackTrace();
            JPanel errorPanel = createResourcePanel("Error while fetching resources.",0,null,0,null);
            resultpane.add(errorPanel);  
            resultpane.revalidate();
            resultpane.repaint();
            scrollPane.setVisible(true);  
        }
    }

    
    JPanel createResourcePanel(String resourceName,int resource_id,String desc,int owner_id,String status) {
        JPanel resourcePanel = new JPanel();
        resourcePanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        JLabel resourceLabel = new JLabel(resourceName);
        resourcePanel.add(resourceLabel);

        JButton viewButton = new JButton("View");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f2 = new JFrame(resourceName);
                JLabel nlabel = new JLabel("Name");
                nlabel.setBounds(50, 50, 80, 30); 

                JLabel name = new JLabel(resourceName);
                name.setBounds(150, 50, 180, 30); 

                JLabel dlabel = new JLabel("Description");
                dlabel.setBounds(50, 100, 100, 30);
        
                JTextArea descriptionArea = new JTextArea();
                descriptionArea.setText(desc);
                descriptionArea.setLineWrap(true);
                descriptionArea.setWrapStyleWord(true);
                descriptionArea.setEditable(false); 

                JButton mes = new JButton("Interested");
                mes.setBounds(50, 255, 130, 30);

                JButton borrow = new JButton("Borrow");
                borrow.setBounds(220, 255, 100, 30);

                descriptionArea.setBounds(150, 100, 200, 130);
                descriptionArea.setLineWrap(true);
                descriptionArea.setWrapStyleWord(true);
                f2.setLayout(null);
                f2.add(name);f2.add(nlabel);f2.add(dlabel);f2.add(descriptionArea);f2.add(mes);f2.add(borrow);
                f2.setSize(400,400);
                f2.setVisible(true);
                f2.setLocationRelativeTo(null);
                f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                mes.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae)
                    {
                        send(owner_id,user,f2,status);
                    }
                });
                borrow.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (status=="borrowed")
                        {
                            JOptionPane.showMessageDialog(f2, "Resource unavailable", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        borrowResource(resource_id, owner_id, resourceName,f2);
                    }
                });
            }
        });
        resourcePanel.add(viewButton);

        return resourcePanel;
    }

    void send(int owner_id,int user_id,JFrame f2,String status)
    {
          PreparedStatement pst;
          try
          {
            pst = con.prepareStatement("insert into messages(sender_id,receiver_id,message_text,status)values(?,?,?,?)");
            pst.setInt(1,user_id);
            pst.setInt(2, owner_id);
            pst.setString(3,"Interested to borrow!");
            pst.setString(4,"sent");
            pst.executeUpdate();
            if(status!="borrowed")
            {
                JOptionPane.showMessageDialog(f2, "Request sent", "Success", JOptionPane.INFORMATION_MESSAGE); 
             } else {
                        JOptionPane.showMessageDialog(f2, "Resource unavailable", "Error", JOptionPane.ERROR_MESSAGE);
            }
            f2.dispose();
          }
          catch(SQLException e)
          {
            e.printStackTrace();
          }
    }

    void borrowResource(int resourceId, int ownerId, String resourceName,JFrame f2)
    {
        PreparedStatement pst;
        try
        {
            pst = con.prepareStatement("SELECT status FROM resources WHERE resource_id = ?");
            pst.setInt(1, resourceId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String status = rs.getString("status");
                
                if ("borrowed".equals(status)) {
                    JOptionPane.showMessageDialog(f2, "Resource unavailable", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
           
            pst = con.prepareStatement("UPDATE resources SET status = ? WHERE resource_id = ?");
           pst.setString(1, "borrowed");
           pst.setInt(2, resourceId);
           int i = pst.executeUpdate();
           if(i>0)
           {
            pst = con.prepareStatement("INSERT INTO borrowedresources (borrower_id, resource_id, borrow_date) VALUES (?, ?, ?)");
            pst.setInt(1, user); 
            pst.setInt(2, resourceId);
            pst.setDate(3, new java.sql.Date(System.currentTimeMillis())); 
            int result = pst.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(f, "You have successfully borrowed the resource!", "Success", JOptionPane.INFORMATION_MESSAGE);
                sendBorrowRequestMessage(ownerId,f2); 
            } else {
                JOptionPane.showMessageDialog(f, "Failed to borrow the resource.", "Error", JOptionPane.ERROR_MESSAGE);
                f2.dispose();
            }
           }
        }
    }
        catch(SQLException e)
          {
            e.printStackTrace();
          }
    }

    void sendBorrowRequestMessage(int ownerId,JFrame f2) {
        try {
            
            PreparedStatement pst = con.prepareStatement("INSERT INTO messages (sender_id, receiver_id, message_text, status) VALUES (?, ?, ?, ?)");
            pst.setInt(1, user); 
            pst.setInt(2, ownerId);
            pst.setString(3, "Your resource has been borrowed!");
            pst.setString(4, "sent");
            int result = pst.executeUpdate();
    
            if (result > 0) {
                JOptionPane.showMessageDialog(f, "Message sent to the owner!", "Success", JOptionPane.INFORMATION_MESSAGE);
                f2.dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
    
class Chat {
    Connection con;
    int user_id;
    int yPos = 0;
    Chat(Connection con, int user, JFrame f) {
        this.con = con;
        user_id = user;
        try {
            JPanel chatpane = new JPanel();
            chatpane.setLayout(new BoxLayout(chatpane, BoxLayout.Y_AXIS)); 
            chatpane.setBounds(f.getWidth() - 300, 0, 300, f.getHeight() - 100); 
            
            JScrollPane scrollPane = new JScrollPane(chatpane);
            scrollPane.setBounds(f.getWidth() - 300, 0, 300, f.getHeight() - 100);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            f.add(scrollPane);

            JPanel inputPanel = new JPanel();
            inputPanel.setBounds(f.getWidth() - 300, f.getHeight() - 100, 300, 100); 
            inputPanel.setLayout(null); 

            f.add(inputPanel); 
            PreparedStatement pst = con.prepareStatement("SELECT * FROM messages WHERE receiver_id = ?");
            pst.setInt(1, user_id);
            ResultSet rs = pst.executeQuery();
            boolean hasMessages = false;
            while (rs.next()) {
                hasMessages = true;
                PreparedStatement pstSender = con.prepareStatement("SELECT name FROM users WHERE id = ?");
                pstSender.setInt(1, rs.getInt("sender_id"));
                ResultSet r = pstSender.executeQuery();

            String senderName = "";
            if (r.next()) {
                 senderName = r.getString("name");
            }
                String message = rs.getString("message_text");
                JLabel messageLabel = new JLabel(message);
                JLabel info = new JLabel("From "+senderName+" On "+rs.getDate("date_sent"));
                JButton reply = new JButton("Reply");
                messageLabel.setBounds(10, yPos, 280, 20);
                yPos += 25;
                info.setBounds(10, yPos, 280, 20);
                yPos += 25;
                reply.setBounds(220, yPos, 70, 30);
                yPos += 35;
        
                chatpane.add(messageLabel);chatpane.add(info);chatpane.add(reply);
                reply.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ar)
                    {
                        JTextField answer = new JTextField(15); 
                        JButton ok = new JButton("Send");

                        JPanel replyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                         replyPanel.add(answer);
                        replyPanel.add(ok);

                        chatpane.add(replyPanel);
                        chatpane.revalidate();
                        chatpane.repaint();

                        chatpane.revalidate(); 
                        chatpane.repaint();
                        ok.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent ae)
                            {
                                answer.setVisible(true);ok.setVisible(true);
                                String message = answer.getText().trim();
                                if (!message.isEmpty()) {
                                    try {
                                                     
                                    PreparedStatement pstSend = con.prepareStatement(
                                "INSERT INTO messages (receiver_id, sender_id, message_text) VALUES (?, ?, ?)"
                        );
                        pstSend.setInt(1, user_id); 
                        pstSend.setInt(2, 1); 
                        pstSend.setString(3, message);
                        pstSend.executeUpdate();

                      
                        chatpane.add(new JLabel("You: " + message));
                        chatpane.revalidate();
                        chatpane.repaint();

                        answer.setText("");
                        answer.setVisible(false);
                        ok.setVisible(false);
                        chatpane.revalidate();
                        chatpane.repaint();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                            }
                        });
                    }
                });
            }
            if (!hasMessages) {
                chatpane.add(new JLabel("No messages yet."));
            }

            f.revalidate();
            f.repaint();    

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class PostResource
{
    Connection con;
    JFrame f;
    int user;
    JFrame f1 = new JFrame("Post Resource");
    PostResource(Connection con,JFrame f,int user)
    {
        this.con = con;
        this.f = f;
        this.user=user;
    }
    void display()
    {
        JLabel nlabel = new JLabel("Name");
        nlabel.setBounds(50, 50, 80, 30); 

        JTextField name = new JTextField();
        name.setBounds(150, 50, 180, 30); 

        JLabel dlabel = new JLabel("Description");
        dlabel.setBounds(50, 100, 100, 30);
        
        JTextArea address = new JTextArea();
        address.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JButton post = new JButton("Post");
        post.setBounds(130, 255, 80, 30);

        address.setBounds(150, 100, 200, 130);
        address.setLineWrap(true);
        address.setWrapStyleWord(true);
        f1.setLayout(null);
        f1.add(name);f1.add(nlabel);f1.add(dlabel);f1.add(address);f1.add(post);
        f1.setSize(400,400);
        f.setVisible(true);
        f1.setVisible(true); 
        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f1.setLocationRelativeTo(null);

        post.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                PreparedStatement pst;
                try
                {
                    pst = con.prepareStatement("insert into resources (owner_id, resource_name, description, status)values(?, ?, ?, ?)");
                    pst.setInt(1, user);
                    pst.setString(2, name.getText());
                    pst.setString(3,address.getText());
                    pst.setString(4,"posted");
                    int i=pst.executeUpdate();
                        if (i == 1) {
                            JOptionPane.showMessageDialog(f, "Resource posted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            f1.dispose();  
                            f.setVisible(true);  
                        } else {
                            JOptionPane.showMessageDialog(f, "Resource could not be posted", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        pst.close();
                    } 
                catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }
}

class BorrowedResources {

    private JFrame frame;
    private Connection con;
    private int userId;
    private JPanel borrowedPanel;
    private JScrollPane scrollPane;

    public BorrowedResources(JFrame parentFrame, int userId, Connection con) {
        this.userId = userId;
        this.con = con;

        frame = new JFrame("Borrowed Resources");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(parentFrame);
        frame.setLayout(new BorderLayout());

        borrowedPanel = new JPanel();
        borrowedPanel.setLayout(new BoxLayout(borrowedPanel, BoxLayout.Y_AXIS));
        
        scrollPane = new JScrollPane(borrowedPanel);
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    public void view() {
        loadBorrowedResources();
        frame.setVisible(true);
    }

    private void loadBorrowedResources() {
        borrowedPanel.removeAll();

        try {
            String query = "SELECT r.resource_name, br.borrow_date, r.resource_id " +
                           "FROM borrowedresources br " +
                           "JOIN resources r ON br.resource_id = r.resource_id " +
                           "WHERE br.borrower_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            boolean hasResults = false;
            while (rs.next()) {
                String resourceName = rs.getString("resource_name");
                Date borrowDate = rs.getDate("borrow_date");
                int resourceId = rs.getInt("resource_id");
                
                JPanel resourcePanel = createBorrowedResourcePanel(resourceName, borrowDate, resourceId,1);
                borrowedPanel.add(resourcePanel);
                hasResults = true;
            }

            if (!hasResults) {
                JPanel noResultsPanel = createBorrowedResourcePanel("No borrowed resources found.", null, 0,0);
                borrowedPanel.add(noResultsPanel);
            }

            borrowedPanel.revalidate();
            borrowedPanel.repaint();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading borrowed resources.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createBorrowedResourcePanel(String resourceName, Date borrowDate, int resourceId,int flag) {
        JPanel resourcePanel = new JPanel();
        resourcePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        if(flag!=1)
        {
            JLabel resourceLabel = new JLabel(resourceName);
            resourcePanel.add(resourceLabel);
        }
        else
        {
        JLabel resourceLabel = new JLabel("Resource: " + resourceName);
        JLabel dateLabel = new JLabel("Borrowed on: " + (borrowDate != null ? borrowDate.toString() : ""));
        
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnResource(resourceId);
            }
        });
        resourcePanel.add(resourceLabel);
        resourcePanel.add(dateLabel);
        resourcePanel.add(returnButton);
        }
        return resourcePanel;
    }

    private void returnResource(int resourceId) {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to return this resource?", 
                                                      "Return Resource", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement pst = con.prepareStatement("UPDATE resources SET status = ? WHERE resource_id = ?");
                pst.setString(1, "posted");
                pst.setInt(2, resourceId);
                pst.executeUpdate();

                pst = con.prepareStatement("DELETE FROM borrowedresources WHERE resource_id = ? AND borrower_id = ?");
                pst.setInt(1, resourceId);
                pst.setInt(2, userId);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Resource returned successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                loadBorrowedResources();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error returning resource.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class Posted
{
    Connection con;
    JFrame f;
    int user;
    JFrame frame;
    JPanel PostedPanel;
    JScrollPane scrollPane;
    JFrame f1 = new JFrame("Posted Resource");
    Posted(Connection con,JFrame f,int user)
    {
        this.con = con;
        this.f = f;
        this.user=user;
        frame = new JFrame("Posted Resources");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(f);
        frame.setLayout(new BorderLayout());
        PostedPanel = new JPanel();
        PostedPanel.setLayout(new BoxLayout(PostedPanel, BoxLayout.Y_AXIS));
        
        scrollPane = new JScrollPane(PostedPanel);
        frame.add(scrollPane, BorderLayout.CENTER);
    }
    public void view() {
        loadPostedResources();
        frame.setVisible(true);
    }

    private void loadPostedResources() {
        PostedPanel.removeAll();

        try {
            String query = "SELECT * FROM resources WHERE owner_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, user);
            ResultSet rs = pst.executeQuery();

            boolean hasResults = false;
            while (rs.next()) {
                String resourceName = rs.getString("resource_name");
                int resourceId = rs.getInt("resource_id");
                
                JPanel resourcePanel = createPostedResourcePanel(resourceName,resourceId,1);
                PostedPanel.add(resourcePanel);
                hasResults = true;
            }

            if (!hasResults) {
                JPanel noResultsPanel = createPostedResourcePanel("No borrowed resources found.", 0,0);
                PostedPanel.add(noResultsPanel);
            }

            PostedPanel.revalidate();
            PostedPanel.repaint();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading borrowed resources.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createPostedResourcePanel(String resourceName,int resourceId,int flag) {
        JPanel resourcePanel = new JPanel();
        resourcePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        if(flag!=1)
        {
            JLabel resourceLabel = new JLabel(resourceName);
            resourcePanel.add(resourceLabel);
        }
        else
        {
        JLabel resourceLabel = new JLabel("Resource: " + resourceName);
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnResource(resourceId);
            }
        });
        resourcePanel.add(resourceLabel);
        resourcePanel.add(deleteButton);
        }
        return resourcePanel;
    }

    private void returnResource(int resourceId) {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this resource?", 
                                                      "Delete Resource", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement pst = con.prepareStatement("DELETE FROM resources WHERE resource_id = ?");
                pst.setInt(1, resourceId);
                pst.executeUpdate();

                pst = con.prepareStatement("DELETE FROM borrowedresources WHERE resource_id = ?");
                pst.setInt(1, resourceId);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Resource deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                loadPostedResources();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error deleting resource.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class TotalResourcesAvailable {
    JFrame f;
    Connection con;
    JScrollPane scrollPane;
    JPanel resultPane;

    TotalResourcesAvailable(Connection con,JFrame f) {
        this.con = con;
        this.f = f;
        initializeView();
    }

    void initializeView() {
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);

        resultPane = new JPanel();
        resultPane.setLayout(new BoxLayout(resultPane, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(resultPane);
        scrollPane.setBounds(50, 100, 700, 400);
        f.add(scrollPane);

        loadResources(); 

        f.setVisible(true);
    }

    void loadResources() {
        resultPane.removeAll();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM resources");
            ResultSet rs = pst.executeQuery();

            boolean hasResults = false;
            while (rs.next()) {
                String resourceName = rs.getString("resource_name");
                int resourceId = rs.getInt("resource_id");
                String desc = rs.getString("description");
                int ownerId = rs.getInt("owner_id");
                String status = rs.getString("status");

                JPanel resourcePanel = createResourcePanel(resourceName, resourceId, desc, ownerId, status);
                resultPane.add(resourcePanel);
                hasResults = true;
            }

            if (!hasResults) {
                resultPane.add(new JLabel("No resources available."));
            }

            resultPane.revalidate();
            resultPane.repaint();
        } catch (SQLException ex) {
            ex.printStackTrace();
            resultPane.add(new JLabel("Error loading resources."));
        }
    }

    JPanel createResourcePanel(String resourceName, int resourceId, String desc, int ownerId, String status) {
        JPanel resourcePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel resourceLabel = new JLabel(resourceName);
        resourcePanel.add(resourceLabel);

        JButton viewButton = new JButton("View");
        viewButton.addActionListener(e -> showResourceDetails(resourceName, desc, resourceId, ownerId, status));
        resourcePanel.add(viewButton);

        return resourcePanel;
    }

    void showResourceDetails(String resourceName, String desc, int resourceId, int ownerId, String status) {
        JFrame detailsFrame = new JFrame("Resource Details");
        detailsFrame.setLayout(null);
        detailsFrame.setSize(400, 400);

        JLabel nameLabel = new JLabel("Name: " + resourceName);
        nameLabel.setBounds(20, 20, 300, 30);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setBounds(20, 60, 100, 30);

        JTextArea descArea = new JTextArea(desc);
        descArea.setBounds(20, 100, 340, 100);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEditable(false);

        JLabel statusLabel = new JLabel("Status: " + status);
        statusLabel.setBounds(20, 220, 200, 30);

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(150, 300, 100, 30);
        closeButton.addActionListener(e -> detailsFrame.dispose());

        detailsFrame.add(nameLabel);
        detailsFrame.add(descLabel);
        detailsFrame.add(descArea);
        detailsFrame.add(statusLabel);
        detailsFrame.add(closeButton);

        detailsFrame.setVisible(true);
    }
}
public class Test {
    public static void main(String[] args) {
        Connection con = null;
        try
        {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/resource_platform", "root", "");
            new Login(con);
        }
        catch (SQLException ae)
                {
                    System.out.println("Error connecting to the database: " + ae.getMessage());
                }
        /*finally
        {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                    System.out.println("Connection closed.");
                }
            } catch (Exception e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }*/
                
    }
} 