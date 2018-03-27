package tmall.util.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductDateComparator implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		
		return o2.getCreateDate().compareTo(o1.getCreateDate());
	}
	

}
