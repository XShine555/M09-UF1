import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HexFormat;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 128;

    public long npass = 0;

    private static final char[] CHARSET = "abcdefABCDEF1234567890!".toCharArray();
    private static final int MAX_LENGTH = 6;

    public String getSHA512AmbSalt(String pw, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            String input = pw + salt;
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            HexFormat hexFormat = HexFormat.of();
            return hexFormat.formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public String getPBKDF2AmbSalt(String pw, String salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), ITERATIONS,
                    KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            HexFormat hexFormat = HexFormat.of();
            return hexFormat.formatHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    public String forcaBrutaRecursive(String alg, String hash, String salt, String current) {
        if (current.length() > MAX_LENGTH) {
            return null;
        }

        
        if (computeHash(alg, current, salt).equals(hash)) {
            return current;
        }
        this.npass++;

        for (char c : CHARSET) {
            String attempt = forcaBrutaRecursive(alg, hash, salt, current + c);
            if (attempt != null) {
                return attempt;
            }
        }
        return null;
    }

    public String forcaBruta(String alg, String hash, String salt) {
        return forcaBrutaRecursive(alg, hash, salt, "");
    }

    private String computeHash(String alg, String input, String salt) {
        String result = null;
        if ("SHA-512".equalsIgnoreCase(alg)) {
            result = getSHA512AmbSalt(input, salt);
        } else if ("PBKDF2".equalsIgnoreCase(alg)) {
            result = getPBKDF2AmbSalt(input, salt);
        }
        return result;
    }

    public String getInterval(long t1, long t2) {
        long diff = t2 - t1;

        long millis = diff % 1000;
        long totalSeconds = diff / 1000;
        long seconds = totalSeconds % 60;
        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;
        long totalHours = totalMinutes / 60;
        long hours = totalHours % 24;
        long days = totalHours / 24;

        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis",
                days, hours, minutes, seconds, millis);
    }

    public static void main(String[] args) {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();

        String[] aHashes = {
                h.getSHA512AmbSalt(pw, salt),
                h.getPBKDF2AmbSalt(pw, salt)
        };

        String pwTrobat;
        String[] algorismes = { "SHA-512", "PBKDF2" };
        for (int i = 0; i < aHashes.length; i++) {
            h.npass = 0;
            System.out.println("==============================");
            System.out.format("Algorisme: %s\n", algorismes[i]);
            System.out.format("Hash: %s\n", aHashes[i]);
            System.out.println("==============================");
            System.out.println("-- Inici de força bruta --");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.format("Pass: %s\n", pwTrobat);
            System.out.format("Provats: %d\n", h.npass);
            System.out.format("Temps: %s\n", h.getInterval(t1, t2));
            System.out.println("------------------------------");
        }
    }
}
