package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Gift.class)
public abstract class Gift_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Gift, VoucherCode> voucher;
	public static volatile SingularAttribute<Gift, Long> id;
	public static volatile SingularAttribute<Gift, String> message;
	public static volatile SingularAttribute<Gift, MyUser> giver;

	public static final String VOUCHER = "voucher";
	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String GIVER = "giver";

}

