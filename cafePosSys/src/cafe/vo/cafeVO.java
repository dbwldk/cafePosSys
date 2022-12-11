package cafe.vo;

public class cafeVO {
	//ingredientVO
	private String ingId; //영어 대문자로
	private String ingName; //한글 이름
	private String EXP; //유통기한(yyyy.mm.dd)
	
	//inventoryVO
	private int ingCnt; //재료 재고
	
	//menuTypeVO
	private int menuType; //auto increase
	private String typeName; //한글 이름
	
	//menuVO
	private String menuId; //_ 미포함 4글자, 영어 대문자
	private String menuName; //한글 이름
	
	private boolean soldOutCheck; //품절 여부(0: 품절, 1: 판매중)
	
	//orderVO
	private String orderId; //날짜_재료(ex. 20221212_BEAN) 형식
	private String orderTime; //발주 시각(현재 시간 받아서 문자열로 바꾼 후 저장)(yyyy.mm.dd hh:mm:ss)
	private int orderCnt; //발주(재료) 개수
	
	//recipeVO
	private int recipeId; //auto increase
	
	//sellCO
	private String sellId; //날짜_시간_메뉴(ex. 20221212_120009_CROF) 형식
	private String sellTime; //주문 시각(현재 시간 받아서 문자열로 바꾼 후 저장)(yyyy.mm.dd hh:mm:ss)
	private int sellCnt; //주문(메뉴) 개수
	private int total; //주문 총액
	
	//getters & setters
	public String getIngId() {
		return ingId;
	}
	public void setIngId(String ingId) {
		this.ingId = ingId;
	}
	
	public String getIngName() {
		return ingName;
	}
	public void setIngName(String ingName) {
		this.ingName = ingName;
	}
	
	public String getEXP() {
		return EXP;
	}
	public void setEXP(String eXP) {
		EXP = eXP;
	}
	
	
	public int getIngCnt() {
		return ingCnt;
	}
	public void setIngCnt(int ingCnt) {
		this.ingCnt = ingCnt;
	}
	
	
	public int getMenuType() {
		return menuType;
	}
	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public boolean isSoldOutCheck() {
		return soldOutCheck;
	}
	public void setSoldOutCheck(boolean soldOutCheck) {
		this.soldOutCheck = soldOutCheck;
	}
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
	public int getOrderCnt() {
		return orderCnt;
	}
	public void setOrderCnt(int orderCnt) {
		this.orderCnt = orderCnt;
	}
	
	
	public int getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	
	
	public String getSellId() {
		return sellId;
	}
	public void setSellId(String sellId) {
		this.sellId = sellId;
	}
	
	public String getSellTime() {
		return sellTime;
	}
	public void setSellTime(String sellTime) {
		this.sellTime = sellTime;
	}
	
	public int getSellCnt() {
		return sellCnt;
	}
	public void setSellCnt(int sellCnt) {
		this.sellCnt = sellCnt;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
