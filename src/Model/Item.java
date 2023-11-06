package Model;

import java.io.Serializable;

public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id = "";
	
	public Item(String id) {
		this.id=id;
	}
	
	@Override
	public String toString(){
		return id;
	}
}
