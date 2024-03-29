package eu.aronnax.smartconstraints.parser.common;

record KeyValueDto<V>(String key, V value) {
    public <NV> KeyValueDto<NV> newWithValue(NV newValue) {
        return new KeyValueDto<>(this.key, newValue);
    }
}
