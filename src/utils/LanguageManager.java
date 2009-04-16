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
        new Locale("fr", "FR"),
        new Locale("vi", "VN")
    };

    private String currCountry = "";
    private String currLanguage = "";
    private ResourceBundle labels;

    public LanguageManager(){

        labels =
            ResourceBundle.getBundle("Languages\\LanguageSupport",supportedLocale[1]);

        currCountry = supportedLocale[1].getCountry();
        currLanguage = supportedLocale[1].getLanguage();
    }

    public Boolean ChangeLanguage(int index){

        // kiem tra gia tri hien tai
        //
        String newCountry = supportedLocale[index].getCountry();
        String newLanguage = supportedLocale[index].getLanguage();

        if((currCountry != newCountry) && (currLanguage != newLanguage)) {
            
            labels =
                ResourceBundle.getBundle("Languages\\LanguageSupport",supportedLocale[index]);

            currCountry = newCountry;
            currLanguage = newLanguage;

            return true;
        }

        return false;
    }

    public Boolean ChangeLanguage(String Lang){

        Locale newLocale;
        String newCountry = "";
        String newLanguage = "";
   
        if(Lang.compareTo("spain") == 0){

            newLocale = supportedLocale[2];
        }
        else if(Lang.compareTo("french") == 0){

            newLocale = supportedLocale[3];
        }
        else if (Lang.compareTo("vietnam") == 0){

            newLocale = supportedLocale[4];
        }
        else {

            newLocale = supportedLocale[1];
        }

        newCountry = newLocale.getCountry();
        newLanguage = newLocale.getLanguage();

        if((currCountry != newCountry) && (currLanguage != newLanguage)){

            labels =
                 ResourceBundle.getBundle("Languages\\LanguageSupport",newLocale);

            currCountry = newCountry;
            currLanguage = newLanguage;

            return true;
        }

        return false;
    }

    public String TranslateLang(String NameComponent){

        return labels.getString(NameComponent);
    }
}
