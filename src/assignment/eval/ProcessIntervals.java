package assignment.eval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

import custom.exceptions.WrongInterval;

public class ProcessIntervals {
	
	public static void getFinalInterval(ArrayList<ArrayList<Integer>> intervals, 
			ArrayList<ArrayList<Integer>> excludingIntervals) {
		
		TreeSet<Integer> group1 = new TreeSet<Integer>();
		Iterator<ArrayList<Integer>> intervalsItr = intervals.iterator();  
		
		//Map is using to store various sets of intervals depending on the break up of intervals 
		Map<String, ArrayList<Integer>> preFinal = new HashMap<String, ArrayList<Integer>>();
		ArrayList<Integer> preFinalElements = new ArrayList<Integer>();
		String key = "0";
		while(intervalsItr.hasNext()) {
			ArrayList<Integer> interval = intervalsItr.next();
			int start = interval.get(0);
			int end = interval.get(1);
			
			//if excluding interval is empty then key will not change through out the process
			key = excludingIntervals.isEmpty()?key:start+"#"+end;
			while(start <= end) {
				boolean isValid = true;
				Iterator<ArrayList<Integer>> excludeItr = excludingIntervals.iterator();
				while(excludeItr.hasNext()) {
					ArrayList<Integer> exInterval = excludeItr.next();
					int exStart = exInterval.get(0);
					int exEnd = exInterval.get(1);
					if(start >= exStart && start <= exEnd) {
						isValid = false;
						key = exStart +"#"+ exEnd;
					} 
				}
				//if number is lying within the range and it is not already added to stack then adding it
				if(isValid && !group1.contains(start)) {
					group1.add(start);
					if(preFinal.containsKey(key)){
						preFinalElements = preFinal.get(key);
					}else {
						preFinalElements = new ArrayList<Integer>();
					}
					if(!preFinalElements.contains(start)) {
						preFinalElements.add(start);
						preFinalElements = getMinMax(preFinalElements);
						preFinal.put(key, preFinalElements);
					 }
				}
				start++;
			}
		}
		ArrayList<ArrayList<Integer>> finalList = new ArrayList<ArrayList<Integer>>(preFinal.values()); 
		sortIntervals(finalList);
		System.out.println(finalList);
	}
	
	public static ArrayList<Integer> getMinMax(ArrayList<Integer> interval) {
		ArrayList<Integer> resp = new ArrayList<Integer>();
		resp.add(getMin(interval));
		resp.add(getMax(interval));
		return resp;
	}
	
	public static int getMax(ArrayList<Integer> list) {
      int max = 0;
     
      for(int i=0; i < list.size(); i++ ) {
         if(list.get(i) > max) {
            max = list.get(i);
         }
      }
      return max;
   }
	 
	public static void sortIntervals(ArrayList<ArrayList<Integer>> list) {
		Collections.sort(list,new Comparator<ArrayList<Integer>>(){ 
			public int compare(ArrayList<Integer> a1, ArrayList<Integer> a2) 
			{ 
				return a1.get(0) - a2.get(0); 
			} 
		}); 
   }
   public static int getMin(ArrayList<Integer> list) {
      int min = list.get(0);
     
      for(int i=0; i < list.size(); i++ ) {
         if(list.get(0) < min) {
            min = list.get(0);
         }
      }
      return min;
   }
	   
	public static void main(String[] args) {
		
		int interval_size = 0;
		int exclude_size = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("How many intervals do you want to include?");
		ArrayList<ArrayList<Integer>> intervals = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> excludingIntervals = new ArrayList<ArrayList<Integer>>();
		try {
			interval_size = sc.nextInt();
			for (int i = 0; i < interval_size; i++) {
				ArrayList<Integer> set = new ArrayList<Integer>();
				System.out.println("Enter two numbers for interval "+(i+1));
				int a = sc.nextInt();
				int b = sc.nextInt();
				if(a <= b) {
					set.add(a);
					set.add(b);
					intervals.add(set);
				}else {
					throw new WrongInterval("Interval start value should be less than end value");
				}
				 
			}
			System.out.println("How many intervals do you want to exclude?");
			exclude_size = sc.nextInt();
			for (int i = 0; i < exclude_size; i++) {
				ArrayList<Integer> set = new ArrayList<Integer>();
				System.out.println("Enter two numbers for excluding interval "+(i+1));
				int a = sc.nextInt();
				int b = sc.nextInt();
				if(a <= b) {
					set.add(a);
					set.add(b);
					excludingIntervals.add(set);
				}else {
					throw new WrongInterval("Interval start value should be less than end value");
				}
			}
			sortIntervals(excludingIntervals);
			getFinalInterval(intervals, excludingIntervals);
		} catch (InputMismatchException | NullPointerException | WrongInterval e) {
			System.out.println("Error: "+e.getMessage());
		}finally {
			sc.close();
		}
		
	}
		
}
