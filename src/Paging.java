

import java.io.IOException;

import java.util.Scanner;

public class Paging {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.print("Enter the Number of Page Frames for input");
		int pageFrames=reader.nextInt();
		Helper result = new Helper();
		for(int i=5;i<=100;i=i+5) {
			System.out.println(i+" "+result.process(i));
		}
		//int ans = result.process(pageFrames);
		//System.out.println(ans);       //page faults
	}

}
