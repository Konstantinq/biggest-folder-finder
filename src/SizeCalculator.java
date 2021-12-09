import java.io.File;
import java.util.HashMap;

public class SizeCalculator {
    private static char[] sizeMultipliers = {'B', 'K', 'M','G', 'T'};
    public static  long getFolderSize(File folder){
        if(folder.isFile()){
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for(File file : files){
            sum += getFolderSize(file);
        }
        return  sum;
    }
    public static String getHumanReadableSize(long size){
        for (int i = 0; i < sizeMultipliers.length; i++) {
            double value = ((double) size) / Math.pow(1024, i);
            if(value < 1024){
                return Math.round(value * 100)/100 + "" + sizeMultipliers[i] + (i > 0 ? "b":"");
            }
        }
        return "Very big!";
    }

    public static long getSizeFromHumanReadable(String size){

        HashMap<Character,Integer> char2multiplier = getMultipliers();
        char sizeFactor = size
                .replaceAll("[0-9\\s+]+","")
                .charAt(0);
        // System.out.println(sizeFactor);
        int multiplier = char2multiplier.get(sizeFactor);
        long length =  multiplier * Long.valueOf(
                size.replaceAll("[^0-9]",""
                ));
        return length;
    }
    private static HashMap<Character, Integer> getMultipliers(){

        HashMap<Character,Integer> char2multiplier = new HashMap<>();
        for (int i = 0; i < sizeMultipliers.length; i++) {
            char2multiplier.put(
                    sizeMultipliers[i], (int) Math.pow(1024,i)
            );
        }
        return char2multiplier;

    }
}
