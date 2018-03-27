package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.ProductImage;
import tmall.bean.Product;
import tmall.util.DBUtil;

public class ProductImageDAO {
	
	public static final String type_single = "type_single";
	public static final String type_detail = "type_detail";
	
	public int getTotal() {
		int total = 0;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			
			String sql = "select count(*) from productimage";
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(ProductImage bean) {
		
		String sql = "insert into productimage values(null, ?, ?)";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setInt(1, bean.getProduct().getId());
			ps.setString(2, bean.getType());
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				bean.setId(rs.getInt(1));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(ProductImage bean) {
		
	}
	
	public void delete(int id) {
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			
			String sql = "delete from productimage where id = " + id;
			s.execute(sql);
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ProductImage get(int id) {
		ProductImage bean = null;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			String sql = "select * from productimage where id = " + id;
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				bean = new ProductImage();
				bean.setId(id);
				Product p = new ProductDAO().get(rs.getInt("pid"));
				bean.setProduct(p);
				bean.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<ProductImage> list(Product p, String type, int start, int count){
		List<ProductImage> beans = new ArrayList<>();
		String sql = "select * from productimage where pid = ? and type = ? order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			
			ps.setInt(1, p.getId());
			ps.setString(2, type);
			
			ps.setInt(3,start);
			ps.setInt(4, count);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ProductImage bean = new ProductImage();
				bean.setId(rs.getInt("id"));
				bean.setProduct(p);
				
				bean.setType(type);
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<ProductImage> list(Product p, String type){
		return list(p, type, 0, Short.MAX_VALUE);
	}

}
