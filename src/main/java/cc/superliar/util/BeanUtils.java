package cc.superliar.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Bean utils
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/22/16
 * @since JDK1.8
 */
public class BeanUtils {

  /**
   * Bean properties copy ignore null.
   *
   * @param src     source
   * @param target  target
   */
  public static void copyPropertiesIgnoreNull(Object src, Object target) {
    org.springframework.beans.BeanUtils.copyProperties(src, target, getNamesOfNullProperties(src));
  }

  /**
   * Get names of null properties
   *
   * @param source  source object
   * @return
   */
  private static String[] getNamesOfNullProperties(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);//beanWrapper是bean的一个包装类
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();//得到bean的属性，类型是propertyDescription的数组

    Set<String> emptyNames = new HashSet<>();
    for (java.beans.PropertyDescriptor pd : pds) {//遍历
      Object srcValue = src.getPropertyValue(pd.getName());//得到属性的值
      if (srcValue == null) emptyNames.add(pd.getName());//如果是null则存入数组
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

}
