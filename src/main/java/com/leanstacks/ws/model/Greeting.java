package com.leanstacks.ws.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * The Greeting class is an entity model object.
 * 
 * @author Matt Warman
 */
@Entity
public class Greeting extends TransactionalEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String text;
  
    @NotNull
    private String language;

    public Greeting() {
        super();
    }

    public Greeting(final String text) {
        super();
        this.text = text;
    }
    
    public Greeting(final String text, final String language) {
        super();
        this.text = text;
        this.setLanguage(language);
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
