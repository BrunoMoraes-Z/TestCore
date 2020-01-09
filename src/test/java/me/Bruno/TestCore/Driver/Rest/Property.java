package me.Bruno.TestCore.Driver.Rest;

public class Property {

    private String key, value;

    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

}
