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
        String ENGLISH = "english";
        String HINDI = "hindi";
        String SHOW_SMS_SELECED_DATA = "SHOW_SMS_SELECED_DATA";
    }

    interface INTENT_PARAMS{
        String IS_SUB_CATEGORY_SMS = "is_sub_category_sms";
        String IS_SUB_CATEGORY_JOKES = "is_sub_category_jokes";
        String IS_SUB_CATEGORY_MEMES = "is_sub_category_memes";
        String SMS_SLUG = "sms_slug";
        String JOKE_SLUG = "joke_slug";
        String MEME_SLUG = "meme_slug";
        String SMS_CATEGORY = "sms_category";
        String SHOW_SMS_FRAGMENT = "show_sms_fragment";
        String SHOW_JOKES_FRAGMENT = "show_jokes_fragment";
        String SHOW_MEMES_FRAGMENT = "show_memes_fragment";
        String NAVIGATE_FROM_HOME = "navigate_from_home";
        String NAVIGATE_TYPE = "navigate_type";
        String NAVIGATE_SLUG = "navigate_slug";

    }
}
