package org.example.aspect;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RpcClient {
    @AliasFor("name")
    String value() default "";

    String contextId() default "";

    @AliasFor("value")
    String name() default "";

    String url() default "";
    boolean primary() default true;

    String[] qualifiers() default {};
}
