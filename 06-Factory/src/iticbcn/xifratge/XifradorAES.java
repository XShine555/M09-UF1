package iticbcn.xifratge;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        byte[] aesPassword;
        byte[] iv = generateIv();

        try {
            aesPassword = deriveKey(clau, iv, 256);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ClauNoSuportada(e.getLocalizedMessage());
        }

        byte[] bMsg = msg.getBytes();

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(aesPassword, ALGORISME_XIFRAT);

        Cipher cipher;
        try {
            cipher = Cipher.getInstance(FORMAT_AES);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new ClauNoSuportada(e.getLocalizedMessage());
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new ClauNoSuportada(e.getLocalizedMessage());
        }

        byte[] encryptedMsg;

        try {
            encryptedMsg = cipher.doFinal(bMsg);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new ClauNoSuportada(e.getLocalizedMessage());
        }

        byte[] bMsgWithIv = new byte[iv.length + encryptedMsg.length];
        System.arraycopy(iv, 0, bMsgWithIv, 0, iv.length);
        System.arraycopy(encryptedMsg, 0, bMsgWithIv, iv.length, encryptedMsg.length);

        return new TextXifrat(bMsgWithIv);
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        byte[] iv = new byte[MIDA_IV];
        System.arraycopy(xifrat.getBytes(), 0, iv, 0, iv.length);

        byte[] aesPassword;
        try {
            aesPassword = deriveKey(clau, iv, 256);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ClauNoSuportada(e.getLocalizedMessage());
        }

        byte[] bMsgXifrat = xifrat.getBytes();

        byte[] bMsg = new byte[bMsgXifrat.length - iv.length];
        System.arraycopy(bMsgXifrat, iv.length, bMsg, 0, bMsg.length);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(aesPassword, ALGORISME_XIFRAT);

        Cipher cipher;
        try {
            cipher = Cipher.getInstance(FORMAT_AES);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new ClauNoSuportada(e.getLocalizedMessage());
        }
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new ClauNoSuportada(e.getLocalizedMessage());
        }

        byte[] decryptedData;
        try {
            decryptedData = cipher.doFinal(bMsg);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new ClauNoSuportada(e.getLocalizedMessage());
        }

        return new String(decryptedData);
    }

    private byte[] generateIv() {
        byte[] ivBytes = new byte[MIDA_IV];
        secureRandom.nextBytes(ivBytes);
        return ivBytes;
    }

    private static byte[] deriveKey(String password, byte[] salt, int keySizeBits) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                100_000,
                keySizeBits
        );
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        SecretKey key = skf.generateSecret(spec);
        return key.getEncoded();
    }
}