public class B extends A{
    private int b;

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public static void main(String[] args) {
        boolean b1 = true, b2 = false;
        int i1 = 1, i2 = 2;
        System.out.println((i1 | i2) == 3);
    }
}


