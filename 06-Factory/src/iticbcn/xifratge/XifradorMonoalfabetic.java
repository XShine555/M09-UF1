package iticbcn.xifratge;

import java.util.Random;

class XifradorMonoalfabetic implements Xifrador {
    private static final char[] ALFABET = {
            'A', 'À', 'Á', 'Ä', 'B', 'C', 'Ç', 'D', 'E', 'È', 'É', 'Ë', 'F', 'G', 'H', 'I', 'Ì', 'Í', 'Ï', 'J', 'K',
            'L', 'M', 'N', 'Ñ', 'O', 'Ò', 'Ó', 'Ö', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ù', 'Ú', 'Ü', 'V', 'W', 'X', 'Y', 'Z'
    };

    private final char[] ALFABET_PERMUTAT = permutaAlfabet(ALFABET);

    private static char[] permutaAlfabet(char[] alfabet) {
        Random random = new Random();
        int rot = random.nextInt(1, Integer.MAX_VALUE);

        char[] result = new char[alfabet.length];
        for (int i = 0; i < alfabet.length; i++) {
            char c = alfabet[i];
            int newIndex = (i + rot) % alfabet.length;
            result[newIndex] = c;
        }

        return result;
    }

    private static int indexOf(char c, char[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null)
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");

        String result = "";
        for (char c : msg.toCharArray()) {
            if (!Character.isLetter(c)) {
                result += c;
                continue;
            }

            char normalC = Character.toUpperCase(c);
            int cIndex = indexOf(normalC, ALFABET);
            char newC = ALFABET_PERMUTAT[cIndex];
            result += Character.isUpperCase(c) ? newC : Character.toLowerCase(newC);
        }

        return new TextXifrat(result.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null)
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");

        String result = "";
        for (char c : xifrat.toString().toCharArray()) {
            if (!Character.isLetter(c)) {
                result += c;
                continue;
            }

            char normalC = Character.toUpperCase(c);
            int cIndex = indexOf(normalC, ALFABET_PERMUTAT);
            char newC = ALFABET[cIndex];
            result += Character.isUpperCase(c) ? newC : Character.toLowerCase(newC);
        }
        return result;
    }
}