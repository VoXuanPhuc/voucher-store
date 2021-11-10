package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Event.class)
public abstract class Event_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Event, String> description;
	public static volatile SingularAttribute<Event, Long> id;
	public static volatile SingularAttribute<Event, Store> store;
	public static volatile SingularAttribute<Event, String> title;
	public static volatile SetAttribute<Event, Voucher> vouchers;

	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String STORE = "store";
	public static final String TITLE = "title";
	public static final String VOUCHERS = "vouchers";

}

