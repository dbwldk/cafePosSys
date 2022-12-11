package cafe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cafe.vo.cafeVO;

public class cafeDAO {
	ArrayList<cafeVO> cafeVOList = new ArrayList<cafeVO>();
	
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	
	String[] searchTables = {"ingredient", "inventory", "menu", "menu_type", "order", "recipe", "sell"};
	//검색 기준 테이블: 재료, 재고, 메뉴, 메뉴 종류, 발주, 요리법, 주문내역
	String[] searchIndexes = {"ing_id", "ing_name", "EXP", "cnt", "type_id", "type_name", "menu_id",
			"menu_name", "sold_out", "order_id", "order_time", "cnt", "recipe_id", "sell_id",
			"sell_time", "menu_cnt", "total"};
	//순서 = > vo와 같음
	
	//select
	//
	public ArrayList<cafeVO> select_def(int tbIndex) {
		Connection con = JDBCConnector.getCon();
		cafeVOList.clear();
		
		try {
			String sql = "select * from " + searchTables[tbIndex];
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				cafeVO vo = new cafeVO();
				switch (tbIndex) {
				
				case 0:
					//ingredient
					vo.setIngId(rs.getString("ing_id"));
					vo.setIngName(rs.getString("ing_name"));
					vo.setEXP(rs.getString("EXP"));
					break;
					
				case 1:
					//inventory
					vo.setIngId(rs.getString("ing_id"));
					vo.setIngCnt(rs.getInt("cnt"));
					break;
					
				case 2:
					//menu
					vo.setMenuId(rs.getString("menu_id"));
					vo.setMenuName(rs.getString("menu_name"));
					vo.setMenuType(rs.getInt("type_id"));
					vo.setSoldOutCheck(rs.getBoolean("sold_out"));
					break;
					
				case 3:
					//menuType
					vo.setMenuType(rs.getInt("type_id"));
					vo.setTypeName(rs.getString("type_name"));
					break;
					
				case 4:
					//order
					vo.setOrderId(rs.getString("order_id"));
					vo.setOrderTime(rs.getString("order_time"));
					vo.setIngId(rs.getString("ing_id"));
					vo.setOrderCnt(rs.getInt("cnt"));
					break;
					
				case 5:
					//recipe
					vo.setRecipeId(rs.getInt("recipe_id"));
					vo.setMenuId(rs.getString("menu_id"));
					vo.setIngId(rs.getString("ing_id"));
					break;
					
				case 6:
					//sell
					vo.setSellId(rs.getString("sell_id"));
					vo.setSellTime(rs.getString("sell_time"));
					vo.setMenuId(rs.getString("menu_id"));
					vo.setSellCnt(rs.getInt("menu_cnt"));
					vo.setTotal(rs.getInt("total"));
					break;
				}
				
				cafeVOList.add(vo);
				
			} //while end
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(stmt != null) {
					stmt.close();
				}
				
				if(con != null) {
					con.close();
				}
			}catch (SQLException e) {
				System.out.println("select close할 때 문제 발생");
			}
		}
		
		return cafeVOList;
	} //select default end
	
	
	//
	public ArrayList<cafeVO> select_where(int tbIndex, int indIndex, String searchWord) {
		Connection con = JDBCConnector.getCon();
		cafeVOList.clear();
		
		try {
			String sql = "select * from " + searchTables[tbIndex] + 
					" where " + searchIndexes[indIndex] + " = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, searchWord);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				cafeVO vo = new cafeVO();
				switch (tbIndex) {
				
				case 0:
					//ingredient
					vo.setIngId(rs.getString("ing_id"));
					vo.setIngName(rs.getString("ing_name"));
					vo.setEXP(rs.getString("EXP"));
					break;
					
				case 1:
					//inventory
					vo.setIngId(rs.getString("ing_id"));
					vo.setIngCnt(rs.getInt("cnt"));
					break;
					
				case 2:
					//menu
					vo.setMenuId(rs.getString("menu_id"));
					vo.setMenuName(rs.getString("menu_name"));
					vo.setMenuType(rs.getInt("type_id"));
					vo.setSoldOutCheck(rs.getBoolean("sold_out"));
					break;
					
				case 3:
					//menuType
					vo.setMenuType(rs.getInt("type_id"));
					vo.setTypeName(rs.getString("type_name"));
					break;
					
				case 4:
					//order
					vo.setOrderId(rs.getString("order_id"));
					vo.setOrderTime(rs.getString("order_time"));
					vo.setIngId(rs.getString("ing_id"));
					vo.setOrderCnt(rs.getInt("cnt"));
					break;
					
				case 5:
					//recipe
					vo.setRecipeId(rs.getInt("recipe_id"));
					vo.setMenuId(rs.getString("menu_id"));
					vo.setIngId(rs.getString("ing_id"));
					break;
					
				case 6:
					//sell
					vo.setSellId(rs.getString("sell_id"));
					vo.setSellTime(rs.getString("sell_time"));
					vo.setMenuId(rs.getString("menu_id"));
					vo.setSellCnt(rs.getInt("menu_cnt"));
					vo.setTotal(rs.getInt("total"));
					break;
				}
				
				cafeVOList.add(vo);
				
			} //while end
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(con != null) {
					con.close();
				}
			}catch (SQLException e) {
				System.out.println("select(where) close할 때 문제 발생");
			}
		}
		
		return cafeVOList;
	} //select where end
	
	
	
	//insert
	public void insert(cafeVO vo, int tbIndex) {
		Connection con = JDBCConnector.getCon();
		
		String sql = "";
		
		switch (tbIndex) {
		
		case 0:
			//ingredient : 3
			sql = "insert into ingredient values(?, ?, ?)";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, vo.getIngId());
				pstmt.setString(2, vo.getIngName());
				pstmt.setString(3, vo.getEXP());
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 1:
			//inventory : 2
			sql = "insert into inventory values(?, ?)";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, vo.getIngId());
				pstmt.setInt(2, vo.getIngCnt());
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 2:
			//menu : 4
			sql = "insert into menu values(?, ?, ?, ?)";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, vo.getMenuId());
				pstmt.setString(2, vo.getMenuName());
				pstmt.setInt(3, vo.getMenuType());
				pstmt.setBoolean(4, vo.isSoldOutCheck());
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 3:
			//menu_type : 2
			sql = "insert into menu_type values(?, ?)";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, vo.getMenuType());
				pstmt.setString(2, vo.getTypeName());
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 4:
			//order : 4
			sql = "insert into order values(?, ?, ?, ?)";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, vo.getOrderId());
				pstmt.setString(2, vo.getOrderTime());
				pstmt.setString(3, vo.getIngId());
				pstmt.setInt(4, vo.getOrderCnt());
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 5:
			//recipe : 3
			sql = "insert into recipe values(?, ?, ?)";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, vo.getRecipeId());
				pstmt.setString(2, vo.getMenuId());
				pstmt.setString(3, vo.getIngId());
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 6:
			//sell : 5
			sql = "insert into sell values(?, ?, ?, ?, ?)";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, vo.getSellId());
				pstmt.setString(2, vo.getSellTime());
				pstmt.setString(3, vo.getMenuId());
				pstmt.setInt(4, vo.getSellCnt());
				pstmt.setInt(5, vo.getTotal());
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
		} //switch end
		
		//finally
		try {
			if(pstmt != null) {
				pstmt.close();
			}
			
			if(con != null) {
				con.close();
			}
		}catch (SQLException e) {
			System.out.println("insert close할 때 문제 발생");
		}
		
	} //insert end
	
	
	//update
	public void update(cafeVO vo, int tbIndex) {
		Connection con = JDBCConnector.getCon();
		
		String sql = "";
		
		switch (tbIndex) {
		
		case 0:
			//ingredient : 3
			sql = "update ingredient ing_name = ?, EXP = ? where ing_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, vo.getIngName());
				pstmt.setString(2, vo.getEXP());
				pstmt.setString(3, vo.getIngId()); //
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 1:
			//inventory : 2
			sql = "update inventory set cnt = ? where ing_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, vo.getIngCnt());
				pstmt.setString(2, vo.getIngId()); //
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 2:
			//menu : 4
			sql = "update menu set menu_name = ?, type_id = ?, sold_out = ? where menu_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, vo.getMenuName());
				pstmt.setInt(2, vo.getMenuType());
				pstmt.setBoolean(3, vo.isSoldOutCheck());
				pstmt.setString(4, vo.getMenuId()); //
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 3:
			//menu_type : 2
			sql = "update menu_type set type_name = ? where type_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTypeName());
				pstmt.setInt(2, vo.getMenuType()); //
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 4:
			//order : 4
			sql = "update order set order_time = ?, ing_id = ?, cnt = ? where order_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, vo.getOrderTime());
				pstmt.setString(2, vo.getIngId());
				pstmt.setInt(3, vo.getOrderCnt());
				pstmt.setString(4, vo.getOrderId()); //
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 5:
			//recipe : 3
			sql = "update recipe set menu_id = ?, ing_id = ? where recipe_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, vo.getMenuId());
				pstmt.setString(2, vo.getIngId());
				pstmt.setInt(3, vo.getRecipeId()); //
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 6:
			//sell : 5
			sql = "update sell set sell_time = ?, menu_id = ?, menu_cnt = ?, total = ? where sell_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, vo.getSellTime());
				pstmt.setString(2, vo.getMenuId());
				pstmt.setInt(3, vo.getSellCnt());
				pstmt.setInt(4, vo.getTotal());
				pstmt.setString(5, vo.getSellId()); //
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
		} //switch end
		
		//finally
		try {
			if(pstmt != null) {
				pstmt.close();
			}
			
			if(con != null) {
				con.close();
			}
		}catch (SQLException e) {
			System.out.println("update close할 때 문제 발생");
		}
		
	} //update end
	
	
	
	//delete
	public void delete(cafeVO vo, int tbIndex) {
		Connection con = JDBCConnector.getCon();
		
		String sql = "";
		
		switch (tbIndex) {
		
		case 0:
			//ingredient
			sql = "delete from ingredient where ing_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getIngId()); //
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 1:
			//inventory
			sql = "delete from inventory where ing_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getIngId()); //
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 2:
			//menu
			sql = "delete from menu where menu_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getMenuId()); //
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 3:
			//menu_type
			sql = "delete from menu_type where type_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, vo.getMenuType()); //
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 4:
			//order
			sql = "delete from order where order_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getOrderId()); //
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 5:
			//recipe
			sql = "delete from recipe where recipe_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, vo.getRecipeId()); //
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 6:
			//sell
			sql = "delete from sell where sell_id = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vo.getSellId()); //
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
		} //switch end
		
		//finally
		try {
			if(pstmt != null) {
				pstmt.close();
			}
			
			if(con != null) {
				con.close();
			}
		}catch (SQLException e) {
			System.out.println("delete close할 때 문제 발생");
		}
		
	} //delete end
	
	
}
