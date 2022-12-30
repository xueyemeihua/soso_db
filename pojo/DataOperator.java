package pojo;

import utils.JDBCUtil;
import utils.MD5Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * 有关业务的方法
 */
public class DataOperator {

	//显示所有用户信息
	public void showAllUser() {
		List<User> users = getAllUser();
		if(users != null) {
			for (User u : users) {
				System.out.println(u);
			}
		}
	}
	//从数据库获取所有用户并且实例化为用户集合
	public List<User> getAllUser() {
		List<User> users = null;
		Connection conn = JDBCUtil.getConnection();
		String sql = "SELECT * FROM `user`";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			// 遍历ResultSet创建对象
			users = new ArrayList<User>();
			while (rs.next()) {
				int id = rs.getInt("uid");
				String uname = rs.getString("username");
				String upwd = rs.getString("pwd");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String cardID = rs.getString("cardid");
				double money = rs.getDouble("money");
				int points = rs.getInt("points");
				if (!uname.equals("admin")) {
					User u = new User(id, uname, upwd, name, phone, cardID,
							money, points);
					users.add(u);
				}
			}
			JDBCUtil.close(statement, conn);
		} catch (Exception e) {
			System.out.println("查询失败");
		}
		return users;
	}
	// 添加新业务
	public void addNewBusiness() {
		System.out.println("请输入业务标题:");
		String title = Initialization.sc.next();
		System.out.println("请输入业务描述:");
		String describe = Initialization.sc.next();
		System.out.println("请输入业务价格:");
		try {
			double price = Initialization.sc.nextDouble();
			Business business = new Business(0, title, describe, price, 0);
			Connection conn = JDBCUtil.getConnection();
			String sql = "INSERT INTO `soso_db`.`Business` (`businessTitle`, `businessInfo`, `businessPrice`, `businessCondition`) VALUES ( ?, ?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, business.getBusinessTitle());
			statement.setString(2, business.getBusinessInfo());
			statement.setDouble(3, business.getBusinessPrice());
			statement.setInt(4, business.getBusinessCondition());
			int i = statement.executeUpdate();
			System.out.println(i);
			JDBCUtil.close(statement, conn);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("业务价格输入格式错误,请重新操作");
			Initialization.sc.next();
			addNewBusiness();
		}
	}
	// 查看所有业务
	public void showAllBusiness() {
		List<Business> busis = getAllBusiness();
		if(busis != null) {
			for (Business bus : busis) {
				System.out.println(bus);
			}
		}
	}
	//从数据库获取所有业务并且实例化为业务对象集合
	public List<Business> getAllBusiness() {
		List<Business> busis = null;
		Connection conn = JDBCUtil.getConnection();
		String sql = "SELECT * FROM `business`";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			// 遍历ResultSet创建对象
			busis = new ArrayList<Business>();
			while (rs.next()) {
				int id = rs.getInt("businessId");
				String title = rs.getString("businessTitle");
				String info = rs.getString("businessInfo");
				double price = rs.getDouble("businessPrice");
				int condi = rs.getInt("businessCondition");
				Business bus = new Business(id, title, info, price, condi);
				busis.add(bus);
			}
			JDBCUtil.close(statement, conn);
		} catch (Exception e) {
			System.out.println("查询失败");
		}
		return busis;
	}
	// 查看下架业务
	public void showUnPublishedBusiness() {
		List<Business> busis = getUnPublishedBusiness();
		if(busis != null) {
			for (Business bus : busis) {
				System.out.println(bus);
			}
		}
	}
	//从数据库获取下架业务并且实例化为业务对象集合
	public List<Business> getUnPublishedBusiness() {
		List<Business> busis = null;
		Connection conn = JDBCUtil.getConnection();
		String sql = "SELECT * FROM `business` WHERE businessCondition=0";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			// 遍历ResultSet创建对象
			busis = new ArrayList<Business>();
			while (rs.next()) {
				int id = rs.getInt("businessId");
				String title = rs.getString("businessTitle");
				String info = rs.getString("businessInfo");
				double price = rs.getDouble("businessPrice");
				int condi = rs.getInt("businessCondition");
				Business bus = new Business(id, title, info, price, condi);
				busis.add(bus);
			}
			JDBCUtil.close(statement, conn);

		} catch (Exception e) {
			System.out.println("查询失败");
		}
		return busis;
	}
	// 查看上架中业务
	public void showPublishedBusiness() {
		List<Business> busis = getPublishedBusiness();
		if(busis != null) {
			for (Business bus : busis) {
				System.out.println(bus);
			}
		}
	}
	//从数据库获取上架业务并且实例化为业务对象集合
	public List<Business> getPublishedBusiness() {
		List<Business> busis = null;
		Connection conn = JDBCUtil.getConnection();
		String sql = "SELECT * FROM `business` WHERE businessCondition=1";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			// 遍历ResultSet创建对象
			busis = new ArrayList<Business>();
			while (rs.next()) {
				int id = rs.getInt("businessId");
				String title = rs.getString("businessTitle");
				String info = rs.getString("businessInfo");
				double price = rs.getDouble("businessPrice");
				int condi = rs.getInt("businessCondition");
				Business bus = new Business(id, title, info, price, condi);
				busis.add(bus);
			}
			JDBCUtil.close(statement, conn);
		} catch (Exception e) {
			System.out.println("查询失败");
		}
		return busis;
	}
	//更新业务价格
	public void updatePrice() {
		showAllBusiness();
		System.out.println("选择要修改价格的业务编号");
		try {
			int cbid = Initialization.sc.nextInt();
			System.out.println("请输入新价格:");
			double newPrice = Initialization.sc.nextDouble();
			Business bus = searchByIDbus(cbid);
			if (bus != null) {
				Connection conn = JDBCUtil.getConnection();
				String sql = "UPDATE business SET businessPrice = ? WHERE businessId=?";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setDouble(1, newPrice);
				statement.setInt(2, cbid);
				int i = statement.executeUpdate();
				System.out.println(i);
				JDBCUtil.close(statement, conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("业务编号或者价格输入格式错误,请重新输入");
			Initialization.sc.next();
			Initialization.sc.next();
			updatePrice();
		}
	}
	// 通过业务编号查询业务,并返回业务对象
	public Business searchByIDbus(int bid) throws Exception {
		Business bus = null;
		Connection conn = JDBCUtil.getConnection();
		String sql = "select * from business where businessId=?";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, bid);
		ResultSet rs = statement.executeQuery();
		if (rs.next()) {
			int busId = rs.getInt("businessId");
			String busTitle = rs.getString("businessTitle");
			String busInfo = rs.getString("businessInfo");
			double busPrice = rs.getDouble("businessPrice");
			int busCondi = rs.getInt("businessCondition");
			bus = new Business(busId, busTitle, busInfo, busPrice, busCondi);
		}
		JDBCUtil.close(statement, conn);
		return bus;
	}
	// 上架业务
	public void publishBusiness() {
		showUnPublishedBusiness();
		System.out.println("请选择即将发布的业务编号:");
		try {
			int pbid = Initialization.sc.nextInt();
			Connection conn = JDBCUtil.getConnection();
			String sql = "SELECT * FROM `business` where businessCondition='0'";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			// 遍历ResultSet创建对象
			int flag = 0;
			while (rs.next()) {
				int id = rs.getInt("businessId");
				if (id == pbid) {
					flag = 1;
				}
			}
			JDBCUtil.close(statement, conn);
			if (flag == 1) {
				Connection conn1 = JDBCUtil.getConnection();
				String sql1 = "UPDATE business SET businessCondition=1 WHERE businessId=?";
				PreparedStatement statement1 = conn1.prepareStatement(sql1);
				statement1.setInt(1, pbid);
				int i = statement1.executeUpdate();
				System.out.println(i);
				JDBCUtil.close(statement1, conn1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("业务编码格式错误,请重新输入");
			Initialization.sc.next();
			publishBusiness();
		}
	}
	// 下架业务
	public void removeBusiness() {
		showPublishedBusiness();
		System.out.println("选择即将下架业务的编号:");
		try {
			int ubid = Initialization.sc.nextInt();
			Connection conn = JDBCUtil.getConnection();
			String sql = "SELECT * FROM `business` where businessCondition='1'";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			// 遍历ResultSet创建对象
			int flag = 0;
			while (rs.next()) {
				int id = rs.getInt("businessId");
				if (id == ubid) {
					flag = 1;
				}
			}
			JDBCUtil.close(statement, conn);
			if (flag == 1) {
				Connection conn1 = JDBCUtil.getConnection();
				String sql1 = "UPDATE business SET businessCondition=0 WHERE businessId=?";
				PreparedStatement statement1 = conn1.prepareStatement(sql1);
				statement1.setInt(1, ubid);
				int i = statement1.executeUpdate();
				System.out.println(i);
				JDBCUtil.close(statement1, conn1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("业务编号格式错误,请重新输入");
			Initialization.sc.next();
			removeBusiness();
		}
	}
	// 查看个人业务
	public void showMyBusiness() {
		List<HashMap<String, Object>> blist = getMyBusiness(Initialization.logier
				.getUid());
		if (blist != null) {
			for (HashMap<String, Object> hashMap : blist) {
				System.out.println(hashMap);
			}
		} else {
			System.out.println("没有业务");
		}
	}
	// 获取个人业务
	public List<HashMap<String, Object>> getMyBusiness(int bid) {
		List<HashMap<String, Object>> blist = null;
		Connection conn = JDBCUtil.getConnection();
		String sql = "SELECT business.* FROM uabtab LEFT JOIN business ON uabtab.businessId=business.businessId WHERE uabtab.uid=?";
		try {
			blist = new ArrayList<HashMap<String, Object>>();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, bid);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("businessId", rs.getInt("businessId"));
				map.put("businessTitle", rs.getString("businessTitle"));
				map.put("businessInfo", rs.getString("businessInfo"));
				map.put("businessPrice", rs.getDouble("businessPrice"));
				map.put("businessCondition", rs.getInt("businessCondition"));
				blist.add(map);
			}
			JDBCUtil.close(statement, conn);
		} catch (Exception e) {
			System.out.println("从数据库获取业务失败");
		}
		return blist;
	}
	// 终止业务:展示用户的业务列表,用户输入编号,判断是否编号是否合理,删除业务
	public void endBusiness() {
		List<HashMap<String, Object>> busis = getMyBusiness(Initialization.logier
				.getUid());
		for (HashMap<String, Object> bus : busis) {
			System.out.println(bus);
		}
		System.out.println("请输入需要终止的业务编号:");
		try {
			int ebid = Initialization.sc.nextInt();
			int flag = 0;
			for (HashMap<String, Object> bus : busis) {
				//遍历我的业务集合获取业务编号
				int bid = (int)bus.get("businessId");
				if(bid == ebid){
					//进来则说明我的业务中有用户正在终止的业务
					flag = 1;
				}
			}
			if(flag==1){
				Connection conn = JDBCUtil.getConnection();
				String sql = "Delete FROM uabtab WHERE uid=? AND businessId=?";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, Initialization.logier.getUid());
				statement.setInt(2, ebid);
				int i = statement.executeUpdate();
				System.out.println(i);
				System.out.println("从数据库终止业务成功");
				JDBCUtil.close(statement, conn);
			} else {
				System.out.println("编号不存在,终止业务失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("业务编号格式错误,请重新输入");
			Initialization.sc.next();
			endBusiness();
		}
	}
	/*
	 * 办理业务:展示可办理业务列表,用户输入业务编号 判断可办理的业务编号是否合理,若存在判断余额是否充足,充足则将该业务添加进我的业务列表,更新余额
	 */
	public void buyBusiness() {
		showPublishedBusiness();
		System.out.println("请输入需要办理的业务编号:");
		try {
			int bbid = Initialization.sc.nextInt();
			// 循环的同时取出该业务对象
			Business bBus = null;
			// 全局变量使用类名调用
			List<Business> blist = getPublishedBusiness();
			for (Business business : blist) {
				if (business.getBusinessId() == bbid
						&& business.getBusinessCondition() == 1) {
					bBus = business;
				}
			}
			if (bBus == null) {
				System.out.println("业务编号不合理,请重新操作");
				Index.skip();
			} else {
				// 判断余额是否充足
				if ((Initialization.logier.getMoney() - bBus.getBusinessPrice()) > 0) {
					// 进来则余额充足
					double rmoney = Initialization.logier.getMoney()
							- bBus.getBusinessPrice();
					int rpoints = (int) bBus.getBusinessPrice() * 10
							+ Initialization.logier.getPoints();
					Connection conn1 = JDBCUtil.getConnection();
					String sql1 = "UPDATE `user` SET `money`=?,`points`=? WHERE (`uid`=?)";
					PreparedStatement statement1 = conn1.prepareStatement(sql1);
					statement1.setDouble(1, rmoney);
					statement1.setInt(2, rpoints);
					statement1.setInt(3, Initialization.logier.getUid());
					int i1 = statement1.executeUpdate();
					System.out.println(i1);
					JDBCUtil.close(statement1, conn1);
					Connection conn2 = JDBCUtil.getConnection();
					String sql2 = "INSERT INTO `uabtab` (`uid`, `businessId`) VALUES (?, ?)";
					PreparedStatement statement2 = conn2.prepareStatement(sql2);
					statement2.setInt(1, Initialization.logier.getUid());
					statement2.setInt(2, bbid);
					int i2 = statement2.executeUpdate();
					System.out.println(i2);
					JDBCUtil.close(statement2, conn2);
					Initialization.logier.setMoney(rmoney);
					Initialization.logier.setPoints(rpoints);
					System.out.println("添加购买业务成功");
					Index.skip();
				} else {
					System.out.println("余额不足,购买失败");
					Index.skip();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("业务编号格式错误,请重新输入");
			Initialization.sc.next();
			buyBusiness();
		}
	}
	// 充值
	public void addMoney() {
		try {
			System.out.println("请输入你要充值的金额:");
			double money = Initialization.sc.nextDouble();
			double rMoney = Initialization.logier.getMoney() + money;
			Connection conn = JDBCUtil.getConnection();
			String sql = "UPDATE `user` SET `money`=? WHERE (`uid`=?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setDouble(1, rMoney);
			statement.setInt(2, Initialization.logier.getUid());
			int i = statement.executeUpdate();
			System.out.println(i);
			//更新全局变量
			Initialization.logier.setMoney(rMoney);
			System.out.println("充值成功");
			JDBCUtil.close(statement, conn);
			Index.userView();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("输入金额格式错误,请重新输入");
			Initialization.sc.next();
			addMoney();
		}
		
	}
	//修改密码
	public void updatePwd() {
		System.out.println("请输入新的密码:");
		String pwd1 = Initialization.sc.next();
		System.out.println("请再次输入新的密码:");
		String pwd2 = Initialization.sc.next();
		if (pwd1.length() < 6) {
			System.out.println("密码应该大于5位,修改失败");
			Index.skip();
		} else {
			if (pwd1.equals(pwd2)) {
				String newPwd = MD5Util.getMD5(pwd1);
				try {
					Connection conn = JDBCUtil.getConnection();
					String sql = "UPDATE `user` SET `pwd`=? WHERE (`uid`=?)";
					PreparedStatement statement = conn.prepareStatement(sql);
					statement.setString(1, newPwd);
					statement.setInt(2, Initialization.logier.getUid());
					int i = statement.executeUpdate();
					System.out.println(i);
					Initialization.logier.setPwd(newPwd);
					System.out.println("密码修改成功");
					JDBCUtil.close(statement, conn);
					Index.skip();
				} catch (Exception e) {
					System.out.println("新密码写入数据库失败");
				}
			} else {
				System.out.println("两次密码不一致");
				Index.skip();
			}
		}
	}
	// 展示个人信息
	public void showMyInfo() {
		HashMap map = getUser(Initialization.logier.getUid());
		System.out.println(map);
	}
	public HashMap getUser(int uid) {
		HashMap u = null;
		try {
			Connection conn = JDBCUtil.getConnection();
			String sql = "SELECT * FROM `user` WHERE uid=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, uid);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getInt("uid"));
				u = new HashMap();
				u.put("uid", rs.getInt("uid"));
				u.put("username", rs.getString("username"));
				u.put("pwd", rs.getString("pwd"));
				u.put("name", rs.getString("name"));
				u.put("phone", rs.getString("phone"));
				u.put("cardid", rs.getString("cardid"));
				u.put("money", rs.getDouble("money"));
				u.put("points", rs.getInt("points"));
			}
			JDBCUtil.close(statement, conn);
			return u;
		} catch (Exception e) {
			System.out.println("获取失败");
		}
		return u;
	}
	// 修改个人信息
	public void updataMyInfo() {
		try {
			System.out.println("请输入你的姓名:");
			String myname = Initialization.sc.next();
			System.out.println("请输入你的联系方式:");
			String myphone = Initialization.sc.next();
			System.out.println("请输入你的身份证号:");
			String mycardid = Initialization.sc.next();
			Connection conn = JDBCUtil.getConnection();
			String sql = "UPDATE user SET name=?,phone=?,cardid=? WHERE uid=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, myname);
			statement.setString(2, myphone);
			statement.setString(3, mycardid);
			statement.setInt(4, Initialization.logier.getUid());
			int i = statement.executeUpdate();
			System.out.println(i);
			JDBCUtil.close(statement, conn);
			System.out.println("修改个人信息成功");
		} catch (Exception e) {
			System.out.println("修改失败");
		}
	}
	/* 
	 * 充值积分
	 * 使用用户余额充值积分,1:10
	 */
	public void addPoints() {
		System.out.println("请输入你要花费的金额:");
		try {
			double cmoney = Initialization.sc.nextDouble();// 冲积分的钱
			double myMoney = Initialization.logier.getMoney();// 账户余额
			if (myMoney < cmoney) {
				System.out.println("您的余额不足,请重新输入或者充值后再冲积分");
			} else {
				int rPoints = (int) (cmoney * 10)+Initialization.logier.getPoints();// 充值后的积分
				double rmoney = myMoney - cmoney;//充值后的账户余额
				Connection conn = JDBCUtil.getConnection();
				String sql = "UPDATE user SET money=?,points=? WHERE uid=?";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setDouble(1, rmoney);
				statement.setInt(2, rPoints);
				statement.setInt(3, Initialization.logier.getUid());
				int i = statement.executeUpdate();
				System.out.println(i);
				Initialization.logier.setMoney(rmoney);
				Initialization.logier.setPoints(rPoints);
				JDBCUtil.close(statement, conn);
				System.out.println("充值成功");
				Index.userView();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("输入花费金额格式错误,请重新输入");
			Initialization.sc.next();
			addPoints();
		}
	}
}
