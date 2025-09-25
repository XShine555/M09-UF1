public class Rot13X {
    private static final char[] ALPHABET_LOWER = {
            'a', 'à', 'á', 'ä', 'b', 'c', 'ç', 'd', 'e', 'è', 'é', 'ë', 'f', 'g', 'h', 'i', 'ì', 'í', 'ï', 'j', 'k',
            'l', 'm',
            'n', 'ñ', 'o', 'ò', 'ó', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ù', 'ú', 'ü', 'v', 'w', 'x', 'y', 'z'
    };

    private static final char[] ALPHABET_UPPER = getAlphabetUpper();

    public static void main(String[] args) {
        System.out.println("Xifrat");
        System.out.println("---------");
        System.out.println("(0)-ABC => " + xifraRotX("ABC", 0));
        System.out.println("(2)-XYZ => " + xifraRotX("XYZ", 2));
        System.out.println("(4)-Hola, Mr. calçot => " + xifraRotX("Hola, Mr. calçot", 4));
        System.out.println("(6)-Perdó, per tu què és? => " + xifraRotX("Perdó, per tu què és?", 6));

        System.out.println();

        System.out.println("Desxifrat");
        System.out.println("---------");
        System.out.println("(0)-ABC => " + desxifraRotX("ABC", 0));
        System.out.println("(2)-ZAÀ => " + desxifraRotX("ZAÀ", 2));
        System.out.println("(4)-Ïpob, Òù. èboépü => " + desxifraRotX("Ïpob, Òù. èboépü", 4));
        System.out.println("(6)-Ùhügt, ùhü wx úxi ìv? => " + desxifraRotX("Ùhügt, ùhü wx úxi ìv?", 6));

        System.out.println();
        forcaBrutaRotX("Ùhügt, ùhü wx úxi ìv?");
    }

    public static String xifraRotX(String input, int offset) {
        String result = "";

        for (char character : input.toCharArray()) {
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

        return result;
    }

    public static String desxifraRotX(String input, int offset) {
        String result = "";

        for (char character : input.toCharArray()) {
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

    public static void forcaBrutaRotX(String input) {
        System.out.format("Missatge xifrat: %s%n", input);
        System.out.println("----------------");

        for (int offset = 0; offset < ALPHABET_LOWER.length; offset++) {
            String result = desxifraRotX(input, offset);
            System.out.format("(%s)->%s%n", offset, result);
        }
    }

    private static int findIndex(char character, char[] alphabet) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == character) {
                return i;
            }
        }
        return -1;
    }

    private static char[] getAlphabetUpper() {
        char[] alphabetUpper = new char[ALPHABET_LOWER.length];
        for (int i = 0; i < ALPHABET_LOWER.length; i++) {
            alphabetUpper[i] = Character.toUpperCase(ALPHABET_LOWER[i]);
        }
        return alphabetUpper;
    }
}