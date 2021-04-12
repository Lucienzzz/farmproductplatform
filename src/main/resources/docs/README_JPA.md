### Spring Data Jpa 集成指南

#### 1、引入依赖

在 pom 依赖中加入依赖，并刷新依赖。

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

#### 2、修改配置

在 `application.yml` 中加入数据库对应的依赖，jpa 的配置等。

#### 3、开启 Spring Boot 对 Jpa 的支持

在启动类上加 `@EnableJpaRepositories` 注解，来开启 Spring Boot 对 Jpa 的支持。

#### 4、编写实体类

详见 `com.zzq.farmproductplatform.jpa.model.JpaUser`。

#### 5、编写 Repository

详见 `com.zzq.farmproductplatform.jpa.repository.JpaUserRepository`。

#### 6、测试
详见 `com.zzq.farmproductplatform.controller.TestJpaController`。

#### 参考
[Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)