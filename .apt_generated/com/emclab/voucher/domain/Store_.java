package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Store.class)
public abstract class Store_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SetAttribute<Store, StoreUser> storeUsers;
	public static volatile SingularAttribute<Store, Address> address;
	public static volatile SingularAttribute<Store, String> phone;
	public static volatile SingularAttribute<Store, String> background;
	public static volatile SingularAttribute<Store, String> name;
	public static volatile SingularAttribute<Store, BenifitPackage> benifit;
	public static volatile SingularAttribute<Store, String> description;
	public static volatile SingularAttribute<Store, Long> id;
	public static volatile SingularAttribute<Store, String> avartar;
	public static volatile SetAttribute<Store, Category> categories;
	public static volatile SingularAttribute<Store, String> email;
	public static volatile SetAttribute<Store, Event> events;

	public static final String STORE_USERS = "storeUsers";
	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";
	public static final String BACKGROUND = "background";
	public static final String NAME = "name";
	public static final String BENIFIT = "benifit";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String AVARTAR = "avartar";
	public static final String CATEGORIES = "categories";
	public static final String EMAIL = "email";
	public static final String EVENTS = "events";

}

