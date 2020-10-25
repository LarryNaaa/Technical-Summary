public class Enclosing {
    static String str0 = "enclosing class static member";
    String str = "enclosing class member";

    public static class StaticNested {

        public String str1 = "public access modifier";
        protected String str2 = "protected access modifier";
        private String str3 = "private access modifier";

        static String str4 = "define static member";
        String str5 = "define non-static member";

        // They can have all types of access modifiers in their declaration
        private void testAllAccessModifier(){
            System.out.println(str1);
            System.out.println(str2);
            System.out.println(str3);
        }

        // They only have access to static members in the enclosing class
        private void testAccessStaticMemberOfEnclosingClass(){
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
        private void run(){
            System.out.println("Inner class running!");
        }
    }

    public static void main(String[] args) {
        StaticNested staticNested = new StaticNested();
        staticNested.run();
        staticNested.testAllAccessModifier();
        staticNested.testAccessStaticMemberOfEnclosingClass();
        staticNested.testDefineStaticAndNonStaticMember();

        Enclosing enclosing = new Enclosing();
        InnerClass innerClass = enclosing.new InnerClass();
        innerClass.run();
    }
}
