package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Review;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ReviewDAO {
	public int getTotal() {
		int total = 0;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			
			String sql = "select count(*) from review";
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	
	public void add(Review bean) {
		
		String sql = "insert into review values(null, ?, ?, ?, ?)";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
			
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				bean.setId(rs.getInt(1));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Review bean) {
		String sql = "update review set content=?, uid=?, pid=?, createDate=?  where id = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			ps.setString(1, bean.getContent());
			ps.setInt(2, bean.getUser().getId());
			ps.setInt(3, bean.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
			ps.setInt(5, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			
			String sql = "delete from review where id = " + id;
			s.execute(sql);
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Review get(int id) {
		Review bean = null;
		try(Connection c = DBUtil.getConnection();Statement s = c.createStatement(); ) {
			String sql = "select * from review where id = " + id;
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				bean = new Review();
				bean.setId(id);
				bean.setContent(rs.getString("content"));
				bean.setProduct(new ProductDAO().get(rs.getInt("pid")));
				bean.setUser(new UserDAO().get(rs.getInt("uid")));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<Review> list(int pid, int start, int count){
		List<Review> beans = new ArrayList<>();
		String sql = "select * from review where pid = ? order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			
			ps.setInt(1, pid);
			ps.setInt(2,start);
			ps.setInt(3, count);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Review bean = new Review();
				bean.setId(rs.getInt(1));
				bean.setContent(rs.getString("content"));
				bean.setProduct(new ProductDAO().get(rs.getInt("pid")));
				bean.setUser(new UserDAO().get(rs.getInt("uid")));
				bean.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				
				beans.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<Review> list(int pid){
		return list(pid , 0, Short.MAX_VALUE);
	}

	/*
	 * service
	 */
	
	public int getCount(int pid) {
		String sql = "select count(*) from review where pid = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			
			ps.setInt(1, pid);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean isExist(String content, int pid) {
		String sql = "select * from review where content = ? and pid = ?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql, 1);){
			
			ps.setString(1, content);
			ps.setInt(2, pid);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
}
