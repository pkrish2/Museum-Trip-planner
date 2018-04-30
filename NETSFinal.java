import java.io.BufferedWriter;
import java.io.File;	
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class NETSFinal {
	
	private static ArrayList<String> page;
	private static String url;
	private static HashMap<String,ArrayList<String>> map=new HashMap<String,ArrayList<String>>();
	
	
	
	public static void main(String[] args) {
		String target= "museums";
		String location;
		
		System.out.println("Enter your address:"+"\n");
		
		Scanner sc = new Scanner(System.in);
		location = sc.nextLine().trim();
		String myLocation=location.replaceAll(" ", "+");
		
//		location.replace(',', '');
//		System.out.println(output);
		
		
		url= "https://www.yelp.com/search?find_desc="+target+"&find_loc="+myLocation;
//		System.out.println(url);
		
		URLGetter website = 
				new URLGetter(url);
		//3650 Spruce St Philadelphia PA
		
		
		website.printStatusCode();
		page = website.getContents();
//		for(String ln:page) {
//			System.out.println(ln);
//		}
		

		 
		HashMap<String,ArrayList<String>> mp= findMuseums();
//		System.out.println(mp.get("Psychic readings"));
		mp.remove("");
//		for(String str:mp.keySet()) {
//			System.out.println(str+":"+mp.get(str));
//		}
        
      //do dijkstra
        
    	int counter=1;
    	Node[] result=new Node[10];
    	
    	Node curr=new Node("Me", "0", myLocation);
    	result[0]=curr;
    	
    	for(int i=0;i<6;i++) {
    		TreeSet<Edge> edgeSet=new TreeSet<Edge>();
        	
        	for(String entry:mp.keySet()) {
        		Node n1=curr;
        		if(entry.equals(curr.name)) {
        			continue;
        		}
        		Node n2=new Node(entry, mp.get(entry).get(0), mp.get(entry).get(1));
        		
        		edgeSet.add(new Edge(n1,n2));
        	}
        	
        	curr=edgeSet.pollFirst().add2;
        	
        	result[counter]= curr;
        	counter+=1;
        	
        	mp.remove(curr.name);//make sure I never see this entry again
    	}
    	for(Node n:result) {
    		System.out.println(n.name);
    	}
//    	System.out.println(result[1].name);

	}
	
	public static HashMap<String,ArrayList<String>> findMuseums() {
		
		int counter=0;
		
		HashMap<String,ArrayList<String>> map=new HashMap<String,ArrayList<String>>();
		//name to ranking and address
		
		//1. pattern match for continent name
		Iterator<String> iterator = page.iterator();
		String line="";

		while(iterator.hasNext()) {
			String name="";
			ArrayList<String> info=new ArrayList<String>();
			
			line= iterator.next();
			counter++;
			
			String template=".*<a class=\"biz-name.*<span >(.*)</span>";
             Pattern p= Pattern.compile(template);
             Matcher m  = p.matcher(line);
             
             if(m.find()){ 
            	
            	if(page.get(counter-1).contains("Ad")) {
            		continue;
            	}
            	
            	name=m.group(1).trim();
//            	System.out.println(name.trim());
            	
            	
            	
            	Iterator<String> iter = iterator;
     			String linex="";
     			while(iter.hasNext()) {
     				linex=iter.next();
     				counter++;
     				
	             	Pattern p1= Pattern.compile("<div.*i-stars.*title=\"(.*)star");
	             	Matcher m1  = p1.matcher(linex);
	             	
             		if(m1.find()) {
//             			System.out.print(m1.group(1));
             			info.add(m1.group(1).trim());
             			break;
             		} 							   	             	
     			}
     			
     			//get addresses
     			Iterator<String> iter1 = iter;
     			String liney="";
     			while(iter1.hasNext()) {
     				liney=iter1.next();
     				counter++;
     				
	             	Pattern p2= Pattern.compile("<address>");
	             	Matcher m2  = p2.matcher(liney);
	             	
             		if(m2.find()) {
             			liney=iter1.next();
             			counter++;
             			Pattern p3= Pattern.compile(" (.*)<br>(.*)");
    	             	Matcher m3  = p3.matcher(liney);
    	             	if(m3.find()) {
                 			info.add((m3.group(1)+" "+m3.group(2)).trim());
    	             	}
             			
             			break;
             		} 							   	             	
     			}
      	
    		 }
             map.put(name,info);		
         		
        }
		return map;
	}
	

   
	


}
