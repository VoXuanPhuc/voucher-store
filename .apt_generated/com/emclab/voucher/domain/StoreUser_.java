package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StoreUser.class)
public abstract class StoreUser_ {

	public static volatile SingularAttribute<StoreUser, Long> id;
	public static volatile SingularAttribute<StoreUser, Store> store;
	public static volatile SingularAttribute<StoreUser, RelationshipType> type;
	public static volatile SingularAttribute<StoreUser, MyUser> user;

	public static final String ID = "id";
	public static final String STORE = "store";
	public static final String TYPE = "type";
	public static final String USER = "user";

}

