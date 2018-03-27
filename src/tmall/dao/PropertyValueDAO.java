package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;

import tmall.util.DBUtil;

public class PropertyValueDAO {
	public int getTotal() {
		int total = 0;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			
			String sql = "select count(*) from propertyvalue";
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(PropertyValue bean) {
		
		String sql = "insert into propertyvalue values(null, ?, ?, ?)";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2, bean.getProperty().getId());
			ps.setString(3, bean.getValue());
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				bean.setId(rs.getInt(1));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(PropertyValue bean) {
		String sql = "update propertyvalue set pid =?,ptid=?, value=?  where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2, bean.getProperty().getId());
			ps.setString(3, bean.getValue());
			ps.setInt(4, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			
			String sql = "delete from propertyvalue where id = " + id;
			s.execute(sql);
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public PropertyValue get(int id) {
        PropertyValue bean = new PropertyValue();
  
        try (Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
  
            String sql = "select * from PropertyValue where id = " + id;
  
            ResultSet rs = s.executeQuery(sql);
 
            if (rs.next()) {
                int pid = rs.getInt("pid");
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");
                 
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
            }
  
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return bean;
    }
	
	public PropertyValue get(int pid, int ptid) {
		PropertyValue bean = null;
		String sql = "select * from propertyvalue where pid = " + pid + " and ptid = " + ptid;
		System.out.println("草你妈"+sql);
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				bean = new PropertyValue();
				bean.setId(rs.getInt("id"));
				bean.setProduct(new ProductDAO().get(pid));
				bean.setProperty(new PropertyDAO().get(ptid));
				bean.setValue(rs.getString("value"));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<PropertyValue> list(int start, int count){
		List<PropertyValue> beans = new ArrayList<>();
		String sql = "select * from propertyvalue order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setInt(1,start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PropertyValue bean = new PropertyValue();
				bean.setId(rs.getInt("id"));
				bean.setProduct(new ProductDAO().get(rs.getInt("pid")));
				bean.setProperty(new PropertyDAO().get(rs.getInt("ptid")));
				bean.setValue(rs.getString("value"));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<PropertyValue> list(){
		return list(0, Short.MAX_VALUE);
	}
	
	/**
	 * service
	 * 
	 */
	
	public List<PropertyValue> list(int pid){
		List<PropertyValue> beans = new ArrayList<>();
		String sql = "select * from propertyvalue where pid = ? order by ptid desc ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setInt(1,pid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PropertyValue bean = new PropertyValue();
				bean.setId(rs.getInt("id"));
				bean.setProduct(new ProductDAO().get(pid));
				bean.setProperty(new PropertyDAO().get(rs.getInt("ptid")));
				bean.setValue(rs.getString("value"));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public void init(Product p) {
		List<Property> pts = new PropertyDAO().list(p.getCategory().getId());
		
		for (Property pt : pts) {
			PropertyValue pv = get(p.getId(), pt.getId());
			if(null == pv) {
				System.out.println("抓取失败了！");
				pv = new PropertyValue();
				pv.setProduct(p);
				pv.setProperty(pt);
				add(pv);
			}
		}
		
	}
}
