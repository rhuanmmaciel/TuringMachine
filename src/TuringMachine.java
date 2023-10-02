import java.util.*;

public class TuringMachine {

    private final List<String> states;
    private final List<Character> inputAlphabet;

    public final List<Character> inputTape;
    public final List<Integer> historyTape;
    private List<Character> outputTape;

    public int headIndex = 0;

    private int inputHead;
    private int historyHead;

    public String currentState;
    private String startState;
    private String finalState;
    public final List<Transition> transitions;
    private final char blank;

    public TuringMachine() {

        states = new ArrayList<>();
        inputAlphabet = new ArrayList<>();

        inputTape = new ArrayList<>();
        historyTape = new ArrayList<>();
        outputTape = new ArrayList<>();

        inputHead = 0;
        historyHead = 0;

        currentState = null;
        startState = null;
        finalState = null;
        transitions = new ArrayList<>();
        blank = 'B';

    }

    public static class Transition {

        private final String from_state;
        private final char input_symbol;
        private final String to_state;
        private final char output_symbol;
        private final char shift_direction;

        private Map<String, Object> rw_quadruple;
        private Map<String, Object> shift_quadruple;

        public Transition(String from_state, char input_symbol, String to_state, char output_symbol, char shift_direction) {

            this.from_state = from_state;
            this.input_symbol = input_symbol;
            this.to_state = to_state;
            this.output_symbol = output_symbol;
            this.shift_direction = shift_direction;

            setRwQuadruple();
            setShiftQuadruple();

        }

        private void setRwQuadruple() {

            rw_quadruple = new HashMap<>();
            rw_quadruple.put("from_state", from_state);
            rw_quadruple.put("input_symbol", input_symbol);
            rw_quadruple.put("output_symbol", output_symbol);
            rw_quadruple.put("temporary_state", from_state + "_");

        }

        private void setShiftQuadruple() {

            shift_quadruple = new HashMap<>();
            shift_quadruple.put("temporary_state", from_state + "_");
            shift_quadruple.put("blank_space", '/');
            shift_quadruple.put("shift_direction", shift_direction);
            shift_quadruple.put("to_state", to_state);

        }
    }

    public Transition getTransition(String state, char symbol) {

        for (Transition transition : transitions)
            if (transition.from_state.equals(state) && transition.input_symbol == symbol)
                return transition;

        System.exit(1);
        return null;

    }

    public String getTransitions(){

        StringBuilder sb = new StringBuilder();

        for(Transition t : transitions) {
            int i = 0;
            for (Map.Entry<String, Object> a : t.rw_quadruple.entrySet()) {
                sb.append(a.getValue()).append(" ");
                i++;
                if(i % 4 == 0) sb.append("\n");

            }
        }


        return sb.toString();

    }

    public void addState(String name) {

        name = name.trim();

        if (currentState == null) {

            startState = name;
            currentState = name;

        }

        states.add(name);
        finalState = name;

    }

    public void addTransition(String from_state, char input_symbol, String to_state, char output_symbol, char shift_direction) {

        if (states.contains(from_state) && states.contains(to_state)) {

            Transition transition = new Transition(from_state, input_symbol, to_state, output_symbol, shift_direction);
            transitions.add(transition);
            return;

        }

        System.exit(1);

    }

    public void setInputAlphabet(String input_alphabet) {

        for (char symbol : input_alphabet.toCharArray())
            this.inputAlphabet.add(symbol);

        this.inputAlphabet.add(blank);

    }

    public void setInputTape(String tape) {

        for (char symbol : tape.toCharArray())
            if (!inputAlphabet.contains(symbol))
                System.exit(1);

        inputTape.addAll(tape.chars().mapToObj(c -> (char) c).toList());
        inputTape.add(blank);

    }

    public void execute(Transition transition, boolean backward) {

        if (backward) {
            if (transition.shift_quadruple.get("shift_direction").equals('L')) {
                inputHead += 1;
            } else if (transition.shift_quadruple.get("shift_direction").equals('R')) {
                inputHead -= 1;
            }

            inputTape.set(inputHead, (char) transition.rw_quadruple.get("input_symbol"));
            currentState = (String) transition.rw_quadruple.get("from_state");

            return;

        }

        inputTape.set(inputHead, (char) transition.rw_quadruple.get("output_symbol"));
        currentState = (String) transition.shift_quadruple.get("to_state");

        if (transition.shift_quadruple.get("shift_direction").equals('R'))
            inputHead += 1;

        else if (transition.shift_quadruple.get("shift_direction").equals('L'))
            inputHead -= 1;

    }

    public boolean retracedComputation() {

        if (currentState.equals(startState) && inputHead == 0)
            return false;

        int transition_index = historyTape.get(historyHead);

        Transition transition = transitions.get(transition_index);

        execute(transition, true);

        historyTape.remove(historyHead);
        historyHead -= 1;

        return true;

    }

    public void next(){

        if(!isForwardDone){

            isForwardDone = !forwardComputation();

            if(isForwardDone) copy_output();

            headIndex = inputHead;

            return;

        }

        if(!isRetracedDone) {

            isRetracedDone = !retracedComputation();
            headIndex = inputHead;

        }

    }

    public boolean hasNext(){

        return !isForwardDone || !isRetracedDone;

    }

    private boolean isForwardDone = false;
    private boolean isRetracedDone = false;

    public boolean forwardComputation() {

        if (currentState.equals(finalState) && inputHead >= inputTape.size()) {

            historyHead = historyTape.size() - 1;
            return false;

        }

        char current_symbol = inputTape.get(inputHead);

        Transition transition = getTransition(currentState, current_symbol);

        int transition_index = transitions.indexOf(transition);

        historyTape.add(transition_index);

        execute(transition, false);

        return true;

    }

    public String getInputTape(){

        StringBuilder sb = new StringBuilder();

        inputTape.forEach(x -> sb.append(x).append(" "));

        return sb.toString();

    }

    public void copy_output() {

        outputTape = new ArrayList<>(inputTape);

    }

}
