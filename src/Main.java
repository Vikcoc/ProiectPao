public class Main {
    public static void main(String[] args) {
        System.out.println("here");

    }
}

class Thing{
    private Integer number;

    public Thing(Integer number) {
        this.number = number;
    }
}

class Eh extends Thing{
    private Integer fur;

    public Eh(Integer number, Integer fur) {
        super(number);
        this.fur = fur;
    }
}


