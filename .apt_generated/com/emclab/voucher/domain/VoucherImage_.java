package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VoucherImage.class)
public abstract class VoucherImage_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<VoucherImage, Voucher> voucher;
	public static volatile SingularAttribute<VoucherImage, String> name;
	public static volatile SingularAttribute<VoucherImage, Long> id;

	public static final String VOUCHER = "voucher";
	public static final String NAME = "name";
	public static final String ID = "id";

}

