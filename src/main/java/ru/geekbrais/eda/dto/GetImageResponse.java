package ru.geekbrais.eda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.geekbrais.eda.service.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetImageResponse extends ServiceResponse<GetImageResponse.ImageData> {

    @Data
    public static class ImageData {

        @JsonProperty("id")
        public String id;
        @JsonProperty("title")
        public String title;
        @JsonProperty("description")
        public String description;
        @JsonProperty("datetime")
        public Integer datetime;
        @JsonProperty("type")
        public String type;
        @JsonProperty("animated")
        public Boolean animated;
        @JsonProperty("width")
        public Integer width;
        @JsonProperty("height")
        public Integer height;
        @JsonProperty("size")
        public Integer size;
        @JsonProperty("views")
        public Integer views;
        @JsonProperty("bandwidth")
        public Integer bandwidth;
        @JsonProperty("vote")
        public String vote;
        @JsonProperty("favorite")
        public Boolean favorite;
        @JsonProperty("nsfw")
        public Boolean nsfw;
        @JsonProperty("section")
        public String section;
        @JsonProperty("account_url")
        public String accountUrl;
        @JsonProperty("account_id")
        public Integer accountId;
        @JsonProperty("is_ad")
        public Boolean isAd;
        @JsonProperty("in_most_viral")
        public Boolean inMostViral;
        @JsonProperty("has_sound")
        public Boolean hasSound;
        @JsonProperty("tags")
        public List<Object> tags = new ArrayList<>();
        @JsonProperty("ad_type")
        public Integer adType;
        @JsonProperty("ad_url")
        public String adUrl;
        @JsonProperty("edited")
        public String edited;
        @JsonProperty("in_gallery")
        public Boolean inGallery;
        @JsonProperty("deletehash")
        public String deletehash;
        @JsonProperty("name")
        public String name;
        @JsonProperty("link")
        public String link;
        @JsonProperty("ad_config")
        public AdConfig adConfig;
    }

    public static class AdConfig {

        @JsonProperty("safeFlags")
        public List<String> safeFlags = new ArrayList<>();
        @JsonProperty("highRiskFlags")
        public List<Object> highRiskFlags = new ArrayList<>();
        @JsonProperty("unsafeFlags")
        public List<String> unsafeFlags = new ArrayList<>();
        @JsonProperty("wallUnsafeFlags")
        public List<Object> wallUnsafeFlags = new ArrayList<>();
        @JsonProperty("showsAds")
        public Boolean showsAds;

    }
}
