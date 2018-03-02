package outerhaven.cip.listing.three;

import java.math.BigInteger;
import java.util.Arrays;

import outerhaven.cip.common.Immutable;
import outerhaven.cip.common.PhantomCode;
import outerhaven.cip.common.ThreadSafe;

/**
 * Listing 3.13. Caching the Last Result Using a Volatile Reference to an Immutable Holder Object.<br><br>
 * This combination of an immutable holder object for multiple state variables related by an invariant, 
 * and a volatile reference used to ensure its timely visibility, allows VolatileCachedFactorizer 
 * to be thread-safe even though it does no explicit locking.<br><br>
 * 
 * However, immutable objects can sometimes provide a weak form of atomicity.<br>
 * Example: Using Volatile to Publish Immutable Objects<br><br>
 * 
 * Race conditions in accessing or updating multiple related variables can be eliminated by 
 * using an immutable object to hold all the variables.<br><br>
 * 
 * PS: It does guarantee atomic operation without explicit synchronization, however, it doesn't prevent 
 * duplicated calculation, mutileple requests for same value coming at same time would be calculated multiple times.
 * @author threepwood
 *
 */
@ThreadSafe
public class VolatileCachedFactorizer implements Servlet {
    private volatile OneValueCache cache = new OneValueCache(null, null);

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = cache.getFactors(i);
        if (factors == null) {
            factors = factor(i);
            cache = new OneValueCache(i, factors);
        }
        encodeIntoResponse(resp, factors);
    }
    
    @PhantomCode
    private BigInteger[] factor(BigInteger i){
    	return null;
    }
    @PhantomCode
    private BigInteger extractFromRequest(ServletRequest req){
    	return null;
    }
    
    @PhantomCode
    private void encodeIntoResponse (ServletResponse resp, BigInteger[] factors){
    	
    }
    
    @PhantomCode
    private class ServletRequest{
    	
    }
    @PhantomCode
    private class ServletResponse{
    	
    }
}
@PhantomCode
interface Servlet{
	
}

/**
 * Listing 3.12. Immutable Holder for Caching a Number and its Factors.
 * 
 * @author threepwood
 *
 */
@Immutable
class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    // BigInteger is immutable, so need not to be copied
    public OneValueCache(BigInteger i, BigInteger[] factors) {
        lastNumber = i;
        lastFactors = Arrays.copyOf(factors, factors.length);
    }
    // even publish the copy so as to keep itself immutable
    public BigInteger[] getFactors(BigInteger i) {
        if (lastNumber == null || !lastNumber.equals(i))
            return null;
        else
            return Arrays.copyOf(lastFactors, lastFactors.length);
    }
}