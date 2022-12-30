package pojo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import utils.MD5Util;
import utils.JDBCUtil;

public class Index {
    public static void index() {
        System.out.println("欢迎来到嗖嗖移动大厅");
        System.out.println("请选择:");
        System.out.println("1:注册");
        System.out.println("2:登录");
        System.out.println("0:退出程序");
        System.out.print("请输入:");
        String num = Initialization.sc.next();
        switch (num) {
            case "1":
                register();
                break;
            case "2":
                login();
                break;
            case "0":
                System.out.println("欢迎下次再来");
                System.exit(0);
            default:
                System.out.println("输入错误");
                index();
                break;
        }
    }
    public static String yanzhengma() {
        String random = Math.random() + "";
        String str = random.substring(random.length() - 4);
        System.out.println(str);
        return str;
    }
    public static void register() {
        System.out.println("请输入用户名:");
        String myname = Initialization.sc.next();
        System.out.println("请输入密码:");
        String mypwd1 = Initialization.sc.next();
        System.out.println("请再次输入密码:");
        String mypwd2 = Initialization.sc.next();
        System.out.println("请输入验证码:");
        String str = yanzhengma();
        String yanzhengma = Initialization.sc.next();
        //从数据库实例化所有用户对象集合
        List<User> users = Initialization.dataOperator.getAllUser();
        int flag = 0;
        for (User u :
                users) {
            //遍历users查找有没有和输入的用户名相同的对象
            if (u.getUsername().equals(myname)) {
                //进来则有相同用户
                flag = 1;
                System.out.println("用户名相同,注册失败");
                break;
            }
        }
        if (flag == 1) {
            //用户名相同,重新注册
            index();
        } else {
            if (myname.length() < 10 && myname.length() > 4 && mypwd1.length() > 5) {
                //进来表示用户名符合规范
                if (mypwd1.equals(mypwd2)) {
                    //进来表示两次密码输入一致
                    if (yanzhengma.equals(str)) {
                        //用户设置的密码先转成密文再存储
                        mypwd1 = MD5Util.getMD5(mypwd1);
                        //初始化一个新的用户对象存入用户集合
                        User user = new User();
                        user.setUsername(myname);
                        user.setPwd(mypwd1);
                        user.setMoney(0.0);
                        user.setPoints(10);
                        //获取连接对象
                    	Connection conn = JDBCUtil.getConnection();
                    	//编写sql语句
                        String sql = "INSERT INTO `soso_db`.`user`(username,pwd,money,points) VALUES (?, ?, ?, ?)";
                        try{
                        	PreparedStatement statement = conn.prepareStatement(sql);
                        	statement.setString(1, user.getUsername());
                        	statement.setString(2, user.getPwd());
                        	statement.setDouble(3, user.getMoney());
                        	statement.setInt(4, user.getPoints());
                        	int i = statement.executeUpdate();
                        	System.out.println(i);
                        	JDBCUtil.close(statement, conn);
                        }catch(Exception e){
                        	System.out.println("注册失败");
                        }
                        System.out.println("注册成功");
                        index();
                    } else {
                        System.out.println("验证码错误");
                        index();
                    }
                } else {
                    System.out.println("密码输入不一致");
                    index();
                }
            } else {
                System.out.println("用户名不合规范,4-10位,或者密码小于6位");
                index();
            }
        }
    }
    public static void login() {
        System.out.println("请输入用户名:");
        String myname = Initialization.sc.next();
        System.out.println("请输入密码:");
        String mypwd = Initialization.sc.next();
        String md5pwd = MD5Util.getMD5(mypwd);
        System.out.println("请输入验证码:");
        String str = yanzhengma();
        String yanzhengma = Initialization.sc.next();
        
        //创建user对象接受查询到的数据
        User u = null;
        Connection conn = JDBCUtil.getConnection();
        String sql = "SELECT * FROM `user`";
        try{
        	Statement statement = conn.createStatement();
        	ResultSet rs = statement.executeQuery(sql);
        	while (rs.next()) {
        		int uid = rs.getInt("uid");
				String uname = rs.getString("username");
				String upwd = rs.getString("pwd");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String cardId = rs.getString("cardid");
				double money = rs.getDouble("money");
				int points = rs.getInt("points");
				if(uname.equals(myname)&&upwd.equals(md5pwd)){
					u = new User(uid, uname, upwd, name, phone, cardId, money, points);
					System.out.println("登录成功");
				}
			}
        }catch(Exception e){
        	System.out.println("登录失败");
        }
        if (u!=null) {
            //注意,用户输入的密码需要先转成密文再比较
            if (str.equals(yanzhengma)) {
            	Initialization.logier = u;
                System.out.println("登录成功");
                skip();
            } else {
                System.out.println("验证码错误");
                index();
            }
        } else {
            System.out.println("错误的用户名或密码");
            index();
        }
    }
    //判断是管理员还是普通用户,跳转不同界面
    public static void skip() {
        if (Initialization.logier.getUsername().equals("admin")) {
            adminView();
        } else {
            userView();
        }
    }
    public static void userView() {
        System.out.println("欢迎你:" + Initialization.logier.getUsername());
        System.out.println("1:查看个人信息");
        System.out.println("2:更新密码");
        System.out.println("3:充值");
        System.out.println("4:显示个人业务");
        System.out.println("5:业务大厅");
        System.out.println("6:办理业务");
        System.out.println("7:终止业务");
        System.out.println("8:修改个人信息");
        System.out.println("9:充值积分");
        System.out.println("10:返回主页");
        String num = Initialization.sc.next();
        DataOperator dataOperator = new DataOperator();
        switch (num) {
            case "1":
                dataOperator.showMyInfo();
                userView();
                break;
            case "2":
                dataOperator.updatePwd();
                userView();
                break;
            case "3":
                dataOperator.addMoney();
                userView();
                break;
            case "4":
                dataOperator.showMyBusiness();
                userView();
                break;
            case "5":
                dataOperator.showPublishedBusiness();
                userView();
                break;
            case "6":
                dataOperator.buyBusiness();
                userView();
                break;
            case "7":
                dataOperator.endBusiness();
                userView();
                break;
            case "8" :
                dataOperator.updataMyInfo();
                userView();
                break;
            case "9":
                dataOperator.addPoints();
                userView();
                break;
            case "10":
                index();
                break;
            default:
                System.out.println("欢迎下次再来");
                System.exit(0);
        }
    }
    public static void adminView() {
        System.out.println("欢迎你:管理员");
        System.out.println("1:查看用户");
        System.out.println("2:查看已发布业务");
        System.out.println("3:添加业务");
        System.out.println("4:下架业务");
        System.out.println("5:上架业务");
        System.out.println("6:查看未发布业务");
        System.out.println("7:修改业务价格");
        System.out.println("8:返回主页");
        System.out.println("9:查看所有业务");
        System.out.println("请选择要办理的业务");
        String num = Initialization.sc.next();
        switch (num) {
            case "1":
                Initialization.dataOperator.showAllUser();
                adminView();
                break;
            case "2":
                Initialization.dataOperator.showPublishedBusiness();
                adminView();
                break;
            case "3":
                Initialization.dataOperator.addNewBusiness();
                adminView();
                break;
            case "4":
                Initialization.dataOperator.removeBusiness();
                adminView();
                break;
            case "5":
                Initialization.dataOperator.publishBusiness();
                adminView();
                break;
            case "6":
                Initialization.dataOperator.showUnPublishedBusiness();
                adminView();
                break;
            case "7":
                Initialization.dataOperator.updatePrice();
                adminView();
                break;
            case "8":
                index();
                break;
            case "9":
            	Initialization.dataOperator.showAllBusiness();
            	adminView();
            	break;
            default:
                System.exit(0);
        }
    }
}

