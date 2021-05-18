package org.example.common.utils;


import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 管理工具类
 */
public class CommonUtil {


  public static String getUUIDKey() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

  /**
   * String为null时转换为"",同时过滤掉前后空格
   */
  public static String filerStr(String str) {
    if (str == null)
      return "";
    return str.trim();
  }

  /**
   * 判断对象值是否为空： 若对象为字符串，判断对象值是否为null或空格; 若对象为数组，判断对象值是否为null，或数组个数是否为0;
   * 若对象为List。判断对象值是否为null，或List元素是否个数为0; 其他类型对象，只判断值是否为null.
   *
   * @param value
   * @return true-是
   */
  public static boolean isEmpty(Object value) {
    if (value == null) {
      return true;
    } else if ((value instanceof String)
            && (((String) value).trim().length() < 1)) {
      return true;
    } else if (value.getClass().isArray()) {
      if (0 == java.lang.reflect.Array.getLength(value)) {
        return true;
      }
    } else if (value instanceof List) {
      if (((List) value).isEmpty()) {
        return true;
      }
    } else if(value instanceof Set) {
      if(((Set)value).isEmpty()) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判空操作
   * @param value
   * @return
   */
  public static boolean isBlank(String value) {
    value = filerStr(value);
    return value == null || "".equals(value) || "null".equals(value) || "undefined".equals(value);
  }

}