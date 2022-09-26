package eu.aronnax.omnivalid.domain;

import java.util.Map;

class MapEntryDto<V> implements Map.Entry<CharSequence, V> {

    private CharSequence key;
    private V value;

    public MapEntryDto(CharSequence key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public CharSequence getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}
