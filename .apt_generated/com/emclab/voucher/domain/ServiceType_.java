package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ServiceType.class)
public abstract class ServiceType_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<ServiceType, String> name;
	public static volatile SingularAttribute<ServiceType, Long> id;
	public static volatile SetAttribute<ServiceType, Voucher> vouchers;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String VOUCHERS = "vouchers";

}

