package outerhaven.cip.common;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

/**
 * indicate target codes have no meanings, but only to get rid of compilation error.
 * @author threepwood
 *
 */
@Retention(RetentionPolicy.CLASS)
public @interface PhantomCode {

}
