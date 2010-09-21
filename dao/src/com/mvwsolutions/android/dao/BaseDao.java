package com.mvwsolutions.android.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author SMineyev
 * 
 * @param <T>
 *            Target bean class
 */
public abstract class BaseDao<T> {

	protected SQLiteDatabase database;

	public abstract android.content.ContentValues createContentValues(T bean);

	public abstract void fillBean(android.database.Cursor cursor, T bean);

	protected abstract void fillBean(android.database.Cursor values,
			int[] columnIndices, T bean);

	public abstract void fillBean(android.content.ContentValues values, T bean);

	protected abstract T createNewBean();

	/**
	 * Returns the name of this table
	 * 
	 * @return Name of the table that holds instances of this class
	 */
	public abstract String getTableName();

	/**
	 * Returns the name of primary key column and null if there is no PK
	 * 
	 * @return primary key column name
	 */
	public abstract String getPkColumnName();

	/**
	 * Return an array that gives the column index in the cursor for each field
	 * defined
	 * 
	 * @param cursor
	 *            Database cursor over some columns, possibly including this
	 *            table
	 * @return array of column indices; -1 if the column with that id is not in
	 *         cursor
	 */
	protected abstract int[] createColumnIndices(Cursor cursor);

	public void fillCollection(Cursor c, Collection<T> collection) {
		if (c.moveToFirst()) {
			int[] columnIndices = createColumnIndices(c);
			do {
				T bean = createNewBean();
				fillBean(c, columnIndices, bean);

				collection.add(bean);
			} while (c.moveToNext());
		}
	}

	public List<T> findAll() {
		List<T> res = new ArrayList<T>();
		Cursor c = database.query(getTableName(), null, null, null, null, null,
				null);
		try {
			fillCollection(c, res);
		} finally {
			c.close();
		}
		return res;
	}

	public Cursor findCursor(String condition) {
		return database.query(getTableName(), null, condition, null, null, null,
				null);
	}
	
	public Cursor findCursor(String condition, String orderBy) {
		return database.query(getTableName(), null, condition, null, null, null,
				orderBy);
	}
	
	public List<T> find(String condition) {
		List<T> res = new ArrayList<T>();
		Cursor c = database.query(getTableName(), null, condition, null, null, null,
				null);
		try {
			fillCollection(c, res);
		} finally {
			c.close();
		}
		return res;
	}
	
	public long insert(T bean) {
		ContentValues cv = createContentValues(bean);
		return database.insert(getTableName(), null, cv);
	}

	public int deleteWhere(String condition) {
		return database.delete(getTableName(), condition, null);
	}

	
	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}
	
	

}
