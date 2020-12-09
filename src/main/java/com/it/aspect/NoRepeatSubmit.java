package com.it.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: NoRepeatSubmit    
 * @Description: TODO  防止重复提交注解
 * @author hwk    
 * @date 2020年12月9日 上午10:37:58    
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {

}
