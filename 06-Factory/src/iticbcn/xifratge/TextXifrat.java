package iticbcn.xifratge;

public class TextXifrat {
    private final byte[] bytes;

    public TextXifrat(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        if (bytes == null)
            return "null";
        return new String(bytes);
    }

    public byte[] getBytes() {
        return bytes;
    }
}
