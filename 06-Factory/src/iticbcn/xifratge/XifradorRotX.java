package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {
    private static final char[] ALPHABET_LOWER = {
            'a', 'à', 'á', 'ä', 'b', 'c', 'ç', 'd', 'e', 'è', 'é', 'ë', 'f', 'g', 'h', 'i', 'ì', 'í', 'ï', 'j', 'k',
            'l', 'm', 'n', 'ñ', 'o', 'ò', 'ó', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ù', 'ú', 'ü', 'v', 'w', 'x', 'y', 'z'
    };

    private static final char[] ALPHABET_UPPER = getAlphabetUpper();

    private static char[] getAlphabetUpper() {
        char[] alphabetUpper = new char[ALPHABET_LOWER.length];
        for (int i = 0; i < ALPHABET_LOWER.length; i++) {
            alphabetUpper[i] = Character.toUpperCase(ALPHABET_LOWER[i]);
        }
        return alphabetUpper;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        int offset;
        try {
            offset = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        if (offset < 0 || offset >= ALPHABET_LOWER.length) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        String result = "";

        for (char character : msg.toCharArray()) {
            if (!Character.isLetter(character)) {
                result += character;
                continue;
            }

            int originalIndex = Character.isLowerCase(character)
                    ? findIndex(character, ALPHABET_LOWER)
                    : findIndex(character, ALPHABET_UPPER);

            int newIndex = (originalIndex + offset) % ALPHABET_UPPER.length;
            char newChar = Character.isLowerCase(character) ? ALPHABET_LOWER[newIndex] : ALPHABET_UPPER[newIndex];

            result += newChar;
        }

        return new TextXifrat(result.getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        int offset;
        try {
            offset = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        if (offset < 0 || offset >= ALPHABET_LOWER.length) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        String result = "";

        for (char character : xifrat.toString().toCharArray()) {
            if (!Character.isLetter(character)) {
                result += character;
                continue;
            }

            int originalIndex = Character.isLowerCase(character)
                    ? findIndex(character, ALPHABET_LOWER)
                    : findIndex(character, ALPHABET_UPPER);

            int newIndex = (ALPHABET_LOWER.length + originalIndex - offset) % ALPHABET_LOWER.length;

            char newChar = Character.isLowerCase(character) ? ALPHABET_LOWER[newIndex] : ALPHABET_UPPER[newIndex];

            result += newChar;
        }

        return result;
    }

    private static int findIndex(char character, char[] alphabet) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == character) {
                return i;
            }
        }
        return -1;
    }
}