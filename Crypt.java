import java.io.*;
import java.util.*;

public class Crypt
{
  public static void main(String[] args)
  {
    Scanner input1 = new Scanner(System.in);
    System.out.println("Welcome!\nType \"1\" to input at command-line.\nType \"2\" to insert a filepath.");
    try
    {
      int option = input1.nextInt();
      if(option == 1)
      {
        input1.close();
        System.out.println("Insert text:");
        Scanner input2 = new Scanner(System.in);
        String inputString = input2.next();
        input2.close();
        ArrayList<String> input = new ArrayList<String>();
        Scanner input3 = new Scanner(System.in);
        System.out.println("Type \"1\" to encrypt.\nType \"2\" to decrypt.");
        try
        {
          int cryptOption = input3.nextInt();
          if(cryptOption == 1)
          {
            input3.close();
            Scanner input4 = new Scanner(System.in);
            System.out.println("Insert keyword (letters only; 6 minimum):");
            String keyword = input4.next();
            System.out.println("Type \"1\" if you wish to export to a text file.\nType \"2\" if you wish to print to screen.\nType \"3\" if you wish to do both.");
            try
            {
              int exportPrintOrBoth = input4.nextInt();
              input4.close();
              if(exportPrintOrBoth == 1 || exportPrintOrBoth == 2 || exportPrintOrBoth == 3)
              {
                Encrypt(input, keyword, exportPrintOrBoth);
              }
              else
              {
                System.out.println("Not a valid input.");
                System.exit(1);
              }
            }
            catch(Exception InputMismatchException)
            {
              System.out.println("Not a valid input.");
              System.exit(1);
            }
          }
          else if(cryptOption == 2)
          {
            input3.close();
            Scanner input4 = new Scanner(System.in);
            System.out.println("Insert keyword: (letters only; 6 minimum)");
            String keyword = input4.next();
            System.out.println("Type \"1\" if you wish to export to a text file.\nType \"2\" if you wish to print to screen.\nType \"3\" if you wish to do both.");
            try
            {
              int exportPrintOrBoth = input4.nextInt();
              if(exportPrintOrBoth == 1 || exportPrintOrBoth == 2 || exportPrintOrBoth == 3)
              {
                Decrypt(input, keyword, exportPrintOrBoth);
              }
              else
              {
                System.out.println("Not a valid input.");
                System.exit(1);
              }
            }
            catch(Exception InputMismatchException)
            {
              System.out.println("Not a valid input.");
              System.exit(1);
            }
          }
          else
          {
            System.out.println("Not a valid input.");
            System.exit(1);
          }
        }
        catch(Exception InputMismatchException)
        {
          System.out.println("Not a valid input.");
          System.exit(1);
        }
        
      }
      else if(option == 2)
      {
        input1.close();
        Scanner input2 = new Scanner(System.in);
        System.out.println("Insert filename:");
        String filename = input2.next();
        input2.close();
        //Read input into an arraylist
        ArrayList<String> input = new ArrayList<String>();
        try{
          FileReader fr = new FileReader(filename);
          BufferedReader br = new BufferedReader(fr);
          String currentLine = br.readLine();
          while( currentLine != null)
          {
            currentLine = br.readLine();
            input.add(currentLine);
          }
          br.close();
          fr.close();
        }
        catch(Exception e)
        {
          System.out.println(e);
        }
        Scanner input3 = new Scanner(System.in);
        System.out.println("Type \"1\" to encrypt.\nType \"2\" to decrypt.");
        try
        {
          int cryptOption = input3.nextInt();
          if(cryptOption == 1)
          {
            input3.close();
            Scanner input4 = new Scanner(System.in);
            System.out.println("Insert keyword: (letters only; 6 minimum)");
            String keyword = input4.next();
            System.out.println("Type \"1\" if you wish to export to a text file.\nType \"2\" if you wish to print to screen.\nType \"3\" if you wish to do both.");
            try
            {
              int exportPrintOrBoth = input4.nextInt();
              if(exportPrintOrBoth == 1 || exportPrintOrBoth == 2 || exportPrintOrBoth == 3)
              {
                Encrypt(input, keyword, exportPrintOrBoth);
              }
              else
              {
                System.out.println("Not a valid input.");
                System.exit(1);
              }
            }
            catch(Exception InputMismatchException)
            {
              System.out.println("Not a valid input.");
              System.exit(1);
            }
          }
          else if(cryptOption == 2)
          {
            input3.close();
            Scanner input4 = new Scanner(System.in);
            System.out.println("Insert keyword: (letters only; 6 minimum)");
            String keyword = input4.next();
            System.out.println("Type \"1\" if you wish to export to a text file.\nType \"2\" if you wish to print to screen.\nType \"3\" if you wish to do both.");
            try
            {
              int exportPrintOrBoth = input4.nextInt();
              if(exportPrintOrBoth == 1 || exportPrintOrBoth == 2 || exportPrintOrBoth == 3)
              {
                Decrypt(input, keyword, exportPrintOrBoth);
              }
              else
              {
                System.out.println("Not a valid input.");
                System.exit(1);
              }
            }
            catch(Exception InputMismatchException)
            {
              System.out.println("Not a valid input.");
              System.exit(1);
            }
          }
          else
          {
            System.out.println("Not a valid input.");
            System.exit(1);
          }
        }
        catch(Exception InputMismatchException)
        {
          System.out.println("Not a valid input.");
          System.exit(1);
        }
      }
      else
      {
        System.out.println("Not a valid input.");
        System.exit(1);
      }
    }
    catch(Exception InputMismatchException)
    {
      System.out.println("Not a valid input.");
      System.exit(1);
    }
  }
  static void Encrypt(ArrayList<String> input, String keyword, int exportPrintOrBoth)
  {
    System.out.println("Encrypted");
    try
    {
      ArrayList<String> cryptFile = new ArrayList<String>();
      FileReader fr = new FileReader("CryptWP.txt");
      BufferedReader br = new BufferedReader(fr);
      String currentLine = br.readLine();
      currentLine = br.readLine();
      ArrayList<String> cryptSplit = new ArrayList<String>();
      while( currentLine != null)
      {
        String[] lineSplit = currentLine.split(" ");
        for(String part : lineSplit)
        {
          cryptSplit.add(part);
        }
        currentLine = br.readLine();
      }
      br.close();
      fr.close();
    }
    catch(Exception e)
    {
      System.out.println("Encryption file not found: " + e);
    }
    //Create encryption key using keyword
    int key = 0;
    for(int i=0; i<keyword.length(); i++)
    {
      key = key + (int) keyword.charAt(i);
    }
    //create encryption word
    
    System.out.println(key);
    for(String line : input)
    {
      for(char unit : line.toCharArray())
      {
        ;
      }
    }
    
  }
  
  static void Decrypt(ArrayList<String> input, String keyword, int exportPrintOrBoth)
  {
    System.out.println("Decrypted");
  }
  
  static void ExportToTxt(ArrayList<String> input)
  {
    String name = "crypt.txt";
    try
    {
      FileWriter fw = new FileWriter(name);
      BufferedWriter bw = new BufferedWriter(fw);
      for(String line : input)
      {
        bw.write(line + "/n");
      }
      bw.close();
      fw.close();
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
    System.out.println("Exported to " + name);
  }
  
  static void PrintToScreen(String[] input)
  {
    ;
  }
}