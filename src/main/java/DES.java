public class DES {

    private static String[] C = new String[17];
    private static String[] D = new String[17];
    private static String[] K = new String[16];
    private static String[] L = new String[17];
    private static String[] R = new String[17];
    private static String IPm;
    private static String [] E = new String [17];
    private static String[] Boxes= new String [8];
    private static String []CD = new String [16];

    private static String initPermutation(String message){

        String str = "";
        for(int i=0;i<Tables.IP.length;i++){
            str+=  message.charAt(Tables.IP[i]-1);
        }
        return str;
    }
    private static String leftShift(String str, int times){

        for(int i =0 ;i<times;i++)
            str= str.substring(1,str.length())+str.charAt(0);
        return str;
    }
    private static String f (String key, String E){
        String str = "";

        for(int i= 0;i<key.length();i++){
            str +=key.charAt(i)^E.charAt(i);
        }
        return str;
    }

    public static String enc(String plainText,String key){
        while(key.length()!=16)
        {
            key = '0'+key;
        }
        Tables.initMap();
        String plainBin=  Tables.hexToBin(plainText);
        String keyBin = Tables.hexToBin(key);
        String actualKey = Tables.permKey(keyBin);
        C[0]= actualKey.substring(0,actualKey.length()/2);
        D[0]= actualKey.substring(actualKey.length()/2,actualKey.length());
        for(int i =1 ;i<=16;i++){
            C[i]= leftShift(C[i-1],Tables.shifts[i-1]);
            D[i]= leftShift(D[i-1],Tables.shifts[i-1]);
        }
        for(int i =0 ;i<16;i++){
            CD[i] =C[i+1]+D[i+1];
        }
        for(int i =0 ;i<16;i++){
            String str = "";

            for(int j = 0;j<48;j++){
                str +=CD[i].charAt(Tables.pc2[j]-1);
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
            for(int j =0;j<Tables.eSelection.length;j++){
                E[i-1]+=R[i-1].charAt(Tables.eSelection[j]-1);
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
            //compute the R[i] here
            String tmp ="";
            for(int j=0 ;j<out.length();j++){
                tmp +=L[i-1].charAt(j)^out.charAt(j);
            }
            R[i]=tmp;
        }
        String finalBlock = Sboxes.permuteMinusUno(R[16]+L[16]);
        return Converter.binToHex(finalBlock);
    }
    public static String dec(String cryptoText,String key){
        while(key.length()!=16)
        {
            key = '0'+key;
        }
        Tables.initMap();
        String plainBin=  Tables.hexToBin(cryptoText);
        String keyBin = Tables.hexToBin(key);
        String actualKey = Tables.permKey(keyBin);
        C[0]= actualKey.substring(0,actualKey.length()/2);
        D[0]= actualKey.substring(actualKey.length()/2,actualKey.length());
        for(int i =1 ;i<=16;i++){
            C[i]= leftShift(C[i-1],Tables.shifts[i-1]);
            D[i]= leftShift(D[i-1],Tables.shifts[i-1]);
        }
        for(int i =0 ;i<16;i++){
            CD[i] =C[i+1]+D[i+1];
        }
        for(int i =0 ;i<16;i++){
            String str = "";

            for(int j = 0;j<48;j++){
                str +=CD[i].charAt(Tables.pc2[j]-1);
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
        for(int i= 1 ;i<=16;i++){
            L[i]=R[i-1];
            E[i-1]="";
            //F CALCULATION
            for(int j =0;j<Tables.eSelection.length;j++){
                E[i-1]+=R[i-1].charAt(Tables.eSelection[j]-1);
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
        String finalBlock = Sboxes.permuteMinusUno(R[16]+L[16]);
        return Converter.binToHex(finalBlock);
    }
    public static void main (String [] args){
        //plaint text and key must be string of lenght 16 and be in hexa,
        //0 = 0... A = 10 ,... F = 15 ...
        //also the cripto text
        String cript = DES.enc("0123456789ABCDEF",
                "E8F62DF12777C1A1");
        System.out.println(cript);
        String cript1= DES.enc(cript,"86546633B42B9C1");
        System.out.println(cript1);

    }

}
