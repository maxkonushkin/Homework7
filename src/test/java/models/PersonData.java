package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonData {

    private String gender;

    public String getGender() {
        return gender;
    }

}
