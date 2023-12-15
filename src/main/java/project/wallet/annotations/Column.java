package project.wallet.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Column {
  String name() default "";
  String defaultValue() default "";
  String columnType() default ColumnType.NONE;


  int size() default 0;
  int precision() default 0;
  int scale() default 0;


  boolean required() default false;
  boolean identity() default false;
  boolean references() default false;
  boolean unique() default false;

  GenerativeValue generative() default GenerativeValue.NONE;
}
