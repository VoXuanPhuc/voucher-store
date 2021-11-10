package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Product, String> image;
	public static volatile SingularAttribute<Product, String> code;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, Category> category;
	public static volatile SetAttribute<Product, Voucher> vouchers;

	public static final String IMAGE = "image";
	public static final String CODE = "code";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String VOUCHERS = "vouchers";

}

