public class Demo {
    public void test(String title) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(title);
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Demo demo = new Demo();
            demo.test("我要吃鸡" + i);
        }
    }
}
