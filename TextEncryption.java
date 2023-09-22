import java.io.*;
import java.util.Scanner;

public class TextEncryption {

    public static String transformWord(String word) {
        // Check if the word has 3 or more odd letters
        if (hasThreeOrMoreOddLetters(word)) {
            // Swap the first and last letter of the word
            char[] chars = word.toCharArray();
            char temp = chars[0];
            chars[0] = chars[chars.length - 1];
            chars[chars.length - 1] = temp;
            // Convert the result to lowercase and return it
            return new String(chars).toLowerCase();
        }

        // Check if the word has 2 or more even letters
        if (hasTwoOrMoreEvenLetters(word)) {
            // Swap adjacent letters in the word
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length - 1; i += 2) {
                char temp = chars[i];
                chars[i] = chars[i + 1];
                chars[i + 1] = temp;
            }
            // Convert the result to lowercase and return it
            return new String(chars).toLowerCase();
        }

        // Check if the word is a single character
        if (word.length() == 1) {
            char c = word.charAt(0);
            if (Character.isLetter(c)) {
                // Shift the character to the next character or wrap around from 'z' to 'a'
                if (c == 'z') {
                    // Convert the result to lowercase and return it
                    return "a".toLowerCase();
                } else {
                    // Convert the result to lowercase and return it
                    return String.valueOf((char) (c + 1)).toLowerCase();
                }
            }
        }

        // Check if the word is a number
        if (word.matches("-?\\d+")) {
            // Add 2501 to the number and return it as a string
            return String.valueOf(Integer.parseInt(word) + 2501);
        }

        // If none of the conditions are met, return the original word in lowercase
        return word.toLowerCase();
    }

    public static boolean hasThreeOrMoreOddLetters(String word) {
        // Count the odd letters, return true if 3 or more
        int oddCount = 0;
        for (char c : word.toCharArray()) {
            if (Character.isLetter(c) && (c - 'a') % 2 == 1) {
                oddCount++;
                if (oddCount >= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasTwoOrMoreEvenLetters(String word) {
        // Count the even letters, return true if 2 or more
        int evenCount = 0;
        for (char c : word.toCharArray()) {
            if (Character.isLetter(c) && (c - 'a') % 2 == 0) {
                evenCount++;
                if (evenCount >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Loop until the user types "no"
        while (true) {
            // Prompt for input file and output file names
            System.out.print("Enter the input file name: ");
            String inputFileName = sc.nextLine();
            System.out.print("Enter the output file name: ");
            String outputFileName = sc.nextLine();

            try {
                // Create a Scanner to read from the input file
                Scanner inputFileScanner = new Scanner(new File(inputFileName));

                // Create a PrintWriter to write to the output file
                PrintWriter outputFileWriter = new PrintWriter(new FileWriter(outputFileName));

                // Process each line in the input file
                while (inputFileScanner.hasNextLine()) {
                    String inputLine = inputFileScanner.nextLine();
                    String[] words = inputLine.split(" ");
                    StringBuilder transformedLine = new StringBuilder();

                    for (String word : words) {
                        // Separate special characters from the start and end of the word
                        String startSpecial = "";
                        String endSpecial = "";
                        int startIndex = 0;
                        int endIndex = word.length() - 1;

                        while (startIndex <= endIndex && !Character.isLetterOrDigit(word.charAt(startIndex))) {
                            startSpecial += word.charAt(startIndex);
                            startIndex++;
                        }

                        while (endIndex >= startIndex && !Character.isLetterOrDigit(word.charAt(endIndex))) {
                            endSpecial = word.charAt(endIndex) + endSpecial;
                            endIndex--;
                        }

                        if (startIndex <= endIndex) {
                            String middleWord = word.substring(startIndex, endIndex + 1);
                            String transformedWord = transformWord(middleWord);
                            transformedLine.append(startSpecial)
                                    .append(transformedWord)
                                    .append(endSpecial)
                                    .append(" ");
                        } else {
                            transformedLine.append(startSpecial).append(endSpecial).append(" ");
                        }
                    }

                    outputFileWriter.println(transformedLine.toString().trim());
                }

                // Close the input and output files
                inputFileScanner.close();
                outputFileWriter.close();

                System.out.println("Encryption complete. Check the output file: " + outputFileName);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }

            System.out.print("Would you like to encrypt more text (yes/no)? ");
            String input = sc.nextLine();

            if (input.equals("no")) {
                System.out.println("Exiting...");
                break;
            } else if (!input.equals("yes")) {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        sc.close();
    }

}

    

    
