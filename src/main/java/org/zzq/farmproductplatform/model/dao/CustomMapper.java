package org.zzq.farmproductplatform.model.dao;

import org.springframework.stereotype.Component;
import org.zzq.farmproductplatform.model.entity.Privilege;
import org.zzq.farmproductplatform.model.entity.Role;
import org.zzq.farmproductplatform.model.entity.User;
import org.zzq.farmproductplatform.model.entity.custom.OrderCustom;

import java.util.List;

/**
 * 自定义mapper
 */
@Component
public interface CustomMapper {

    List<OrderCustom> findOrdersByUserId(int userId);

    List<OrderCustom> findOrdersByStoreId(int storeId);

    List<Role> findRolesByUserId(int userId);

    List<Privilege> findPrivilegesByRoleId(int roleId);

    List<User> findBusinesses(int roleId);
}
