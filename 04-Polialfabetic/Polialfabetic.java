
import java.util.Random;

public class Polialfabetic {
    public static final char[] ALFABET_ORIGINAL = {
            'A', 'À', 'Á', 'Ä', 'B', 'C', 'Ç', 'D', 'E', 'È', 'É', 'Ë', 'F', 'G', 'H', 'I', 'Ì', 'Í', 'Ï', 'J', 'K',
            'L', 'M', 'N', 'Ñ', 'O', 'Ò', 'Ó', 'Ö', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ù', 'Ú', 'Ü', 'V', 'W', 'X', 'Y',
            'Z'
    };
    public static final long CLAU_SECRETA = 8475982749374L;

    public static char[] alfabet_permuted;
    private static Random random;

    public static void main(String[] args) {
        String[] msgs = {
                "Test 01 àrbritre, coixí, Perímetre",
                "Test 02 Taüll, DÍA, año",
                "Test 03 Peça, Òrrius, Bòvila"
        };

        String[] msgsXifrats = new String[msgs.length];

        System.out.println("Xifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(CLAU_SECRETA);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n: ", msgs[i], msgsXifrats[i]);
        }

        System.out.println("\nDesxifratge:\n-----------");
        for (String msgsXifrat : msgsXifrats) {
            initRandom(CLAU_SECRETA);
            String desxifrat = desxifraPoliAlfa(msgsXifrat);
            System.out.printf("%-34s -> %s%n: ", msgsXifrat, desxifrat);
        }
    }

    public static void permutaAlfabet() {
        int rot = random.nextInt(1, Integer.MAX_VALUE);

        alfabet_permuted = new char[ALFABET_ORIGINAL.length];
        for (int i = 0; i < ALFABET_ORIGINAL.length; i++) {
            char c = ALFABET_ORIGINAL[i];
            int newIndex = (i + rot) % ALFABET_ORIGINAL.length;
            alfabet_permuted[newIndex] = c;
        }
    }

    public static void initRandom(long key) {
        random = new Random(key);
    }

    public static String xifraPoliAlfa(String input) {
        String result = "";

        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) {
                result += c;
                continue;
            }

            permutaAlfabet();

            char normalC = Character.toUpperCase(c);
            int cIndex = findIndex(normalC, ALFABET_ORIGINAL);

            char newC = alfabet_permuted[cIndex];
            result += Character.isUpperCase(c) ? newC : Character.toLowerCase(newC);
        }

        return result;
    }

    public static String desxifraPoliAlfa(String input) {
        String result = "";

        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) {
                result += c;
                continue;
            }

            permutaAlfabet();

            char normalC = Character.toUpperCase(c);
            int cIndex = findIndex(normalC, alfabet_permuted);

            char newC = ALFABET_ORIGINAL[cIndex];
            result += Character.isUpperCase(c) ? newC : Character.toLowerCase(newC);
        }

        return result;
    }

    private static int findIndex(char c, char[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }
}
