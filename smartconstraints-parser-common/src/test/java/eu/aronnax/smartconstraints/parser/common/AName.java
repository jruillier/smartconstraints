package eu.aronnax.smartconstraints.parser.common;

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
        return this.str.contentEquals(cs);
    }

    @Override
    public int length() {
        return this.str.length();
    }

    @Override
    public char charAt(int index) {
        return this.str.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.str.subSequence(start, end);
    }

    @Override
    public String toString() {
        return this.str;
    }
}
