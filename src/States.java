import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class States {

    public final int AMOUNT;

    public final List<State> states = new ArrayList<>();

    public States(State ...states){

        this.AMOUNT = states.length;
        this.states.addAll(Arrays.asList(states));

    }

    public States(String ...states){

        this(Arrays.stream(states).map(State::new).toArray(State[]::new));

    }

}
