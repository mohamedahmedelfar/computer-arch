import java.util.HashMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;

public class Processor {
	private Memory memory;
	private ControlUnit cu;
	private ALU alu;
	private Register PC;
	private RegisterFile rf;
	private int cycles;
	HashMap<String, Integer> IF_ID;
	HashMap<String, Integer> Decode;
	HashMap<String, Integer> WRITEBACK;
	HashMap<String, Integer> MEMORY;
	boolean branchflag;
	boolean fetchFlag;
	boolean memFlag;
	boolean writebackFlag;
	boolean lastFetch;
	int decodeCounter;
	int excuteCounter;
	int instructionNum;
	int fetchCounter;
	private int sizeInst;
	String print;

	public Processor() {
		this.instructionNum = 0;
		this.memory = new Memory(2048);
		this.cu = new ControlUnit();
		this.alu = new ALU();
		this.PC = new Register("PC");
		this.rf = new RegisterFile();
		this.cycles = 1;
		IF_ID = new HashMap<>();
		Decode = new HashMap<>();
		WRITEBACK = new HashMap<>();
		MEMORY = new HashMap<>();
		this.branchflag = false;
		this.fetchFlag = true;
		this.memFlag = false;
		this.writebackFlag = false;
		this.lastFetch = false;
		this.excuteCounter = 0;
		this.decodeCounter = 0;
		this.fetchCounter = 6;
		this.sizeInst = 0;
		this.print = "";
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}

	public ControlUnit getCu() {
		return cu;
	}

	public void setCu(ControlUnit cu) {
		this.cu = cu;
	}

	public ALU getAlu() {
		return alu;
	}

	public void setAlu(ALU alu) {
		this.alu = alu;
	}

	public Register getPC() {
		return PC;
	}

	public void setPC(Register pC) {
		PC = pC;
	}

	public RegisterFile getRf() {
		return rf;
	}

	public void setRf(RegisterFile rf) {
		this.rf = rf;
	}

	public int getCycles() {
		return cycles;
	}

	public void setCycles(int cycles) {
		this.cycles = cycles;
	}

