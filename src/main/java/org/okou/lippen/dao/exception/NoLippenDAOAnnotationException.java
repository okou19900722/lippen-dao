package org.okou.lippen.dao.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class NoLippenDAOAnnotationException extends RuntimeException
{
	private static final long serialVersionUID = -6335377899907672156L;

	public NoLippenDAOAnnotationException(AnnotatedElement element, Annotation annotation)
	{
		super("type " + element + " need a annotation with " + annotation.getClass());
	}
}
