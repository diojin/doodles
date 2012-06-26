package outerhaven.spring.aop.aspectj;

public class SimpleBean {
    private String name;
    private int age;

    public SimpleBean() {
    }

    public SimpleBean(final String name, int age) {
            this.name = name;
            this.age = age;
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public int getAge() {
            return age;
    }

    public void setAge(int age) {
            this.age = age;
    }

    public String setNameAndAge() {
            return this.name + " (" + this.age + ")";
    }
}