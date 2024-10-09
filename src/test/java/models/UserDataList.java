package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDataList {
    private Integer id, year;
    private String name, color;
    @JsonProperty("pantone_value")
    private String pantoneValue;
}
