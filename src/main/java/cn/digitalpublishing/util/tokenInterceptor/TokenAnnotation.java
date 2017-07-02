package cn.digitalpublishing.util.tokenInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
public @interface TokenAnnotation {

	  boolean save() default false;  
	  boolean remove() default false;  
}
