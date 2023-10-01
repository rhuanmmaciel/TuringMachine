import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TuringMachineGUI extends JDialog implements ActionListener {

    private final JTextPane textPane = new JTextPane();
    private final JTextPane historyTextPane = new JTextPane();

    private final JScrollPane scrollPane = new JScrollPane(textPane);

    private final TuringMachine machine;

    private final JButton btnNext = new JButton(">");
    private final JButton btnSkip = new JButton(">>");

    private final JPanel centerPane = new JPanel();

    public TuringMachineGUI(TuringMachine machine, String initialString){

        this.machine = machine;
        textPane.setText(initialString);
        textPane.setEditable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        initGUI();

    }

    public void initGUI(){

        getContentPane().setLayout(new BorderLayout());

        getContentPane().setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width * 0.6),
                (int)(Toolkit.getDefaultToolkit().getScreenSize().height * 0.6)));

        getContentPane().add(scrollPane, BorderLayout.WEST);

        JPanel pane = new JPanel(new FlowLayout());

        pane.add(btnNext);
        pane.add(btnSkip);

        getContentPane().add(pane, BorderLayout.SOUTH);

        getContentPane().add(historyTextPane, BorderLayout.EAST);
        getContentPane().add(centerPane, BorderLayout.CENTER);

        btnNext.addActionListener(this);
        btnSkip.addActionListener(this);

        updateCurrentTape();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void updateCurrentTape(){

        centerPane.removeAll();

        CustomBackgroundLabel currentTape = new CustomBackgroundLabel(machine.getInputTape(), machine.headIndex * 2);

        currentTape.setFont(new Font("", Font.PLAIN, 30));

        historyTextPane.setText(historyTextPane.getText()+"\n"+machine.historyTape);

        centerPane.add(currentTape);

        currentTape.revalidate();
        centerPane.revalidate();

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(actionEvent.getSource() == btnNext)
            machine.next();

        else if(actionEvent.getSource() == btnSkip){

            while (machine.hasNext())
                machine.next();

        }

        updateCurrentTape();

        btnNext.setEnabled(machine.hasNext());
        btnSkip.setEnabled(machine.hasNext());

        revalidate();
        setLocationRelativeTo(null);

    }

}
