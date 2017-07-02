package cn.digitalpublishing.util;

import java.lang.reflect.Method;
import java.util.Map;

public class Map2Object {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object convert(Map<String,Object> map,String className)throws Exception{
		Object obj = null;
		obj = Class.forName(className).newInstance();
		for (Map.Entry<String,Object> entry : map.entrySet()) {
			   String key = entry.getKey().toString();
			   Object value = entry.getValue();
			   String fieldName=key;
			   //属性的Set方法名
			   String setField="set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());
			   //属性类型
			   Class fieldClass;
			   //属性的Set方法
			   Method setFieldMtd;
			   //值类型
			   Class valueClass;
			   if(value!=null){
					fieldClass=obj.getClass().getDeclaredField(fieldName).getType();
					valueClass=value.getClass();
					//获得属性的Set方法
					setFieldMtd=obj.getClass().getDeclaredMethod(setField,fieldClass);
					if(fieldClass.getName().equalsIgnoreCase(valueClass.getName())){
						//将属性的值赋值给对象Obj相关的属性
						setFieldMtd.invoke(obj,value);
					}else{
						if(value instanceof Map)
						setFieldMtd.invoke(obj, convert((Map<String,Object>)value,fieldClass.getName()));
					}
				}
		}
		return obj;
	}

}
