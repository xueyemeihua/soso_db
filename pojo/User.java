package pojo;

public class User {
    //static Scanner sc = new Scanner(System.in);
    private int uid;
    private String username;
    private String pwd;
    private String name;
    private String phone;
    private String cardid;
    private double money;
    private int points;
    public User() {
    }
    public User(int uid, String username, String pwd, String name, String phone, String cardid, double money, int points) {
        this.uid = uid;
        this.username = username;
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
        this.cardid = cardid;
        this.money = money;
        this.points = points;
    }
    @Override
    public String toString() {
        return username+
                "用户信息{" +
                "uid=" + uid +
                ", 用户名='" + username + '\'' +
                ", 密码密文='" + pwd + '\'' +
                ", 姓名='" + name + '\'' +
                ", 联系方式='" + phone + '\'' +
                ", 身份证号码='" + cardid + '\'' +"\n"+
                ", 余额=" + money +
                ", 积分=" + points+
                '}'+"\n";
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public int getUid() {
        return uid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
    }
}
