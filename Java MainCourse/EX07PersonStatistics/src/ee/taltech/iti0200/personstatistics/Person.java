package ee.taltech.iti0200.personstatistics;

public class Person {

    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private double heightInMeters;
    private String occupation;
    private String nationality;

    public Person(String firstName, String lastName, int age, Gender gender, double heightInMeters, String occupation,
                  String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.heightInMeters = heightInMeters;
        this.occupation = occupation;
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public String getOccupation() {
        return occupation;
    }

    public double getHeightInMeters() {
        return heightInMeters;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
