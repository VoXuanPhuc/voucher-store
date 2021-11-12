package com.emclab.voucher.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Voucher.class)
public abstract class Voucher_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Voucher, Integer> quantity;
	public static volatile SingularAttribute<Voucher, Double> price;
	public static volatile SetAttribute<Voucher, VoucherImage> voucherImages;
	public static volatile SetAttribute<Voucher, VoucherCode> voucherCodes;
	public static volatile SetAttribute<Voucher, Feedback> feedbacks;
	public static volatile SingularAttribute<Voucher, Instant> startTime;
	public static volatile SingularAttribute<Voucher, Long> id;
	public static volatile SingularAttribute<Voucher, Event> event;
	public static volatile SingularAttribute<Voucher, ServiceType> type;
	public static volatile SingularAttribute<Voucher, Instant> expriedTime;
	public static volatile SetAttribute<Voucher, Product> products;
	public static volatile SingularAttribute<Voucher, VoucherStatus> status;

	public static final String QUANTITY = "quantity";
	public static final String PRICE = "price";
	public static final String VOUCHER_IMAGES = "voucherImages";
	public static final String VOUCHER_CODES = "voucherCodes";
	public static final String FEEDBACKS = "feedbacks";
	public static final String START_TIME = "startTime";
	public static final String ID = "id";
	public static final String EVENT = "event";
	public static final String TYPE = "type";
	public static final String EXPRIED_TIME = "expriedTime";
	public static final String PRODUCTS = "products";
	public static final String STATUS = "status";

}

