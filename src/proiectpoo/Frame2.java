package proiectpoo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Frame2 extends JFrame
{
    String[] coduri = new String[15];
    JButton bImprumuta = new JButton("Imprumuta");
    JTextField TNumeSiPrenume, TCNP, TDataReturnare;
    JTextArea aText = new JTextArea(5, 5);
    JComboBox TAlegeCarti;
    ArrayList<Carte> carti = new ArrayList<Carte>();
    ArrayList<Imprumut> imprumuturi = new ArrayList<Imprumut>();
    Frame2() throws FileNotFoundException, IOException
    {
        super("Imprumuta");
        
        carti = citesteCarti();
        coduri = getCoduri(carti);
        imprumuturi = citesteImprumuturi();
        
        JPanel p1, p2, p3, p4, p5;
        JLabel LNumeSiPrenume, LCNP, LAlegeCarti, LDataReturnare;
        LNumeSiPrenume = new JLabel("Nume si prenume");
        LCNP = new JLabel("CNP");
        LAlegeCarti = new JLabel("Alege carti");
        LDataReturnare = new JLabel("Data returnare");
        TNumeSiPrenume = new JTextField(20);
        TCNP = new JTextField(13);
        TDataReturnare = new JTextField(11);
        Ascultator buton = new Ascultator();
        p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 2));
        p1.setPreferredSize(new Dimension(500, 100));
        p1.add(LNumeSiPrenume);
        p1.add(TNumeSiPrenume);
        p1.add(LCNP);
        p1.add(TCNP);
        TAlegeCarti = new JComboBox(coduri);
        TAlegeCarti.addActionListener(buton);
        p2 = new JPanel();
        p2.add(LAlegeCarti);
        p2.add(TAlegeCarti);
        aText.setEditable(true);
        JScrollPane scrollArea = new JScrollPane(aText);
        JScrollPane scroll = new JScrollPane(aText);
        p2.add(aText);
        
        p3 = new JPanel();
        p3.setLayout(new GridLayout(1, 1));
        p3.add(LDataReturnare);
        p3.add(TDataReturnare);
        p4 = new JPanel();
        p4.add(bImprumuta);
        bImprumuta.addActionListener(buton);
        p5 = new JPanel();
        p5.setLayout(new GridLayout(2,1));
        p5.add(p3);
        p5.add(p4);
       
        getContentPane().add(p1, BorderLayout.NORTH);
        getContentPane().add(p2, BorderLayout.CENTER);
        getContentPane().add(p5, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
    }
    class Ascultator implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent ev) 
        {
            if(ev.getSource() == bImprumuta)
            {
                if(VerificareConditii())
                {
                    int aux = CautaCarte(TAlegeCarti.getSelectedItem().toString(), carti);
                    if(carti.get(aux).stareImprumut() == false)
                    {
                        Imprumut imp = new Imprumut(carti.get(aux).getCodISBN(), carti.get(aux).getTitlu(), carti.get(aux).getEditura(), TNumeSiPrenume.getText(), TCNP.getText(), TDataReturnare.getText());
                        imprumuturi.add(imp);
                        carti.get(aux).setStareImprumut(true);
                        editFisier("carti.txt", carti);
                        JOptionPane.showMessageDialog(null, "Cartea a fost imprumutata cu succes.");
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Cartea este deja imprumutata.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
            try {
                scriereImprumuturi("imprumuturi.txt", imprumuturi);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Frame2.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(ev.getSource() == TAlegeCarti)
            {
                String cod = TAlegeCarti.getSelectedItem().toString();
                int c = CautaCarte(cod, carti);
                if(verificaImprumut(cod))
                    JOptionPane.showMessageDialog(null, "Cartea este deja imprumutata.", "Eroare", JOptionPane.ERROR_MESSAGE);
                else
                    aText.setText(carti.get(c).getTitlu() + ", " + carti.get(c).getEditura());
            }
        }
    }

    public boolean contineLitere(char[] c){
        for(int i=0; i<13; i++)
            if (c[i]<'0' || c[i]>'9')
                return true;
        return false;
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
    
    public ArrayList<Imprumut> citesteImprumuturi() throws FileNotFoundException, IOException
    {
        BufferedReader buf;
        File file = new File("D:\\Java Problems\\ProiectPOO\\imprumuturi.txt");
        try{
            buf = new BufferedReader(new FileReader("D:\\Java Problems\\ProiectPOO\\imprumuturi.txt"));
            String help=buf.readLine();
            if(file.length() != 0)
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
    
    
    public String[] getCoduri(ArrayList<Carte> carti)
    {
        String[] coduri = new String[carti.size()];
        for(int i=0; i<carti.size(); i++)
            coduri[i] = carti.get(i).getCodISBN();
        return coduri;
    }
    
    public boolean VerificareDouaSaptamani(Date date, Date date2) 
    {
        long diferentaInMilisecunde = Math.abs(date2.getTime() - date.getTime());
        long diferentaInZile = TimeUnit.DAYS.convert(diferentaInMilisecunde, TimeUnit.MILLISECONDS);
        if(diferentaInZile<14 && (date2.getTime() > date.getTime()))
            return false;
        return true;
    }
    
    public int CautaCarte(String codISBN, ArrayList<Carte> carti)
    {
        for(int i=0; i<carti.size(); i++)
        {
            if(carti.get(i).getCodISBN() == codISBN)
                return i;
        }
        return -1; 
    }
    
    public boolean verificaImprumut(String codISBN)
    {
        int c = CautaCarte(codISBN, carti);
        if(carti.get(c).stareImprumut())
            return true;
        return false;
    }
    
    public boolean VerificareConditii()
    {
        char[] charCNP = TCNP.getText().toCharArray();
        char[] charData = TDataReturnare.getText().toCharArray();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = TDataReturnare.getText();
        Calendar cal2 = Calendar.getInstance();
        Date date = null;
        java.util.Date date2 = cal2.getTime(); //data din prezent
        Pattern pattern = Pattern.compile("^[a-zA-Z -]+$");
        Matcher matcher = pattern.matcher(TNumeSiPrenume.getText());
        try{
            date = sdf.parse(strDate); // data de returnare introdusa in frame2
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal2.setTime(date2);
            if(!(matcher.find()) || charCNP[0]=='9'|| charCNP[0] == '0' || charCNP.length != 13 || contineLitere(charCNP)==true || VerificareDouaSaptamani(date2, date))
                throw new Exception();
            }catch(ParseException ex) {
                JOptionPane.showMessageDialog(null, "Data introdusa este invalida.", "Eroare", JOptionPane.ERROR_MESSAGE);
                return false;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Datele introduse nu sunt corecte.", "Eroare", JOptionPane.ERROR_MESSAGE);
                return false;
        }
        return true;
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
    
}
