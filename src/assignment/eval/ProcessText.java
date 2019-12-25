package assignment.eval;

import java.util.Scanner;

public class ProcessText {
	public String getReplacedText(String text) {
		String finalText = "";
		//Replacing all tabs with tabs+~, space by |space, new line with ]new line
		text = text.replaceAll("\t", "\t~").replace(" ", "| ").replace("\n", "]\n");
		String[] splittedText = text.split("[.,?;\t\n\\s]+"); 
		for (String t : splittedText) {
			t = t.replaceAll("~", "\t").replace("|", " ").replace("]", "\n");
			String tempText = t.replaceAll("[\\t\\n\\s]", "");
			if(tempText.length() == 6) {
				t = t.replace(tempText, "--java--");
			}
			finalText += t;
		}
		return finalText;
	}
	
	public static void main(String[] args) {
		System.out.println("Please type text to process and press enter 3 times to get result...");
		Scanner sc = new Scanner(System.in);
		String text = "";
        String prevText = "0";
        while (sc.hasNextLine()) {
        	String currentText = sc.nextLine();  
        	text += "\n"+currentText;
			if(currentText.isEmpty() && prevText.isEmpty()) {
        		break;
        	}
			prevText = currentText;
        }
		ProcessText pt = new ProcessText();
		//Passing input to a function which returns desired output.
		System.out.println(pt.getReplacedText(text));
		sc.close();
	}
}
