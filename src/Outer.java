public class Outer {
    public int age = 18;
    static int b = 2;
    class Inner {
        public int age = 20;
        static final int b = 3;
        public void showAge() {
            int age  = 25;

            System.out.println(age);//空1
            System.out.println(this.age);//空2
            System.out.println(Outer.this.age);//空3
            System.out.println(Outer.b);
            System.out.println(Outer.this.b);
            System.out.println(b);
        }
    }


    // How to create instance of static and non static nested class
    public static void main(String... args){

        Outer outer = new Outer();
        Inner inner = outer.new Inner();
        inner.showAge();
    }
}
