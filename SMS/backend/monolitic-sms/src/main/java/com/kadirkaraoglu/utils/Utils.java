package com.kadirkaraoglu.utils;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	    public static boolean isValidTcNo(String tc) {
	 
//	            if (tc == null || tc.length() != 11 || !tc.matches("\\d+")) 
//	            	return false;
//	            
//	            if (tc.charAt(0) == '0')
//	            	return false;
//
//	            int[] digits = new int[11];
//	            for (int i = 0; i < 11; i++) {
//	                digits[i] = Character.getNumericValue(tc.charAt(i));
//	            }
//
//	            int sumOdd = digits[0] + digits[2] + digits[4] + digits[6] + digits[8];
//	            int sumEven = digits[1] + digits[3] + digits[5] + digits[7];
//
//	            int digit10 = ((sumOdd * 7) - sumEven) % 10;
//	            int digit11 = (Arrays.stream(digits, 0, 10).sum()) % 10;
//
//	            return digits[9] == digit10 && digits[10] == digit11;
	    	return true;
	        }
	    
		
		public static String usernameCreator(Long facultyId , String tckn) throws Exception {
			String year = Integer.toString(LocalDate.now().getYear()) ;
		 	return year.substring(2) + facultyId.toString() + tckn.toString().substring(7);
		
		}
		
		
		public static String defualtPasswordCreator(String tckn) {
			return tckn.substring(7);
			
		}

	
}
