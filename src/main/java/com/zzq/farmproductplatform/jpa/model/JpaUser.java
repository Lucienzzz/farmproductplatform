package com.zzq.farmproductplatform.jpa.model;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * {@link Entity} 注解是声明当前类是一个 Jpa 的实体类。
 * <p>
 * {@link Table} 注解是标注当前类的实体对应数据库的哪张表，如果实体类的名称和数据库表名不一致，则必须通过 name 属性指明。
 * 如果表名和实体类名相同，该注解同样可以省略。
 *
 * @author Moore
 * @since 2021/04/12
 */
@Data
@Entity
@Table(name = "user")
public class JpaUser {

    /**
     * strategy 表示主键生成策略，这里选择 identity，可以点进去看字段的注释，注释如下：
     * <p>
     * Indicates that the persistence provider must assign primary keys for the entity using a database identity column.
     * 意思是主键的生成策略由数据库决定
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标识是列，同样的，如果字段名和数据库字段名不一致，需要通过 name 属性进行表明。
     */
    @Column(name = "username")
    private String username;

    /**
     * {@code Column} 如果字段名一致，且没有其他需要声明的东西，注解省略也是可以的。
     */
    private String password;

    @Column
    private OffsetDateTime created;
}
