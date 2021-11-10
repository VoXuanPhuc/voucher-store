package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BenifitPackage.class)
public abstract class BenifitPackage_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<BenifitPackage, Long> cost;
	public static volatile SetAttribute<BenifitPackage, Store> stores;
	public static volatile SingularAttribute<BenifitPackage, String> name;
	public static volatile SingularAttribute<BenifitPackage, String> description;
	public static volatile SingularAttribute<BenifitPackage, Long> id;
	public static volatile SingularAttribute<BenifitPackage, String> time;

	public static final String COST = "cost";
	public static final String STORES = "stores";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String TIME = "time";

}

