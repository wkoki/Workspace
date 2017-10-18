class Source {
    public final String str;
    public int pos;

    public Source(String str) {
        this.str = str;
    }

    public final int peek() {
        if (pos < str.length()) {
            return str.charAt(pos);
        }
        return -1;
    }

    public final void next() {
        ++pos;
    }
}

class Parser extends Source {
    public Parser(String str) {
        super(str);
    }

    public final int number() {
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = peek()) >= 0 && Character.isDigit(ch)) {
            sb.append((char) ch);
            next();
        }
        return Integer.parseInt(sb.toString());
    }

    public final int expr() {
        int x = term();
        for (;;) {
            switch (peek()) {
            case '+':
                next();
                x += term();
                continue;
            case '-':
                next();
                x -= term();
                continue;
            }
            break;
        }
        return x;
    }

    public final int term() {
        int x = number();
        for (;;) {
            switch (peek()) {
            case '*':
                next();
                x *= number();
                continue;
            case '/':
                next();
                x /= number();
                continue;
            }
            break;
        }
        return x;
    }
}

public class Main {

    static void test(String s) {
        System.out.println(s + " = " + new Parser(s).expr());
    }

    public static void main(String[] args) {
        test("12+34+56");
        test("1-2*3");
        test("100/10/2");
    }
}
