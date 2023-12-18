public class RegisterFile {
	private Register [] registers;
	private int readReg1;
	private int readReg2;
	private int readData1;
	private int raedData2;
	private int writeData;
	private int regWrite;
	public RegisterFile()
	{
		this.registers = new Register[32];
		for (int i = 0; i < 32; i++) {
			registers[i] = new Register("R" + i);
		}
	
		
	}
	public int getReadReg1() {
		return readReg1;
	}
	public void setReadReg1(int readReg1) {
		this.readReg1 = readReg1;
	}
	public int getReadReg2() {
		return readReg2;
	}
	public void setReadReg2(int readReg2) {
		this.readReg2 = readReg2;
	}
	public int getReadData1() {
		return readData1;
	}
	public void setReadData1(int readData1) {
		this.readData1 = readData1;
		this.setReadData1(this.getRegisters()[readReg1].getData());
	}
	public int getRaedData2() {
		return raedData2;
	}
	public void setRaedData2(int raedData2) {
		this.raedData2 = raedData2;
		this.setRaedData2(this.getRegisters()[readReg2].getData());
	}
	public int getWriteData() {
		return writeData;
	}
	public Register [] getRegisters(){
		return this.registers;
	}
	public void setRegisters(Register[] registers) {
		this.registers = registers;
	}
	public void setWriteData(int writeData) {
		if (this.regWrite == 1) {
			this.getRegisters()[this.getRegWrite()].setData(writeData);
		}
	}
	public int getRegWrite() {
		return regWrite;
	}
	public void setRegWrite(int regWrite) {
		this.regWrite = regWrite;
	}

}
