import java.util.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Main
{
   
    /**
     * Computes the MD5 hash of the input string.
     * @param input The string to hash (e.g., the message/document content).
     * @return The hexadecimal MD5 hash string.
     */
    public static String computeMD5(String input) {
        try {
            // Get an instance of the MessageDigest class with MD5 algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Compute the hash
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert the byte array into a signum representation
            // The following logic converts the byte array into a 32-character hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                // Convert byte to hex, ensuring it's 2 digits long
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            System.err.println("MD5 algorithm not found: " + e.getMessage());
            return null; // Should not happen in standard Java environments
        }
    }

    /**
     * Verifies if a given hash matches the computed hash of the original data.
     * @param originalData The original message/data.
     * @param receivedHash The hash that is claimed to be the signature.
     * @return true if the computed hash matches the received hash, false otherwise.
     */
    public static boolean verifySignature(String originalData, String receivedHash) {
        String computedHash = computeMD5(originalData);
        if (computedHash == null) {
            return false;
        }
        // Case-insensitive comparison is often used, but strict comparison is safer for hashes
        return computedHash.equals(receivedHash);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- DYNAMIC INPUTS ---

        // 1. Input the original message/data
        System.out.println("Enter the original message/data to 'sign':");
        String originalMessage = scanner.nextLine();

        // 2. Generate and Output the 'Signature' (MD5 Hash)
        String generatedSignature = computeMD5(originalMessage);
        System.out.println("\n--- GENERATION PHASE ---");
        System.out.println("Original Data: \"" + originalMessage + "\"");
        System.out.println("Generated MD5 'Signature': " + generatedSignature);

        System.out.println("\n" + "-".repeat(40) + "\n");

        // --- VERIFICATION PHASE (Dynamic Input for Verification) ---

        // 3. Input the data *claimed* to be the original
        System.out.println("Enter the message/data to verify against (e.g., received document):");
        String verificationMessage = scanner.nextLine();

        // 4. Input the 'signature' *received*
        System.out.println("Enter the received MD5 'Signature' for verification:");
        String receivedSignature = scanner.nextLine();

        // 5. Verification Check
        System.out.println("\n--- VERIFICATION PHASE ---");
        boolean isAuthentic = verifySignature(verificationMessage, receivedSignature);

        // --- DYNAMIC OUTPUT ---
        if (isAuthentic) {
            System.out.println("✅ Signature **AUTHENTIC**! The data has not been altered.");
        } else {
            System.out.println("❌ Signature **NOT AUTHENTIC**! The data may have been tampered with or the signature is incorrect.");
        }

        scanner.close();
    }

}


