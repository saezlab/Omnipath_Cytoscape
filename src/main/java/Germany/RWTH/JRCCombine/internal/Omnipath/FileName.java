package Germany.RWTH.JRCCombine.internal.Omnipath;

public class FileName {
	

    // static variable single_instance of type Singleton
    private static FileName single_instance = null;
 
    // variable of type String
    public String s;
 
    // private constructor restricted to this class itself
    private FileName()
    {
        s = "";
    }
 
    // static method to create instance of Singleton class
    public static void setInstance(String filename)
    {
       
        single_instance.s = filename;
 
    }
    
    public static FileName getInstance()
    {
        if (single_instance == null)
            single_instance = new FileName();
         
        return single_instance;
    }
    
  

}
