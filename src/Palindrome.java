public class Palindrome {
    public static boolean isPalindrome(String input){
        int start = 0, end = input.length() - 1;
        while(start <= end){
            char left = input.charAt(start);
            char right = input.charAt(end);

            if(left != right){
                return false;
            }

            start++;
            end--;
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("kayak"));
        System.out.println(isPalindrome("boston"));
    }
}
