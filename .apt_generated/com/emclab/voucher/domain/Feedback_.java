package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Feedback.class)
public abstract class Feedback_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Feedback, Integer> rate;
	public static volatile SingularAttribute<Feedback, Voucher> voucher;
	public static volatile SetAttribute<Feedback, FeedbackImage> feedbackImages;
	public static volatile SingularAttribute<Feedback, Long> id;
	public static volatile SingularAttribute<Feedback, String> detail;
	public static volatile SingularAttribute<Feedback, MyUser> user;

	public static final String RATE = "rate";
	public static final String VOUCHER = "voucher";
	public static final String FEEDBACK_IMAGES = "feedbackImages";
	public static final String ID = "id";
	public static final String DETAIL = "detail";
	public static final String USER = "user";

}

