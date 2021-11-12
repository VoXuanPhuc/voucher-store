package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Address, Integer> zipCode;
	public static volatile SingularAttribute<Address, String> street;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, Village> village;

	public static final String ZIP_CODE = "zipCode";
	public static final String STREET = "street";
	public static final String ID = "id";
	public static final String VILLAGE = "village";

}

