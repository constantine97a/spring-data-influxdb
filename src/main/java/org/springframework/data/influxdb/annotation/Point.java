package org.springframework.data.influxdb.annotation;

import org.springframework.data.annotation.Persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Point {


    /**
     * @return database name of influxdb
     * <ul>
     * <li>Lowercase only</li>
     * <li>Cannot include \, /, *, ?, ", <, >, |, ` ` (space character), ,, #</li>
     * <li>Cannot start with -, _, +</li>
     * <li>Cannot be . or ..</li>
     * <li>Cannot be longer than 255 bytes (note it is bytes, so multi-byte characters will count towards the 255 limit faster) for you programming sake</li>
     * </ul>
     * </ul>
     */
    String database();


    /**
     * @return the measurement that point below to
     */
    String measurement();

    /**
     * @return Configuration whether to create a database on repository bootstrapping
     */
    boolean createDatabase() default true;

}
