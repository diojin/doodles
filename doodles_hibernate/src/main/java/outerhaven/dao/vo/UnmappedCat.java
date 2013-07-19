package outerhaven.dao.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UnmappedCat {
    private Integer id; // identifier

    private Date birthdate;
    private String color;
    private char sex;
    private float weight;
    private int litterId;

    private UnmappedCat mother;
    private Set kittens = new HashSet();
   
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public Date getBirthdate() {
		return birthdate;
	}



	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}



	public String getColor() {
		return color;
	}



	public void setColor(String color) {
		this.color = color;
	}



	public char getSex() {
		return sex;
	}



	public void setSex(char sex) {
		this.sex = sex;
	}



	public float getWeight() {
		return weight;
	}



	public void setWeight(float weight) {
		this.weight = weight;
	}



	public int getLitterId() {
		return litterId;
	}



	public void setLitterId(int litterId) {
		this.litterId = litterId;
	}



	public UnmappedCat getMother() {
		return mother;
	}



	public void setMother(UnmappedCat mother) {
		this.mother = mother;
	}



	public Set getKittens() {
		return kittens;
	}



	public void setKittens(Set kittens) {
		this.kittens = kittens;
	}



	// addKitten not needed by Hibernate
    public void addKitten(UnmappedCat kitten) {
    	kitten.setMother(this);
	kitten.setLitterId( kittens.size() ); 
        kittens.add(kitten);
    }
}
