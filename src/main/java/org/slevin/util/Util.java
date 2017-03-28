package org.slevin.util;

public class Util {

	public static int sort(float[] scores){
	
		float value = Float.MIN_VALUE;
		int index = Integer.MIN_VALUE;
		
		for(int i =0;i<scores.length;i++) {
		
		            if(scores[i] > value) {
		            	value = scores[i];

		            	index = i;
	
		            }
		
		        }

		return index;
	}

	public static void chechCpu(){
		int processors = Runtime.getRuntime().availableProcessors();
		System.out.println(processors);
	}
	
	public static void main(String[] args) {
		chechCpu();
	}
}
