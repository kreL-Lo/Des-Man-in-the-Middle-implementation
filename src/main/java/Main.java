import java.util.HashMap;
import java.util.Map;
// this it the original implementation, very primitive i might add
public class Main {
    private static int [] permTable=
            {
                    57,   49 ,   41,   33 ,   25,    17,    9,
                    1,   58   , 50  , 42  ,  34 ,   26,   18,
                    10   , 2   , 59 ,  51 ,   43 ,   35,   27,
                    19   ,11   ,  3 ,  60   , 52   , 44 ,  36,
                    63  , 55  ,  47  , 39  ,  31  ,  23 ,  15,
                    7  , 62   , 54  , 46  ,  38   , 30  , 22,
                    14  ,  6  ,  61  , 53  ,  45  ,  37  , 29,
                    21 ,  13 ,    5   ,28 ,   20  ,  12   , 4
            };
    private static int[] pc2={

            14   , 17 ,  11 ,   24    , 1,    5,
            3   , 28  , 15   ,  6    ,21  , 10,
            23  ,  19 ,  12   ,  4   , 26  ,  8,
            16  ,   7  , 27   , 20   , 13  ,  2,
            41  ,  52 ,  31   , 37   , 47  , 55,
            30  ,  40 ,  51   , 45  ,  33  , 48,
            44  ,  49 ,  39   , 56 ,   34  , 53,
            46  ,  42 ,  50   , 36,    29  , 32
    };

    static Map<String,String > map = new HashMap<>();
    private static void initMap(){
        map.put("0","0000");
        map.put("1","0001");
        map.put("2","0010");
        map.put("3","0011");
        map.put("4","0100");
        map.put("5","0101");
        map.put("6","0110");
        map.put("7","0111");
        map.put("8","1000");
        map.put("9","1001");
        map.put("A","1010");
        map.put("B","1011");
        map.put("C","1100");
        map.put("D","1101");
        map.put("E","1110");
        map.put("F","1111");
    }
    private static int [] IP ={

            58 ,   50  , 42,    34,    26  , 18,    10    ,2,
            60  ,  52  , 44 ,   36 ,   28 ,  20 ,   12   , 4,
            62   , 54   ,46  ,  38  ,  30  , 22  ,  14  ,  6,
            64   , 56  , 48   , 40   , 32,   24   , 16 ,   8,
            57   , 49   ,41    ,33    ,25   ,17    , 9    ,1,
            59   , 51  , 43   , 35,    27  , 19,    11   , 3,
            61   , 53 ,  45  ,  37  ,  29 ,  21 ,   13  ,  5,
            63   , 55,   47 ,   39 ,   31,   23  ,  15 ,   7
    };
    private static String hexToBin(String message){
        char [] chars = message.toCharArray();
        String out = "";
        for(char ch : chars){
            out+=map.get(String.valueOf(ch));
        }
        return out;
    }

    private static String permKey(String key){
        String K ="";
        char [] chars = key.toCharArray();
        for(int i= 0 ;i<56;i++)
        {
            K+= key.charAt( permTable[i]-1);
        }

        return K;
    }
    private static String initPermutation(String message){

        String str = "";
        for(int i=0;i<IP.length;i++){
            str+=  message.charAt(IP[i]-1);
        }
        return str;
    }
    static int shifts[] = {
      1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1
    };
    private static String leftShift(String str, int times){

        for(int i =0 ;i<times;i++)
            str= str.substring(1,str.length())+str.charAt(0);
        return str;
    }
    static int eSelection[]={
            32     ,1,    2,     3,    4,    5,
            4    , 5   , 6  ,   7   ,  8 ,   9,
            8    , 9  , 10   , 11  ,  12  , 13,
            12   , 13   ,14   , 15    ,16  , 17,
            16 ,   17  , 18 ,   19   , 20  , 21,
            20  ,  21  , 22  ,  23  ,  24 ,  25,
            24    ,25 ,  26   , 27 ,   28 ,  29,
            28   , 29  , 30   , 31 ,   32   , 1
    };

    private static String f (String key, String E){
        String str = "";

        for(int i= 0;i<key.length();i++){
            str +=key.charAt(i)^E.charAt(i);
        }
        return str;
    }

