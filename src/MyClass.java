public class MyClass extends Thread {
    public static int amount = 0;

    public static void main(String[] args) {
        MyClass thread = new MyClass();
        thread.start();
        System.out.println(amount);
        amount++;
        System.out.println(amount);
    }

    public void run() {
        amount++;
    }
}