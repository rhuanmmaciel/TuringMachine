import javax.swing.*;
import java.util.*;

public class TuringMachine{

    private final States states;
    public final List<Character> inputAlphabet;
    public final TapeAlphabet tapeAlphabet;
    public final Map<Input, Output> functions;

    private final List<Character> currentTape = new ArrayList<>();
    private int currentPosition = 0;

    private State currentState;
    private final State FINAL_STATE;

    public TuringMachine(States states, Character[] inputAlphabet, TapeAlphabet tapeAlphabet, Map<Input, Output> functions){

        this.states = states;
        this.inputAlphabet = Arrays.asList(inputAlphabet);
        this.tapeAlphabet = tapeAlphabet;
        this.functions = Map.copyOf(functions);

        this.FINAL_STATE = states.states.get(states.states.size()-1);
        currentState = states.states.get(0);

    }

    public void setTape(String input){

        currentTape.clear();

        for(Character c : input.toCharArray())
            currentTape.add(c);

        currentTape.add('B');

        currentPosition = 0;

    }

    public boolean next(){

        Input currentInput = new Input(currentState, tapeAlphabet.get(currentTape.get(currentPosition)));

        if(Objects.equals(currentInput.state.STATE_ID, FINAL_STATE.STATE_ID)) return false;

        Output output = functions.get(currentInput);

        currentState = output.state;

        currentTape.set(currentPosition, output.tapeChar.character);

        currentPosition += output.direction == Direction.RIGHT ? currentPosition < currentTape.size() - 1 ? 1 : 0 : (currentPosition > 0 ? -1 : 0);

        return true;

    }

    public int getHeadPosition(){
        return currentPosition;
    }

    public String getCurrentTape(){

        StringBuilder sb = new StringBuilder();

        for (Character c : currentTape)
            sb.append(" ").append(c);

        return sb.toString();
    }

    public String getCurrentState(){
        return currentState.toString();
    }

    public String getInitialState(){
        return states.states.get(0).toString();
    }

    public String getStates(){
        return states.states.toString();
    }

    public String getInputAlphabet(){
        return inputAlphabet.toString();
    }

    public String getTapeAlphabet(){
        return tapeAlphabet.alphabet.toString();
    }

    public Map<Input, Output> getFunctions(){
        return functions;
    }

    public String toString(){

        return "STATES: " + states.states + "\n" +
                "INPUT ALPhABET: " + inputAlphabet + "\n" +
                "TAPE ALPHABET: " + tapeAlphabet + "\n" +
                "FUNCTIONS: " + functions;

    }

}
