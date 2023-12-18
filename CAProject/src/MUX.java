public class MUX {
private int c;	
private int in1;
private int in2;
private  int out;
public MUX() 
{
	this.c=0;
	this.in1=0;
	this.in2=0;
	this.out=0;
	
}
public MUX(int c,int in1,int in2) 
{
	this.c=c;
	this.in1=in1;
	this.in2=in2;
    setOut(c);	
}
public int getC() {
	return c;
}
public void setC(int c) {
	this.c = c;
}
public int getIn1() {
	return in1;
}
public void setIn1(int in1) {
	this.in1 = in1;
}
public int getIn2() {
	return in2;
}
public void setIn2(int in2) {
	this.in2 = in2;
}
public int getOut() {
	return out;
}
public void setOut(int c) {
	if(c==0) 
	{
		this.out=this.in1;
		
	}
	else 
	{
		this.out=this.in2;
		
	}
}

}
