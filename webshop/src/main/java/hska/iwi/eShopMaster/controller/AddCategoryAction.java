package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.DataHandler;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.Category;
import hska.iwi.eShopMaster.model.User;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddCategoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6704600867133294378L;
	
	private String newCatName = null;
	
	private List<Category> categories;
	
	User user;

	DataHandler dh = new DataHandler();

	public String execute() throws Exception {

		String res = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (User) session.get("webshop_user");
		if(user != null && (user.getRole().equals("Admin"))) {
			
			dh.createCategory(newCatName);
			
			// Go and get new Category list
			this.setCategories(dh.getCategories());
			
			res = "success";
		}
		
		return res;
	
	}
	
	@Override
	public void validate(){
		if (getNewCatName().length() == 0) {
			addActionError(getText("error.catname.required"));
		}
		// Go and get new Category list
		this.setCategories(dh.getCategories());
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public String getNewCatName() {
		return newCatName;
	}

	public void setNewCatName(String newCatName) {
		this.newCatName = newCatName;
	}
}
