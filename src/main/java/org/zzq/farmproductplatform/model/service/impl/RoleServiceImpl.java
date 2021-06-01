package org.zzq.farmproductplatform.model.service.impl;

import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.common.utils.BSResultUtil;
import org.zzq.farmproductplatform.model.dao.RoleMapper;
import org.zzq.farmproductplatform.model.dao.RolePrivilegeMapper;
import org.zzq.farmproductplatform.model.dao.UserRoleMapper;
import org.zzq.farmproductplatform.model.entity.Role;
import org.zzq.farmproductplatform.model.entity.RolePrivilege;
import org.zzq.farmproductplatform.model.entity.UserRole;
import org.zzq.farmproductplatform.model.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePrivilegeMapper rolePrivilegeMapper;

    @Override
    public List<Role> findAllRoles() {
        return roleMapper.selectAll();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames="authorizationCache",allEntries = true)
    public BSResult updateUserRoleRelationship(Integer userId, int[] roleIds) {

        if(userId != null && roleIds != null && roleIds.length != 0 ){
            Example example = new Example(UserRole.class);
            example.createCriteria().andEqualTo("userId", userId);
            userRoleMapper.deleteByExample(example);

            for (int roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setCreated(new Date());
                userRole.setUpdated(new Date());
                userRoleMapper.insert(userRole);
            }
        }
        return BSResultUtil.success();
    }

    @Override
    public Role findById(int roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    @CacheEvict(cacheNames="authorizationCache",allEntries = true)
    public BSResult deleteById(int roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
        return BSResultUtil.success();
    }

    @Override
    @Transactional
    public BSResult addRole(Role role) {
        role.setCreated(new Date());
        role.setUpdated(new Date());
        roleMapper.insert(role);
        return BSResultUtil.success();
    }

    @Override
    @Transactional
    public BSResult updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
        role.setUpdated(new Date());
        return BSResultUtil.success();
    }

    @Override
    @CacheEvict(cacheNames="authorizationCache",allEntries = true)
    public BSResult updateRolesPrivilege(int[] ids, int roleId) {

        //先删了这个角色所对应的权限，然后再把权限树选中的权限重新写入数据库
        Example example = new Example(RolePrivilege.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        rolePrivilegeMapper.deleteByExample(example);

        for (int privilegeId : ids) {
            RolePrivilege rolePrivilege = new RolePrivilege();
            rolePrivilege.setPrivilegeId(privilegeId);
            rolePrivilege.setRoleId(roleId);
            rolePrivilege.setCreated(new Date());
            rolePrivilege.setUpdated(new Date());
            rolePrivilegeMapper.insert(rolePrivilege);
        }
        return BSResultUtil.success(roleId);
    }
}
