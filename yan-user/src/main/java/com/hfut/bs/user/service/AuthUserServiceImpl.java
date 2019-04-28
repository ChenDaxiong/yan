package com.hfut.bs.user.service;

import com.hfut.bs.user.model.UserInfoModel;
import com.hfut.bs.user.model.UserModel;
import com.hfut.bs.common.redis.keys.AuthUserKey;
import com.hfut.bs.common.storage.QiniuStorage;
import com.hfut.bs.common.utils.MD5Utils;
import com.hfut.bs.common.utils.UUIDUtil;
import com.hfut.bs.user.common.CodeMsg;
import com.hfut.bs.user.dao.AuthUserMapper;
import com.hfut.bs.user.domain.AuthUser;
import com.hfut.bs.user.exception.GlobalException;
import com.hfut.bs.user.redis.JedisUtils;
import com.hfut.bs.user.utils.CookieUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service("authUserService")
public class AuthUserServiceImpl implements IAuthUserService {

    private final static Logger log = LoggerFactory.getLogger(AuthUserServiceImpl.class);

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private JedisUtils jedisUtils;

    @Override
    public UserModel getByUsername(String username) {
        AuthUser authUser =  authUserMapper.getByUsername(username);
        UserModel userModel = toUserModel(authUser);
        return userModel;
    }

    @Override
    public boolean createSelectivity(UserInfoModel entity){
        AuthUser authUser = toAuthUser(entity);
        authUserMapper.insertSelective(authUser);
        return true;
    }

//    @Override
    public UserInfoModel getById(Integer id) {
        AuthUser authUser =  authUserMapper.selectByPrimaryKey(id);
        return toUserInfoModel(authUser);
    }


public UserInfoModel getByUsernameAndPassword2(UserInfoModel userInfoModel) {
    // 先根据ID查出user，再根据
    AuthUser user = authUserMapper.getByUsername(userInfoModel.getUsername());
    if (user == null) {
        throw new GlobalException(CodeMsg.USER_NOT_EXIST);
    }
    // 获取数据库中用户的MD5盐值
    String salt = user.getSalt();
    // 根据salt和md5Password推出dbPassword
    String dbPassword = MD5Utils.formPassToDbPass(userInfoModel.getPassword(), salt);
    if (!dbPassword.equals(user.getPassword())) {
        // 密码错误
        throw new GlobalException(CodeMsg.PASSWORD_ERROR);
    }
    return toUserInfoModel(user);
}

