package com.taotao.sso.service.impl;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by h on 2017-03-09.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_USER_SEESION_KEY}")
    private String REDIS_USER_SEESION_KEY;

    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String content, Integer type) {
       //创建查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //对数据进行校验 1=username 2=phone 3=email
        if(1 == type){
            criteria.andUsernameEqualTo(content);
        }else  if(2 == type){
            criteria.andPhoneEqualTo(content);
        }else {
            criteria.andEmailEqualTo(content);
        }
        //执行查询
        List<TbUser> list =userMapper.selectByExample(example);
        if(list == null || list.size()==0){
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult createUser(TbUser user) {
        user.setUpdated(new Date());
        user.setCreated(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        //如果没有此用户名
        if(null ==list || list.size()==0){
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        TbUser user = list.get(0);
        //比对密码
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        //生成token
        String token = UUID.randomUUID().toString();
        //保存用户之前,把用户对象中的密码清空
        user.setPassword(null);
        //把用户信息写入redis
        jedisClient.set(REDIS_USER_SEESION_KEY+":" + token, JsonUtils.objectToJson(user));
        //设置session的过期时间
        jedisClient.expire(REDIS_USER_SEESION_KEY + ":" + token,SSO_SESSION_EXPIRE);

        //添加写cookie的逻辑,cookie有效期是关闭浏览器就失效
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);
        //返回token
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {

        //根据token从redis中查询用户信息
        String json = jedisClient.get(REDIS_USER_SEESION_KEY + ":" + token);
        //判断是否为空
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400, "此session已经过期,请重新登录!");
        }
        //更新过期时间
        jedisClient.expire(REDIS_USER_SEESION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        //返回用户信息
        return TaotaoResult.ok(JsonUtils.jsonToPojo(json,TbUser.class));
    }

    @Override
    public TaotaoResult delUserByToken(String token) {

        //从redis中取得用户信息
        String json =jedisClient.get(REDIS_USER_SEESION_KEY + ":" + token);
        //删除当前token信息
        jedisClient.del(REDIS_USER_SEESION_KEY + ":" + token);
        //判断是否为空
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400, "此session已经过期,请重新登录!");
        }
        return TaotaoResult.ok();
    }
}
