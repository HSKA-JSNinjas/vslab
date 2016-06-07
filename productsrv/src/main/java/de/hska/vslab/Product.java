package de.hska.vslab;

/**
 * Created by d059314 on 02.06.16.
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;

	@Column(name = "categoryId")
	private int categoryId;

	@Column(name = "details")
	private String details;

	public Product() {
	}

	public Product(String name, double price, int category) {
		this.name = name;
		this.price = price;
		this.categoryId = category;
	}

	public Product(String name, double price, int category, String details) {
		this.name = name;
		this.price = price;
		this.categoryId = category;
		this.details = details;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCategory() {
		return this.categoryId;
	}

	public void setCategory(int category) {
		this.categoryId = category;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
