package top.fsfsfs.main.magicapi.interceptor;

import org.ssssssss.magicapi.core.context.MagicUser;
import org.ssssssss.magicapi.core.exception.MagicLoginException;
import org.ssssssss.magicapi.core.interceptor.Authorization;
import org.ssssssss.magicapi.core.interceptor.AuthorizationInterceptor;
import org.ssssssss.magicapi.core.servlet.MagicHttpServletRequest;


/**
 * 自定义UI界面鉴权
 * https://ssssssss.org/magic-api/pages/security/operation/
 * @see AuthorizationInterceptor
 */
public class CustomUIAuthorizationInterceptor implements AuthorizationInterceptor {

	/**
	 * 配置UI是否需要登录
	 */
	@Override
	public boolean requireLogin() {
		return true;
	}

	/**
	 * 自定义登录方法
	 *  @param username 用户名
	 * @param password 密码
	 * @return
	 */
	@Override
	public MagicUser login(String username, String password) throws MagicLoginException {
		if (!"123456".equals(password) && !"admin".equals(username)) {
			throw new MagicLoginException("密码不正确");
		}
		return new MagicUser("1", username, "token..123456");
	}

	/**
	 * 根据Token获取用户信息
	 */
	@Override
	public MagicUser getUserByToken(String token) throws MagicLoginException {
		if ("token..123456".equals(token)) {
			return new MagicUser("1", "admin", "token..123456");
		}
		throw new MagicLoginException("token无效");
	}

	/**
	 * 是否允许访问
	 * @param magicUser	用户信息
	 * @return
	 */
	@Override
	public boolean allowVisit(MagicUser magicUser, MagicHttpServletRequest request, Authorization authorization) {
		if(authorization == Authorization.DELETE || authorization == Authorization.UPLOAD){
			// 禁止上传和删除
			return false;
		}
		return true;
	}
}
