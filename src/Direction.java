public enum Direction {

    LEFT, RIGHT;

    public static Direction getDirection(String string){

        if(string.equals("R")) return RIGHT;

        return LEFT;

    }

    public static Direction getDirection(Character string){

        if(string.equals('R')) return RIGHT;

        return LEFT;

    }

}
