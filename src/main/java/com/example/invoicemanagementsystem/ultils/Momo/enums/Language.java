package com.example.invoicemanagementsystem.ultils.Momo.enums;
import com.example.invoicemanagementsystem.ultils.Momo.shared.constants.Constants;
import com.google.gson.annotations.SerializedName;

public enum Language {

    @SerializedName("vi")
    VI(Constants.LANGUAGE_VI),

    @SerializedName("en")
    EN(Constants.LANGUAGE_EN);

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public static Language findByName(String name) {
        for (Language type : values()) {
            if (type.getLanguage().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String getLanguage() { return value; }

}
