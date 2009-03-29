/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import ExtendComponent.TextImageObj;
import java.util.Comparator;

/**
 *
 * @author pmchanh
 */
public class Comparer implements Comparator {
   
    public int compare(Object o1, Object o2) {
       Object[] item1 = (Object[]) o1;
       Object[] item2 = (Object[]) o2;
      TextImageObj name1 = (TextImageObj) item1[0];
      TextImageObj name2 = (TextImageObj) item2[0];
      return name1.getText().compareToIgnoreCase(name2.getText());
    }

}
