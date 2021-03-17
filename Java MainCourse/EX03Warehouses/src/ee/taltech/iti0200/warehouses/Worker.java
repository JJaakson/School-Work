package ee.taltech.iti0200.warehouses;
import java.util.Objects;

public class Worker {
    private String firstName;
    private String lastName;
    private String idCode;
    private Integer age;

    public Worker(String firstName, String lastName, String idCode, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idCode = idCode;
        this.age = age;

    }

    public String getWorkerSummary() {
        return this.firstName + " " + this.lastName + ", " + this.age + " (" + this.idCode + ")";
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdCode() {
        return this.idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Worker worker = (Worker) o;
        return Objects.equals(firstName, worker.firstName)
                && Objects.equals(lastName, worker.lastName)
                && Objects.equals(idCode, worker.idCode)
                && Objects.equals(age, worker.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, idCode, age);
    }
}
