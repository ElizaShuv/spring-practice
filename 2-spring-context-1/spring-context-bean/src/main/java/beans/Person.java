package beans;

public class Person {

    private String name;
    private Parrot first_parrot;
    private Parrot second_parrot;
    private Cat cat;
    private Dog dog;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parrot getFirstParrot() {
        return first_parrot;
    }
    public void setFirstParrot(Parrot first_parrot) {
        this.first_parrot = first_parrot;
    }
    public Parrot getSecondParrot() {
        return second_parrot;
    }
    public void setSecondParrot(Parrot second_parrot) {
        this.second_parrot=second_parrot;
    }

    public Cat getCat(){
        return cat;
    }
    public void setCat(Cat cat) {
        this.cat = cat;
    }
    public Dog getDog(){
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
