package Germany.RWTH.JRCCombine.internal.Omnipath;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAnnotationTable {
	
 
    // variable of type String
    public static HashMap multiMap;
 
    // private constructor restricted to this class itself
    MyAnnotationTable()
    {
    	multiMap = new HashMap<String,ArrayList<String>>();
    }
 
    // static method to create instance of Singleton class
    public static void setInstance(String key, String[] values)
    {
       
        multiMap.put(key, values);
 
    }
    
    public static HashMap getInstance()
    {     
        return multiMap;
    }
}
    
    