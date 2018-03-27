package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.util.DBUtil;

public class OrderItemDAO {
	
	public int getTotal() {
		int total = 0;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			
			String sql = "select count(*) from orderitem";
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(OrderItem bean) {
		
		String sql = "insert into orderitem values(null, ?, ?, ?, ?)";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setInt(1, bean.getProduct().getId());
			
			if(null == bean.getOrder())
				ps.setInt(2, -1);
			else
				ps.setInt(2, bean.getOrder().getId());
			ps.setInt(3, bean.getUser().getId());
			ps.setInt(4, bean.getNumber());
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				bean.setId(rs.getInt(1));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(OrderItem bean) {
		String sql = "update orderitem set pid=?, oid=?, uid=?, number=? where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setInt(1, bean.getProduct().getId());
			
			if(null == bean.getOrder())
				ps.setInt(2, -1);
			else
				ps.setInt(2, bean.getOrder().getId());
			ps.setInt(3, bean.getUser().getId());
			ps.setInt(4, bean.getNumber());
			ps.setInt(5, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			
			String sql = "delete from orderitem where id = " + id;
			s.execute(sql);
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public OrderItem get(int id) {
		OrderItem bean = null;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			String sql = "select * from orderitem where id = " + id;
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				bean = new OrderItem();
				int pid = rs.getInt("pid");
				int oid = rs.getInt("oid");
				int uid = rs.getInt("uid");
				int number = rs.getInt("number");
				
				bean.setId(id);
				bean.setProduct(new ProductDAO().get(pid));
				bean.setUser(new UserDAO().get(uid));
				bean.setNumber(number);
				
				if(-1 != oid)
					bean.setOrder(new OrderDAO().get(oid));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<OrderItem> listByUser(int uid, int start, int count){
		List<OrderItem> beans = new ArrayList<>();
		String sql = "select * from orderitem where uid = ? and oid = -1 order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setInt(1, uid);
			ps.setInt(2,start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				OrderItem bean = new OrderItem();
				int id = rs.getInt("id");
				int pid = rs.getInt("pid");
				int oid = rs.getInt("oid");

				int number = rs.getInt("number");
				
				bean.setId(id);
				bean.setProduct(new ProductDAO().get(pid));
				bean.setUser(new UserDAO().get(uid));
				bean.setNumber(number);
				
				bean.setOrder(new OrderDAO().get(oid));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<OrderItem> listByUser(int uid){
		return listByUser(uid ,0, Short.MAX_VALUE);
	}
	
	
	public List<OrderItem> listByOrder(int oid, int start, int count){
		List<OrderItem> beans = new ArrayList<>();
		String sql = "select * from orderitem where oid = ?  order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setInt(1, oid);
			ps.setInt(2,start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				OrderItem bean = new OrderItem();
				int id = rs.getInt("id");
				int pid = rs.getInt("pid");
				int uid = rs.getInt("uid");

				int number = rs.getInt("number");
				
				bean.setId(id);
				bean.setProduct(new ProductDAO().get(pid));
				bean.setUser(new UserDAO().get(uid));
				bean.setNumber(number);
				
				bean.setOrder(new OrderDAO().get(oid));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<OrderItem> listByOrder(int oid){
		return listByOrder(oid ,0, Short.MAX_VALUE);
	}
	
	public List<OrderItem> listByProduct(int pid, int start, int count){
		List<OrderItem> beans = new ArrayList<>();
		String sql = "select * from orderitem where pid = ?  order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setInt(1, pid);
			ps.setInt(2,start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				OrderItem bean = new OrderItem();
				int id = rs.getInt("id");
				int oid = rs.getInt("oid");
				int uid = rs.getInt("uid");

				int number = rs.getInt("number");
				
				bean.setId(id);
				bean.setProduct(new ProductDAO().get(pid));
				bean.setUser(new UserDAO().get(uid));
				bean.setNumber(number);
				
				bean.setOrder(new OrderDAO().get(oid));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<OrderItem> listByProduct(int pid){
		return listByProduct(pid ,0, Short.MAX_VALUE);
	}
	
	public int getSaleCount(int pid) {
		int total=0;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			
			String sql = "select sum(number) from orderitem where pid = " + pid + " and oid != -1";
			ResultSet rs = s.executeQuery(sql);
			while(rs.next())
				total = rs.getInt(1);
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void fill(List<Order> os) {
		for (Order o : os) {
			List<OrderItem> ois = listByOrder(o.getId());
			float total = 0;
			int totalNumber = 0;
			for (OrderItem oi : ois) {
				total += oi.getNumber()*oi.getProduct().getPromotePrice();
				totalNumber += oi.getNumber();
			}
			o.setTotal(total);
			o.setTotalNumber(totalNumber);
			o.setOrderItems(ois);
		}
	}

	public void fill(Order o) {
		List<OrderItem> ois = listByOrder(o.getId());
		float total = 0;
		int totalNumber = 0;
		for (OrderItem oi : ois) {
			total += oi.getNumber()*oi.getProduct().getPromotePrice();
			totalNumber += oi.getNumber();
			
		}
		o.setTotal(total);
		o.setTotalNumber(totalNumber);
		o.setOrderItems(ois);
	}
	
	public static void main(String[] args) {
		Order o = new Order();
		o.setId(1);
		new OrderItemDAO().fill(o);
	}
}
