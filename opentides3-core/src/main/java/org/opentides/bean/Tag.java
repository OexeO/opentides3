package org.opentides.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "TAG")
public class Tag extends BaseEntity {

	private static final long serialVersionUID = -7643692652937089079L;
	
	public Tag(String text) {
		this.text = text;
	}
	
	public Tag() {
	}
	
	@Column(name = "TEXT", length = 2000, unique=true)
	private String text;

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
}