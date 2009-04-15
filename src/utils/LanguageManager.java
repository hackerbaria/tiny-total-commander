/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.*;

/**
 *
 * @author Spazee
 */
public class LanguageManager {

    private Locale[] supportedLocale = {
        Locale.ENGLISH,
        new Locale("en", "US"),
        new Locale("es", "ES"),
        new Locale("vi", "VN")
    };

    private ResourceBundle labels;

    public LanguageManager(){

        labels =
            ResourceBundle.getBundle("Languages\\LanguageSupport",supportedLocale[0]);
    }

    public void ChangeLanguage(int index){

        labels = 
           ResourceBundle.getBundle("Languages\\LanguageSupport",supportedLocale[index]);
    }

    public void ChangeLanguage(String Lang){

        if(Lang.compareTo("united state") == 0){

            labels =
           ResourceBundle.getBundle("Languages\\LanguageSupport",supportedLocale[1]);
        }
        else if(Lang.compareTo("spain") == 0){

            labels =
           ResourceBundle.getBundle("Languages\\LanguageSupport",supportedLocale[2]);
        }
        else if (Lang.compareTo("vietnam") == 0){

            labels =
           ResourceBundle.getBundle("Languages\\LanguageSupport",supportedLocale[3]);
        }
        else {

            labels =
           ResourceBundle.getBundle("Languages\\LanguageSupport",supportedLocale[0]);
        }

    }

    public String TranslateLang(String NameComponent){

        return labels.getString(NameComponent);
    }
}
