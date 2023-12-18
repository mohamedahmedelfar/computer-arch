
public class ALU {
	private int input1;
	private int input2;
	private int output;
	private int control;
	private int zero;
	public ALU() 
	{
		this.input1=0;
		this.input2=0;
		this.control=0;
		this.output=0;
		this.zero=0;
		
	}
	public int getInput1() {
		return input1;
	}
	public void setInput1(int input1) {
		this.input1 = input1;
	}
	public int getInput2() {
		return input2;
	}
	public void setInput2(int input2) {
		this.input2 = input2;
	}
	public int getControl() {
		return control;
	}
	public void setControl(int control) {
		this.control = control;
	}
	public int getZero() {
		return zero;
	}
	public void setZero(int output) {
		if(output==0)
			this.zero=1;
		else 
			this.zero=0;
	}
	public int getOutput()
	{
		return output;
		
	}
	public int setOutput(int input1,int input2,int control ) 
	{
		int output=0;
		switch(control) 
		{
		case 0:
		{
			output=input1+input2;
			
			break;
		}
		case 1:
		{
			output=input1-input2;
			break;
		}
		case 2:
		{
			output=input1*input2;
			break;
		}
		case 3:
		{
			output=input1+input2;
			break;
		}
		case 4:
		{
			output=input1-input2;
			break;
		}
		case 5:
		{
			output=input1 & input2;
			break;
		}
		case 6:
		{
			output=input1 | input2;
			break;
		}
		case 7:
		{
			output=input1+input2;
			break;
		}
		case 8:
		{
			output = input1 << input2;
			break;
		}
		case 9:
		{
			output = input1 >>> input2;
			break;
		}
		case 10 :
			output=input1+input2;
			break;
		case 11:
			output=input1+input2;
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + control);
		}
		return output;
		
	}

}
