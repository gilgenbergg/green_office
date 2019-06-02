package model;

public class Instruction {
    private Integer instructionID;
    private String instruction;

    public Instruction(Integer instructionID, String instruction) {
        this.instructionID = instructionID;
        this.instruction = instruction;
    }

    public Integer getInstructionID() {
        return instructionID;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstructionID(Integer instructionID) {
        this.instructionID = instructionID;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}