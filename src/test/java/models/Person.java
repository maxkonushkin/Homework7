package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person
{
    @JsonProperty("Value")
    private String value;
    private PersonData data;

    public String getValue() {
        return value;
    }

    public PersonData getData() {
        return data;
    }

}
