package util;

import java.util.Scanner;

public class InputFromKeyboard {
	private Scanner console;
	
	public InputFromKeyboard(){
		console = new Scanner(System.in);
	}
	
	public String getString(){
		return this.console.nextLine();
	}
	
	public Integer getInt(){
		return this.console.nextInt();
	}
}