	public void parser(String textFile) {
		String line;
		String type = "";
		int code = 0;
		int i = 0;
		int opcode = 0;
		Queue<Integer> q = new LinkedList<>();
		try {
			FileReader file = new FileReader(textFile);
			BufferedReader br = new BufferedReader(file);
			while ((line = br.readLine()) != null) {
				String[] instruction = line.split(" ");
				String operation = instruction[0];
				switch (operation) {
				case "ADD": {
					opcode = 0;
					type = "R";
					code |= 0 << 28;
					break;

				}
				case "SUB": {
					opcode = 1;
					type = "R";
					code |= 1 << 28;
					break;
				}
				case "MULI": {
					opcode = 2;
					type = "I";
					code |= 2 << 28;
					break;
				}
				case "ADDI": {
					opcode = 3;
					type = "I";
					code |= 3 << 28;
					break;
				}
				case "BNE": {
					opcode = 4;
					type = "I";
					code |= 4 << 28;
					break;
				}
				case "ANDI": {
					opcode = 5;
					type = "I";
					code |= 5 << 28;
					break;
				}
				case "ORI": {
					opcode = 6;
					type = "I";
					code |= 6 << 28;
					break;
				}
				case "J": {
					opcode = 7;
					type = "J";
					code |= 7 << 28;
					break;
				}
				case "SLL": {
					opcode = 8;
					type = "R";
					code |= 8 << 28;
					break;
				}
				case "SRL": {
					opcode = 9;
					type = "R";
					code |= 9 << 28;
					break;
				}
				case "LW": {
					opcode = 10;
					type = "I";
					code |= 10 << 28;
					break;
				}
				case "SW": {
					opcode = 11;
					type = "I";
					code |= 11 << 28;
					break;
				}
				default: {
					System.out.println("Invalid Instruction");
					return;
				}

				}
				if (type.equals("I")) {

					int R1 = Integer.parseInt(instruction[1].replace("R", " ").trim());
					int R2 = Integer.parseInt(instruction[2].replace("R", " ").trim());
					int R3 = Integer.parseInt(instruction[3].replace("R", " ").trim());
					code |= R1 << 23;
					code |= R2 << 18;
					R3 = R3 & 0b00000000000000000111111111111111111;
					code |= R3;

				} else if (type.equals("J")) {
					int R1 = Integer.parseInt(instruction[1].replace("R", " ").trim());
					R1 = R1 & 0b00001111111111111111111111111111;
					code |= R1;

				} else {
					int R1 = Integer.parseInt(instruction[1].replace("R", " ").trim());
					int R2 = Integer.parseInt(instruction[2].replace("R", " ").trim());
					int R3 = Integer.parseInt(instruction[3].replace("R", " ").trim());
					code |= R1 << 23;
					code |= R2 << 18;
					if ((opcode == 0) || (opcode == 1)) {
						code |= R3 << 13;

					} else {
						code |= 0 << 13;
						code |= R3;
					}

				}
				this.memory.getMemoryData()[i] = code;
				q.add(code);
				i++;
				code = 0;

			}
			this.setSizeInst(q.size());
			br.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void fetch() {
		if (this.getPC().getData() < this.getSizeInst()) {
			if (fetchFlag) {
				int currentInstruction = memory.getMemoryData()[this.getPC().getData()];
				print += "Instruction in Fetch: Instruction " + currentInstruction + "\n";
				System.out.println("Instruction in Fetch: Instruction " + this.PC.getData() + "\n");
				System.out.println("PC value: " + this.PC.getData());
				print += "PC value: " + this.PC.getData() + "\n";
				this.IF_ID.put("instruction", currentInstruction);
				this.IF_ID.put("pc", this.getPC().getData());
				this.getPC().setData(this.getPC().getData() + 1);
				if (this.getPC().getData() == this.getSizeInst()) {
					this.lastFetch = true;
				}
				this.fetchFlag = false;
				System.out.println("PC value after incrementation: " + this.getPC().getData());
				this.decodeCounter = 2;
			}
		}
	}

	public void Decode() {
		if (IF_ID.size() != 0) {

			if (decodeCounter == 2) {
				if (lastFetch && fetchCounter == 6)
					fetchCounter--;
				System.out.println("decodeConter1" + decodeCounter);
				// DECODE COUNTER ZBTEEH
				int instuction = IF_ID.get("instruction");
				int opcode = instuction & 0b11110000000000000000000000000000;
				opcode = opcode >> 28;
				opcode = opcode & 0b00000000000000000000000000001111;
				System.out.println("Decoding instrucyion " + IF_ID.get("pc"));
				System.out.println("with opcode " + opcode);
				if ((opcode == 0) || (opcode == 1)) {
					System.out.println("Decoding R instruction");
					int R1 = instuction & 0b00001111100000000000000000000000;
					R1 = R1 >> 23;
					System.out.println("Destination Register is " + R1);
					int R2 = instuction & 0b00000000011111000000000000000000;
					R2 = R2 >> 18;
					System.out.println("SRC Register1 is " + R2);
					int R3 = instuction & 0b00000000000000111110000000000000;
					R3 = R3 >> 13;
					System.out.println("SRC Register2 is " + R3);
					int shamt = instuction & 0b00000000000000000001111111111111;
					System.out.println("Shift Amont is " + shamt);
					Decode.put("type", 0);
					Decode.put("opcode", opcode);
					Decode.put("R1", R1);
					Decode.put("R2", R2);
					Decode.put("R3", R3);
					Decode.put("shamt", shamt);
				} else if ((opcode == 8) || (opcode == 9)) {
					System.out.println("Decoding R instruction");
					int R1 = instuction & 0b00001111100000000000000000000000;
					R1 = R1 >> 23;
					System.out.println("Destination Register is " + R1);
					int R2 = instuction & 0b00000000011111000000000000000000;
					R2 = R2 >> 18;
					System.out.println("SRC Register is " + R2);
					int shamt = instuction & 0b00000000000000000001111111111111;
					System.out.println("Shift Amount is " + shamt);
					Decode.put("type", 1);
					Decode.put("opcode", opcode);
					Decode.put("R1", R1);
					Decode.put("R2", R2);
					Decode.put("shamt", shamt);
				} else if (opcode == 7) {
					System.out.println("Decoding A jump Instruction");
					int address = instuction & 0b00001111111111111111111111111111;
					System.out.println("Address= " + address);
					Decode.put("type", 2);
					Decode.put("opcode", opcode);
					Decode.put("address", address);
				}

				else {
					System.out.println("Decoding an I instruction");
					int R1 = instuction & 0b00001111100000000000000000000000;
					R1 = R1 >> 23;
					System.out.println("Destination Register = " + R1);
					int R2 = instuction & 0b00000000011111000000000000000000;
					R2 = R2 >> 18;
					System.out.println("Source Register = " + R2);
					int immediate = instuction & 0b00000000000000111111111111111111;
					System.out.println("Immediate Value = " + immediate);
					Decode.put("type", 3);
					Decode.put("opcode", opcode);
					Decode.put("R1", R1);
					Decode.put("R2", R2);
					Decode.put("immediate", immediate);
				}
			}
			if (decodeCounter == 1) {
				System.out.println("Decoding 2nd cycle");
				System.out.println("decodeConter1" + decodeCounter);
				fetchFlag = true;

				if (lastFetch && fetchCounter == 5) {
					fetchCounter--;
				}
				excuteCounter = 2;
			}

			decodeCounter--;

		}
	}

	public void excute() {
		if (Decode.size() == 0) {
			return;
		} // making sure to nut access null
		if (excuteCounter == 2) {
			int instructionType = Decode.get("type");
			int opcode = Decode.get("opcode");
			System.out.println("Executing Instruction " + IF_ID.get("pc"));
			if (lastFetch && fetchCounter == 4) {
				fetchCounter--;
			}

			if (instructionType == 2) // jump inst
			{
				int pc4bits = this.PC.getData() & 0b1111;
				int addresss = Decode.get("address");
				String getAdd = Integer.toBinaryString(pc4bits) + "" + Integer.toBinaryString(addresss);
				int getAdd1 = Integer.parseInt(getAdd);
				int output = alu.setOutput(IF_ID.get("pc"), addresss, opcode);// jumping to the address itself
				WRITEBACK.put("opcode", opcode);
				WRITEBACK.put("regNum", 111);
				WRITEBACK.put("regValue", output);
				System.out.println("Addres of the pc after the branch should be equal to " + output);
				IF_ID.clear();
				Decode.clear();
				if (lastFetch) {
					fetchCounter = 6;
					lastFetch = false;
					
				}
				this.fetchFlag=true;

			} else if (instructionType == 0) // executing add or sub
			{
				int R1 = Decode.get("R1");
				int R2 = Decode.get("R2");
				int R3 = Decode.get("R3");
				int output = alu.setOutput(rf.getRegisters()[R2].getData(), rf.getRegisters()[R3].getData(), opcode);
				System.out.println(
						"output that should be written in " + rf.getRegisters()[R1].getName() + " is " + output);
				WRITEBACK.put("opcode", opcode);
				WRITEBACK.put("regNum", R1);
				WRITEBACK.put("regValue", output);
			} else if (instructionType == 1) {// executing sll or srl

				int R1 = Decode.get("R1");
				int R2 = Decode.get("R2");
				int shamt = Decode.get("shamt");
				int output = alu.setOutput(rf.getRegisters()[R2].getData(), shamt, opcode);
				System.out.println(
						"output that should be written in " + rf.getRegisters()[R1].getName() + " is " + output);
				WRITEBACK.put("opcode", opcode);
				WRITEBACK.put("regNum", R1);
				WRITEBACK.put("regValue", output);

			}

			else// executing an I inst
			{
				int R1 = Decode.get("R1");
				int R2 = Decode.get("R2");
				int immediate = Decode.get("immediate");
				System.out.println("imm" + immediate);
				String imediateBinary = Integer.toBinaryString(immediate);
				char firstChar = imediateBinary.charAt(0);
				int finalImediate = immediate;
				if ((firstChar + "").equals("1") && imediateBinary.length() == 18) {
					String adding = "11111111111111";
					String finall = adding + imediateBinary;
					finalImediate = (int) Long.parseLong(finall, 2);
				}
				if ((opcode == 2) || (opcode == 3) || (opcode == 5) || (opcode == 6)) {
					int output = alu.setOutput(rf.getRegisters()[R2].getData(), finalImediate, opcode);
					WRITEBACK.put("opcode", opcode);
					WRITEBACK.put("regNum", R1);
					WRITEBACK.put("regValue", output);
				} 
				else if (opcode == 4) // BNE
				{
					int output = alu.setOutput(rf.getRegisters()[R1].getData(), rf.getRegisters()[R2].getData(),
							opcode);
					if (output != 0) {
						int PC = IF_ID.get("pc");
						int pc1 = alu.setOutput(PC, 0, 0);
						int finalPcValue = alu.setOutput(pc1, immediate, 0);
						WRITEBACK.put("opcode", opcode);
						WRITEBACK.put("regNum", 111);
						WRITEBACK.put("regValue", finalPcValue);
						System.out.println(WRITEBACK.get("regNum") + " regNum");
						System.out.println(WRITEBACK.get("regValue") + " reg Val");
						System.out.println(finalPcValue);
						this.branchflag = false;
						if (lastFetch) {
							fetchCounter = 6;
							lastFetch = false;
						}
						System.out.println("BNE occured and value of the pc should be equal to " + finalPcValue);
						IF_ID.clear();
						Decode.clear();
						this.fetchFlag=true;
					}
				} else if (opcode == 10)// load memory
				{
					int LW = alu.setOutput(rf.getRegisters()[R2].getData(), immediate, 0);
					int value = LW + 1024;
					MEMORY.put("opcode", opcode);
					MEMORY.put("regNum", R1);
					MEMORY.put("regValue", value);
					System.out.println("Loading Word from the memory");
				} else {
					int memPosition = alu.setOutput(rf.getRegisters()[R2].getData(), immediate, 0);
					MEMORY.put("opcode", opcode);
					int value = memPosition + 1024;
					MEMORY.put("regValue", value);
					MEMORY.put("regNum", R1);
					System.out.println("storing Word into the memory");

				}
			}

		}

		if (excuteCounter == 1) {
			System.out.println("excuting for the second time ");
			if (lastFetch && fetchCounter == 3) {
				fetchCounter--;
			}
		}

		excuteCounter--;

	}

	public void Memory()// load and store only
	{

		if (lastFetch && fetchCounter == 2) {
			fetchCounter--;
		}

		if ((MEMORY.size() == 0) && !(fetchFlag) && (WRITEBACK.size() != 0)) {

			this.writebackFlag = true;
			System.out.println("memory");
			return;
		}
		if (MEMORY.size() == 0 && !fetchFlag)
			return;
		if (!fetchFlag) {
			this.writebackFlag = true;
			System.out.println("memory");
			writebackFlag = true;
			int opcode = MEMORY.get("opcode");
			if (opcode == 10) {
				int regValue = MEMORY.get("regValue");
				int valueFromMem = memory.getMemoryData()[regValue];
				int regNum = MEMORY.get("regNum");
				WRITEBACK.put("regNum", regNum);
				WRITEBACK.put("regValue", valueFromMem);
			} else {

				int word = MEMORY.get("regValue");
				int reg = MEMORY.get("regNum");
				memory.getMemoryData()[word] = rf.getRegisters()[reg].getData();

			}

		}

	}

	public void writeBack() {
		
		if (WRITEBACK.size() == 0) {
			return;

		}
		if (writebackFlag) {
			int regNum = WRITEBACK.get("regNum");
			if (regNum == 111) {
				this.PC.setData(WRITEBACK.get("regValue"));

			} else {
				rf.getRegisters()[regNum].setData(WRITEBACK.get("regValue"));
				System.out.println("saved correctly" + regNum + "regNum");

			}
		}

		if (lastFetch && fetchCounter == 1) {
			fetchCounter--;
		}

	}

	public int getSizeInst() {
		return sizeInst;
	}

	public void setSizeInst(int sizeInst) {
		this.sizeInst = sizeInst;
	}

}