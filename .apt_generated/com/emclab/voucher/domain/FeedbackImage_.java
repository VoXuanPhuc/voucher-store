package com.emclab.voucher.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FeedbackImage.class)
public abstract class FeedbackImage_ extends com.emclab.voucher.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<FeedbackImage, Feedback> feedback;
	public static volatile SingularAttribute<FeedbackImage, Long> id;
	public static volatile SingularAttribute<FeedbackImage, String> content;

	public static final String FEEDBACK = "feedback";
	public static final String ID = "id";
	public static final String CONTENT = "content";

}

