package com.leanstacks.ws.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Country extends TransactionalEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;
  
    @NotNull
    private String capital;

    public Country() {
        super();
    }

    public Country(final String name) {
        super();
        this.setName(name);
    }
    
    public Country(final String name, final String capital) {
        super();
        this.setName(name);
        this.setCapital(capital);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}


}