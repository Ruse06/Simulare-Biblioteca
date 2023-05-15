package proiectpoo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
public abstract class Frame3 extends JFrame implements ActionListener{

    JButton bCauta = new JButton("Cauta");
    Ascultator2 buton = new Ascultator2();
    JTextField tCNP;
    Frame3(){
        super("Returnare");
        JLabel lCNP;
        JPanel p1, p2;
        p1 = new JPanel();
        p2 = new JPanel();
        p1.setPreferredSize(new Dimension(250, 30));
        p2.setPreferredSize(new Dimension(150, 30));
        p1.setLayout(new GridLayout(1, 2));
        lCNP = new JLabel("CNP");
        tCNP = new JTextField(20);
        p1.add(lCNP);
        p1.add(tCNP);
        p2.add(bCauta);
        bCauta.addActionListener((ActionListener) buton);
        getContentPane().add(p1, BorderLayout.NORTH);
        getContentPane().add(p2, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    class Ascultator2 implements ActionListener
    {
        ArrayList<Imprumut> imprumuturi = new ArrayList<Imprumut>();
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(e.getSource() == bCauta)
            {
                int aux;
                try {
                    imprumuturi = citesteImprumuturi();
                    aux = CautaImprumut(tCNP.getText(), imprumuturi);
                    float pret = calculPret(imprumuturi.get(aux));
                    String strPret = String.valueOf(pret);
                    if(aux == -1)
                        throw new Exception();
                    Frame4 f4 = new Frame4();
                    
                    f4.UmplereText(imprumuturi.get(aux).getNumeClient(), imprumuturi.get(aux).getCNP(), imprumuturi.get(aux).getNumeCarte(), strPret);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Nu a fost gasit niciun imprumut.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }catch (Exception ev){
                    JOptionPane.showMessageDialog(null, "Nu a fost gasit niciun imprumut.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
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
        
        public int CautaImprumut(String CNP, ArrayList<Imprumut> imp)
        {
            for(int i=0; i<imp.size(); i++)
            {
                if(imp.get(i).getCNP().equals(CNP))
                    return i;
            }
            return -1; 
        }
        
        public long calculZile(Date date, Date date2) 
        {
            long diferentaInMilisecunde = Math.abs(date2.getTime() - date.getTime());
            long diferentaInZile = TimeUnit.DAYS.convert(diferentaInMilisecunde, TimeUnit.MILLISECONDS);
            return diferentaInZile;
        }
        
        public float calculPret(Imprumut imp) throws Exception
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String strDate = imp.getData();
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            Date date = null;
            java.util.Date date2 = cal2.getTime();
            date = sdf.parse(strDate); // data de returnare introdusa in frame2
            cal.setTime(date);
            cal2.setTime(date2);
            if(date2.after(date))
                return (calculZile(date2, date));
            return (float) (0.5 * calculZile(date2, date));
        }
    }
}
