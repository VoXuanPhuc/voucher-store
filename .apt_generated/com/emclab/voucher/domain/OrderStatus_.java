package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderStatus.class)
public abstract class OrderStatus_ {

	public static volatile SetAttribute<OrderStatus, MyOrder> myOrders;
	public static volatile SingularAttribute<OrderStatus, String> name;
	public static volatile SingularAttribute<OrderStatus, Long> id;

	public static final String MY_ORDERS = "myOrders";
	public static final String NAME = "name";
	public static final String ID = "id";

}

