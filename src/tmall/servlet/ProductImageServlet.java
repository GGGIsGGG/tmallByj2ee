package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.ProductImageDAO;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class ProductImageServlet extends BaseBackServlet{

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String, String> params = new HashMap<String, String>();
		InputStream is = parseUpload(request, params);
		
		String type = params.get("type");
		int pid = Integer.parseInt(params.get("pid"));
		Product p = productDAO.get(pid);
		
		ProductImage pi = new ProductImage();
		
		pi.setProduct(p);
		pi.setType(type);
		productImageDAO.add(pi);
			
		//生成文件
		String fileName = pi.getId()+ ".jpg";
		String imageFloder;
		String imageFloder_small=null;
		String imageFloder_middle=null;
		if(ProductImageDAO.type_single.equals(pi.getType())) {
			imageFloder = request.getSession().getServletContext().getRealPath("img/productSingle");
			imageFloder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
			imageFloder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
		}else
			imageFloder = request.getSession().getServletContext().getRealPath("img/productDetail");
		File f = new File(imageFloder, fileName);
		f.getParentFile().mkdirs();
		try {
			if(null!= is &&0!=is.available()) {
				FileOutputStream fos = new FileOutputStream(f);
				byte[] b = new byte[1024 * 1024]; 
				int len = 0;
				while(-1 != (len = is.read(b)))
					fos.write(b, 0, len);
				fos.flush();
				fos.close();
				
				BufferedImage img = ImageUtil.change2jpg(f);
				ImageIO.write(img, "jpg", f);
				
				if(ProductImageDAO.type_single.equals(pi.getType())) {
					File f_small = new File(imageFloder_small, fileName);
					File f_middle = new File(imageFloder_middle, fileName);
					f_small.getParentFile().mkdirs();
					f_middle.getParentFile().mkdirs();
					
					ImageUtil.resizeImage(f, 56, 56, f_small);
					ImageUtil.resizeImage(f, 217, 190, f_middle);
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "@admin_productImage_list?pid=" + pid;
	}
	

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		ProductImage pi = productImageDAO.get(id);
		productImageDAO.delete(id);
		String fileName = pi.getId() + ".jpg";
		if(ProductImageDAO.type_single.equals(pi.getType())) {
			String imageFloder = request.getSession().getServletContext().getRealPath("img/productSingle");
			String imageFloder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
			String imageFloder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
			
			File f = new File(imageFloder, fileName);
			File f_small = new File(imageFloder_small, fileName);
			File f_middle = new File(imageFloder_middle, fileName);
			
			f.delete();
			f_small.delete();
			f_middle.delete();
		}else {
			String imageFloder = request.getSession().getServletContext().getRealPath("img/productDetail");
			File f = new File(imageFloder, fileName);
			f.delete();
			
		}
		return "@admin_productImage_list?pid=" + pi.getProduct().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product p = productDAO.get(pid);
		List<ProductImage> piSingles = productImageDAO.list(p, ProductImageDAO.type_single);
		List<ProductImage> piDetails = productImageDAO.list(p, ProductImageDAO.type_detail);
		request.setAttribute("piSingles", piSingles);
		request.setAttribute("p", p);
		request.setAttribute("piDetails", piDetails);
		return "admin/listProductImage.jsp";
	}

}
