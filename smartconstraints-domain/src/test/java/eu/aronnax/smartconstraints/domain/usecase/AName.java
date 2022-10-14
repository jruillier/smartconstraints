package eu.aronnax.smartconstraints.domain.usecase;

import javax.lang.model.element.Name;

/**
 * Just a data class to make testing easier.
 */
class AName implements Name {

    private final String str;

    AName(String str) {
        this.str = str;
    }

    @Override
    public boolean contentEquals(CharSequence cs) {
        return str.contentEquals(cs);
    }

    @Override
    public int length() {
        return str.length();
    }

    @Override
    public char charAt(int index) {
        return str.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return str.subSequence(start, end);
    }
}
