package com.example.magnus;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class IsActiveUser extends TypeSafeMatcher<User> {
    @Override
    protected boolean matchesSafely(User user) {
        return user.isActive();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an active user");
    }

    @Override
    protected void describeMismatchSafely(User user, Description mismatchDescription) {
        mismatchDescription.appendText("was inactive: ").appendValue(user.getName());
    }

    public static IsActiveUser isActiveUser() {
        return new IsActiveUser();
    }
}
