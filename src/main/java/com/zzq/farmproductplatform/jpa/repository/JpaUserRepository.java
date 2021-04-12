package com.zzq.farmproductplatform.jpa.repository;

import com.zzq.farmproductplatform.jpa.model.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 基本原理：
 * 在 Spring 容器启动的时候，由于 Jpa 框架的设置，它会扫描所有的标注了 {@link Repository} 注解的接口，并且为我们的接口生成代理对象，
 * 通过具体的各种策略来实现我们的增删改查方法。
 *
 * <p>
 * 可以看见这是一个接口，实现了 {@link JpaRepository} 接口
 * 它有两个泛型，分别是 <T, ID>，第一个 T 代表实体类的类型，第二个代表实体类的 id 的类型
 * 这里实体类是 JpaUser，他的主键 id 的类型是 Integer。
 * <p>
 * 在 {@link JpaRepository} 中已经有了很多抽象方法，例如 {@link JpaRepository#findAll()}、{@link JpaRepository#findById(Object)} 等等。
 * 这些方法可以直接调用，Jpa 框架会为我们的 repository 生成代理，并且实现这些抽象方法为我们执行查询，无需我们做任何事情。
 * <p>
 * 同样的，我们也可以按照 Jpa 的一些语法来定义一些方法，详见方法注释。
 *
 * @author Moore
 * @since 2021/04/12
 */
@Repository
public interface JpaUserRepository extends JpaRepository<JpaUser, Integer> {

    /**
     * 这个方法，我们首先看方法名，是 findByUsername，Jpa 框架的策略就是如果你的方法名是 findByXxx，那么生成的 sql 就是
     * select * from Table where xxx = ?
     * <p>
     * 由于 username 有唯一键，所以返回值是单个 JpaUser 对象，而通过这个名字可能是不存在的，所以他是 Optional 包装的。
     * 当然，如果你对 Optional 不熟悉，返回值也是可以写成 JpaUser 的，只不过你需要手动处理空指针异常。
     * <p>
     *
     * @param username the usernanme
     * @return the entity
     */
    Optional<JpaUser> findByUsername(String username);
}
