package ProjectGUI;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductHelper {
	ArrayList<Product> list;
	ProductHelper(ArrayList<Product> list){
		this.list = list;
		
		
	}
	public void display() {
		for(Product p : list) {
			System.out.println(p.barcode);
			System.out.println(p.category);
			System.out.println(p.hsn);
			System.out.println(p.markedPrice);
			System.out.println(p.name);
			System.out.println(p.purchasedPrice);
			System.out.println(p.quantity);
			System.out.println(p.sellingPrice);
			System.out.println(p.subcategory);
			System.out.println(p.tax);
		}
	}
	public String[] ProductPrice() {
		String[] ProPrice = new String[list.size()];
		for(int i=0;i<list.size();i++) {
			ProPrice[i]=list.get(i).name+"  -  "+list.get(i).barcode;
		}
		return ProPrice;
	}
	public static void main(String[] args) {
		Product p = new Product("6561616","Food","25/2025","65161","chocolate","chocolate",15.0,12.0,14.0,5,15);
		ArrayList<Product> list = new ArrayList<>();
		list.add(p);
		ProductHelper ph = new ProductHelper(list);
		ph.display();
		String[] res = ph.ProductPrice();
		System.out.println(Arrays.toString(res));
		
	}
	
}
