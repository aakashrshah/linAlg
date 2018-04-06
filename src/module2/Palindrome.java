import java.util.Arrays;

public class Palindrome {

    public static boolean isDoublePalindrome (char[] digits)
    {
    		if(digits.length % 2 != 0) {
    			return false;
    		}
    		
    		if(isPalindrome(digits)) {
    			char[] left = Arrays.copyOfRange(digits, 0, digits.length/2);
			char[] right = Arrays.copyOfRange(digits, (digits.length/2), digits.length);
				if(isPalindrome(left) && isPalindrome(right)) {
        				return true;
        			}
    		}
		return false;
    }
    
    public static boolean isPalindrome(char[] digits) {
    		int start = 0;
    		int end = digits.length - 1;
    		while(end > start) {
    			if(digits[start] != digits[end] ) {
    				return false;
    			}
    			start++;
    			end--;
    		}
    		return true;
    }

}
