package engine;

import evolution.IChromosome;

public class CodeValidator {
    //constructor
    public CodeValidator(IChromosome secretCode) {
        this.secretCode = secretCode;
    }

    //attributes
    private IChromosome secretCode;
    private int redResponse;
    private int whiteResponse;

    //functions
    private int[] calculateResponse(IChromosome sequenceToCheck){
        int[] response = new int[2];
        System.out.println("CodeValidator - calculateResponse");
        return response;
    }

    //getter + setter
}
