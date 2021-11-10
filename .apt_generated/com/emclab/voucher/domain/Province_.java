package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Province.class)
public abstract class Province_ {

	public static volatile SingularAttribute<Province, String> name;
	public static volatile SetAttribute<Province, District> districts;
	public static volatile SingularAttribute<Province, Long> id;

	public static final String NAME = "name";
	public static final String DISTRICTS = "districts";
	public static final String ID = "id";

}

