package ee.taltech.iti0200.personstatistics;

public class PersonBuilder {

    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private double heightInMeters;
    private String occupation;
    private String nationality;

    public PersonBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PersonBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PersonBuilder withAge(int age) {
        this.age = age;
        return this;
    }

    public PersonBuilder withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public PersonBuilder withHeightInMeters(double heightInMeters) {
        this.heightInMeters = heightInMeters;
        return this;
    }

    public PersonBuilder withOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public PersonBuilder withNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public Person build() {
        return new Person(firstName, lastName, age, gender, heightInMeters, occupation, nationality);
    }
}