    public static void main(String [] args){
        String message = "85E813540F0AB405";
        System.out.println("Initial message: " + message);
        initMap();
        String plainBin=  hexToBin(message);

        String key ="355457799AACDFFF";
        System.out.println("Key: "+ key);
        String keyBin = hexToBin(key);


        String actualKey = permKey(keyBin);

        String []C = new String [17];
        String []D = new String [17];
        C[0]= actualKey.substring(0,actualKey.length()/2);
        D[0]= actualKey.substring(actualKey.length()/2,actualKey.length());

        //generate C[1]....C[15] respectiv pentru D[1]...D[15]
        for(int i =1 ;i<=16;i++){
            C[i]= leftShift(C[i-1],shifts[i-1]);
            D[i]= leftShift(D[i-1],shifts[i-1]);
        }
        String []CD = new String [16];
        for(int i =0 ;i<16;i++){
            CD[i] =C[i+1]+D[i+1];
        }
        String [] K  = new String [16];

        //THE SUB KEYS
        for(int i =0 ;i<16;i++){
            String str = "";

            for(int j = 0;j<48;j++){
                str +=CD[i].charAt(pc2[j]-1);
                //System.out.println(pc2[j]);
            }
            K[i]= str;
        }
        //INITIAL  PERMUTATION
        String IPm =initPermutation(plainBin);
        String [] L = new String [17];
        String [] R = new String [17];
        //
        L[0]= IPm.substring(0,IPm.length()/2);
        R[0]= IPm.substring(IPm.length()/2,IPm.length());

        String [] E = new String [17];
        for(int i= 1 ;i<=16;i++){
            L[i]=R[i-1];
            E[i-1]="";
            //F CALCULATION
            for(int j =0;j<eSelection.length;j++){
                E[i-1]+=R[i-1].charAt(eSelection[j]-1);
            }
            String out = f(K[i-1],E[i-1]);
            //THE BOXES
            int cnt =0 ;
            String[] Boxes= new String [8];
            for(int j =0 ;j<48;j+=6)
            {
                Boxes[cnt]=out.substring(j,j+6);
                ++cnt;
            }
            //compute the boxes Si(Bi)
            out ="";
            for(int j= 0 ;j<8;j++){
                out += Sboxes.computeSbox(Boxes[j],j);
            }
            out = Sboxes.lastPermutation(out);///here is the answer from function f
            //computethe R[i] here
            String tmp ="";
            for(int j=0 ;j<out.length();j++){
                tmp +=L[i-1].charAt(j)^out.charAt(j);
            }
            R[i]=tmp;
        }

        String finalBlock = Sboxes.permuteMinusUno(R[16]+L[16]);
        System.out.println("Cripted Text: "+Converter.binToHex(finalBlock));
        String plainText = finalBlock;
        //decription

        IPm =initPermutation(finalBlock);
        L[0]= IPm.substring(0,IPm.length()/2);
        R[0]= IPm.substring(IPm.length()/2,IPm.length());

        for(int i= 1 ;i<=16;i++){
            L[i]=R[i-1];
            E[i-1]="";
            //F CALCULATION
            for(int j =0;j<eSelection.length;j++){
                E[i-1]+=R[i-1].charAt(eSelection[j]-1);
            }

            String out = f(K[16-i],E[i-1]);
            //THE BOXES
            int cnt =0 ;
            String[] Boxes= new String [8];
            for(int j =0 ;j<48;j+=6)
            {
                Boxes[cnt]=out.substring(j,j+6);
                ++cnt;
            }
            //compute the boxes Si(Bi)
            out ="";
            for(int j= 0 ;j<8;j++){
                out += Sboxes.computeSbox(Boxes[j],j);
            }
            out = Sboxes.lastPermutation(out);///here is the answer from function f
            //computethe R[i] here
            String tmp ="";
            for(int j=0 ;j<out.length();j++){
                tmp +=L[i-1].charAt(j)^out.charAt(j);
            }
            R[i]=tmp;
        }

        String decript = Sboxes.permuteMinusUno(R[16]+L[16]);

        System.out.println("Decript text: "+Converter.binToHex(decript));
        //

    }
}
