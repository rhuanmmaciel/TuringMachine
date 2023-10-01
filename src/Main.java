import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/home/rhuan/Intellij-workspace/turingMachine/src/entrada-quintupla.txt")));

        TuringMachine machine = new TuringMachine();

        StringBuilder sb = new StringBuilder();

        String[] sizes = reader.readLine().split(" ");
        int n_transitions = Integer.parseInt(sizes[3]);

        String[] states = reader.readLine().split(" ");
        for (String state : states)
            machine.addState(state);

        String[] input_alphabet = reader.readLine().split(" ");
        machine.setInputAlphabet(String.join("", input_alphabet));

        sb.append("Alfabeto de entrada: ").append(Arrays.toString(input_alphabet)).append("\n\n");

        String[] output_alphabet = reader.readLine().split(" ");

        sb.append("Alfabeto da fita: ").append(Arrays.toString(output_alphabet)).append("\n\n");

        sb.append("Funções de transição: ").append("\n");
        for (int i = 0; i < n_transitions; i++) {

            String[] transition = reader.readLine().split("=");
            String[] in_parts = transition[0].substring(1, transition[0].length() - 1).split(",");
            String[] out_parts = transition[1].substring(1, transition[1].length() - 1).split(",");
            machine.addTransition(
                    in_parts[0].trim(),
                    in_parts[1].charAt(0),
                    out_parts[0].trim(),
                    out_parts[1].charAt(0),
                    out_parts[2].charAt(0)
            );

            sb.append(Arrays.toString(transition)).append("\n");

        }

        String input_tape = reader.readLine();
        machine.setInputTape(input_tape);

        sb.append("\n").append("Fita de entrada: ").append(input_tape).append("\n\n");

        sb.append("Quadruplas: ").append("\n").append(machine.getTransitions());

        new TuringMachineGUI(machine, sb.toString());
    }
}
