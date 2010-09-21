package com.mvwsolutions.android.daogen;

import com.mvwsolutions.android.dao.meta.FieldType;
import com.mvwsolutions.android.dao.meta.FieldVisibility;

class FieldDefinition {
	/**
     * Name as it appears in java
	 */
	String name;
	/**
	 * Column name in database table
	 */
	String columnName;
	FieldType type;
	String javaTypeCode;
	String javaType;
	String defaultValue;
	boolean nullable;
	boolean putRequired;
	String putName;
	boolean getRequired;
	String getName;
	boolean bothRequired;
	FieldVisibility visibility;
}
