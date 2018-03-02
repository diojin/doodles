package outerhaven.cip.listing.three;

import outerhaven.cip.common.NotThreadSafe;

/**
 * Listing 3.14. Publishing an Object without Adequate Synchronization. Don't Do this.<br><br>
 * 
 * The problem here is not the Holder class itself, but that the Holder is not properly published. 
 * However, Holder can be made immune to improper publication by declaring the n field to be final, 
 * which would make Holder immutable; see Section 3.5.2.<br><br>
 * 
 * Because immutable objects are so important, the Java Memory Model offers a special guarantee of 
 * initialization safety for sharing immutable objects.<br>
 * 
 * Immutable objects can be used safely by any thread without additional synchronization, 
 * even when synchronization is not used to publish them.<br><br>
 * 
 * Because synchronization was not used to make the Holder visible to other threads, 
 * we say the Holder was not properly published.<br><br>
 * 
 * Two things can go wrong with improperly published objects. Other threads could see a stale value for 
 * the holder field, and thus see a null reference or other older value even though a value has been 
 * placed in holder. But far worse, other threads could see an up-to-date value for the holder reference, 
 * but stale values for the state of the Holder.[16] <br> 
 * To make things even less predictable, a thread may see a stale value the first time it reads a field 
 * and then a more up-to-date value the next time, which is why assertSanity can throw AssertionError.<br>
 * [16] While it may seem that field values set in a constructor are the first values written to those fields 
 * and therefore that there are no "older" values to see as stale values, the Object constructor 
 * first writes the default values to all fields before subclass constructors run. 
 * It is therefore possible to see the default value for a field as a stale value.
 * 
 * @author threepwood
 *
 */
@NotThreadSafe
public class UnsafePublication {
    // Unsafe publication
    public Holder holder;

    public void initialize() {
        holder = new Holder(42);
    }
}

/**
 * Listing 3.15. Class at Risk of Failure if Not Properly Published.
 * @author threepwood
 *
 */
class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void assertSanity() {
        if (n != n)
            throw new AssertionError("This statement is false.");
    }
}
