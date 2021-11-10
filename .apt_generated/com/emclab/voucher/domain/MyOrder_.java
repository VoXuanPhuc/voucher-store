package com.emclab.voucher.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MyOrder.class)
public abstract class MyOrder_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SetAttribute<MyOrder, VoucherCode> voucherCodes;
	public static volatile SingularAttribute<MyOrder, Long> id;
	public static volatile SingularAttribute<MyOrder, Instant> paymentTime;
	public static volatile SingularAttribute<MyOrder, MyUser> user;
	public static volatile SingularAttribute<MyOrder, Double> totalCost;
	public static volatile SingularAttribute<MyOrder, OrderStatus> status;

	public static final String VOUCHER_CODES = "voucherCodes";
	public static final String ID = "id";
	public static final String PAYMENT_TIME = "paymentTime";
	public static final String USER = "user";
	public static final String TOTAL_COST = "totalCost";
	public static final String STATUS = "status";

}

