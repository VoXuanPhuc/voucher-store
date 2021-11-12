package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VoucherCode.class)
public abstract class VoucherCode_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<VoucherCode, String> code;
	public static volatile SingularAttribute<VoucherCode, Voucher> voucher;
	public static volatile SingularAttribute<VoucherCode, Long> id;
	public static volatile SetAttribute<VoucherCode, Gift> gifts;
	public static volatile SingularAttribute<VoucherCode, MyOrder> order;

	public static final String CODE = "code";
	public static final String VOUCHER = "voucher";
	public static final String ID = "id";
	public static final String GIFTS = "gifts";
	public static final String ORDER = "order";

}

