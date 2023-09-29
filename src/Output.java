public class Output{

    public final State state;
    public final TapeChar tapeChar;
    public final Direction direction;

    public Output(State state, TapeChar tapeChar, Direction direction){

        this.state = state;
        this.tapeChar = tapeChar;
        this.direction = direction;

    }

    public Output(String string){

        this(
                new State(string.substring(1, string.indexOf(","))),
                new TapeChar(string.charAt(string.lastIndexOf(",")-1)),
                Direction.getDirection(string.charAt(string.lastIndexOf(",")+1))
        );

    }

    public String toString(){

        return "("+state+", " +tapeChar+", "+direction+")";

    }

}
