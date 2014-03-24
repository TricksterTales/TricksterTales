package com.trickstertales.math;

import java.util.LinkedList;
import java.util.Random;

public class Maths {

	private static long seedNum = System.currentTimeMillis();
	private static Random random = new Random(seedNum);
	
	public static boolean isOnList(LinkedList<Long> list, long value) {
		for(Long l : list) {
			if(value == l) {
				return true;
			}
		}
		return false;
	}
	
	public static double randomDouble(double in, double ax) {
		return in + random.nextDouble() * (ax - in);
	}
	
	public static String toTwoDigits(int n) {
		if(n < 10) {
			return "0" + n;
		}
		return "" + n;
	}
	public static String toString(int val) {
		return "" + val;
	}
	
	public static String wordWrap(String str, int lineWidth) {
		if(str == "")
			return "";
		String product = "";
		String word = "";
		String white = "";
		int w = 0;
		boolean bigW = false;
		char ch;
		
		for(int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if(ch == '\n') {
				bigW = false;
				if(word == "") {
					white = "";
					product = product + "\n";
					w = 0;
					continue;
				}
				if(w + word.length() > lineWidth) {
					product = product + "\n";
					while(word.length() > lineWidth) {
						product = product  + word.substring(0, lineWidth - 2) + "-" + "\n";
						word = word.substring(lineWidth - 1);
					}
					product = product + word;
					word = "";
				}
				continue;
			}
			if(Character.isWhitespace(ch)) {
				if(word == "") {
					if(bigW)
						continue;
					white = white + ch;
					if(w + white.length() > lineWidth) {
						white = "";
						product = product + "\n";
						bigW = true;
						w = 0;
					}
					continue;
				}
				bigW = false;
				if(white != "") {
					if(w + word.length() > lineWidth) {
						product = product + "\n";
					} else {
						product = product + white;
						w = w + white.length();
					}
				}
				if(w + word.length() > lineWidth) {
					while(word.length() > lineWidth) {
						product = product + word.substring(0, lineWidth - 1) + "-" + "\n";
						word = word.substring(lineWidth - 1);
					}
					product = product + word;
					w = word.length();
				} else {
					product = product + word;
					w = w + word.length();
				}
				word = "";
				white = "" + ch;
				continue;
			}
			word = word + ch;
			bigW = false;
		}

		if(white != "") {
			if(w + word.length() > lineWidth) {
				product = product + "\n";
				w = 0;
			} else {
				product = product + white;
				w = w + white.length();
			}
		}
		if(word != "") {
			while(word.length() > lineWidth) {
				product = product + word.substring(0, lineWidth - 2) + "-" + "\n";
				word = word.substring(lineWidth - 1);
			}
			product = product + word;
			w = word.length();
		}
		
		return product;
	}

}
