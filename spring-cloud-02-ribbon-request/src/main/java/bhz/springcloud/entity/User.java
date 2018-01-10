package bhz.springcloud.entity;

import java.io.Serializable;

public class User implements Serializable {

	private String id ;
	private String name;
	private int age;
	private String version;
	
	public User() {
		super();
	}
	public User(String id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
