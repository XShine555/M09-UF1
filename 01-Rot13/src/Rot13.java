public class Rot13 {
    private static final int ROT = 13;

    private static final char[] ALPHABET_LOWER = {
        'a', 'à', 'á', 'ä', 'b', 'c', 'ç', 'd', 'e', 'è', 'é', 'ë', 'f', 'g', 'h', 'i', 'ì', 'í', 'ï', 'j', 'k', 'l', 'm',
        'n', 'o', 'ò', 'ó', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ù', 'ú', 'ü', 'v', 'w', 'x', 'y', 'z'
    };

    private static final char[] ALPHABET_UPPER = getAlphabetUpper();

    public static void main(String[] args) {
        System.out.println("Xifrat");
        System.out.println("---------");
        System.out.println("ABC => " + xifraRot13("ABC"));
        System.out.println("XYZ => " + xifraRot13("XYZ"));
        System.out.println("Hola, Mr. calçot => " + xifraRot13("Hola, Mr. calçot"));
        System.out.println("Perdó, per tu què és? => " + xifraRot13("Perdó, per tu què és?"));

        System.out.println();

        System.out.println("Desxifrat");
        System.out.println("---------");
        System.out.println("GÍÏ => " + desxifraRot13("GÍÏ"));
        System.out.println("ÉËF => " + desxifraRot13("ÉËF"));
        System.out.println("Övùg, Úà. ïgùjvä => " + desxifraRot13("Övùg, Úà. ïgùjvä"));
        System.out.println("Zlàkx, zlà äb abm ná? => " + desxifraRot13("Zlàkx, zlà äb abm ná?"));
    }

    public static String xifraRot13(String input) {
        String result = "";

        for (char character : input.toCharArray()) {
            if (!Character.isLetter(character))
            {
                result += character;
                continue;
            }

            int originalIndex = Character.isLowerCase(character) 
                ? findIndex(character, ALPHABET_LOWER)
                : findIndex(character, ALPHABET_UPPER);

            int newIndex = (originalIndex + ROT) % ALPHABET_UPPER.length;
            char newChar = Character.isLowerCase(character) ? ALPHABET_LOWER[newIndex] : ALPHABET_UPPER[newIndex];

            result += newChar;
        }

        return result;
    }

    public static String desxifraRot13(String input) {
        String result = "";

        for (char character : input.toCharArray()) {
            if (!Character.isLetter(character))
            {
                result += character;
                continue;
            }

            int originalIndex = Character.isLowerCase(character)
                ? findIndex(character, ALPHABET_LOWER)
                : findIndex(character, ALPHABET_UPPER);

            int newIndex = (ALPHABET_LOWER.length + originalIndex - ROT) % ALPHABET_LOWER.length;
            
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

    private static char[] getAlphabetUpper() {
        char[] alphabetUpper = new char[ALPHABET_LOWER.length];
        for (int i = 0; i < ALPHABET_LOWER.length; i++) {
            alphabetUpper[i] = Character.toUpperCase(ALPHABET_LOWER[i]);
        }
        return alphabetUpper;
    }
}