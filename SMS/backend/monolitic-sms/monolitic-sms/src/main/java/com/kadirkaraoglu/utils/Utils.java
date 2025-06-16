package com.kadirkaraoglu.utils;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	    public static boolean isValidTcNo(String tc) {
	return true;
	    }
		
		public static String personIdCreator(Long facultyId , String personId) throws Exception {
			int year = LocalDate.now().getYear();
		 	return facultyId.toString() + year +personId.toString().substring(7);
		
		}
		
		
		public static String defualtPasswordCreator(String tckn) {
			return tckn.substring(7);
			
		}

	
}
