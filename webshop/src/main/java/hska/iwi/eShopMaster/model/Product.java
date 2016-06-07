package hska.iwi.eShopMaster.model;

/**
 * Created by d059314 on 02.06.16.
 */

public class Product {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private String name;

	private double price;

	private int categoryId;
	private String categoryName = "";

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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
