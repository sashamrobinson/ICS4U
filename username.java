import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.regex.*;
public class username {
    public static void main(String[] args) {

        String continuePlaying = "no";

        do {
            // Get user input from username and password
            Scanner input = new Scanner(System.in);

            System.out.println("Would you like to create or login a user?");
            String user = input.nextLine();

            // Grab user information from method
            HashMap<String, String> pulledUserInformation; 

            pulledUserInformation = grabUserInformation();

            if (user.equalsIgnoreCase("login")) {
                System.out.println("Enter a valid username: ");
                String username = input.nextLine();
        
                System.out.println("Enter a valid password: ");
                String password = input.nextLine();
        
                // Finished working with file, now verify username and password by checking Hashmap
                String verification = pulledUserInformation.get(username);
                    
                // Check null value
                

                if (verification == null || !verification.equals(password)) {
                    System.out.println("Username or password incorrect");
                    System.out.println("Would you like to try again?");

                    continuePlaying = input.nextLine();
                }

                else if (verification.equals(password)) {
                    System.out.println("Successfully logged in with correct username and password");
                    System.exit(0);
                }
                         
            }

            else if (user.equalsIgnoreCase("create")) {

                // Create FileWriter and add user to file
                try {

                    FileWriter writer = new FileWriter("userinformation.txt", true);
                    PrintWriter output = new PrintWriter(writer);

                    System.out.println("Username: ");
                    String createUsername = input.nextLine();

                    // Check if username is taken already
                    String usernameVerification = pulledUserInformation.get(createUsername);
                    
                    if (usernameVerification != null) {
                        System.out.println("Username is already taken");
                        System.out.println("Would you like to try again?");

                        continuePlaying = input.nextLine();
                    }

                    else {

                        System.out.println("Password: ");
                        String createPassword = input.nextLine();

                        // RegEx
                        Pattern pattern = Pattern.compile("[!@#$%&*]");
                        Pattern pattern2 = Pattern.compile("[123456789]");
                        
                        Matcher matcher = pattern.matcher(createPassword);
                        Matcher matcher2 = pattern2.matcher(createPassword);

                        // Capital check
                        String lowercase = createPassword.toLowerCase();

                        if (createPassword.length() < 8 || (!matcher.find()) || (!matcher2.find()) || (createPassword == lowercase)) {
                            
                            // Password is invalid
                            System.out.println("Your password is invalid. For a password to be valid, it needs to be greater than 8 characters, have a special character, a number and a capital.");
                            System.out.println("Would you like to try again?");

                            continuePlaying = input.nextLine();
                            
                        }
                        
                        else {
                            // Add to file
                            output.println(createUsername + ":" + createPassword);  
                        
                            System.out.println("Successfully added your user to the database");
                            output.close();
                            System.exit(0);
                        }
                    
                    }

                }

                catch (IOException error) {
                    System.out.println("Error writing to file");
                }
            }

            else {
                System.out.println("Invalid input");
                System.out.println("Would you like to try again?");

                continuePlaying = input.nextLine();

            }

        }

        while (continuePlaying.equalsIgnoreCase("yes"));

    }

    // Method for grabbing information and putting it into a HashMap
    public static HashMap<String, String> grabUserInformation() {

        try {
        
            // Create new file, scanner and read from file 
            File file = new File("userinformation.txt"); 
            Scanner fileScanner = new Scanner(file); 

            // Make a hashmap to store key and value pairs of username and passwords, before iterating
            HashMap<String, String> userInformation = new HashMap<String, String>();

            // Iterate through file, storing information into Hashmap
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();

                // Iterate through username:password, find semi-colon, seperate the two and then add to Hashmap
                for (int i = 0; i < data.length(); i++) {
                    if (data.charAt(i) == ':') {
                        
                        String usernameText = "";
                        
                        // Username loop
                        for (int j = 0; j < i; j++) {
                            usernameText += data.charAt(j);
                        }

                        String passwordText = "";

                        // Password loop
                        for (int k = i + 1; k < data.length(); k++) {
                            passwordText += data.charAt(k);
                        }

                        userInformation.put(usernameText, passwordText);

                    }
                }
           }

           fileScanner.close();
           
           return userInformation;
        }

        // Catch any file reading errors
        catch (IOException error) {
            System.out.println("Error reading file");
        }
        
        return null;
    }   
}
