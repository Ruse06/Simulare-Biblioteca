package proiectpoo;

public class Imprumut{
    String numeClient, CNP, numeCarte, data, editura, codISBN;
    Imprumut(String codISBN, String numeCarte, String editura, String numeClient, String CNP, String data)
    {
        this.numeClient = numeClient;
        this.CNP = CNP;
        this.editura = editura;
        this.numeCarte = numeCarte;
        this.data = data;
        this.codISBN = codISBN;
    }
    
    public String getNumeClient()
    {
        return numeClient;
    }
    
    public String getCNP()
    {
        return CNP;
    }
    
    public String getEditura()
    {
        return editura;
    }
    
    public String getNumeCarte()
    {
        return numeCarte;
    }
    
    public String getData()
    {
        return data;
    }
    
    public String getCodISBN()
    {
        return this.codISBN;
    }
    
    public String AfisareFisier() { return this.getCodISBN()+"_"+this.getNumeCarte()+"_"+this.getEditura()+"_"+this.getNumeClient()+"_"+this.getCNP()+"_"+this.getData(); }
    
    public String toString() { return (codISBN + " " + numeCarte + " " + editura + " " + numeClient + " " + CNP + " " + data); }
}
