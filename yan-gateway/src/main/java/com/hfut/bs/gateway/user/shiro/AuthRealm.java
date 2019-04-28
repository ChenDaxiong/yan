package com.hfut.bs.gateway.user.shiro;

import com.hfut.bs.common.storage.QiniuStorage;
import com.hfut.bs.common.storage.ThumbModel;
import com.hfut.bs.gateway.utils.SessionContext;
import com.hfut.bs.user.model.UserInfoModel;
import com.hfut.bs.user.service.IAuthUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * shiro实现用户登陆；
 * 各个模块表不一样，各自处理；
 */
public class AuthRealm extends AuthorizingRealm {
	
	@Autowired
	private IAuthUserService authUserService;
	
	/**
	 * 实现用户登陆
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		String username = token.getUsername();
		String password = String.valueOf(token.getPassword());
		UserInfoModel authUser = null;
		/**
		 * 业务代码-start
		 */
		try {
			UserInfoModel tmpAuthUser = new UserInfoModel();
			tmpAuthUser.setUsername(username);
			tmpAuthUser.setPassword(password);
			
			tmpAuthUser = authUserService.getByUsernameAndPassword(tmpAuthUser);
			if(null != tmpAuthUser){
				authUser = new UserInfoModel();
				authUser.setId(tmpAuthUser.getId());
				authUser.setRealname(tmpAuthUser.getRealname());
				authUser.setUsername(tmpAuthUser.getUsername());
				authUser.setStatus(tmpAuthUser.getStatus());
				if(!StringUtils.isBlank(tmpAuthUser.getHeader())){
					authUser.setHeader(QiniuStorage.getUrl(tmpAuthUser.getHeader(), ThumbModel.THUMB_256));//设置头像
				}
			}else{
				throw new AuthenticationException("## user password is not correct! ");
			}
		} catch (Exception e) {
			throw new AuthenticationException("## user password is not correct! ");
		}
		//业务代码-end
		// 设置用户权限信息
		/*try {
			authUser.setPermissions(permissions);
		} catch (Exception e) {
			throw new AuthenticationException("## user permission setter exception! ");
		}*/
		// 创建授权用户
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(authUser, password, getName());
		return info;
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null)
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		// 获取当前登录用户
		UserInfoModel user = SessionContext.getAuthUser();
		if (user == null) {
			return null;
		}
		// 设置权限
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获取用户权限并设置 以供shiro框架 
		info.setStringPermissions(user.getPermissions());
		return info;
	}

}
