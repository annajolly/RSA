import java.io.*;
import java.util.*;
import java.math.*;

public class User
{
  
  private String username;
  private String password;
  private BigInteger prime1;
  private BigInteger prime2;
  private BigInteger privateKey;
  private BigInteger publicKey;
  
  public User(String u, String p)
  {
    this.username = u;
    this.password = p;
    //Create private and public keys
    BigInteger one = new BigInteger("1");
    //Use java's prime number generator
    Random rnd = new Random();
    prime1 = BigInteger.probablePrime(32, rnd);
    prime2 = BigInteger.probablePrime(32, rnd);
    //Create public key
    this.publicKey = prime1.multiply(prime2);
    //Create private key
    BigInteger phi = prime1.subtract(one).multiply(prime2.subtract(one));
    BigInteger threed = new BigInteger("65537");
    this.privateKey = threed.modInverse(phi);
  }
  
  public User(String u, String p, BigInteger privateKey, BigInteger publicKey)
  {
    this.username = u;
    this.password = p;
    this.privateKey = privateKey;
    this.publicKey = publicKey;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public String getPublicKey()
  {
    return String.valueOf(this.publicKey);
  }
  
  public String getPrivateKey()
  {
    return String.valueOf(this.privateKey);
  }
}
