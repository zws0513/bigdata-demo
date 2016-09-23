package com.zw.framework.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class ShiroDbRealm extends AuthorizingRealm {

//    @Autowired
//    private RolePageListService rolePageListService;
//    @Autowired
//    private LoginService loginService;


    /**
     * 为当前登录的Subject授予角色和权限
     * <p>
     * 经测试:本例中该方法的调用时机为需授权资源被访问时
     * 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
     * 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache
     * 比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache
     * </p>
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
        String currentUsername = (String) super.getAvailablePrincipal(principals);

        return new SimpleAuthorizationInfo();


//        List<String> roleList = new ArrayList<String>();
//        List<String> permissionList = new ArrayList<String>();
//        //从数据库中获取当前登录用户的详细信息
//        Result result = (Result)rolePageListService.execute(currentUsername);
//        if (MessageCode.MSG_CODE_1001.equals(result.getCode())) {
//            List<RelationshipRp> list = (List<RelationshipRp>)result.getEntity();
//            //获取当前登录用户的角色
//            for(RelationshipRp rp :list){
//                if (!roleList.contains(rp.getRoleId())){
//                    roleList.add(rp.getRoleId());
//                }
//                permissionList.add(rp.getPageId());
//            }
//            //为当前用户设置角色和权限
//            SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
//            simpleAuthorInfo.addRoles(roleList);
//            simpleAuthorInfo.addStringPermissions(permissionList);
//            return simpleAuthorInfo;
//        }else{
//            throw new AuthorizationException();
//        }
    }


    /**
     * 验证当前登录的Subject
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌  
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的  
        //两个token的引用都是一样的
        return null;

//        try {
//            UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//            UserDto param = new UserDto();
//            param.setEmail(token.getUsername());
//            param.setPassword(String.valueOf(token.getPassword()));
////        System.out.println("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
//            Result result = (Result) loginService.execute(param);
//            if (MessageCode.MSG_CODE_1001.equals(result.getCode())) {
//                User user = (User) result.getEntity();
//                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token.getUsername(), param.getPassword(), getName());
//                this.setSession(BaseConstant.SESSION_USER_CONTEXT, user);
//                return authcInfo;
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            e.getStackTrace();
//            throw e;
//        }
    }


    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }
}