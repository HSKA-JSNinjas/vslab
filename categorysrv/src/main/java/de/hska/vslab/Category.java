package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;

	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

