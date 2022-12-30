package pojo;

public class Business {
    private int businessId;//业务编号
    private String businessTitle;//业务标题
    private String businessInfo;//业务描述
    private double businessPrice;//业务价格
    private int businessCondition;//业务状态
    public Business(int businessId, String businessTitle, String businessInfo, double businessPrice, int businessCondition) {
        this.businessId = businessId;
        this.businessTitle = businessTitle;
        this.businessInfo = businessInfo;
        this.businessPrice = businessPrice;
        this.businessCondition = businessCondition;
    }
    public int getBusinessId() {
        return businessId;
    }
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }
//管理员添加业务使用的是构造方法,假如需要添加修改业务信息的功能,这些方法可以用上
    public String getBusinessTitle() {
        return businessTitle;
    }
    public String getBusinessInfo() {
        return businessInfo;
    }
    public double getBusinessPrice() {
        return businessPrice;
    }
    public int getBusinessCondition() {
        return businessCondition;
    }
    @Override
    public String toString() {
        return "Business{" +
                "businessId=" + businessId +
                ", businessTitle='" + businessTitle + '\'' +
                ", businessInfo='" + businessInfo + '\'' +
                ", businessPrice=" + businessPrice +
                ", businessCondition=" + businessCondition +
                '}'+"\n";
    }
    public String printUserBusiness() {
        return Initialization.logier.getUsername()+"的业务{" +
                "业务编号:" + businessId +
                ", 业务名:'" + businessTitle + '\'' +
                ", 业务内容:'" + businessInfo + '\'' +
                ", 业务价格:" + businessPrice +
                '}'+"\n";
    }
}
