package com.app.santabanta.Callbacks;

public interface DrawerMenuClickListener {

    void onSmsClicked(String slugName,String slugId,String category);
    void onJokesClicked(String slug,String slugId);
    void onMemesClicked(String slug,String slugId);
}
