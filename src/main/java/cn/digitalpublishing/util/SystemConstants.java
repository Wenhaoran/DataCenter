package cn.digitalpublishing.util;

public class SystemConstants {

	/**
	 * 系统状态					：1-在用 2-停用 3-废弃
	 * 组件状态					：1-有效 2-无效
	 * 模块是否必须					：1-是 2-否
	 * 账户状态					：1-在用 2-停用
	 * 账户类型					：1-本地账户 2-Sina账户
	 * 账户级别					：1-超级用户（不能维护不能删除） 2-普通用户（可维护可删除）
	 * 角色状态					：1-再用 2-停用
	 * 资源状态					：1-在用 2-停用
	 * Tree节点					：1-根节点 2-非根节点
	 */

	/**
	 * 系统状态					：1-在用 2-停用 3-废弃
	 */
	public static final int SYSTEM_STATUS_USING = 1;
	public static final int SYSTEM_STATUS_USELESS = 2;
	public static final int SYSTEM_STATUS_DEPRECATED = 3;

	/**
	 * 组件状态					：1-有效 2-无效
	 */
	public static final int COMPONENT_STATUS_USING = 1;
	public static final int COMPONENT_STATUS_USELESS = 2;

	/**
	 * 模块是否必须					：1-是 2-否
	 */
	public static final int MODULE_MUST_YES = 1;
	public static final int MODULE_MUST_NO = 2;

	/**
	 * 账户状态					：1-在用 2-停用
	 */
	public static final int ACCOUNT_STATUS_USING = 1;
	public static final int ACCOUNT_STATUS_USELESS = 2;

	/**
	 * 账户类型					：1-本地账户 2-Sina账户
	 */
	public static final int ACCOUNT_TYPE_LOCAL = 1;
	public static final int ACCOUNT_TYPE_SINA = 2;

	/**
	 * 账户级别					：1-超级用户（不能维护不能删除） 2-普通用户（可维护可删除）
	 */
	public static final int ACCOUNT_LEVEL_ADMIN = 1;
	public static final int ACCOUNT_LEVEL_USER = 2;
	
	/**
	 * 角色状态					：1-在用 2-停用
	 */
	public static final int ROLE_STATUS_USED = 1;
	public static final int ROLE_STATUS_USELESS = 2;
	
	/**
	 * 资源状态					：1-在用 2-停用
	 */
	public static final int RESOURCE_STATUS_USED = 1;
	public static final int RESOURCE_STATUS_USELESS = 2;
	
	/**
	 * Tree节点					：1-根节点 2-非根节点
	 */
	public static final String TREE_ROOT = "1";
	public static final String TREE_NOT_ROOT = "2";
	
	public static final String REQUEST_METCHOD_POST = "POST";
	public static final String REQUEST_METCHOD_GET = "GET";
	
}
