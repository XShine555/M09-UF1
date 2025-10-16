import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int MIDA_IV = 16;
    private static byte[] iv;
    private static final String CLAU = "UnaClaveDe32BytesHaceFalta.!@·8";

    public static void main(String[] args) {
        String[] msgs = {
                "Lorem ipsum dicet",
                "Hola Andrés cómo esta tu cuñado",
                "Agora illa Òtto"
        };

        for (String msg : msgs) {
            byte[] bXifrats = new byte[0];
            String desxifrat = "";

            try {
                iv = generateIv();
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            } catch (Exception e) {
                System.out.println("Error de xifrat: " + e.getLocalizedMessage());
            }

            System.out.println("-----------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }
    }

    public static byte[] xifraAES(String msg, String password) throws Exception {
        byte[] bMsg = msg.getBytes();

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(password.getBytes(), ALGORISME_XIFRAT);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        return cipher.doFinal(bMsg);
    }

    public static String desxifraAES(byte[] bMsgXifrat, String pasString) throws Exception {
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(pasString.getBytes(), ALGORISME_XIFRAT);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] decryptedData = cipher.doFinal(bMsgXifrat);
        return new String(decryptedData);
    }

    private static byte[] generateIv() {
        byte[] ivBytes = new byte[MIDA_IV];
        secureRandom.nextBytes(ivBytes);
        return ivBytes;
    }
}