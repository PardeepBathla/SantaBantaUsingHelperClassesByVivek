package com.app.santabanta.Utils;

public interface GlobalConstants {

    interface API {
        String BASE_URL = "https://santabantaapi.techpss.com/api/v1/";
    }

    interface COMMON {
        String THEME_MODE_LIGHT = "theme_mode_light";
        String LANGUAGE_SELECTED = "english";
        String SHOW_SMS = "SHOW_SMS";
        String WHATSAPP = "WHATSAPP";
        String TWITTER = "TWITTER";
        String SHOW_MEMES_SELECED_DATA = "SHOW_MEMES_SELECED_DATA";
    }

    interface INTENT_PARAMS{
        String IS_SUB_CATEGORY_SMS = "is_sub_category_sms";
        String IS_SUB_CATEGORY_JOKES = "is_sub_category_jokes";
        String SMS_SLUG = "sms_slug";
        String JOKE_SLUG = "joke_slug";
        String SMS_CATEGORY = "sms_category";
        String SHOW_SMS_FRAGMENT = "show_sms_fragment";
        String SHOW_JOKES_FRAGMENT = "show_jokes_fragment";
    }
}
