package main.java.com;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * This class include all logic
 * our program.
 *
 * @author Marta.
 * @version 1.1.
 * @since 24.04.2017.
 */
final class Methods {

    /**
     * Default constructor Methods.
     */
    private Methods() {
    }

    /**
     * General static variable type HashMap
     * Person, List<Pet> for club.
     */
    private static HashMap<Person, List<Pet>> zooclub = new HashMap<>();

    /**
     * Static Scanner object for
     * read information from console.
     */
    private static Scanner sc = new Scanner(System.in);

    /**
     * @see #printFromFileToConsole
     * @deprecated
     * @throws IOException .
     */
    public static void readFromFile() throws IOException {
        FileReader reader = new FileReader("file.txt");
        int c;
        while ((c = reader.read()) != -1) {      /////Problems
            reader.read();
        }
        reader.close();
    }

    /**
     * Method printToFile write information
     * about variable zooclub into empty file
     * with name "file.txt".
     *
     * @throws IOException .
     */
    public static void printToFile() throws IOException {
        //true - add record for not empty file
        FileWriter writer = new FileWriter("file.txt", false);
        writer.write(zooclub.toString());
        writer.flush();
        writer.close();
    }

    /**
     * Method printFromFileToConsole read information
     * from file "file.txt"  and print this on console.
     *
     * @throws IOException .
     */
    public static void printFromFileToConsole() throws IOException {
        FileReader reader = new FileReader("file.txt");
        int c;
        while ((c = reader.read()) != -1) {
            //reader.read();
            System.out.print((char) c);
        }
        reader.close();
    }

    /**
     * Method begin() print instruction on console
     * how to use our application and give
     * <code>switch</code> to changed options.
     *
     * @throws IOException .
     */
    public static void begin() throws IOException {

        final int one = 1;
        final int two = 2;
        final int three = 3;
        final int four = 4;
        final int fife = 5;
        final int six = 6;
        final int eight1 = 8;

        System.out.println("??? ?????? ???????? ?????, ???????? 1");
        System.out.println(
                "??? ?????? ???????? ?? ???????? ?????, ???????? 2");
        System.out.println(
                "???  ???????? ???????? ? ???????? ?????, ???????? 3");
        System.out.println("??? ???????? ???????? ?????, ???????? 4");
        System.out.println(
                "??? ???????? ???????? ?? ??? ???????? ?????, ???????? 5");
        System.out.println("??? ??????? ?? ????? ???????, ???????? 6");
        System.out.println("??? ????? ? ????????, ???????? 8");

        int a = sc.nextInt();
        switch (a) {
            case one:
                Methods.addPerson(zooclub);
                Methods.printToFile();
                Methods.continueWork();
                break;
            case two:
                Methods.addPetToPerson(zooclub);
                Methods.printToFile();
                Methods.continueWork();
                break;
            case three:
                Methods.deletePet(zooclub);
                Methods.printToFile();
                Methods.continueWork();
                break;
            case six:
                Methods.readFromFile();
                //Methods.print(zooclub)
                Methods.printFromFileToConsole();
                Methods.continueWork();
                break;
            case four:
                Methods.deletePerson(zooclub);
                Methods.printToFile();
                Methods.continueWork();
                break;
            case eight1:
                System.out.println("?????? ? ?????? ????????.");
                sc.close();
                break;
            case fife:
                Methods.deleteAllPets(zooclub);
                Methods.printToFile();
                Methods.continueWork();
                break;

            default:
                System.out.println("??????? ??? ????. ?????? ??? ?? ???");
                Methods.begin();
                break;
        }
    }

    /**
     * Method deletePet() delete pet from Person
     * at first take information from person then
     * take information from pet and delete
     * this pet.
     *
     * @param zooclub type Map<Person, List<Pet>>.
     * @return zooclub type Map<Person, List<Pet>>.
     * @throws IOException .
     */
    private static Map<Person, List<Pet>> deletePet(HashMap<Person,
            List<Pet>> zooclub) {
        System.out.println("?????? ??? ???????? ?????");
        String firstName = sc.next();
        System.out.println("?????? ??????? ???????? ?????");
        String lastName = sc.next();
        System.out.println("?????? ?? ???????? ?????");
        int age = sc.nextInt();
        System.out.println("?????? ??? ????????");
        String petsName = sc.next();
        System.out.println("?????? ?? ????????");
        int petsAge = sc.nextInt();
        int u = 0;
        Person p = new Person(firstName, lastName, age);
        Pet o = new Pet(petsName, petsAge);

        if (zooclub.containsKey(p)) {
            u = 1;

            if (zooclub.get(p).contains(o)) {
                zooclub.get(p).remove(o);
                System.out.println("???????? ???? ????????");
                u = 2;
            }

        }
        if (u == 0) {
            System.out.println("?????? ???????? ???? ? ????");
        }
        if (u == 1) {
            System.out.println("???? ???????? ???? ? ????? ???????? ");
        }
        return zooclub;

    }

