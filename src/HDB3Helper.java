
public class HDB3Helper {
	
	public String encode(String str){ 
		char lastPolarity = '-'; // the last polarity that was sent, either "-" or "+"
		String zeroStr = "";
		String outputStr = ""; // the signal that will be transmitted
		
		//iterate through the string and encode to HDB3
		for(int i=0;i<str.length();i++){
			char c = str.charAt(i);
			
			//handle a '1', alternate between '+' and '-'
			if(c == '1'){
				char newPolarity = alternatePolarity(lastPolarity);
				outputStr += newPolarity;
				lastPolarity = newPolarity;
			}
			
			
		}
		return null;
	}
	
	public char alternatePolarity(char lastPolarity){
		if(lastPolarity == '-'){
			return '+';
		}else{
			return '-';
		}
	}
	
	public String decode(String str){
		return null;
	}

}
