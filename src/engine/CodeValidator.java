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
    public int[] calculateResponse(IChromosome sequenceToCheck){
        redResponse = 0;
        whiteResponse = 0;
        System.out.println("CodeValidator - calculateResponse");
        for(int i = 0; i < secretCode.getSequence().length; i++){
            if(secretCode.getSequence()[i] == sequenceToCheck.getSequence()[i]){
                redResponse++;
            }
        }

        //todo fix function for white response -> count occurence of each of the different available colors!
        int[] sortedSecretCode = secretCode.getSequenceSorted();
        int[] sortedSequenceToCheck = sequenceToCheck.getSequenceSorted();
        for(int i = 0; i < sortedSecretCode.length; i++){
            if(sortedSecretCode[i] == sortedSequenceToCheck[i]){
                whiteResponse++;
            }
        }
        whiteResponse -= redResponse;

        int[] response = {redResponse, whiteResponse};
        return response;
    }

    //getter + setter
}
