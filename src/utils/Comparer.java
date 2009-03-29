/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.Comparator;

/**
 *
 * @author pmchanh
 */
public class Comparer implements Comparator {
   
    public int compare(Object o1, Object o2) {
       Object[] item1 = (Object[]) o1;
                    Object[] item2 = (Object[]) o2;
                    String name1 = (String) item1[0];
                    String name2 = (String) item2[0];
                    return name1.compareToIgnoreCase(name2);
    }

}
