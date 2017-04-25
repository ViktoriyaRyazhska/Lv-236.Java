

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Methods {
	
	static HashMap<Person, List<Pet>> zooclub = new HashMap<>();
	
	static Scanner sc = new Scanner(System.in);
	public static void readFromFile() throws IOException{
		FileReader reader = new FileReader("file.txt");
		int c;
        while((c=reader.read())!=-1){
             reader.read();
            //System.out.print((char)c);
        } 
        reader.close();
	}
	public static void printToFile() throws IOException{
		FileWriter writer = new FileWriter("file.txt", false);
		writer.write(zooclub.toString());
        
        
        writer.flush();
        writer.close();
	}
	public static void printFromFileToConsole() throws IOException{
		FileReader reader = new FileReader("file.txt");
		int c;
        while((c=reader.read())!=-1){
             //reader.read();
            System.out.print((char)c);
        } 
        reader.close();
	}
	
	public static void begin() throws IOException {

		System.out.println("Щоб додати учасника клубу, натисніть 1");
		System.out.println("Щоб додати тваринку до учасника клубу, натисніть 2");
		System.out.println("Щоб  видалити тваринку з учасника клубу, натисніть 3");
		System.out.println("Щоб видалити учасника клубу, натисніть 4");
		System.out.println("Щоб видалити тваринку від усіх учасників клубу, натисніть 5");
		System.out.println("Щоб вивести на екран зооклуб, натисніть 6");
		System.out.println("Щоб вийти з програми, натисніть 8");

		int a = sc.nextInt();
		switch (a) {
		case 1:
			Methods.addPerson(zooclub);

			
			Methods.printToFile();
			Methods.continueWork();
			break;
		case 2:
			Methods.addPetToPerson(zooclub);
			Methods.printToFile();
			Methods.continueWork();
			break;
		case 3:
			Methods.deletePet(zooclub);
			Methods.printToFile();
			Methods.continueWork();
			break;
		case 6:Methods.readFromFile();
		Methods.printFromFileToConsole();			//Methods.print(zooclub);
			Methods.continueWork();
			break;
		case 4:
			Methods.deletePerson(zooclub);
			Methods.printToFile();
			Methods.continueWork();
			break;
		case 8:
			System.out.println("роботу з клубом закінчено.");
			sc.close();
			break;
		case 5:
			Methods.deleteAllPets(zooclub);
			Methods.printToFile();
			Methods.continueWork();
			break;

		default:
			System.out.println("помилка при вводі. введіть дані ще раз");
			Methods.begin();
			break;
		}
	}

	private static Map<Person, List<Pet>> deletePet(HashMap<Person, List<Pet>> zooclub) {
		System.out.println("введіть імя учасника клубу");
		String firstName = sc.next();
		System.out.println("введіть прізвище учасника клубу");
		String lastName = sc.next();
		System.out.println("введіть вік учасника клубу");
		int age = sc.nextInt();
		System.out.println("введіть імя тваринки");
		String petsName = sc.next();
		System.out.println("введіть вік тваринки");
		int petsAge = sc.nextInt();
		int u = 0;
		Person p = new Person(firstName, lastName, age);
		Pet o = new Pet(petsName, petsAge);

		if (zooclub.containsKey(p)) {
			u = 1;

			if (zooclub.get(p).contains(o)) {
				zooclub.get(p).remove(o);
				System.out.println("тваринку було видалено");
				u = 2;
			}

		}
		if (u == 0) {
			System.out.println("такого учасника немає в клубі");
		}
		if (u == 1) {
			System.out.println("такої тваринки немає в цього учасника ");
		}
	return zooclub;

	}

	private static Map<Person, List<Pet>> deleteAllPets(HashMap<Person, List<Pet>> zooclub) {

		System.out.println("введіть імя тваринки");
		String petsName = sc.next();
		System.out.println("введіть вік тваринки");
		int petsAge = sc.nextInt();
		int u = 0;

		Set<Entry<Person, List<Pet>>> set = zooclub.entrySet();
		for (Entry<Person, List<Pet>> entry : set) {
			Object[] pets = entry.getValue().toArray();

			for (int i = 0; i < pets.length; i++) {
				if (((Pet) pets[i]).getName().equals(petsName) && ((Pet) pets[i]).getAge() == petsAge) {
					entry.getValue().remove(i);

					u = 1;
					System.out.println("тваринку було видалено зі всіх учасників");
					break;
				}
			}
		}
		if (u == 0) {
			System.out.println("не всі учасники мають таку тваринку");
		}
		return zooclub;
	}

	public static Map<Person, List<Pet>> addPerson(HashMap<Person, List<Pet>> zooclub)  {
		
		System.out.println("введіть імя учасника клубу");
		String firstName = sc.next();
		System.out.println("введіть прізвище учасника клубу");
		String lastName = sc.next();
		System.out.println("введіть вік учасника клубу");
		int age = sc.nextInt();
		Person a=new Person(firstName, lastName, age);
		zooclub.put(a, new LinkedList<Pet>());
		 
		return zooclub;

	}

	public static Map<Person, List<Pet>> addPetToPerson(HashMap<Person, List<Pet>> zooclub) throws IOException {
		
		System.out.println("введіть імя учасника клубу");
		String firstName = sc.next();
		System.out.println("введіть прізвище учасника клубу");
		String lastName = sc.next();
		System.out.println("введіть вік учасника клубу");
		int age = sc.nextInt();
		System.out.println("введіть імя тваринки");
		String petsName = sc.next();
		System.out.println("введіть вік тваринки");
		int petsAge = sc.nextInt();
		Person p = new Person(firstName, lastName, age);
		Pet o = new Pet(petsName, petsAge);

		int u = 0;

		if (zooclub.containsKey(p)) {
			zooclub.get(p).add(o);
			u = 1;
			System.out.println("тваринку додано");
			
		}
		if (u == 0) {
			System.out.println("такого учасника немає в клубі");
		}

		return zooclub;
	}

	public static Map<Person, List<Pet>> deletePerson(HashMap<Person, List<Pet>> zooclub) {
		System.out.println("введіть імя учасника клубу");
		String firstName = sc.next();
		System.out.println("введіть прізвище учасника клубу");
		String lastName = sc.next();
		System.out.println("введіть вік учасника клубу");
		int age = sc.nextInt();
		Person p = new Person(firstName, lastName, age);
		


			if (zooclub.containsKey(p)) {
				zooclub.remove(p);
				System.out.println("учасника видалено");

			
		}

		return zooclub;

	}

	public static Map<Person, List<Pet>> print(HashMap<Person, List<Pet>> zooclub) {
		Set<Entry<Person, List<Pet>>> set = zooclub.entrySet();
		for (Entry<Person, List<Pet>> entry : set) {
			System.out.println(entry);
		}
		return zooclub;
	}

	public static void continueWork() throws IOException {

		System.out.println("Продовжити роботу з зооклубом? так-натисніть 1, ні-натисніть 2");
		int d = sc.nextInt();
		switch (d) {
		case 1:
			Methods.begin();
			break;
		case 2:
			System.out.println("роботу з клубом закінчено.");
			sc.close();
			break;
		default:
			System.out.println("помилка, введіть дані ще раз");
			Methods.continueWork();
			break;
		}
	}
}
