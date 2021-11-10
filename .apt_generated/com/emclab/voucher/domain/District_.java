package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(District.class)
public abstract class District_ {

	public static volatile SetAttribute<District, Village> villages;
	public static volatile SingularAttribute<District, Province> province;
	public static volatile SingularAttribute<District, String> name;
	public static volatile SingularAttribute<District, Long> id;

	public static final String VILLAGES = "villages";
	public static final String PROVINCE = "province";
	public static final String NAME = "name";
	public static final String ID = "id";

}

