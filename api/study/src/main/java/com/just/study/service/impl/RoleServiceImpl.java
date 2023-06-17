package com.just.study.service.impl;

import com.just.study.entity.Role;
import com.just.study.mapper.RoleMapper;
import com.just.study.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author abc
 * @since 2023-02-24
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
