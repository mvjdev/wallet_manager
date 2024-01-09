package project.wallet.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)/*Executer seulement en runtime*/
@Target(FIELD) /*Attribut pour les column*/
public @interface Column {
  String name() default "";
  String defaultValue() default "";
  String columnType() default ColumnType.NONE;


  int size() default 0;
  int precision() default 0;
  int scale() default 0;


  boolean required() default false; /*true = NOT NULL , false = NULL */
  boolean identity() default false;/*true = PRIMARY KEY , false = */
  boolean references() default false;/*true = NOT NULL , false = NULL */
  boolean unique() default false;/*true = unique , false = */

  GenerativeValue generative() default GenerativeValue.NONE;
}
