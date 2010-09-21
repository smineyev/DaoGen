package com.mvwsolutions.android.dao.meta;

import java.lang.annotation.*;

/**
 * Bean class annotation which used for db and dao generation
 * @author SMineyev
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface TableInterface {
	public String TableName() default "";
	public String ImplementingClassName() default "";
	public boolean ImplementingIsAbstract() default false;
	public boolean ImplementingIsPublic() default true;
}
