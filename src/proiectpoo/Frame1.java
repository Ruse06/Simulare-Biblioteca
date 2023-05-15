package proiectpoo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Frame1 extends JFrame implements ActionListener
{
    //Font myFont = new Font("Serif", Font.BOLD, 12);
    JButton bImprumut, bInapoiere;
    Frame1() throws IOException{
        super("Biblioteca");
        JLabel text;
        JPanel p1, p2;
        text = new JLabel("Alegeti urmatoarea optiune:");
        bImprumut = new JButton("Imprumut");
        //bImprumut.setFont(myFont);
        bInapoiere = new JButton("Inapoiere");
        //bInapoiere.setFont(myFont);
        bImprumut.addActionListener(this);
        bInapoiere.addActionListener(this);
        p1 = new JPanel();
        p2 = new JPanel();
        p1.setLayout(new GridLayout(1,2));
        p1.add(bImprumut);
        p1.add(bInapoiere);
        p1.setPreferredSize(new Dimension(250, 50));
        p2.setPreferredSize(new Dimension(150, 30));
        p2.add(text);
        getContentPane().add(p1, BorderLayout.CENTER);
        getContentPane().add(p2, BorderLayout.NORTH);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==bImprumut)
        {
            Frame2 f2;
            try {
                f2 = new Frame2();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Frame1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Frame1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        if(e.getSource()==bInapoiere)
        {
            Frame3 f3 = new Frame3() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
        }
    }
}
