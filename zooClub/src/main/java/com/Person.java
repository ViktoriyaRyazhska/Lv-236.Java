package main.java.com;

import java.io.Serializable;

/**
 * This class is Entity com.marta_zooclub.Person, which
 * implements Comparable<> for sort
 * person and Serializable for record,
 * read object com.marta_zooclub.Person from file.
 *
 * @author Marta.
 * @version 1.1.
 * @since 24.04.2017.
 */
public class Person implements Comparable<Person>, Serializable {

    /**
     * String field firstName.
     */
    private String firstName;

    /**
     * String field lastName.
     */
    private String lastName;

    /**
     * int field age.
     */
    private int age;

    /**
     * Default constructor class com.marta_zooclub.Person.
     */
    public Person() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor with parameters.
     * <code>String firstName, String lastName, int age</code>
     * @param firstName String.
     * @param lastName String.
     * @param age int.
     */
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /**
     * This method get firstName.
     *
     * @return String firstName person.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method set firstName com.marta_zooclub.Person.
     *
     * @param firstName String This for setFirstName.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This method get lastName com.marta_zooclub.Person.
     *
     * @return String lastName person.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This method set lastName com.marta_zooclub.Person.
     *
     * @param lastName String This for setLastName.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This method get age com.marta_zooclub.Person.
     *
     * @return int age person.
     */
    public int getAge() {
        return age;
    }

    /**
     * This method set age com.marta_zooclub.Person.
     *
     * @param age int This for setAge.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Override toString method.
     */
    @Override
    public String toString() {
        return "com.marta_zooclub.Person [firstName="
                + firstName + ", lastName=" + lastName
                + ", age=" + age + "]";
    }

    /**
     * Override hashCode method.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result
                + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result
                + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }
    /**
     * Override equals method.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Person other = (Person) obj;
        if (age != other.age) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        return true;
        }

    /**
     * This method is sorting com.marta_zooclub.Person by:
     * lastname - <code>(compareLastName != 0)==true</code>;
     * <code>else if</code>
     * firsname - <code>(compareFirstName != 0)==true</code>;
     * <code>else</code>
     * age - <code>return int compareAge = this.age - o.age</code>.
     *
     * @param o object com.marta_zooclub.Person.
     * @return int.
     */
    @Override
    public int compareTo(Person o) {
        int compareLastName = this.lastName.compareTo(o.lastName);
        int compareFirstName = this.firstName.compareTo(o.firstName);
        int compareAge = this.age - o.age;
        if (compareLastName != 0) {
            return compareLastName;
        }
        if (compareFirstName != 0) {
            return compareFirstName;
        }
        return compareAge;
    }
}
