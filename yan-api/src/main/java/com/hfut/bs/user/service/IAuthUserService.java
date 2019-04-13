package com.hfut.bs.user.service;


import javax.servlet.http.HttpServletResponse;
import com.hfut.bs.user.model.UserInfoModel;
import com.hfut.bs.user.model.UserModel;

import java.util.List;

public interface IAuthUserService {

    /**
     *根据username获取
     **/
    UserModel getByUsername(String username);

    /**
     *创建
     **/
    boolean createSelectivity(UserInfoModel entity);

    /**
     *根据id获取
     **/
    UserInfoModel getById(Integer id);


    /**
     *根据username和password获取
     **/
    UserInfoModel getByUsernameAndPassword(UserInfoModel userInfoModel);


    /**
     *分页获取
     **/
//    public TailPage<AuthUser> queryPage(AuthUser queryEntity ,TailPage<AuthUser> page);

    /**
     *根据id更新
     **/
    boolean update(UserInfoModel entity);

    /**
     *根据id 进行可选性更新
     **/
    boolean updateSelectivity(UserInfoModel entity);

    /**
     *物理删除
     **/
    boolean delete(int entityId);

    /**
     *逻辑删除，就是设置某个删除标志位，并不是真正意义上的物理删除。
     **/
    boolean deleteLogic(int entityId);

    /**
     * 获取首页推荐的五个讲师
     * @return
     */
    List<UserModel> getHomePageTeachers();
}
