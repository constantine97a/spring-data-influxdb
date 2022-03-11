package org.springframework.data.influxdb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking a class property as non-modeled. Applied to the getter
 * method or the class field for a non-modeled property. If the annotation is
 * applied directly to the class field, the corresponding getter and setter must
 * be declared in the same class.
 * <p>
 * All getter methods not marked with this annotation are assumed to be modeled
 * properties and included in any save() requests.
 *
 * @author yuri.yin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Ignore {

}
