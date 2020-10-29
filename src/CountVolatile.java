public class CountVolatile implements Runnable {
    public static CountVolatile instance = new CountVolatile();
    public static volatile int i = 0;
    public synchronized void increate() {
        i++;
    }
//    public static void increate() {
//        i++;
//    }

    public void run() {
        for (int c = 0; c < 10000000; c++) {
//            synchronized (instance){
//                increate();
//            }
            increate();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}