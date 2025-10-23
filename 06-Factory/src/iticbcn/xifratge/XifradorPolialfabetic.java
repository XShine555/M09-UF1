package iticbcn.xifratge;

import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {
    private static final char[] ALFABET_ORIGINAL = {
            'A', 'À', 'Á', 'Ä', 'B', 'C', 'Ç', 'D', 'E', 'È', 'É', 'Ë', 'F', 'G', 'H', 'I', 'Ì', 'Í', 'Ï', 'J', 'K',
            'L', 'M', 'N', 'Ñ', 'O', 'Ò', 'Ó', 'Ö', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ù', 'Ú', 'Ü', 'V', 'W', 'X', 'Y',
            'Z'
    };

    private char[] alfabet_permuted;

    private Random random;

    private static int findIndex(char c, char[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        long seed;

        try {
            seed = Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }

        String result = "";
        initRandom(seed);

        for (char c : msg.toCharArray()) {
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

        return new TextXifrat(result.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        long seed;

        try {
            seed = Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }

        initRandom(seed);

        String result = "";

        for (char c : xifrat.toString().toCharArray()) {
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

    private void initRandom(long key) {
        random = new Random(key);
    }

    private void permutaAlfabet() {
        int rot = random.nextInt(1, Integer.MAX_VALUE);

        alfabet_permuted = new char[ALFABET_ORIGINAL.length];
        for (int i = 0; i < ALFABET_ORIGINAL.length; i++) {
            char c = ALFABET_ORIGINAL[i];
            int newIndex = (i + rot) % ALFABET_ORIGINAL.length;
            alfabet_permuted[newIndex] = c;
        }
    }
}