package cn.digitalpublishing.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ObjectUtil<T>
{
  public T setNull(T obj, String[] types)
  {
    Field[] fields = obj.getClass().getDeclaredFields();
    try {
      for (Field field : fields) {
        Object o = null;
        String className = field.getType().getName();
        String fieldName = field.getName();
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
        Method mtd = obj.getClass().getDeclaredMethod(methodName, new Class[0]);
        o = mtd.invoke(obj, new Object[0]);
        for (String type : types)
          if (className.equals(type)) {
            fieldName = field.getName();
            methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
            mtd = obj.getClass().getDeclaredMethod(methodName, new Class[] { Class.forName(type) });
            Object ob = null;
            mtd.invoke(obj, new Object[] { ob });
          }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return obj;
  }
}