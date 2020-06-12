/*
*File: BenchmarkReport.java
*Author: Emily McPherson
*Date: 6/12/2020
*Purpose: GUI that allows input file to be selected and displays results of BenchmarkSorts
*/

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

public class BenchmarkReport extends JFrame {

  private JPanel reportPanel;
  private JPanel buttonPanel;
  private JScrollPane sp;
  private JTable reportTable;
  private JFileChooser fc;
  private JButton openFile;
  private JButton runSort;
  private JLabel fileLabel;

  public BenchmarkReport(String name){
    super(name);
    setLayout(new BorderLayout());
    setSize(1200, 1200);
    setLocationRelativeTo(null);
    setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

    //panels
    reportPanel = new JPanel();
    buttonPanel = new JPanel();

    //elements
    openFile = new JButton("Open");
    fileLabel = new JLabel("no file chosen");
    buttonPanel.add(openFile);
    buttonPanel.add(fileLabel);
    //report
    //String[][] data = new String[][]{};
    String[] columns = new String[]{"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};
    DefaultTableModel mod = new DefaultTableModel();
    for (int i = 0; i < columns.length; i++){
      mod.addColumn(columns[i]);
    }
    reportTable = new JTable(mod);
    reportTable.setFillsViewportHeight(true);
    sp = new JScrollPane(reportTable);

    //open button actionlistener
    openFile.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            JFrame frame = new JFrame("Select Text File");
            fc = new JFileChooser(".");
            int f = fc.showOpenDialog(frame);
            if (f == JFileChooser.APPROVE_OPTION)
              {
                File file = fc.getSelectedFile();
                fileLabel.setText(fc.getSelectedFile().getPath());
                //open chosen file
                //for each line, save to a temp String[]
                //add temp to mod
                try {
                  Scanner sc = new Scanner(file);
                  while(sc.hasNextLine()){
                    String nl = sc.nextLine();
                    String[] nextRow = nl.split("\\s+");
                    mod.addRow(nextRow);
                  }
                } catch (FileNotFoundException fnfe){
                  JFrame errorFrame = new JFrame();
                  JOptionPane.showMessageDialog(errorFrame,
                  "File Not Found", "Error",
                  JOptionPane.WARNING_MESSAGE);
                }
                //mod.addRow(new String[]{"100", "509.0", "0.0", "72424.0", "76.06"});
              }
          }//end actionPerformed
        });//end ActionListener

     this.add(sp, BorderLayout.CENTER);
     this.add(buttonPanel, BorderLayout.NORTH);

     //display the GUI
     this.setVisible(true);
  } // end constructor



  public static void main(String[] args){
    BenchmarkReport build = new BenchmarkReport("Benchmark Report");
  }//end Main
}//end class
