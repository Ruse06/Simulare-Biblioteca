package proiectpoo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class Carte {
    boolean imprumutata;
    private String titlu, editura, codISBN;
    
    Carte(String codISBN, String titlu, String editura, boolean imprumutata)
    {
        this.titlu = titlu;
        this.editura = editura;
        this.codISBN = codISBN;
        this.imprumutata = imprumutata;
    }
    
    public String getTitlu()
    {
        return this.titlu;
    }
    
    public void setStareImprumut(boolean StareImprumut)
    {
        this.imprumutata = StareImprumut;
    }
    
    public String getEditura()
    {
        return this.editura;
    }
    
    public String getCodISBN()
    {
        return codISBN;
    }
    
    public boolean stareImprumut()
    {
        return this.imprumutata;
    }
    
    
    public String toString()
    {
       return (this.titlu +" "+ this.editura +" "+ this.codISBN +" "+ this.imprumutata);
    }

    public Carte CautaCarte(String codISBN, ArrayList<Carte> carti)
    {
        for(int i=0; i<carti.size(); i++)
        {
            if(carti.get(i).getCodISBN() == codISBN)
                return carti.get(i);
        }
        return null; 
    }
    
    public Carte CautaTitlu(String titlu, ArrayList<Carte> carti)
    {
        for (int i = 0; i < carti.size(); i++) {
            if (carti.get(i).getTitlu().equals(titlu))
                return carti.get(i);
        }
        return null;
    }

}
