package org.zxc.service.impl;

import org.zxc.entity.User;
import org.zxc.mapper.UserMapper;
import org.zxc.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 知行成
 * @since 2021-07-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
