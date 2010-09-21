package com.mvwsolutions;

import com.mvwsolutions.android.dao.meta.FieldAccessor;
import com.mvwsolutions.android.dao.meta.FieldType;
import com.mvwsolutions.android.dao.meta.TableInterface;


/**
 * 
 * @author smineyev
 *
 */
@TableInterface
public class TestBean {

	private long id;

	private long userId;

	private long timestamp;
	
	private String text;

	@FieldAccessor (Type = FieldType.INTEGER_PRIMARY_KEY, Name="_id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@FieldAccessor
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@FieldAccessor
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@FieldAccessor
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
