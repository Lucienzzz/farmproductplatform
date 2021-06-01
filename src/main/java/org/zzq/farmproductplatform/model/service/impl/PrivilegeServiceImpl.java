package org.zzq.farmproductplatform.model.service.impl;

import org.zzq.farmproductplatform.common.pojo.BSResult;
import org.zzq.farmproductplatform.common.pojo.ZTreeNode;
import org.zzq.farmproductplatform.common.utils.BSResultUtil;
import org.zzq.farmproductplatform.model.dao.PrivilegeMapper;
import org.zzq.farmproductplatform.model.dao.CustomMapper;
import org.zzq.farmproductplatform.model.entity.Privilege;
import org.zzq.farmproductplatform.model.service.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PrivilegeServiceImpl implements IPrivilegeService {

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private CustomMapper customMapper;

    @Override
    public List<ZTreeNode> getZTreeNodes() {
        List<Privilege> privileges = privilegeMapper.selectAll();
        List<ZTreeNode> zTreeNodes = new ArrayList<>();

        privileges.forEach((privilege)->{
            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(privilege.getPrivId());
            zTreeNode.setName(privilege.getName());
            zTreeNode.setpId(privilege.getParentId());
            if(zTreeNode.getpId() == 0){
                zTreeNode.setOpen(true);
            }
            zTreeNodes.add(zTreeNode);
        });
        return zTreeNodes;
    }

    @Override
    public BSResult findById(int privId) {
        return BSResultUtil.success(privilegeMapper.selectByPrimaryKey(privId));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames="authorizationCache",allEntries = true)
    public BSResult updatePrivilege(Privilege privilege) {
        privilege.setUpdated(new Date());
        privilegeMapper.updateByPrimaryKeySelective(privilege);
        return BSResultUtil.success(privilegeMapper.selectByPrimaryKey(privilege.getPrivId()));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames="authorizationCache",allEntries = true)
    public BSResult addPrivilege(Privilege privilege) {
        privilege.setCreated(new Date());
        privilege.setUpdated(new Date());
        privilege.setIsParent(0);
        privilegeMapper.insert(privilege);

        //更新父节点，如果父节点的isParent字段为0，则修改父节点的isParent字段为1
        Privilege parentPrivilege = privilegeMapper.selectByPrimaryKey(privilege.getParentId());
        if(parentPrivilege.getIsParent() == 0){
            parentPrivilege.setIsParent(1);
            parentPrivilege.setUpdated(new Date());
            updatePrivilege(parentPrivilege);
        }

        return BSResultUtil.success(privilege.getPrivId());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames="authorizationCache",allEntries = true)
    public BSResult deleteById(int privId) {
        try {
            Privilege privilege = privilegeMapper.selectByPrimaryKey(privId);
            Example example = new Example(Privilege.class);
            example.createCriteria().andEqualTo("parentId", privilege.getParentId());
            List<Privilege> privileges = privilegeMapper.selectByExample(example);
            if(privileges.size() <= 1){
                Privilege parentPrivilege = privilegeMapper.selectByPrimaryKey(privilege.getParentId());
                parentPrivilege.setIsParent(0);
                parentPrivilege.setUpdated(new Date());
                updatePrivilege(parentPrivilege);
            }
            privilegeMapper.deleteByPrimaryKey(privId);

        }catch (Exception e){
            BSResultUtil.build(400, "此权限被其他地方引用,不能删除");
        }
        return BSResultUtil.success(privId);
    }

    @Override
    public List<ZTreeNode> getRolePrivileges(int roleId) {
        List<Privilege> privileges = privilegeMapper.selectAll();

        List<Privilege> rolePrivileges = customMapper.findPrivilegesByRoleId(roleId);

        List<ZTreeNode> zTreeNodes = new ArrayList<>();

        privileges.forEach((privilege)->{
            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(privilege.getPrivId());
            zTreeNode.setName(privilege.getName());
            zTreeNode.setpId(privilege.getParentId());
            if(privilege.getIsParent() == 1){
                zTreeNode.setOpen(true);
            }
            for (Privilege rolePrivilege : rolePrivileges) {
                if(rolePrivilege.getPrivId().equals(privilege.getPrivId())){
                    zTreeNode.setChecked(true);
                    break;
                }
            }

            zTreeNodes.add(zTreeNode);
        });

        return zTreeNodes;
    }
}
