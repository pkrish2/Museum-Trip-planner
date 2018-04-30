import java.util.ArrayList;

public class Edge implements Comparable<Edge>{

	public Node add1;
	public Node add2;
	private static final String KEY = "AIzaSyAGrxiPNDPLCx-uEgwlHPtZMJ_jqd5JjDQ";
	
	public Edge(Node ad1, Node ad2) {
		add1=ad1;
		add2=ad2;
	}
	
	public double getScore() {
		double dist;
		
		String distance=getDistance(add1.address,add2.address);
		
		if(distance.substring(distance.indexOf(" ")).trim().equals("ft")){
			String d=distance.substring(0, distance.indexOf(" ")).replaceAll(",","").trim();
			dist=Double.parseDouble(d)/5280.0;
		}
		else {
			dist=Double.parseDouble(distance.substring(0, distance.indexOf(" ")).replaceAll(",","").trim());
		}

		return dist+0.25*(5.0-((Double.parseDouble(add1.rating)+Double.parseDouble(add2.rating))/2.0));
		
	}

	@Override
	public int compareTo(Edge arg0) {
		if(getScore()>arg0.getScore()) {
			return 1;
		}
		
		if(getScore()==arg0.getScore()) {
			return 0;
		}
		else {
			return -1;
		}
	}
	
	 public static String getDistance (String origin, String destination){
	    	String start = origin.replaceAll(" ", "+");
	        String end = destination.replaceAll(" ", "+");
	        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&" + "origins="+ start + "&destinations=" + end + "&key="+KEY;
	        
	        URLGetter getter = new URLGetter(url);
	        ArrayList<String> contents = getter.getContents();
	        String nextLine = null;
	        for (int i =0; i<contents.size(); i++){
	            String currentLine = contents.get(i);
	            if(currentLine.contains("distance")){
	                nextLine = contents.get(i+1);
	                break;
	            }
	        }
	        String[] split = nextLine.split(":");
	        String second = split[1];
	        String ret = second.substring(2, second.length() -2);
	        return ret;
	    }

	    public static String getDuration (String origin, String destination){
	    	String start = origin.replaceAll(" ", "+");
	        String end = destination.replaceAll(" ", "+");
	        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&" + "origins="+ start + "&destinations=" + end + "&key="+KEY;
	        
	        URLGetter getter = new URLGetter(url);
	        ArrayList<String> contents = getter.getContents();
	        String nextLine = null;
	        for (int i =0; i<contents.size(); i++){
	            String currentLine = contents.get(i);
	            if(currentLine.contains("duration")){
	                nextLine = contents.get(i+1);
	                break;
	            }
	        }
	        String[] split = nextLine.split(":");
	        String second = split[1];
	        String ret = second.substring(2, second.length() -2);
	        return ret;
	    }

	
}
