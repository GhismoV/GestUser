package it.ghismo.corso1.gestuser.error;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Result {
	String code();
	String msg() default "Generic Error";
	
}
