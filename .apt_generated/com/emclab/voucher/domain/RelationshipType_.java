package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RelationshipType.class)
public abstract class RelationshipType_ {

	public static volatile SetAttribute<RelationshipType, StoreUser> storeUsers;
	public static volatile SingularAttribute<RelationshipType, String> name;
	public static volatile SingularAttribute<RelationshipType, Long> id;

	public static final String STORE_USERS = "storeUsers";
	public static final String NAME = "name";
	public static final String ID = "id";

}

