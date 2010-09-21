package com.mvwsolutions.android.dao.meta;

import java.lang.annotation.*;

/**
 * Bean field annotation which used for db and dao generation
 * @author SMineyev
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface FieldAccessor {
	public String Name() default "";
	public FieldType Type() default FieldType.DEFAULT;
	public FieldVisibility Visibility() default FieldVisibility.PRIVATE;
	public boolean Nullable() default true;
	public String DefaultValue() default "";
	public boolean Both() default true;
}
