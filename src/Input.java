import java.util.Objects;

public class Input{

    public final State state;
    public final TapeChar tapeChar;

    public Input(State state, TapeChar tapeChar){

        this.state = state;
        this.tapeChar = tapeChar;

    }

    public Input(String string){

        this(
                new State(string.substring(1, string.indexOf(","))),
                new TapeChar(string.charAt(string.indexOf(",")+1))
        );

    }

    public String toString(){

        return "("+state+"," +tapeChar+")";

    }

    @Override
    public int hashCode() {
        return Objects.hash(state.STATE_ID, tapeChar.character);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Input input = (Input) o;
        return Objects.equals(state.STATE_ID, input.state.STATE_ID) &&
                Objects.equals(tapeChar.character, input.tapeChar.character);
    }

}
