import java.util.concurrent.TimeUnit;

public class Demo{
    private static Object lock=new Object();

    public static void main(String[] args) throws Exception {
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    System.out.println("t1 executing...");
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    System.out.println("t2 executing...");
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println("t1 "+ t1.getState());
        System.out.println("t2 "+ t2.getState());

        t1.start();
        t2.start();
        System.out.println("t1 "+ t1.getState());
        System.out.println("t2 "+ t2.getState());

        TimeUnit.SECONDS.sleep(11);
        System.out.println("t1 "+ t1.getState());
        System.out.println("t2 "+ t2.getState());

        TimeUnit.SECONDS.sleep(11);
        System.out.println("t1 "+ t1.getState());
        System.out.println("t2 "+ t2.getState());

    }
}