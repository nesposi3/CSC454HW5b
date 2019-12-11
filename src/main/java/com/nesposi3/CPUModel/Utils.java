package com.nesposi3.CPUModel;

public class Utils {
    public static final class BitTypes{
        public static final String AND = "0000";
        public static final String MOD = "0001";
        public static final String MUL = "0010";
        public static final String OR = "0011";
        public static final String XOR = "0111";
        public static final String ADD = "1000";
    }
    public static String getBitRep(String opType){
        switch (opType){
            case "AND":return BitTypes.AND;
            case "MOD": return BitTypes.MOD;
            case "MUL": return BitTypes.MUL;
            case "OR" : return BitTypes.OR;
            case "XOR": return BitTypes.XOR;
            case "ADD":return BitTypes.ADD;
            default: return "";
        }
    }
    public static String integerToBinary(int x){
        return String.format("%32s", Integer.toBinaryString(x)).replace(' ', '0');
    }
    public static boolean correctModel(String modelBits, String binary){
        String bits = binary.substring(0,4);
        return (bits.equals(modelBits));
    }
    public static int[] intsFromBits(String bits){
        int[] arr = new int[2];
        String inta = bits.substring(4,36);
        String intb = bits.substring(36,68);
        arr[0] = Integer.parseInt(inta,2);
        arr[1] = Integer.parseInt(intb,2);
        return arr;
    }
}
