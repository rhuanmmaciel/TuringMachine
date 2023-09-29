import java.util.ArrayList;
import java.util.List;

public class TapeAlphabet {

    public final List<TapeChar> alphabet;

    public TapeAlphabet(Character[] characters){

        List<TapeChar> aux = new ArrayList<>();

        for(Character c :characters)
            aux.add(new TapeChar(c));

        alphabet = List.copyOf(aux);

    }

    public TapeChar get(Character c){

        return alphabet.stream().filter(x -> x.character.equals(c)).findFirst().orElseThrow();

    }

}
