
package com.kevin.common.annotation;

import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * <p>启动类</p>
 * @author licoy.cn
 * @version 2020/5/27
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SecretAnnotation.class})
public @interface EnableEncryptBody {
}