    /**
     * Method deleteAllPets() delete all pets from Person
     * at first take information from person then
     * delete all this pets.
     *
     * @param zooclub type Map<Person, List<Pet>>.
     * @return zooclub type Map<Person, List<Pet>>.
     * @throws IOException .
     */
    private static Map<Person, List<Pet>> deleteAllPets(HashMap<Person,
            List<Pet>> zooclub) {

        System.out.println("?????? ??? ????????");
        String petsName = sc.next();
        System.out.println("?????? ?? ????????");
        int petsAge = sc.nextInt();
        int u = 0;

        Set<Entry<Person, List<Pet>>> set = zooclub.entrySet();
        for (Entry<Person, List<Pet>> entry : set) {
            Object[] pets = entry.getValue().toArray();

            for (int i = 0; i < pets.length; i++) {
                if (((Pet) pets[i]).getName()
                        .equals(petsName) && ((Pet) pets[i])
                        .getAge() == petsAge) {
                    entry.getValue().remove(i);

                    u = 1;
                    System.out.println(
                            "???????? ???? ???????? ? ??? ????????");
                    break;
                }
            }
        }
        if (u == 0) {
            System.out.println("?? ?? ???????? ????? ???? ????????");
        }
        return zooclub;
    }

    /**
     * Method addPerson() add one person for global variable.
     *
     * @param zooclub type HashMap<Person, List<Pet>>.
     * @return zooclub Map<Person, List<Pet>>.
     * @throws IOException .
     */
    public static Map<Person, List<Pet>> addPerson(HashMap<Person,
            List<Pet>> zooclub) {

        System.out.println("?????? ??? ???????? ?????");
        String firstName = sc.next();
        System.out.println("?????? ??????? ???????? ?????");
        String lastName = sc.next();
        System.out.println("?????? ?? ???????? ?????");
        int age = sc.nextInt();
        Person a = new Person(firstName, lastName, age);
        zooclub.put(a, new LinkedList<Pet>());

        return zooclub;

    }

    /**
     * Method addPetToPerson() add one pet for
     * person and global variable.
     *
     * @param zooclub type HashMap<Person, List<Pet>>.
     * @return zooclub Map<Person, List<Pet>>.
     * @throws IOException .
     */
    public static Map<Person, List<Pet>> addPetToPerson(HashMap<Person,
            List<Pet>> zooclub) throws IOException {

        System.out.println("?????? ??? ???????? ?????");
        String firstName = sc.next();
        System.out.println("?????? ??????? ???????? ?????");
        String lastName = sc.next();
        System.out.println("?????? ?? ???????? ?????");
        int age = sc.nextInt();
        System.out.println("?????? ??? ????????");
        String petsName = sc.next();
        System.out.println("?????? ?? ????????");
        int petsAge = sc.nextInt();
        Person p = new Person(firstName, lastName, age);
        Pet o = new Pet(petsName, petsAge);

        int u = 0;

        if (zooclub.containsKey(p)) {
            zooclub.get(p).add(o);
            u = 1;
            System.out.println("???????? ??????");

        }
        if (u == 0) {
            System.out.println("?????? ???????? ???? ? ????");
        }

        return zooclub;
    }

    /**
     * Method deletePerson() delete person from global variable.
     *
     * @param zooclub type HashMap<Person, List<Pet>>.
     * @return zooclub Map<Person, List<Pet>>.
     * @throws IOException .
     */
    public static Map<Person, List<Pet>> deletePerson(HashMap<Person,
            List<Pet>> zooclub) {
        System.out.println("?????? ??? ???????? ?????");
        String firstName = sc.next();
        System.out.println("?????? ??????? ???????? ?????");
        String lastName = sc.next();
        System.out.println("?????? ?? ???????? ?????");
        int age = sc.nextInt();
        Person p = new Person(firstName, lastName, age);
        if (zooclub.containsKey(p)) {
            zooclub.remove(p);
            System.out.println("???????? ????????");
        }
        return zooclub;
    }

    /**
     * Method print() printing all information.
     *
     * @param zooclub type HashMap<Person, List<Pet>>.
     * @return zooclub Map<Person, List<Pet>>.
     * @deprecated print().
     * @throws IOException .
     */
    public static Map<Person, List<Pet>> print(HashMap<Person,
            List<Pet>> zooclub) {
        Set<Entry<Person, List<Pet>>> set = zooclub.entrySet();
        for (Entry<Person, List<Pet>> entry : set) {
            System.out.println(entry);
        }
        return zooclub;
    }

    /**
     * Method continueWork() do some like recursion.
     *
     * @throws IOException .
     */
    public static void continueWork() throws IOException {
        System.out.println(
            "?????????? ?????? ? ?????????? ???-???????? 1, ?-???????? 2");
        int d = sc.nextInt();
        switch (d) {
            case 1:
                Methods.begin();
                break;
            case 2:
                System.out.println("?????? ? ?????? ????????.");
                sc.close();
                break;
            default:
                System.out.println("???????, ?????? ??? ?? ???");
                Methods.continueWork();
                break;
        }
    }

    /**
     * Method get Zooclub.
     * @return zooclub HashMap<Person, List<Pet>>.
     */
    public static HashMap<Person, List<Pet>> getZooclub() {
        return zooclub;
    }

    /**
     * Method set Zooclub.
     * @param  zooclub HashMap<Person, List<Pet>>.
     */
    public static void setZooclub(HashMap<Person, List<Pet>> zooclub) {
        Methods.zooclub = zooclub;
    }

    /**
     * Method get Scanner.
     * @return  sc Scanner.
     */
    public static Scanner getSc() {
        return sc;
    }

    /**
     * Method set Scanner.
     * @param sc Scanner.
     */
    public static void setSc(Scanner sc) {
        Methods.sc = sc;
    }
}
