package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Village.class)
public abstract class Village_ {

	public static volatile SetAttribute<Village, Address> addresses;
	public static volatile SingularAttribute<Village, District> district;
	public static volatile SingularAttribute<Village, String> name;
	public static volatile SingularAttribute<Village, Long> id;

	public static final String ADDRESSES = "addresses";
	public static final String DISTRICT = "district";
	public static final String NAME = "name";
	public static final String ID = "id";

}

