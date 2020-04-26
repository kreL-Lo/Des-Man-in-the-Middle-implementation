import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ManInTheMiddle {

    //mostly inspired by https://en.wikipedia.org/wiki/Meet-in-the-middle_attack
    //and http://cacr.uwaterloo.ca/hac/about/chap7.pdf
    //INPUT : PLAINTEXT AND CRIPTO TEXT
    //OUTPUT : THE 2 KEYS used in double DES
    //double des : c =enc(enc(p,k1),k2)
    //  p =dec( dec(c,k2),k1)

    public static void main(String[] args) {
        ManInTheMiddle.doubleDESAttack("0123456789ABCDEF", "90157CB354375148");
    }// key is

    //
    public static String chr(int nr) {
        switch (nr) {
            case 0:
                return "0";
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "7";
            case 8:
                return "8";
            case 9:
                return "9";
            case 10:
                return "A";
            case 11:
                return "B";
            case 12:
                return "C";
            case 13:
                return "D";
            case 14:
                return "E";
            case 15:
                return "F";
        }
        return "-";
    }
    static String fitString(String input ){
        String str= "";
        char[] chars = input.toCharArray();
        for(char ch : chars){
            if(ch=='a'){
            str= str +'A';
            }else if(ch=='b'){
                str=str+'B';
            }else if(ch=='c'){
                str=str+'C';
            }else if(ch=='d'){
                str=str+'D';
            }else if(ch=='e'){
                str=str+'E';
            }else if(ch=='f') {
                str =str+'F';
            }else{
                str=str+ch;
            }
        }
        return str;
    }
    public static void doubleDESAttack(String p, String c) {
        Map<String,String> setA = new HashMap<>();
        for(int i= 0;i<256;i++){
            BigInteger a=  new BigInteger(Integer.toString(i));
            BigInteger b= new BigInteger("8");
            a= a.pow(8);
            String key = fitString(a.toString(16));

            setA.put(key,DES.enc(p,key));
        }
        Map<String,String> setB = new HashMap<>();

        for(int i= 0;i<256;i++){
            BigInteger a=  new BigInteger(Integer.toString(i));
            BigInteger b= new BigInteger("8");
            a= a.pow(8);
            String key = fitString(a.toString(16));
            setB.put(key,DES.dec(c,key));
        }

        for(String key : setA.keySet()){
            String val1=setA.get(key);
            for(String key2 :setB.keySet() ){
                String val2= setB.get(key2);
                if(val1.compareTo(val2)==0){
                    System.out.println(key+ "\n"+key2);
                }
            }
        }
    }

}