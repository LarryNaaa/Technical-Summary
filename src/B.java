public class B extends A{
    private int b;

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public static void main(String[] args) {
        B b1 = new B();
        b1.getA();
    }
}


