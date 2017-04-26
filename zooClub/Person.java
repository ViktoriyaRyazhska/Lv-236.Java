import java.io.Serializable;

/**
 * This class is Entity Person, which
 * implements Comparable<> for sort
 * person and Serializable for record,
 * read object Person from file.
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
     * Default constructor class Person.
     */
    public Person() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor with parameters.
     * <code>String firstName, String lastName, int age</code>
     */
    public Person(String firstName, String lastName, int age) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /**
     * This method get firstName.
     *
     * @param Nothing.
     * @return String firstName person.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method set firstName Person.
     *
     * @param String This for setFirstName.
     * @return Nothing.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This method get lastName Person.
     *
     * @param Nothing.
     * @return String lastName person.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This method set lastName Person.
     *
     * @param String This for setLastName.
     * @return Nothing.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This method get age Person.
     *
     * @param Nothing.
     * @return int age person.
     */
    public int getAge() {
        return age;
    }

    /**
     * This method set age Person.
     *
     * @param int This for setAge.
     * @return Nothing.
     */
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [firstName=" + firstName + ", lastName=" + lastName
                + ", age=" + age + "]";
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (age != other.age)
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        return true;
    }

    /**
     * This method is sorting Person by:
     * lastname - <code>(compareLastName != 0)==true</code>;
     * <code>else if</code>
     * firsname - <code>(compareFirstName != 0)==true</code>;
     * <code>else</code>
     * age - <code>return int compareAge = this.age - o.age</code>.
     *
     * @param object Person.
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
