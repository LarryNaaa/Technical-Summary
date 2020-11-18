package Clone;

import java.util.ArrayList;

public class CloneTest {
    public static void main(String[] args) {
        // person1
        Person person1 = new Person();
        person1.name = "Fred";
        Address add1 = new Address();
        add1.street = "Main Street";
        person1.address = add1;
        System.out.println("Before Change: " + person1);

        Person person2 = new Person();
        try{
            person2 = (Person) person1.clone();

        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }

        System.out.println("Before Change: " + person2);

        // change person1
        person1.name = "Bob";
        person1.address.street = "Front Str";
        System.out.println("After Change: " + person1);
        System.out.println("After Change: " + person2);

        System.out.println(person1.address == person2.address ? "clone is shallow copy" : "clone is deep copy");
    }
}


class Person implements Cloneable{
    public String name;
    public Address address;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // pc2 with shallow copy
        return super.clone();

        // pc2 with deep copy
//        PersonClone person = (PersonClone) super.clone();
//        person.address = (AddressClone) this.address.clone();
//        return person;
    }

    @Override
    public String toString() {
        return "PersonClone{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}

class Address implements Cloneable{
    public int houseId;
    public String street;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "AddressClone{" +
                "houseId=" + houseId +
                ", street='" + street + '\'' +
                '}';
    }
}
