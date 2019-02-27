
import java.util.*;
public class SecretSanta {
    public static String[] enterNames(Scanner scanInt, Scanner scanStr){
        System.out.print("How many participants are there?: ");
        int numParticipants = scanInt.nextInt();

        String[] names = new String[numParticipants + 1];

        System.out.println("Enter the participant's names: ");
        String name;
        for (int i = 1 ; i <= numParticipants; i++){
            System.out.print("    Participant " + i + ": ");
            name = scanStr.nextLine();
            if (Participant.containsIgnoreCase(name , names)){
                System.out.println("    \nYou have already entered this name\n");
                i--;
            }
            else{
                names[i] = name;
            }
        }
        return names;
    }

    public static Participant[] wishListAndPassword(Scanner scanInt, Scanner scanStr, String[] names){
        ArrayList<String>wishList = new ArrayList<String>();
        String password;
        Participant[] participants = new Participant[names.length];
        for(int i = 1; i < names.length; i++){
            clear();
            System.out.println("participant " + i + "(" + names[i] +") : ");
            password = enterPassword(scanInt);
            System.out.println();
            wishList = enterWishList(scanStr);
            participants[i] = new Participant(names[i], password, wishList);
            System.out.print("Enter any key to continue: ");
            scanStr.nextLine();

        }
        return participants;
    }

    public static String enterPassword(Scanner scan){
        System.out.print("   Enter your password: ");
        String password = scan.next();
        return password;
    }

    public static ArrayList<String> enterWishList(Scanner scan){
        ArrayList<String> wishList = new ArrayList<String>();
        String wish;
        do{
            System.out.print("   What would you like to add to your wish list? (enter x if you are finished): ");
            wish = scan.nextLine();
            if(!wish.equalsIgnoreCase("x")){
                wishList.add(wish);
            }
        }while(!wish.equalsIgnoreCase("x"));
        return wishList;
    }

    public static void showDrawNames(Random rand, Scanner scanInt, Scanner scanStr, Participant[] participants){
        Participant.drawNames(rand, participants);

        System.out.println("\nThe names have been drawn");
        System.out.print("Enter any key to see them: ");
        scanStr.nextLine();

        Participant drawName;
        for (int i = 1; i < participants.length; i++){
            clear();
            System.out.println("Participant " + i + " (" + participants[i].getName() + ") : ");
            drawName = participants[i].getDrawName(scanInt);
            System.out.println("Your draw name is " + drawName.getName() + "!");
            System.out.println("\n" + drawName.getName() + "'s wish list: ");
            for (int j = 0; j < drawName.getWishList().size(); j++){
                System.out.println("   > " + drawName.getWishList().get(j));
            }
            System.out.print("Enter any key to continue: ");
            scanStr.nextLine();
        }
    }
    public static void clear(){
        for(int i = 0; i < 40; i++){
            System.out.println();
        }
    }


  public static void main(String[]args){

    Scanner scanStr = new Scanner(System.in);
    Scanner scanInt = new Scanner(System.in);
    Random rand = new Random();



    String[] names = enterNames(scanInt, scanStr);
    Participant[] participants = wishListAndPassword(scanInt, scanStr, names);
    Participant.drawNames(rand, participants);
    showDrawNames(rand, scanInt, scanStr, participants);

  }
}

class Participant {
    private String name;
    public Participant drawnName;
    private String password;
    private ArrayList<String> wishList = new ArrayList<String>();

    public Participant(String name , String password, ArrayList<String> wishList){
        this.name = name;
        this.password = password;
        this.wishList = wishList;
        if (wishList.size() == 0){
            this.wishList.add("Nothing! Surprise me!!");
        }
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<String> getWishList(){
        return this.wishList;
    }

    public Participant getDrawName(Scanner scan){
        System.out.print("Enter your password to see your draw name: ");
        String password = scan.next();
        while(!this.password.equals(password)){
            System.out.print("Incorrect password. Try again: ");
            password = scan.next();
        }
        return this.drawnName;
    }


    public static void drawNames(Random rand, Participant[] names){
        ArrayList<Participant> namePool = arrToList(names);

        int num;
        for (int i = 1; i<names.length; i++){
            do{
                num = rand.nextInt(namePool.size() - 1)+1;
            }while(names[i].name.equals(namePool.get(num).name));
            names[i].drawnName = namePool.get(num);
            namePool.remove(num);
        }
    }


    private static ArrayList<Participant> arrToList(Participant[]arr){
        ArrayList<Participant> list = new ArrayList<Participant>();
        for(int i = 0; i< arr.length ; i++){
            list.add(arr[i]);
        }
        return list;
    }

    public static boolean containsIgnoreCase(String value, String[] arr){
        boolean contains = false;
        for (int i = 0; i < arr.length; i++){
            if(value.equalsIgnoreCase(arr[i])){
                contains = true;
            }
        }
        return contains;
    }
}
