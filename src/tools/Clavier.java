package tools;

import java.io.*;
 
public class Clavier
 {
 static char readChar()
   {
    boolean erreur;
    String s;
 
    do {
          erreur=false;
          s=readString();
          if(s.length()!=1)
            {
             System.out.println("Taper 1 caractere, puis entree" );
             erreur=true;
            }
         }while(erreur);
    return s.charAt(0);
   }
 
  static int readInt()
   {
    boolean erreur;
    int n=0;
 
    do {
          erreur=false;
          try {
                n=Integer.parseInt(readString());
               }
         catch(NumberFormatException e)
              {
               System.out.println("Erreur de saisie, recommencez" );
              erreur=true;
              }
        }while(erreur);
    return n;
   }
  static double readDouble()
   {
    boolean erreur;
    double n=0;
 
    do {
          erreur=false;
          try {
                n=new Double(readString()).doubleValue();
               }
         catch(NumberFormatException e)
              {
               System.out.println("Erreur de saisie, recommencez" );
              erreur=true;
              }
        }while(erreur);
    return n;
   }
 
 static String readString()
  {
   byte b[]=new byte[256];
   int n=0;
   boolean erreur;
 
  do {
        erreur=false;
        try{
             n=System.in.read(b);
            }
        catch(IOException e)
           {
            System.out.println(e);
            erreur=true;
            }
       }while(erreur);
   return new String(b,0,n-2);
  }
 } 