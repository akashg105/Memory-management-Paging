

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Helper {
	
	
	//core method
	public int process(int pageFrames) throws IOException {
		int fault=0;
		File file=new File("memoryReferences.txt");

		FileReader f=new FileReader(file);
		BufferedReader buffer = new BufferedReader(f);
	
		String line=null;
		String[] Ref = null; 
		while((line=buffer.readLine())!=null){
			Ref = line.trim().split(",");          //storing in an array
        }
		HashMap<Integer, Reference> page = new HashMap<Integer, Reference>();
		
		for(int i=0;i<Ref.length;++i){					//iterating among the requests
			int ref = Integer.parseInt(Ref[i]);
//			System.out.println("--------------------------------------");
//			System.out.println("Incoming Request "+ ref);
			
			if(page.containsKey(ref)) {
				//System.out.println("Hit");
				page.get(ref).setRefBit((byte)1);
			}
			else {
				//System.out.println("Miss: " +ref);
				fault++;
				this.insert(page, ref, pageFrames);
				
			}
			for(Map.Entry<Integer, Reference> entry: page.entrySet()) {
				Reference reference = entry.getValue();
				reference.shift();
				if(reference.getRefBit()==1) {
					reference.setMin();
					reference.setRefBit((byte)0);
				}
			}
			
			//System.out.println("***********Page Table***********");
			for(Map.Entry<Integer, Reference> entry: page.entrySet()) {
				String temp = Integer.toBinaryString(entry.getValue().getCount());
				if(temp.length()>8) {
					temp = temp.substring(temp.length() - 8);
					
				}
				//System.out.println(String.format("%16s",entry.getKey())+" "+ String.format("%16s", entry.getValue().getRefBit())+" "+ String.format("%16s", temp) );
			}
			
		}
		//System.out.println("----------------------------");
		
		return fault;
	}
	
	public void addPage(HashMap<Integer, Reference> page,Integer ref ) {
		page.put(ref, new Reference());
	}
	
	//adding page to the table based on page fault
	public void insert(HashMap<Integer, Reference> page, Integer ref, int pageFrames) {
		Byte min = (byte)255;
		if(page.size()<pageFrames) {
			this.addPage(page, ref);
			return;
		}
		int key=0;
		for(Map.Entry<Integer, Reference> entry: page.entrySet()) {   //page replacement
			Reference reference = entry.getValue();
		    int temp1 = (int)reference.getCount() & 0x00ff;
		    int temp2 = (int)min & 0x00ff;
			if(temp1<temp2) {
				min = reference.getCount();
				key = entry.getKey();
			}

	    }
		page.remove(key);
		//System.out.println("Removing "+ key);
		this.addPage(page, ref);
	}
}
