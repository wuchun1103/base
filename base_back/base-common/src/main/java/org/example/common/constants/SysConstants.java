package org.example.common.constants;

/**
 * 常量管理
 */
public interface SysConstants {

	/**
	 * 系统管理员用户名
	 */
	String ADMIN = "admin";

	//token is out time
	String TokenExpire = "TOKENEXIPRE";



	//shiro的session在redis中常量前缀
	String REDIS_SESSION_PRE="FP:SESSION:";

    //存储的SESSIONKEY
    String SESSIONKEY="SHIRO_SESSION";
    String SESSIONKEYRIGHT="SHIRO_SESSION_RIGHT";




	
}
