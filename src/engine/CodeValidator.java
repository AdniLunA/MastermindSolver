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

        int[] sortedSecretCode = secretCode.getSequenceSorted();
        int[] sortedSequenceToCheck = sequenceToCheck.getSequenceSorted();
        int[] colorCounter = new int[secretCode.getNumberOfColors()];
        for(int i = 0; i < sortedSecretCode.length; i++){
            colorCounter[sortedSecretCode[i]]++;
            colorCounter[sortedSequenceToCheck[i]]++;
        }
        for(int colorCount : colorCounter){
            if (colorCount == 2){
                whiteResponse++;
            }
        }
        whiteResponse -= redResponse;

        int[] response = {redResponse, whiteResponse};
        return response;
    }

    //getter + setter
}
