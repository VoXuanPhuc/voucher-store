package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MyUser.class)
public abstract class MyUser_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<MyUser, String> lastName;
	public static volatile SetAttribute<MyUser, StoreUser> storeUsers;
	public static volatile SingularAttribute<MyUser, Address> address;
	public static volatile SingularAttribute<MyUser, Role> role;
	public static volatile SingularAttribute<MyUser, String> gender;
	public static volatile SetAttribute<MyUser, MyOrder> myOrders;
	public static volatile SetAttribute<MyUser, Feedback> feedbacks;
	public static volatile SingularAttribute<MyUser, String> firstName;
	public static volatile SingularAttribute<MyUser, String> password;
	public static volatile SingularAttribute<MyUser, String> phone;
	public static volatile SingularAttribute<MyUser, Long> id;
	public static volatile SingularAttribute<MyUser, String> email;
	public static volatile SingularAttribute<MyUser, String> username;
	public static volatile SetAttribute<MyUser, Gift> gifts;

	public static final String LAST_NAME = "lastName";
	public static final String STORE_USERS = "storeUsers";
	public static final String ADDRESS = "address";
	public static final String ROLE = "role";
	public static final String GENDER = "gender";
	public static final String MY_ORDERS = "myOrders";
	public static final String FEEDBACKS = "feedbacks";
	public static final String FIRST_NAME = "firstName";
	public static final String PASSWORD = "password";
	public static final String PHONE = "phone";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";
	public static final String GIFTS = "gifts";

}

