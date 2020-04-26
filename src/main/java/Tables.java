import java.util.HashMap;
import java.util.Map;

public class Tables {
    //pc-1
    public static int [] permTable=
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
    public static int[] pc2={

            14   , 17 ,  11 ,   24    , 1,    5,
            3   , 28  , 15   ,  6    ,21  , 10,
            23  ,  19 ,  12   ,  4   , 26  ,  8,
            16  ,   7  , 27   , 20   , 13  ,  2,
            41  ,  52 ,  31   , 37   , 47  , 55,
            30  ,  40 ,  51   , 45  ,  33  , 48,
            44  ,  49 ,  39   , 56 ,   34  , 53,
            46  ,  42 ,  50   , 36,    29  , 32
    };
    public static Map<String,String > map = new HashMap<>();
    public static void initMap(){
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
    public static int [] IP ={

            58 ,   50  , 42,    34,    26  , 18,    10    ,2,
            60  ,  52  , 44 ,   36 ,   28 ,  20 ,   12   , 4,
            62   , 54   ,46  ,  38  ,  30  , 22  ,  14  ,  6,
            64   , 56  , 48   , 40   , 32,   24   , 16 ,   8,
            57   , 49   ,41    ,33    ,25   ,17    , 9    ,1,
            59   , 51  , 43   , 35,    27  , 19,    11   , 3,
            61   , 53 ,  45  ,  37  ,  29 ,  21 ,   13  ,  5,
            63   , 55,   47 ,   39 ,   31,   23  ,  15 ,   7
    };
    public static int shifts[] = {
            1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1
    };
    public static int eSelection[]={
            32     ,1,    2,     3,    4,    5,
            4    , 5   , 6  ,   7   ,  8 ,   9,
            8    , 9  , 10   , 11  ,  12  , 13,
            12   , 13   ,14   , 15    ,16  , 17,
            16 ,   17  , 18 ,   19   , 20  , 21,
            20  ,  21  , 22  ,  23  ,  24 ,  25,
            24    ,25 ,  26   , 27 ,   28 ,  29,
            28   , 29  , 30   , 31 ,   32   , 1
    };

    public static String hexToBin(String message){
        char [] chars = message.toCharArray();
        String out = "";
        for(char ch : chars){
            out+=map.get(String.valueOf(ch));
        }
        return out;
    }
    public static String permKey(String key){
        String K ="";
        char [] chars = key.toCharArray();
        for(int i= 0 ;i<56;i++)
        {
            K+= key.charAt( permTable[i]-1);
        }

        return K;
    }

}
