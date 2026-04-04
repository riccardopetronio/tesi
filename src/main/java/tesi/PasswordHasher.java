package tesi;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordHasher {
	
	private static int iterazioni = 100000;  // numero di iterazioni dell'algoritmo
	private static int lunghezzaHash = 256;  // lunghezza dell'hash finale in bit

	public static String[] gerenaSaltAndHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
        // 1) creo un array di 16 byte vuoto
        byte[] salt = new byte[16];

        // 2) creo un generatore di numeri casuali sicuro
        SecureRandom random = new SecureRandom();

        // 3) riempio l'array con byte casuali
        random.nextBytes(salt);

        // 4) converto la password in array di caratteri
        char[] charPassword = password.toCharArray();

        // 5) creo l'oggetto che descrive: password + salt + iterazioni + lunghezza hash
        PBEKeySpec keySpec = new PBEKeySpec(charPassword, salt, iterazioni, lunghezzaHash);

        // 6) scelgo l'algoritmo che deriva l'hash dalla password
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        // 7) genero il risultato finale
        byte[] hash = keyFactory.generateSecret(keySpec).getEncoded();

        // 8) converto hash e salt in stringhe salvabili
        String hashString = Base64.getEncoder().encodeToString(hash);
        String saltString = Base64.getEncoder().encodeToString(salt);
        String risultato[] = {hashString, saltString};
        
        return risultato;
	}
	
	
	public static boolean verifyPassword(String passwordInserita, String hashSalvato, String saltSalvato) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    
	    // 1. Convertiamo il salt recuperato dal DB da String stringa a byte[]
	    byte[] salt = Base64.getDecoder().decode(saltSalvato);
	    
	    char[] charPassword = passwordInserita.toCharArray();
	    
	    PBEKeySpec keySpec = new PBEKeySpec(charPassword, salt, iterazioni, lunghezzaHash);
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	    
	    // 3. Generiamo l'hash della password appena inserita usando il vecchio salt
	    byte[] hashNuovo = keyFactory.generateSecret(keySpec).getEncoded();
	    String hashNuovoString = Base64.getEncoder().encodeToString(hashNuovo);
	    
	    // 4. Confrontiamo l'hash appena calcolato con quello nel database
	    return hashNuovoString.equals(hashSalvato);
	}
        
        
        
        
        
        
}