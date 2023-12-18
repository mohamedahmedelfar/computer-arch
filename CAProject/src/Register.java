public class Register {
 private int data;
 private String  name;
  public Register(String name) 
  {
	  this.data=0;
	  this.name=name;
	
	  
  }
public int getData() {
	return data;
}

public String getName() {
	return name;
}
public void setData(int data) {
	if (this.name.equals("R0")) {
		System.out.println("Cannot write to R0");
	} else {
		this.data = data;
	}
}
public void setName(String name) {
	this.name=name;
}

}
