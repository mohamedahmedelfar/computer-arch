public class Memory {
	private int memWriteS;
	private int memReadS;
	private int address;
	private int readData;
	private int writeData;
	private int[]memoryData;
	public Memory(int size)
	{
		this.setMemoryData(new int[size]);
		this.setAddress(0);
		this.setWriteData(0);
		this.setReadData(0);
		this.setMemWriteS(0);
		this.setMemReadS(0);
	}
	public int getMemWriteS() {
		return memWriteS;
	}
	public void setMemWriteS(int memWriteS) {
		this.memWriteS = memWriteS;
	}
	public int getMemReadS() {
		return memReadS;
	}
	public void setMemReadS(int memReadS) {
		this.memReadS = memReadS;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public int[] getMemoryData() {
		return memoryData;
	}
	public void setMemoryData(int[] memoryData) {
		this.memoryData = memoryData;
	}
	public int getReadData() {
		return readData;
	}
	public void setReadData(int readData) {
		this.readData = readData;
	}
	public int getWriteData() {
		return writeData;
	}
	public void setWriteData(int writeData) {
		this.writeData = writeData;
	}

}
