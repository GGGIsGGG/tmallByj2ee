package tmall.util.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductAllComparator implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		
		return o2.getReviewCount()*o2.getSaleCount() - o1.getReviewCount()*o1.getSaleCount();
	}
	
}
