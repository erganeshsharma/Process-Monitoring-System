package in.ganeshsharma.ProcessMonitoring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AbstractDocument.Content;

/**
 *
 * @author Ganesh Sharma
 */
class SaveTextFileFilter extends FileFilter
 {
    public boolean accept(File f)
   {
        if (f.isDirectory())
        	return true;
         String s = f.getName();

        return s.endsWith(".txt");
   }

   public String getDescription()
  {
       return "*.txt";
  }

}

class JavaFileFilter extends FileFilter{
	public boolean accept(File file)
	{
		if(file.getName().endsWith(".txt")) return true;
		//if(file.getName().endsWith(".java")) return true;
		if(file.isDirectory()) return true;
		return false;
	}
	public String getDescription()
	{
		return "Result text file";
	}
}
public class ProcessMonitoring extends JFrame implements ActionListener
{
    FileOutputStream fop = null;
    File file;
    int flag=0;
    String data;
    String ext="";
    String op="",temp;
    //StringBuffer op,temp;
    Process p;
    BufferedReader br;
    JPanel panel1,panel2,panel3;
    JTextArea displayArea;
    ArrayList<String> stringList = new ArrayList<String>();
    JButton submit,cancel,refresh;
    JTextArea msgArea;
    JLabel lblPro,lblTime,lblTotal;
    JComboBox proc,interval,jcomTotal;
    JFileChooser jfc;
    //String processName[]={"Select process","vlc.exe","netbeans.exe"};
    String[] processName;
    String interValue[] = {"Time(Seconds)","10","20","30","40","50"};
    String loopValue[]= {"5","10","12","20","25","30"};
    Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public ProcessMonitoring() throws IOException, InterruptedException
    {
        super("Process Monitoring Application");
 panel1=new JPanel();
 panel2=new JPanel();
 panel3=new JPanel();
panel1.setPreferredSize(new Dimension(100,100));
panel2.setPreferredSize(new Dimension(100,100));
panel3.setPreferredSize(new Dimension(100,100));
panel1.setOpaque(true);
panel2.setOpaque(true);
panel3.setOpaque(true);
Records();
jfc=new JFileChooser();
        jfc.setFileFilter(new JavaFileFilter());
         processName = new String[stringList.size()];
         processName = stringList.toArray(processName);
         /*msgArea = new JTextArea(screenSize.width,screenSize.height);
         msgArea.setAutoscrolls(true);
         msgArea.setBackground(Color.YELLOW);
         msgArea.setForeground(Color.blue);*/
         displayArea = new JTextArea();
         displayArea.setBackground(Color.lightGray);
         displayArea.setForeground(Color.BLUE);
        lblPro = new JLabel("Process name : ");
        lblTime = new JLabel("Time interval : ");
        lblTotal = new JLabel("Total no. of readings : ");
        submit = new JButton("Submit");
        cancel = new JButton("Cancel");
        refresh = new JButton("Refresh");
        proc = new JComboBox(processName);
        interval = new JComboBox(interValue);
        jcomTotal = new JComboBox(loopValue);
        panel1.add(lblPro);
        panel1.add(proc);
        panel1.add(lblTime);
        panel1.add(interval);
        panel1.add(lblTotal);
        panel1.add(jcomTotal);
        panel2.add(new JScrollPane(displayArea));
        panel3.add(submit);
        panel3.add(refresh);
        panel3.add(cancel);
        panel1.setBackground(Color.green);
        panel2.setForeground(Color.BLUE);
        panel3.setBackground(Color.green);
        //setLayout(new FlowLayout());
        add(panel1,BorderLayout.NORTH);
        add(panel2,BorderLayout.CENTER);
        add(panel3,BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenSize.width,screenSize.height-30);
        setVisible(true);
        submit.addActionListener(this);
        refresh.addActionListener(this);
        cancel.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()== submit)
        {
            if(proc.getSelectedItem().equals("Select process") || interval.getSelectedItem().equals("Time(Seconds)"))
            JOptionPane.showMessageDialog(null,"Please select proper process & time interval ...!", "ERROR", JOptionPane.ERROR_MESSAGE);
            else
            {
                int splashTime;
                splashTime = Integer.parseInt((String)interval.getSelectedItem())*Integer.parseInt((String)jcomTotal.getSelectedItem());
             JOptionPane.showMessageDialog(null,"Please wait for "+splashTime+" seconds...!", "Processing...", JOptionPane.INFORMATION_MESSAGE);
                monitor();
        //System.out.println("\n\n Lastly again"+op);
                //msgArea.setText(op);
        //JOptionPane.showMessageDialog(null,"Successfully done...!!", "COMPLETED", JOptionPane.INFORMATION_MESSAGE);
                int dialogButton = JOptionPane.YES_NO_OPTION;
int dialogResult = JOptionPane.showConfirmDialog(this, "Would you like to save the result?", "Save the result",dialogButton);
if(dialogResult==0)
{
    try
{
JFileChooser jFileChooser = new JFileChooser();
jFileChooser.addChoosableFileFilter(new SaveTextFileFilter());
jFileChooser.setSelectedFile(new File("fileToSave"));
int responce = jFileChooser.showSaveDialog(null);
if(responce==JFileChooser.APPROVE_OPTION)
{
    String extension=jFileChooser.getFileFilter().getDescription();
    if(extension.equals("*.txt"))
      {
          ext=".txt";
      }
}

fop=new FileOutputStream(jFileChooser.getSelectedFile()+ext);
byte[] contentInBytes = op.getBytes();
fop.write(contentInBytes);
fop.flush();
fop.close();
}    catch (IOException e) {
	e.printStackTrace();
	} finally {
	try {
	if (fop != null) {
		fop.close();
		}
	} catch (IOException e) {
	e.printStackTrace();
}
//else

        }
            }
            }
        }
        if (ae.getSource()== cancel)
        {
            System.exit(0);
        }
        if (ae.getSource()== refresh)
        {
            try {
                new ProcessMonitoring();
                this.dispose();
            } catch (IOException ex) {
                Logger.getLogger(ProcessMonitoring.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProcessMonitoring.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }

    public void Records() throws IOException, InterruptedException
    {
        String data;
        int len;
        int spacePosition=0;
        stringList.add("Select process");
            //p = Runtime.getRuntime().exec("tasklist /v /fi \"Imagename eq "+in+"\"");
            p = Runtime.getRuntime().exec("tasklist");
            br= new BufferedReader(new InputStreamReader(p.getInputStream()));
            br.readLine();
            br.readLine();
            br.readLine();
            data = br.readLine();
        while(data !=null)
        {
          len=data.length();
          for(int k=0;k<len;k++)
          {
           if(data.charAt(k)==' ')
           {
                spacePosition = k;
                break;
           }
         }
          stringList.add(data.substring(0, spacePosition));
          data = br.readLine();
        }
        br.close();
        p.destroy();
       }

    public void monitor()
    {
        for(int j=0;j<Integer.parseInt((String) jcomTotal.getSelectedItem());j++)
            {
                try
                {
            p = Runtime.getRuntime().exec("tasklist /v /fi \"Imagename eq "+(String)proc.getSelectedItem()+"\"");
            br= new BufferedReader(new InputStreamReader(p.getInputStream()));
            if(flag==0)
            {
             /*System.out.println(br.readLine());
             System.out.println(br.readLine());
             System.out.println(br.readLine());   */
                for(int g=0;g<3;g++)
                {
                    temp=br.readLine();
                    System.out.println(temp);
                    displayArea.append(temp+"\n");
                    panel2.validate();
                   op=op+System.lineSeparator()+temp;
                   
                }
                    //msgArea.append(br.readLine());
             flag=1;
            }
            data = br.readLine();
        while(data !=null)
        {
            if((data.indexOf((String) proc.getSelectedItem()))!=-1)
            {
            System.out.println(data);
            displayArea.append(data+"\n");
            panel2.validate();
                op=op+System.lineSeparator()+data;
                //msgArea.append("\n"+data);
            break;
            }
          data = br.readLine();
        }
        br.close();
        p.destroy();
        Thread.sleep(Integer.parseInt((String)(interval.getSelectedItem()+"000")));
       }catch(Exception e){}
            }

    }
}
