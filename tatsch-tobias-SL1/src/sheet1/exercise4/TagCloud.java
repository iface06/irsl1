
package sheet1.exercise4;

import java.util.HashMap;
import java.util.Map;

public class TagCloud {
    
    
    public static void main(String[] args){
        Map<String, Integer> cloud = new HashMap<String, Integer>();
        cloud.put("love", 87);
        cloud.put("sweet", 44);
        cloud.put("beauty", 41);
        cloud.put("eyes", 35);
        cloud.put("make", 33); //
        cloud.put("fair", 32);
        cloud.put("time", 32);
        cloud.put("true", 31);
        
        String cloudAsString = new String();
        for (String key : cloud.keySet()) {
            for (int i = 1; i < cloud.get(key); i++) {
                cloudAsString += key + " ";
            }
        }
        
        System.out.println(cloudAsString);
    }
}
