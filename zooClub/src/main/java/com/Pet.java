package main.java.com;

/**
 * This class is Entity com.marta_zooclub.Pet.
 *
 * @author Marta.
 * @version 1.1.
 * @since 24.04.2017.
 */
public class Pet {

    /**
     * String field name.
     */
    private String name;

    /**
     * int field age.
     */
    private int age;

    /**
     * Constructor with parameters.
     * <code>String name, int age</code>
     * @param name String.
     * @param age int.
     */
    public Pet(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    /**
     * Default constructor class com.marta_zooclub.Pet.
     */
    public Pet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * This method get name.
     *
     * @return String name pet.
     */
    public String getName() {
        return name;
    }

    /**
     * This method set name com.marta_zooclub.Pet.
     *
     * @param name String This for set name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method get age.
     *
     * @return int age pet.
     */
    public int getAge() {
        return age;
    }

    /**
     * This method set age com.marta_zooclub.Pet.
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
        return "com.marta_zooclub.Pet [name=" + name + ", age=" + age + "]";
    }

    /**
     * Override hashCode method.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Pet other = (Pet) obj;
        if (age != other.age) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
