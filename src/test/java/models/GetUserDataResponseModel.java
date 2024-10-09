package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserDataResponseModel {

    private int page, total;
    @JsonProperty("total_pages")
    private String totalPages;
    @JsonProperty("per_page")
    private String perPage;
    private List<UserDataList> data;

}
