package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDAO {
	public int getTotal() {
		int total = 0;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			
			String sql = "select count(*) from user";
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(User bean) {
		
		String sql = "insert into user values(null, ?, ?)";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				bean.setId(rs.getInt(1));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(User bean) {
		String sql = "update user set name=? , password = ? where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			
			String sql = "delete from user where id = " + id;
			s.execute(sql);
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public User get(int id) {
		User bean = null;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			String sql = "select * from user where id = " + id;
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				bean = new User();
				bean.setId(id);
				bean.setName(rs.getString(2));
				bean.setPassword(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<User> list(int start, int count){
		List<User> beans = new ArrayList<>();
		String sql = "select * from user order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setInt(1,start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User bean = new User();
				bean.setId(rs.getInt(1));
				bean.setPassword(rs.getString(3));
				bean.setName(rs.getString(2));
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<User> list(){
		return list(0, Short.MAX_VALUE);
	}
	
	/*
	 * 
	 * 
	 */
	
	public User get(String name) {
		User bean = null;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			String sql = "select * from user where name = '" + name + "'";
			ResultSet rs = s.executeQuery(sql);
			
			if(rs.next()) {
				bean = new User();
				bean.setId(rs.getInt(1));
				bean.setPassword(rs.getString(3));
				bean.setName(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public boolean isExist(String name) {
		return null != get(name);
	}
	
	public User get(String name, String password) {
		User bean = null;
		String sql = "select * from user where name = ? and password = ? ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setString(1,name);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				bean = new User();
				bean.setId(rs.getInt(1));
				bean.setPassword(password);
				bean.setName(name);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
