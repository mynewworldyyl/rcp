package com.digitnexus.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.digitnexus.base.uidef.FieldDef;

public class ReflectUtils {

	private static final ReflectUtils instance = new ReflectUtils();
	private ReflectUtils(){};
	public static ReflectUtils getInstance(){return instance;}
	
	public String getFieldValue(Object element, String fieldName) {
		try {
			Field f = this.getField(element.getClass(), fieldName);
			f.setAccessible(true);
			Object vo = f.get(element);
			if(vo != null) {
				String value = vo.toString();
				return value;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	   return "";
	}
	
	public Field getField(Class cls,String fieldName) {
		if(cls == null || cls == Object.class) {
			return null;
		}
		
		Field f = null;
		try {
			 f = cls.getDeclaredField(fieldName);
			
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(f == null) {
			return this.getField(cls.getSuperclass(), fieldName);
		}
		return f;
	}
	
	public String setFieldValue(Object element, FieldDef fd,Object fieldValue) {
		try {
			if(element == null) {
				throw new NullPointerException();
			}
			String clsName = fd.getClsName();
			if(clsName == null) {
				throw new NullPointerException();
			}
			Class<?> type = null; 
			Object value = fieldValue;
			if("float".equals(clsName)) {
				type =float.class;
				value = Float.valueOf(fieldValue.toString());
			}else if("byte".equals(clsName)) {
				type =byte.class;
				value = Byte.valueOf(fieldValue.toString());
			}else if("short".equals(clsName)) {
				type =short.class;
				value = Short.valueOf(fieldValue.toString());
			}else if("int".equals(clsName)) {
				type =int.class;
				value = Integer.valueOf(fieldValue.toString());
			}else if("double".equals(clsName)) {
				type =double.class;
				value = Double.valueOf(fieldValue.toString());
			}else if("boolean".equals(clsName)) {
				type =boolean.class;
				value = Boolean.valueOf(fieldValue.toString());
			}else if("long".equals(clsName)) {
				type =long.class;
				value = Long.valueOf(fieldValue.toString());
			} else {
				type = ReflectUtils.class.getClassLoader().loadClass(clsName);
				if(Byte.class.getName().equals(clsName)) {
					value = Byte.valueOf(fieldValue.toString());
				}else if(Short.class.getName().equals(clsName)) {
					value = Short.valueOf(fieldValue.toString());
				}else if(Integer.class.getName().equals(clsName)) {
					value = Integer.valueOf(fieldValue.toString());
				}else if(Long.class.getName().equals(clsName)) {
					value = Long.valueOf(fieldValue.toString());
				}else if(Double.class.getName().equals(clsName)) {
					value = Double.valueOf(fieldValue.toString());
				}else if(Float.class.getName().equals(clsName)) {
					value = Float.valueOf(fieldValue.toString());
				}else if(Boolean.class.getName().equals(clsName)) {
					value = Boolean.valueOf(fieldValue.toString());
				}else if(String.class.getName().equals(clsName)) {
					String[] vs = this.getStringArray(value);
					if(vs != null) {
						value = vs[0];
					}
				}else if(java.util.List.class.getName().equals(clsName)) {
					String[] vs = this.getStringArray(value);
					if(vs != null) {
						value = Arrays.asList(vs);
					}
				}else if(java.util.Set.class.getName().equals(clsName)) {
					String[] vs = this.getStringArray(value);
					if(vs != null) {
						Set s = new HashSet();
						s.addAll(Arrays.asList(vs));
						value = s;
					}
				}else if((new String[0]).getClass().getName().equals(clsName)) {
					value = this.getStringArray(value);
				}
			}
			String methodName = "set" + fd.getFieldName().substring(0,1).toUpperCase() + fd.getFieldName().substring(1);
			Method method = element.getClass().getDeclaredMethod(methodName,type);
			if(method == null) {
				throw new NoSuchMethodException(methodName + " for field " + fd.getFieldName() );
			}
			method.invoke(element, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	   return null;
	}
	
	
	private String[] getStringArray(Object value) {
        if(value == null) {
        	return null;
        }
        String[] vs = null;
		if(value instanceof String) {
			vs = Utils.getInstance().stringToArray((String)value);
		}else if(value instanceof Set) {
			Set s = (Set) value;
			vs = new String[s.size()];
			int index = 0;
			for(Object o : s) {
				vs[index++] = o.toString();
			}
		}else if(value instanceof String[]) {
			vs = (String[])value;
		}else if(value instanceof List) {
			List s = (List) value;
			vs = new String[s.size()];
			int index = 0;
			for(Object o : s) {
				vs[index++] = o.toString();
			}
		}
	return vs;
	}
	
	public Object newInstance(String clsName,ClassLoader classLoader) {
		 try {
			 Class<?> itemClass = null;
			 if(classLoader == null) {
				 itemClass =  ReflectUtils.class.getClassLoader().loadClass(clsName);
			 }else {
				 itemClass = classLoader.loadClass(clsName);
			 }
			return itemClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		 
	}
}
