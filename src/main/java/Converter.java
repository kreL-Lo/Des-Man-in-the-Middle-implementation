import java.util.HashMap;
import java.util.Map;

public class Converter {
    private static Map<String,String > myMap = new HashMap<>();
    private static void initMap(){
        myMap.put("0000","0");
        myMap.put("0001","1");
        myMap.put("0010","2");
        myMap.put("0011","3");
        myMap.put("0100","4");
        myMap.put("0101","5");
        myMap.put("0110","6");
        myMap.put("0111","7");
        myMap.put("1000","8");
        myMap.put("1001","9");
        myMap.put("1010","A");
        myMap.put("1011","B");
        myMap.put("1100","C");
        myMap.put("1101","D");
        myMap.put("1110","E");
        myMap.put("1111","F");
    }
    public static String binToHex(String input){
        initMap();
        String str="";
        for(int i =0 ;i<input.length();i+=4){
            String tmp = input.substring(i,i+4);
            str+=myMap.get(tmp);
        }
        return str;
    }
}
