import javax.swing.*;
import javax.swing.plaf.UIResource;
import javax.swing.text.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main extends JDialog{

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int stateAmount = sc.nextInt();
        int inputAlphabetAmount = sc.nextInt();
        int tapeAlphabetAmount = sc.nextInt();
        int functionAmount = sc.nextInt();

        String[] states = new String[stateAmount];
        for(int i = 0; i < stateAmount; i++)
            states[i] = sc.next();

        Character[] inputAlphabet = new Character[inputAlphabetAmount];
        for(int i = 0; i < inputAlphabetAmount; i++)
            inputAlphabet[i] = sc.next().charAt(0);

        Character[] tapeAlphabet = new Character[tapeAlphabetAmount];
        for(int i = 0; i < tapeAlphabetAmount; i++)
            tapeAlphabet[i] = sc.next().charAt(0);

        sc.nextLine();

        Map<Input, Output> functions = new HashMap<>();
        for (int i = 0; i < functionAmount; i++) {

            String line = sc.nextLine();
            functions.put(new Input(line.substring(0, line.indexOf("="))), new Output(line.substring(line.indexOf("=")+1)));

        }

        TuringMachine TM = new TuringMachine(new States(states), inputAlphabet, new TapeAlphabet(tapeAlphabet), functions);

        TM.setTape(sc.nextLine());

        new Main().initGUI(TM);

    }

    Box vertical = Box.createVerticalBox();
    CustomBackgroundLabel lblCurrentTape;
    JLabel lblCurrentState = new JLabel();

    JButton btnNext = new JButton(">");
    JButton btnEnd = new JButton(">>");

    Font font = new Font("a", Font.PLAIN, 40);

    TuringMachine TM = null;

    private void initGUI(TuringMachine TM){

        lblCurrentTape = new CustomBackgroundLabel(TM.getCurrentTape(), 1);
        lblCurrentTape.setFont(font);
        lblCurrentState.setFont(font);

        this.TM = TM;

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        JPanel centerPane = new JPanel(new FlowLayout());

        contentPane.add(centerPane, BorderLayout.CENTER);

        vertical.add(lblCurrentState);
        vertical.add(lblCurrentTape);

        centerPane.add(vertical);

        JPanel buttons = new JPanel(new FlowLayout());

        buttons.add(btnNext);
        buttons.add(btnEnd);

        contentPane.add(buttons, BorderLayout.SOUTH);

        btnNext.addActionListener(a -> updateTape(false));
        btnEnd.addActionListener(a -> updateTape(true));

        JPanel initialInfo = new JPanel();
        initialInfo.setLayout(new BoxLayout(initialInfo, BoxLayout.Y_AXIS));
        contentPane.add(initialInfo, BorderLayout.WEST);
        JTextPane textPane = new JTextPane();
        initialInfo.add(textPane);
        textPane.setEditable(false);

        textPane.setText("Estados: " + TM.getStates() + "\n\n" +
                "Alfabeto de entrada: " + TM.getInputAlphabet() + "\n\n" +
                "Alfabeto da fita: " + TM.getTapeAlphabet() + "\n\n" +
                "Funções de transição: \n");

        for(Map.Entry<Input, Output> f : TM.getFunctions().entrySet())
            textPane.setText(textPane.getText() + f.getKey() + " = " + f.getValue() + "\n");

        lblCurrentState.setText("Estado atual: "+TM.getInitialState());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void updateTape(boolean terminate){

        boolean hasNext;

        do {

            hasNext = TM.next();

            vertical.remove(lblCurrentTape);

            lblCurrentTape = new CustomBackgroundLabel(TM.getCurrentTape(), TM.getHeadPosition() * 2 + 1);

            lblCurrentTape.setFont(font);

            vertical.add(lblCurrentTape);

            lblCurrentState.setText("Estado atual: " + TM.getCurrentState());

            btnNext.setEnabled(hasNext);
            btnEnd.setEnabled(hasNext);

            pack();
            revalidate();

        }while (terminate && hasNext);
    }

}