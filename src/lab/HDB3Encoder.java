package lab;

public class HDB3Encoder {
	public String lastPolarity = "-"; // the last polarity that was sent, either "-" or "+"
	public String lastViolationPolarity = null; // the last violation polarity that was sent, either "-" or "+"

	public String encode(String str){ 

		String zeroStr = null;
		String outputStr = ""; // the signal that will be transmitted

		//iterate through the string and encode to HDB3
		for(int i=0;i<str.length();i++){
			char c = str.charAt(i);

			//handle a '1'
			if(c == '1'){
				// if the '1' was preceded by '0's, add their encoded string
				if(zeroStr != null){ 
					outputStr += encodeStringOfZeroes(zeroStr);
					zeroStr = null;
				}

				// add alternating '+' and '-' for each '1'
				String newPolarity = alternatePolarity();
				outputStr += newPolarity;
				lastPolarity = newPolarity;
			}else{
				//handle a zero
				if(zeroStr == null){ zeroStr = "0";}
				else{zeroStr += '0';}
			}
		}
		
		//for strings of only 
		if(zeroStr != null){ 
			outputStr += encodeStringOfZeroes(zeroStr);
			zeroStr = null;
		}

		return outputStr;
	}

	public String alternatePolarity(){
		if(lastPolarity.equals("-")){lastPolarity = "+";}
		else{lastPolarity = "-";}

		return lastPolarity;
	}

	public String alternateViolationPolarity(){
		if(lastViolationPolarity.equals("-")){lastViolationPolarity = "+";}
		else{lastViolationPolarity = "-";}

		return lastViolationPolarity;
	}

	public String encodeStringOfZeroes( String str){
		String outputStr = "";
		int len = str.length();
		int numPatterns = (int)Math.floor(len/4); // number of 000V or B00V patterns

		//encode the blocs of four consecutive zeros
		for(int i=0;i<numPatterns;i++){
			if(lastViolationPolarity == null){// first violation in the String
				String violationPolarity = lastPolarity;
				outputStr +="000";
				outputStr += violationPolarity;
				//lastPolarity = violationPolarity;
				lastViolationPolarity = violationPolarity;
			}else{
				String violationPolarity = alternateViolationPolarity();// consecutive violations must not have the same polarity
				if(lastPolarity.equals(violationPolarity) && i==0){// the violation bit is the same as the last pulse
					outputStr +="000";
					outputStr += lastPolarity;
					//lastPolarity = violationPolarity;
					lastViolationPolarity = lastPolarity;
				}else{
					outputStr += violationPolarity; // the violation bit is not the same as the last pulse
					outputStr +="00";
					outputStr += violationPolarity;
					//lastPolarity = violationPolarity;
					lastViolationPolarity = violationPolarity;
				}
			}
		}

		//add the zeros which are left over
		for(int i=numPatterns*4;i<len;i++){
			outputStr+="0";
		}



		return outputStr;
	}

}
