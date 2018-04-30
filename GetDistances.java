import java.util.ArrayList;

/**
 * Created by bharadwajsh on 4/26/18.
 */
public class GetDistances {

    private static final String KEY = "AIzaSyAGrxiPNDPLCx-uEgwlHPtZMJ_jqd5JjDQ";


    public static String constructURl (String origin, String destination){
        String start = origin.replaceAll(" ", "+");
        String end = destination.replaceAll(" ", "+");
        String output = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&" + "origins="+ start + "&destinations=" + end + "&key="+KEY;
        return output;
    }

    public static String getDistance (String url){
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

    public static String getDuration (String url){
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


    public static void main (String [] args){
        String a = "Portland OR";
        String b = "Seattle WA";
        System.out.println(constructURl(a,b));

        System.out.println(getDistance(constructURl(a,b)));
        System.out.println(getDuration(constructURl(a,b)));
    }
}
