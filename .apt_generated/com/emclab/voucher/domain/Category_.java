package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Category, String> name;
	public static volatile SingularAttribute<Category, Long> id;
	public static volatile SingularAttribute<Category, Store> store;
	public static volatile SetAttribute<Category, Product> products;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String STORE = "store";
	public static final String PRODUCTS = "products";

}

