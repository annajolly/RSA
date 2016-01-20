import java.io.*;
import java.util.*;
import java.math.*;

public class TestRSA
{
  public static void main(String[] args)
  {
    
    //Encryption
    String message = "hi";
    //4775244950
    BigInteger intMessage = new BigInteger("0");
    BigInteger multiplyThis;
    BigInteger bigIn;
    char ch = 0;
    int in = 0;
    for(int i=0; i<message.length(); i++)
    {
      ch = message.charAt(i);
      in = (int) ch;
      System.out.println(in);
      bigIn = new BigInteger(String.valueOf(in));
      multiplyThis = new BigInteger("1000");
      intMessage = intMessage.multiply(multiplyThis);
      intMessage = intMessage.add(bigIn);
      System.out.println(intMessage);
    }
    //intMessage = new BigInteger("48194775244950");
    System.out.println(intMessage);
    
    BigInteger publicKey = new BigInteger("825641896390631");
    BigInteger privateKey = new BigInteger("553699199426609");
    BigInteger threed = new BigInteger("65537");
    BigInteger encMessage = intMessage.modPow(threed, publicKey);
    System.out.println("Encrypted message " + encMessage);
    
    //Decryption
    
    //Decrypt message
    BigInteger decMessage = encMessage.modPow(privateKey, publicKey);
    System.out.println("ASCII Decrypted message is " + decMessage);
    
    //Convert bigInteger message of ASCII triplets to String message
    String message2 = String.valueOf(decMessage);
    String triplet = "";
    String endMessage = "";
    ch = 0;
    in = 0;
    System.out.println(message2.length());
    for(int i=0; i<message2.length(); i=i+3)
    {
      triplet = String.valueOf(message2.charAt(i)) + String.valueOf(message2.charAt(i+1)) + String.valueOf(message2.charAt(i+2));
      System.out.println(triplet);
      in = Integer.parseInt(triplet);
      ch = (char) in;
      endMessage = endMessage + ch;
    }
    System.out.println("Decrypted message is " + endMessage);
    
  }
}