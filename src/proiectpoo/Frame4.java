package proiectpoo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static proiectpoo.Frame2.scriereImprumuturi;

public class Frame4 extends JFrame{
    JTextArea ANumeSiPrenume, ACNP, APlata, ACarti;
    JButton bOK = new JButton("OK");
    Ascultator3 buton = new Ascultator3();
    ArrayList<Imprumut> imprumuturi = new ArrayList<Imprumut>();
    ArrayList<Carte> carti = new ArrayList<Carte>();
    Frame4() throws FileNotFoundException, IOException
    {
        super("Returnare");
        
        carti = citesteCarti();
        imprumuturi = citesteImprumuturi();
        
        JPanel p1,p2;
        JLabel LNumeSiPrenume, LCNP, LCartiImprumutate, LPlata;
        LNumeSiPrenume = new JLabel("Nume si prenume");
        LCNP = new JLabel("CNP");
        LCartiImprumutate = new JLabel("Carti imprumutate");
        LPlata = new JLabel("Total de plata");
        ANumeSiPrenume = new JTextArea(5,3);
        ACNP = new JTextArea(5,3);
        APlata = new JTextArea(5,3);
        ACarti = new JTextArea(5, 3);
        
        p1 = new JPanel();
        p1.setLayout(new GridLayout(4, 2));
        p1.setPreferredSize(new Dimension(500, 100));
        p1.add(LNumeSiPrenume);
        p1.add(ANumeSiPrenume);
        p1.add(LCNP);
        p1.add(ACNP);
        p1.add(LCartiImprumutate);
        p1.add(ACarti);
        p1.add(LPlata);
        p1.add(APlata);
        
        p2 = new JPanel();
        p2.add(bOK);
        bOK.addActionListener((ActionListener) buton);
        getContentPane().add(p1, BorderLayout.NORTH);
        getContentPane().add(p2, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
   class Ascultator3 implements ActionListener
   {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == bOK)
            {
                try {
                    scriereArhiva("arhiva.txt", ANumeSiPrenume.getText(), ACNP.getText(), ACarti.getText(), APlata.getText());
                    removeImprumut(imprumuturi, ACarti.getText());
                    int c = CautaTitlu(ACarti.getText(), carti);
                    carti.get(c).setStareImprumut(false);
                    editFisier("D:\\Java Problems\\ProiectPOO\\carti.txt", carti);
                    scriereImprumuturi("D:\\Java Problems\\ProiectPOO\\imprumuturi.txt", imprumuturi);
                    dispose();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Frame4.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Frame4.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
       
    }
   
    public void UmplereText(String NumeSiPrenume, String CNP, String Carte, String pret)
    {
        ANumeSiPrenume.setText(NumeSiPrenume);
        ACNP.setText(CNP);
        ACarti.setText(Carte);
        APlata.setText(pret);
    }
    
    public void removeImprumut(ArrayList<Imprumut> imprumuturi, String titlu) {
        for(int i=0; i<imprumuturi.size(); i++)
        {
            if (imprumuturi.get(i).getNumeCarte().equals(titlu)) {
                imprumuturi.remove(i);
            }
        }
    }
    
    public static void scriereArhiva(String nume_fisier, String nume, String CNP, String titlu, String plata) throws FileNotFoundException
    {
        try{
            FileWriter fw = new FileWriter(nume_fisier, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(nume + " " +  CNP + " " +titlu + " " + plata);
            
            pw.flush();
            pw.close();
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR!!!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ArrayList<Imprumut> citesteImprumuturi() throws FileNotFoundException, IOException
    {
        ArrayList<Imprumut> imprumuturi = new ArrayList<Imprumut>();
        BufferedReader buf;
        try{
            buf = new BufferedReader(new FileReader("D:\\Java Problems\\ProiectPOO\\imprumuturi.txt"));
            String help=buf.readLine();
            while(help != null)
            {
                String info[] = help.split("_");
                Imprumut imp = new Imprumut(info[0], info[1], info[2], info[3], info[4], info[5]);
                imprumuturi.add(imp);
                help = buf.readLine();
            }
        }catch (IOException e)
        {
        e.printStackTrace();
        }
        return imprumuturi;
        }
    
    public static void scriereImprumuturi(String nume_fisier, ArrayList<Imprumut> imprumuturi) throws FileNotFoundException
    {
        File file = new File(nume_fisier);
        try{
            FileWriter fw = new FileWriter(nume_fisier, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter("D:\\Java Problems\\ProiectPOO\\imprumuturi.txt");
            for(int i=0; i<imprumuturi.size(); i++)
                pw.println(imprumuturi.get(i).AfisareFisier());
            pw.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void editFisier(String nume_fisier, ArrayList<Carte> carte)
    {
        try{
            FileWriter fw = new FileWriter(nume_fisier, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(fw);
            for(int i=0; i<carte.size(); i++)
            {
                pw.println(carti.get(i).getCodISBN() + "_" + carti.get(i).getTitlu() + "_" + carti.get(i).getEditura() + "_" + carti.get(i).stareImprumut());
            }
            
            pw.flush();
            pw.close();
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR!!!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ArrayList<Carte> citesteCarti() throws FileNotFoundException, IOException
    {
        BufferedReader buf;
        try{
            buf = new BufferedReader(new FileReader("D:\\Java Problems\\ProiectPOO\\carti.txt"));
            String help=buf.readLine();
            while(help != null)
            {
                String info[] = help.split("_");
                boolean imprumut = Boolean.parseBoolean(info[3]);
                Carte carte = new Carte(info[0], info[1], info[2], imprumut);
                carti.add(carte);
                help = buf.readLine();
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return carti;
    }
    
    public int CautaTitlu(String titlu, ArrayList<Carte> carti)
    {
        for(int i=0; i<carti.size(); i++)
        {
            if(carti.get(i).getTitlu().equals(titlu))
                return i;
        }
        return -1;
    }
    
}