    @Override
    public UserInfoModel getByUsernameAndPassword(UserInfoModel userInfoModel) {
        // 先根据ID查出user，再根据
        AuthUser user = authUserMapper.getByUsername(userInfoModel.getUsername());
        if (user == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        // 获取数据库中用户的MD5盐值
//        String salt = user.getSalt();
//        // 根据salt和md5Password推出dbPassword
//        String dbPassword = MD5Utils.formPassToDbPass(userInfoModel.getPassword(), salt);
        if (!userInfoModel.getPassword().equals(user.getPassword())) {
            // 密码错误
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        return toUserInfoModel(user);
    }

    /**
     * 在redis中缓存cookie和往客户端浏览器种cookie
     *
     * @param response
     * @param user
     */
    private void addCookie(HttpServletResponse response, UserInfoModel user) {
        String token = UUIDUtil.uuid();
        cookieUtil.writeLoginToken(response, token);
        jedisUtils.set(AuthUserKey.token, token, user);
    }

    //    @Override
    public boolean update(UserInfoModel entity) {
        AuthUser authUser = toAuthUser(entity);
        int resultCount = authUserMapper.updateByPrimaryKey(authUser);
        if (resultCount > 0) {
            return true;
        }
        return false;
    }



//    @Override
    public boolean updateSelectivity(UserInfoModel entity) {
        AuthUser authUser = toAuthUser(entity);
        int resultCount = authUserMapper.updateByPrimaryKeySelective(authUser);
        if (resultCount > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int entityId) {
        int resultCount =authUserMapper.deleteByPrimaryKey(entityId);
        if (resultCount > 0){
            return true;
        }
        return false;

    }

    @Override
    public boolean deleteLogic(int entityId) {
        int resultCount =authUserMapper.deleteLogic(entityId);
        if (resultCount > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<UserModel> getHomePageTeachers() {
        List<AuthUser> teachers = authUserMapper.getHomepageTeachers();
        List<UserModel> results = new ArrayList<UserModel>();
        if (CollectionUtils.isNotEmpty(teachers)) {
            for (AuthUser teacher : teachers) {
                if (StringUtils.isNotEmpty(teacher.getHeader())) {
                    // 填充头像url
                    teacher.setHeader(QiniuStorage.getUrl(teacher.getHeader()));
                    results.add(toUserModel(teacher));
                }
            }
        }
        return results;
    }

    private UserModel toUserModel(AuthUser authUser){
        if (authUser == null){
            return null;
        }
        UserModel userModel = new UserModel();
        userModel.setWechatId(authUser.getWechatId());
        userModel.setUsername(authUser.getUsername());
        userModel.setTitle(authUser.getTitle());
        userModel.setStatus(authUser.getStatus());
        userModel.setSign(authUser.getSign());
        userModel.setRealname(authUser.getRealname());
        userModel.setProvince(authUser.getProvince());
//       authUser userModel.setPassword(authUser.getPassword());
        userModel.setOpenId(authUser.getOpenId());
        userModel.setMobile(authUser.getMobile());
        userModel.setId(authUser.getId());
        userModel.setHeader(authUser.getHeader());
        userModel.setGender(authUser.getGender());
        userModel.setEducation(authUser.getEducation());
        userModel.setDistrict(authUser.getDistrict());
        userModel.setCollegeName(authUser.getCollegeName());
        userModel.setCollegeCode(authUser.getCollegeCode());
        userModel.setCity(authUser.getCity());
        userModel.setBirthday(authUser.getBirthday());
        return userModel;
    }

    private UserInfoModel toUserInfoModel(AuthUser authUser){
        if (authUser == null){
            return null;
        }
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setWechatId(authUser.getWechatId());
        userInfoModel.setUsername(authUser.getUsername());
        userInfoModel.setTitle(authUser.getTitle());
        userInfoModel.setStatus(authUser.getStatus());
        userInfoModel.setSign(authUser.getSign());
        userInfoModel.setRealname(authUser.getRealname());
        userInfoModel.setProvince(authUser.getProvince());
//        userInfoModel.setPassword(authUser.getPassword());
        userInfoModel.setOpenId(authUser.getOpenId());
        userInfoModel.setMobile(authUser.getMobile());
        userInfoModel.setId(authUser.getId());
        userInfoModel.setHeader(authUser.getHeader());
        userInfoModel.setGender(authUser.getGender());
        userInfoModel.setEducation(authUser.getEducation());
        userInfoModel.setDistrict(authUser.getDistrict());
        userInfoModel.setCollegeName(authUser.getCollegeName());
        userInfoModel.setCollegeCode(authUser.getCollegeCode());
        userInfoModel.setCity(authUser.getCity());
        userInfoModel.setBirthday(authUser.getBirthday());
        userInfoModel.setWeight(authUser.getWeight());
        userInfoModel.setWechatId(authUser.getWechatId());
        userInfoModel.setWeight(authUser.getWeight());
        userInfoModel.setIp(authUser.getIp());
        userInfoModel.setDel(authUser.getDel());
        userInfoModel.setLoginTime(authUser.getLoginTime());
        userInfoModel.setQq(authUser.getQq());
        return userInfoModel;

    }

    private  AuthUser toAuthUser(UserInfoModel userInfoModel){
        AuthUser authUser = new AuthUser();
        BeanUtils.copyProperties(userInfoModel,authUser);
        return authUser;
    }



}
