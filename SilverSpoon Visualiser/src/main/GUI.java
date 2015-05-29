/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XMLHandling.XMLHandler;
import XMLHandling.XMLHandlerException;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.w3c.dom.Document;
import sun.misc.IOUtils;

/**
 *
 * @author matej
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        String[] model = new String[3];
        model[1] = "BeagleBoneBlack";
        model[0] = "Raspberry Pi B+";
        model[2] = "CubieBoard 2";
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(model));

        String[] modelOutput = new String[2];
        modelOutput[0] = "svg";
        modelOutput[1] = "html";
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(modelOutput));

        saveTo.setText(System.getProperty("user.dir").toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        xmlConfigFile = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        saveTo = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        xmlConfigFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xmlConfigFileActionPerformed(evt);
            }
        });

        jButton2.setText("Start");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        saveTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveToActionPerformed(evt);
            }
        });

        jButton3.setText("Browse");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Show output");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Choose desk :");

        jLabel2.setText("Save as :");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Save file to");

        jLabel4.setText("XML configuration file");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(xmlConfigFile, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveTo, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(40, 40, 40)
                                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xmlConfigFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(saveTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jCheckBox1))
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void xmlConfigFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xmlConfigFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xmlConfigFileActionPerformed

    /*private String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }*/

    public static final String header = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<!-- Generator: Adobe Illustrator 16.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->\n"
            + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">";
    public static final String header2 = "<?xml version='1.0' encoding='UTF-8' standalone='no'?>\n"
            + "<!-- Created with Fritzing (http://www.fritzing.org/) -->";

    private File fileComplete;
    private String content = new String();

    /*private void saveFile(String fileName) throws IOException {
        fileComplete = new File(fileName);
        PrintWriter writer = new PrintWriter(fileComplete.getAbsolutePath(), "UTF-8");
        if (jComboBox2.getSelectedIndex() == 0) {
            writer.println(content
                    + "</svg>");
        } else {
            writer.println("<html> <body>" + content
                    + "</svg> </body> </html>");

        }
        writer.close();
    }*/

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        BackendHandler bh = new BackendHandler();
        
        try {
            bh.setInputXMLPath(xmlConfigFile.getText());
            bh.setOutputDirPath(saveTo.getText());
            bh.setShowOut(jCheckBox1.isSelected());
            bh.setBoardType(jComboBox1.getSelectedIndex());
            bh.setOutputType(jComboBox2.getSelectedIndex());

            bh.drawBoard();
        } catch (BackendHandlerException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        /*try {
            int index = jComboBox1.getSelectedIndex();
            //XMLHandler xmlh = new XMLHandler();
            Document doc = XMLHandler.loadNewXML(xmlConfigFile.getText());
            
            switch (index) {

                case 0: {

                    try {
                        pathCreator pc = new pathCreator(XMLHandler.parseRoute(doc));
                        pc.run(saveTo.getText(), index);
//                        InputStream in
//                                = getClass().getResourceAsStream("/incompleteDesks/beagleboneblack.txt");
//                        content = convertStreamToString(in);
//                        //content = readFile(System.getProperty("user.dir") + "/incompleteSvgs/beagleboneblack.svg");
//                        if (jComboBox2.getSelectedIndex() == 0) {
//                            saveFile(saveTo.getText() + "/beagleboneblack_full.svg");
//                        } else {
//                            saveFile(saveTo.getText() + "/beagleboneblack_full.html");
//                        }
                    } catch (IOException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case 1: {
                    try {
                        pathCreator pc = new pathCreator(XMLHandler.parseRoute(doc));
                        pc.run(saveTo.getText(), index);
//                        InputStream in
//                                = getClass().getResourceAsStream("/incompleteDesks/raspberry_pi_b+_breadboard.txt");
//                        content = convertStreamToString(in);
//                        //content = readFile(System.getProperty("user.dir") + "/incompleteSvgs/raspberry_pi_b+_breadboard.svg");
//                        if (jComboBox2.getSelectedIndex() == 0) {
//                            saveFile(saveTo.getText() + "/raspberry_pi_b+_breadboard_full.svg");
//                        } else {
//                            saveFile(saveTo.getText() + "/raspberry_pi_b+_breadboard_full.html");
//                        }
//                    } catch (IOException ex) {
//                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    } catch (XMLHandlerException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case 2: {
                    try {
                        pathCreator pc = new pathCreator(XMLHandler.parseRoute(doc));
                        pc.run(saveTo.getText(), index);
//                        InputStream in
//                                = getClass().getResourceAsStream("/incompleteDesks/CubieBoard2.txt");
//                        content = convertStreamToString(in);
//                        //content = readFile(System.getProperty("user.dir") + "/incompleteSvgs/CubieBoard2.svg");
//                        if (jComboBox2.getSelectedIndex() == 0) {
//                            saveFile(saveTo.getText() + "/CubieBoard2_full.svg");
//                        } else {
//                            saveFile(saveTo.getText() + "/CubieBoard2_full.html");
//                        }
                    } catch (IOException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }

            if (jCheckBox1.isSelected()) {
                Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        URI uri = new URI("file://" + replaceWhiteSpaces(fileComplete.getAbsolutePath()));
                        desktop.browse(uri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (XMLHandlerException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }//GEN-LAST:event_jButton2ActionPerformed

    /*private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private String replaceWhiteSpaces(String path) {
        return path.replaceAll("\\s+", "%20");
    }*/

    private File file;

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        file = chooser.getSelectedFile();
        String fileName = file.getAbsolutePath();
        if (!fileName.substring(fileName.length() - 4).equals(".xml")) {
            JOptionPane.showMessageDialog(null, "This is not a xml file, please choose correct file.");
            jButton2.setEnabled(false);
        } else {
            jButton2.setEnabled(true);
        }
        xmlConfigFile.setText(fileName);
        
        /*JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        file = chooser.getSelectedFile();
        String fileName = file.getAbsolutePath();
        if (!fileName.substring(fileName.length() - 4).equals(".xml") || xmlConfigFile.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "This is not a xml file, please choose correct file.");
            jButton2.setEnabled(false);
        } else {
            jButton2.setEnabled(true);
        }
        xmlConfigFile.setText(fileName);*/

    }//GEN-LAST:event_jButton1ActionPerformed

    private void saveToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveToActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("choosertitle");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            saveTo.setText(chooser.getSelectedFile().getAbsolutePath());
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    public void run() {
        new GUI().setVisible(true);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField saveTo;
    private javax.swing.JTextField xmlConfigFile;
    // End of variables declaration//GEN-END:variables
}
