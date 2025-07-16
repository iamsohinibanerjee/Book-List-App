package guiapplicationpack;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class MainPanel extends JPanel
{
    private JLabel lblListCaption;
    private JTextField txtBookName;
    private JComboBox combxBookCat;
    private String[] bookCat = {"Classic","Romance","Comic Book","Detective","Fantasy","Historical Fiction","Horror","Adventure","Literary Fiction","Short Stories","Womens' Fiction","Thrillers","Biographies","Cook Books","Essays"};
    private Vector<String> bookList = new Vector<String>();
    private JList lstBookList;
    private JScrollPane spnBookList;
    private JButton btnAddNew,btnRemove,btnClear,btnExit;
    
    private JLabel makeLabel(String cap,int x,int y,int w,int h,int mode)
    {
        JLabel temp = new JLabel(cap);
        if(mode == 1)
        {
            temp.setFont(new Font("Verdana", 1, 30));
            temp.setOpaque(true);
            temp.setBackground(Color.ORANGE);
            temp.setForeground(Color.BLUE);
            temp.setHorizontalAlignment(JLabel.CENTER);
            temp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));            
        }
        else
            temp.setFont(new Font("Courier New", 1, 18));
        temp.setBounds(x,y,w,h);
        super.add(temp);
        return temp;
    }
    private JTextField makeTextField(int x,int y,int w,int h)
    {
        JTextField temp = null;
        temp = new JTextField();
        temp.setFont(new Font("Courier New",1,16));
        temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        temp.setBounds(x,y,w,h);
        temp.setHorizontalAlignment(JTextField.CENTER);
        temp.addKeyListener(new KeyListener() 
        {
            @Override
            public void keyTyped(KeyEvent e) 
            {
                JTextField ob = (JTextField)e.getSource();
                if(ob.getText().length() == 20) e.setKeyChar('\0');
                if(e.getKeyChar() == KeyEvent.VK_ENTER)
                {
                    String rec = String.format("%-20s|%-20s",txtBookName.getText(),combxBookCat.getSelectedItem());
                    bookList.add(rec);
                    lstBookList.setListData(bookList);
                    txtBookName.selectAll();
                    combxBookCat.setSelectedIndex(0);
                    btnClear.setEnabled(true);
                }
            }

            @Override
            public void keyPressed(KeyEvent e){}

            @Override
            public void keyReleased(KeyEvent e)
            {
                JTextField ob = (JTextField)e.getSource();
                if(ob.getText().length() == 0)
                    btnAddNew.setEnabled(false);
                else
                    btnAddNew.setEnabled(true);
            }
        });
        add(temp);
        return temp;
    }
    private JComboBox makeComboBox(int x,int y,int w,int h,String[] items)
    {
        JComboBox temp = new JComboBox(items);
        temp.setFont(new Font("Verdana", 1, 12));
        temp.setBounds(x,y,w,h);
        add(temp);
        return temp;
    }
    private JScrollPane makeScrollPane(int x,int y,int w,int h,JList list)
    {
        JScrollPane temp = new JScrollPane(list);
        temp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        temp.setBounds(x,y,w,h);
        add(temp);
        return temp;
    }
    private JButton makeButton(String caption,int x,int y,int w,int h)
    {
        JButton temp = new JButton(caption);
        temp.setBounds(x,y,w,h);
        temp.setFont(new Font("Verdana", 1, 12));
        temp.setMargin(new Insets(0,0,0,0));
        temp.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Object ob = e.getSource();
                if(ob == btnAddNew)
                {
                    String rec = String.format("%-20s|%-20s",txtBookName.getText(),combxBookCat.getSelectedItem());
                    bookList.add(rec);
                    lstBookList.setListData(bookList);
                    txtBookName.selectAll();
                    combxBookCat.setSelectedIndex(0);
                    btnClear.setEnabled(true);
                    txtBookName.grabFocus();
                }
                else if(ob == btnRemove)
                {
                    bookList.remove(lstBookList.getSelectedIndex());
                    lstBookList.setListData(bookList);
                    btnRemove.setEnabled(false);
                    if(bookList.size()==0) btnClear.setEnabled(false);
                }
                else if(ob == btnClear)
                {
                    int confirm = JOptionPane.showConfirmDialog(null,"Want to delete all?","Confirm",JOptionPane.YES_NO_OPTION);
                    if(confirm == JOptionPane.YES_OPTION)
                    {
                        bookList.clear();
                        lstBookList.setListData(bookList);
                        btnRemove.setEnabled(false);
                        btnClear.setEnabled(false);
                    }
                }
                else if(ob == btnExit)
                {
                    System.exit(0);
                }
            }
        });
        super.add(temp);
        return temp;
    }
    public MainPanel()
    {
        makeLabel("Bibliography",       10,10,610,50,1);
        makeLabel("Enter Book Name",    10,70,300,30,2);
        txtBookName = makeTextField(300,70,320,30);
        makeLabel("Select Book Category",10,120,300,30,2);
        combxBookCat = makeComboBox(300,120,320,30,bookCat);
        makeLabel("List of Books with Category:",10,170,320,30,2);
        
        String cap = String.format("%-20s|%-20s","Name of Books","Book Category");
        lblListCaption = new JLabel(cap);
        lblListCaption.setOpaque(true);
        lblListCaption.setBackground(Color.red);
        lblListCaption.setForeground(Color.yellow);
        lblListCaption.setFont(new Font("Courier New",1,14));
        
        lstBookList = new JList(bookList);
        lstBookList.setFont(new Font("Courier New",1,14));
        lstBookList.addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e) 
            {
                btnRemove.setEnabled(true);
            }
        });
        
        spnBookList = makeScrollPane(10,210,470,180,lstBookList);
        spnBookList.setColumnHeaderView(lblListCaption);
        
        btnAddNew = makeButton("Add New",490,210,130,30);
        btnAddNew.setEnabled(false);
        btnRemove = makeButton("Remove Item",490,260,130,30);
        btnRemove.setEnabled(false);
        btnClear = makeButton("Clear Items",490,310,130,30);
        btnClear.setEnabled(false);
        btnExit = makeButton("Exit",490,360,130,30);
    }
}

class MainFrame extends JFrame
{
    private MainPanel panel = null;
    public MainFrame()
    {
        panel = new MainPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(215,240,160));
        super.add(panel);
    }
}
public class MainClass
{
    public static void main(String[] args)
    {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
        frame.setSize(650,450);
        frame.setTitle("Book List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}