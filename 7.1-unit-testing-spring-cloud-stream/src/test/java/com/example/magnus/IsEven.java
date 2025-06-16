package com.example.magnus;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class IsEven extends TypeSafeMatcher<Integer> {
    /**
     * Subclasses should implement this. The item will already have been checked for
     * the specific type and will never be null.
     *
     * @param item
     */
    @Override
    protected boolean matchesSafely(Integer item) {
        return item % 2 == 0;
    }

    /**
     * Generates a description of the object.  The description may be part of a
     * a description of a larger object of which this is just a component, so it
     * should be worded appropriately.
     *
     * @param description The description to be built or appended to.
     */
    @Override
    public void describeTo(Description description) {
        description.appendText("an even number");
    }

    /**
     * Subclasses should override this. The item will already have been checked for
     * the specific type and will never be null.
     *
     * @param item
     * @param mismatchDescription
     */
    @Override
    protected void describeMismatchSafely(Integer item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
        mismatchDescription.appendText("Was ").appendValue(item);
    }

    /**
        the full error message becomnes:
        Expected: an even number
        but: was 3
     */
    public static IsEven isEven() {
        return new IsEven();
    }
}
