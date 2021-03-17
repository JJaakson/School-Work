package ee.taltech.iti0200.lambda;
public class Person {

    public enum Gender { MALE, FEMALE }
    private String firstName;
    private String surname;
    private String  gender;
    private Integer age;
    private Integer weight;
    private Integer height;
    private Integer rating;

    public Person(String firstName, String surname, String gender,
                  Integer age, Integer weight, Integer height, Integer rating) {
        this.firstName = firstName;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.rating = rating;
    }

    public Integer increaseRating(Integer number) {
        this.rating *= number;
        return this.rating;
    }

    @Override
    public String toString() {
        return "firstName='" + firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getRating() {
        return rating;
    }

    public Integer getNameLentgh() {
        return firstName.length() + surname.length();
    }
}
