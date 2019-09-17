package com.hewentian.crawler.entity;

/**
 * @Description App实体类
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2014-7-22 下午06:28:47
 * @version 1.0
 * @since JDK 1.7
 */
public class App {
	/** 在app所属网站中的唯一标识 */
	private String id;

	/** 来自哪家网站 */
	private String from;

	/** 目录分类 */
	private String category;

	/** app的名称 */
	private String name;

	/** app的连接 */
	private String url;

	/** app截图的URL */
	private String imgUrl;

	/** 游戏开发商 */
	private String gameFactory;

	/** 是否免费，0为免费，1为收费 */
	private int isFree;

	/** app的说明 */
	private String description;

	/** app所属分类 */
	private String type;

	/** app的最近更新时间 */
	private String updateTime;

	/** app的大小，单位：M */
	private double size;

	/** app的安装数 */
	private String installNum;

	/** app的版本 */
	private String version;

	/** app的系统版本要求 */
	private String systemRequire;

	/** 评论数 */
	private Integer commentNum = 0;

	/** 评论得分, appStore无此属性 */
	private float score;

	/** 语言, google play无此属性 */
	private String language;

	/** 年龄限制，google play无此属性 */
	private String ageLimit;

	/** app添加时间 */
	private Long createAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getGameFactory() {
		return gameFactory;
	}

	public void setGameFactory(String gameFactory) {
		this.gameFactory = gameFactory;
	}

	public int getIsFree() {
		return isFree;
	}

	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getInstallNum() {
		return installNum;
	}

	public void setInstallNum(String installNum) {
		this.installNum = installNum;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSystemRequire() {
		return systemRequire;
	}

	public void setSystemRequire(String systemRequire) {
		this.systemRequire = systemRequire;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAgeLimit() {
		return ageLimit;
	}

	public void setAgeLimit(String ageLimit) {
		this.ageLimit = ageLimit;
	}

	public Long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Long createAt) {
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\",\"from\":\"" + from + "\",\"category\":\"" + category
				+ "\",\"name\":\"" + name + "\",\"url\":\"" + url + "\",\"imgUrl\":\"" + imgUrl
				+ "\",\"gameFactory\":\"" + gameFactory + "\",\"isFree\":" + isFree
				+ ",\"description\":\"" + description + "\",\"type\":\"" + type
				+ "\",\"updateTime\":\"" + updateTime + "\",\"size\":" + size
				+ ",\"installNum\":\"" + installNum + "\",\"version\":\"" + version
				+ "\",\"systemRequire\":\"" + systemRequire + "\",\"commentNum\":" + commentNum
				+ ",\"score\":" + score + ",\"language\":" + language + ",\"ageLimit\":" + ageLimit
				+ ",\"createAt\":" + createAt + "}";
	}
}