package org.zzq.farmproductplatform.model.service;

import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.model.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> findAllRoles();

    BSResult updateUserRoleRelationship(Integer userId, int[] roleIds);

    Role findById(int roleId);

    BSResult deleteById(int roleId);

    BSResult addRole(Role role);

    BSResult updateRole(Role role);

    BSResult updateRolesPrivilege(int[] ids, int roleId);
}

