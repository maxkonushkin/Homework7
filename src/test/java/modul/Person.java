package modul;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person
{
    @JsonProperty("Value")
    private String value;
    private String unrestricted_value;
    private PersonInner data;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnrestricted_value() {
        return unrestricted_value;
    }

    public void setUnrestricted_value(String unrestricted_value) {
        this.unrestricted_value = unrestricted_value;
    }

    public PersonInner getData() {
        return data;
    }

    public void setData(PersonInner data) {
        this.data = data;
    }
}
