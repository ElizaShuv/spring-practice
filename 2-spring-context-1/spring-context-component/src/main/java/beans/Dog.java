package beans;

import org.springframework.stereotype.Component;

@Component
public class Dog {

    private String name = "Сырок";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
