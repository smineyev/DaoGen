package com.mvwsolutions.android.dao.meta;

/**
 * Enum of supported field types
 * @author SMineyev
 *
 */
public enum FieldType {
	INTEGER_PRIMARY_KEY,
	INTEGER,
	TEXT,
	BLOB,
	REAL,
	// Derive type from type of accessor
	DEFAULT
}
