package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Role, String> code;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile SetAttribute<Role, MyUser> myUsers;
	public static volatile SingularAttribute<Role, Long> id;

	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String MY_USERS = "myUsers";
	public static final String ID = "id";

}

