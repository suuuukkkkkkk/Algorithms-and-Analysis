import java.util.*;


public class DataGenerator {

    protected ArrayList<String> array;
    protected ArrayList<String> init;
    protected int size;
    protected int initSize = 10000;
    protected String currentCase;
    protected static int length = 2;
    

    
    public DataGenerator(int size, String currentCase){
    	
    	this.size = size;
    	this.currentCase = currentCase;
    	array = new ArrayList<String>();
    	
    	init = new ArrayList<String>();
    	
    	for (int i = 0; i < initSize; i++){
    		init.add("A " + getRandomString());
    	}
 
    	
    	switch(currentCase){
    	
    	case "OnlyAdd": 
    		for(int i = 0; i < size; i++){
    			array.add("A " + getRandomString());
    		}
    		break;
    	
    	case "OnlyRemove":
    		for(int i = 0; i < size; i++){
    			array.add(getOnlyRemove() + getRandomString());
    		}
    		break;
    		
    	case "Balance":
    		for(int i = 0; i < size; i++){
    			array.add(getBanlance() + getRandomString());
    		}
    		break;
    		
    	case "MoreSearch":
    		for(int i = 0; i < size; i++){
    			array.add(getMoreSearch() + getRandomString());
    		}
    		break;    

    	default:
    		System.err.println("Unknown command");
    	
    	}
    	
    }
    
    
    public static String getRandomString (){  
        String str = "abcdefghijklmnopqrstuvwxyz";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
  
        for (int i = 0; i < length; ++i) {  
 
            sb.append(str.charAt(random.nextInt(26)));  
        }  
        return sb.toString();  
    }  
    
    
    
    public static String getOnlyRemove (){  
        String str = "OA";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer(); 
        sb.append("R");
  
  
 
            sb.append(str.charAt(random.nextInt(2)));  

        
        sb.append(" ");
        return sb.toString();  
    }  
    
    public static String getBanlance(){  
        String[] str = {"A","A","RO","RA","S","S"};
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
  
     
 
            sb.append(str[random.nextInt(6)]);  

        
        sb.append(" ");
        return sb.toString();  
    }  
    
    public static String getMoreSearch(){  
        String[] str = {"A","A","RO","RA","S","S","S","S","S","S"};
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
  
        
 
        sb.append(str[random.nextInt(10)]);  
        sb.append(" ");
        return sb.toString();  
    }  
    
    
    public void testPrint(){
    	
    	
    	
    	
    }
    
    public ArrayList<String> getInitArray(){
    	
    	return this.init;
    	
    }
    
    public ArrayList<String> getArray(){
    	
    	return this.array;
    	
    }
    
    
    
}