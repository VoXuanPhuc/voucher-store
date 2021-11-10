package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VoucherStatus.class)
public abstract class VoucherStatus_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<VoucherStatus, String> name;
	public static volatile SingularAttribute<VoucherStatus, Long> id;
	public static volatile SetAttribute<VoucherStatus, Voucher> vouchers;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String VOUCHERS = "vouchers";

}

