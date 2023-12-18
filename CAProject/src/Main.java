
public class Main {
	

	public static void run( Processor p) {
		int clockCycles=1;
 while(p.fetchCounter>0) {
		System.out.println("Cycle: " + clockCycles++);
			p.writeBack();
			p.Memory();
			p.excute();
			p.Decode();
			p.fetch();}
		}
	
	public static void main(String[]args)
	{
		
		Processor p=new Processor();
		p.parser("CAtesting.txt");
		p.getRf().getRegisters()[3].setData(3);
		p.getRf().getRegisters()[2].setData(2);
		p.getRf().getRegisters()[4].setData(4);
	System.out.println(p.getSizeInst()+"noor");
		run(p);
		System.out.println(p.getRf().getRegisters()[1].getData()+"R1");
		System.out.println(p.getRf().getRegisters()[3].getData()+"R3");
		System.out.println(p.getRf().getRegisters()[4].getData()+"R4");
		System.out.println(p.getRf().getRegisters()[6].getData()+"R6");
		System.out.println(p.getRf().getRegisters()[7].getData()+"R7");
		System.out.println(p.getRf().getRegisters()[5].getData()+"R5");
		System.out.println(p.getRf().getRegisters()[11].getData()+"R11");
		System.out.println(p.getRf().getRegisters()[9].getData()+"R9");
		System.out.println(p.getRf().getRegisters()[5].getData()+"R5");
		System.out.println(p.getRf().getRegisters()[8].getData()+"R8");
}}
