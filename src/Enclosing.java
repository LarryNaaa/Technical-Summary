public class Enclosing {
    static String str0 = "enclosing class static member";
    String str = "enclosing class member";

    void run(){

        class Local {
            // static String str4 = "define static member";
            String str5 = "define non-static member";

            void run(){
                System.out.println("Local class running!");
                System.out.println(str);
                System.out.println(str0);
                System.out.println(str5);
            }
        }
        Local local = new Local();
        local.run();
    }

    public static class StaticNested {
        static String str4 = "define static member";
        String str5 = "define non-static member";
        static final String str1 = "define a const";

        // They only have access to static members in the enclosing class
        private void testAccessStaticMemberOfEnclosingClass(){
            // System.out.println(str);
            System.out.println(str0);
        }

        private void testDefineStaticAndNonStaticMember(){
            System.out.println(str4);
            System.out.println(str5);
        }

        private void run(){
            System.out.println("static nested class running!");
        }
    }

    public class InnerClass{
        // They can only define non-static members
        // static String str4 = "define static member";
        static final String str1 = "define a const";
        String str5 = "define non-static member";

        private void run(){
            System.out.println("Inner class running!");
            System.out.println(str);
            System.out.println(str0);
            System.out.println(str5);
            System.out.println(str1);
        }
    }

    public static void main(String[] args) {
        System.out.println("----------- Static Nested class ------------");
        Enclosing.StaticNested staticNested = new StaticNested();
        staticNested.run();
        staticNested.testAccessStaticMemberOfEnclosingClass();
        staticNested.testDefineStaticAndNonStaticMember();

        System.out.println("----------- Inner class ------------");
        Enclosing enclosing = new Enclosing();
        InnerClass innerClass = enclosing.new InnerClass();
        innerClass.run();

        System.out.println("----------- Local class ------------");
        enclosing.run();
    }
}
