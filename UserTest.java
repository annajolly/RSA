import java.io.*;
import java.util.*;
import java.math.*;

public class UserTest
{
  public static void main(String[] args)
  {
    ArrayList<User> users = new ArrayList<User>();
    users = importUsers();
    
    System.out.println();
    System.out.println("****************************************************************");
    System.out.println();
    System.out.println("Welcome to 'Encrypt My Files'!");
    System.out.println();
    System.out.println("This system will allow you to encrypt your files in a secure manner.");
    System.out.println();
    System.out.println("Enter \"1\" to sign in, \"2\" to create a user account.");
    System.out.println();
    System.out.println("****************************************************************");
    System.out.println();
    
    try
    {
      Scanner s1 = new Scanner(System.in);
      int option = s1.nextInt();
      s1.close();
      if(option == 1)
      {
        login(users);
      }
      else if(option == 2)
      {
        createNewUser(users);
      }
      else
      {
        System.out.println("Not a valid input");
        System.exit(1);
      }
    }
    catch(Exception e)
    {
      System.out.println(e);
      System.exit(1);
    }
  }
  
  public static void createNewUser(ArrayList<User> users)
  {
    String name = "";
    String pw = "";
    try
    {
        //Ask for username
        boolean exists = true;
        Scanner s = new Scanner(System.in);
        while(exists == true)
        {
          System.out.println("Insert a username: ");
          name = s.next();
          exists=false;
          String word;
          for(User user : users)
          {
            word = user.getUsername();
            if(word.equals(name))
            {
              exists = true;
              System.out.println("Username already in use.");
            }
          }
        }
        //Ask for password
        System.out.println("Insert a password: ");
        pw = s.next();
        s.close();
        User addMe = new User(name, pw);
        //add User to ArrayList and users.csv
        users.add(addMe);
        addUser(addMe, users);
        //Redirect to login
        System.out.println("Redirecting to login...");
        login(users);
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
  }
  
  public static void addUser(User u, ArrayList<User> users)
  {
    try
    {
      FileWriter fw = new FileWriter("./users.csv");
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write("username password privateKey publicKey");
      bw.newLine();
      for(User user : users)
      {
        String name, pw, privateKey, publicKey;
        name = user.getUsername();
        pw = user.getPassword();
        privateKey = user.getPrivateKey();
        publicKey = user.getPublicKey();
        bw.write(name + " " + pw + " " + privateKey + " " + publicKey);
        bw.newLine();
      }
      bw.close();
      fw.close();
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
  }
  
  public static void login(ArrayList<User> users)
  {
    String name, pw, username;
    String correctPW = null;
    User currentUser = null;
    try
    {
      Scanner s = new Scanner(System.in);
      boolean userExists = false;
      while(userExists == false)
      {
        System.out.println("Insert your username: ");
        name = s.next();
        for(User user : users)
        {
          username = user.getUsername();
          if(name.equals(username))
          {
            currentUser = user;
            correctPW = user.getPassword();
            userExists = true;
          }
        }
        if(userExists == false)
        {
          System.out.println("Username does not exist.");
          System.out.println("Press \"1\" to enter a new username, \"2\" to create a user account");
          int option = s.nextInt();
          if(option == 1)
          {
            continue;
          }
          else if(option == 2)
          {
            s.close();
            createNewUser(users);
          }
          else
          {
            System.out.println("Invalid input");
          }
        }
      }
      boolean pwCorrect = false;
      while(pwCorrect == false)
      {
        System.out.println("Insert your password: ");
        pw = s.next();
        if(pw.equals(correctPW))
        {
          pwCorrect = true;
        }
        else
        {
          System.out.println("Password incorrect.");
        }
      }
      s.close();
    }
    catch(Exception e)
    {
      System.out.print("Login error: " + e);
    }
    System.out.println();
    System.out.println("****************************************************************");
    System.out.println();
    System.out.println("Login successful. Welcome " + currentUser.getUsername() + "!");
    System.out.println();
    homeScreen(currentUser, users);
  }
  
  public static ArrayList<User> importUsers()
  {
    try
    {
      FileReader fr = new FileReader("./users.csv");
      BufferedReader br = new BufferedReader(fr);
      String currentLine = br.readLine();
      String[] currentLineSplit;
      BigInteger privateKey, publicKey;
      ArrayList<User> userList = new ArrayList<User>();
      currentLine = br.readLine();
      while( currentLine != null)
      {
        currentLineSplit = currentLine.split(" ");
        privateKey = new BigInteger(currentLineSplit[2]);
        publicKey = new BigInteger(currentLineSplit[3]);
        User user = new User(currentLineSplit[0], currentLineSplit[1], privateKey, publicKey);
        userList.add(user);
        currentLine = br.readLine();
      }
      return userList;
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
    return null;
  }
  
  public static void homeScreen(User user, ArrayList<User> users)
  {
    System.out.println("Enter \"1\" to encrypt a message, \"2\" to decrypt a message.");
    System.out.println();
    System.out.println("****************************************************************");
    System.out.println();
    try
    {
      Scanner sc = new Scanner(System.in);
      int option = sc.nextInt();
      sc.close();
      if(option == 1)
      {
        encrypt(user, users);
      }
      else if(option == 2)
      {
        decrypt(user, users);
      }
      else
      {
        System.out.println("Not a valid input");
        System.exit(1);
      }
    }
    catch(Exception e)
    {
      System.out.println(e);
      //System.exit(1);
    }
  }
  
  public static void encrypt(User user, ArrayList<User> users)
  {
    System.out.println("Enter \"1\" to type at the command line, \"2\" to use a text file.");
    String message = "";
    try
    {
      Scanner sc = new Scanner(System.in);
      int option = sc.nextInt();
      if(option == 1)
      {
        message = sc.next();
      }
      else if(option == 2)
      {
        System.out.println("Enter filepath: ");
        String path = sc.next();
        //Import message
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String currentLine = br.readLine();
        message = message + currentLine;
        currentLine = br.readLine();
        while(currentLine != null)
        {
          message = message + " " + currentLine;
          currentLine = br.readLine();
        }
      }
      else
      {
        System.out.println("Not a valid input.");
        //System.exit(1);
      }
    }
    catch(Exception e)
    {
      System.out.println(e);
      //System.exit(1);
    }
    
    //Convert String message to bigInteger message of ASCII triplets
    BigInteger intMessage = new BigInteger("0");
    BigInteger multiplyThis;
    BigInteger bigIn;
    char ch = 0;
    int in = 0;
    for(int i=0; i<message.length(); i++)
    {
      ch = message.charAt(i);
      in = (int) ch;
      bigIn = new BigInteger(String.valueOf(in));
      multiplyThis = new BigInteger("1000");
      intMessage = intMessage.multiply(multiplyThis);
      intMessage = intMessage.add(bigIn);
    }
    
    //message = "48194775244950";
    BigInteger threed = new BigInteger("65537");
    BigInteger publicKey = new BigInteger(user.getPublicKey());
    BigInteger encMessage = intMessage.modPow(threed, publicKey);
    System.out.println("Your message: " + message);
    System.out.println("Your message in ASCII: " + intMessage);
    System.out.println("Your message encrypted: " + encMessage);
    
    System.out.println("Enter \"1\" to go back to the homepage.");
    try
    {
      Scanner sc = new Scanner(System.in);
      int option = sc.nextInt();
      if(option == 1)
      {
        System.out.println();
        System.out.println("****************************************************************");
        System.out.println();
        homeScreen(user, users);
      }
      else
      {
        System.out.println("Not a valid input.");
        //System.exit(1);
      }
    }
    catch(Exception e)
    {
      System.out.println(e);
      //System.exit(1);
    }
  }
  
  public static void decrypt(User user, ArrayList<User> users)
  {
    System.out.println("Enter \"1\" to type at the command line, \"2\" to use a text file.");
    String message = "";
    try
    {
      Scanner sc = new Scanner(System.in);
      int option = sc.nextInt();
      if(option == 1)
      {
        message = sc.next();
      }
      else if(option == 2)
      {
        System.out.println("Enter filepath: ");
        String path = sc.next();
        //Import message
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String currentLine = br.readLine();
        message = message + currentLine;
        currentLine = br.readLine();
        while(currentLine != null)
        {
          message = message + " " + currentLine;
          currentLine = br.readLine();
        }
      }
      else
      {
        System.out.println("Not a valid input.");
        //System.exit(1);
      }
    }
    catch(Exception e)
    {
      System.out.println(e);
      //System.exit(1);
    }
    
    //Decrypt message
    BigInteger encMessage = new BigInteger(message);
    System.out.println("Encrypted message is: " + encMessage);
    BigInteger publicKey = new BigInteger(user.getPublicKey());
    BigInteger privateKey = new BigInteger(user.getPrivateKey());
    BigInteger decMessage = encMessage.modPow(privateKey, publicKey);
    System.out.println("ASCII decrypted message is: " + decMessage);
    
    //Convert bigInteger message of ASCII triplets to String message
    String message2 = String.valueOf(decMessage);
    if(message2.length() % 3 != 0)
    {
      message2 = "0" + message2;
    }
    String triplet = "";
    String endMessage = "";
    char ch = 0;
    int in = 0;
    for(int i=0; i<message2.length(); i=i+3)
    {
      triplet = String.valueOf(message2.charAt(i)) + String.valueOf(message2.charAt(i+1)) + String.valueOf(message2.charAt(i+2));
      in = Integer.parseInt(triplet);
      ch = (char) in;
      endMessage = endMessage + ch;
    }
    System.out.println("Decrypted message is: " + endMessage);
    
  }
}