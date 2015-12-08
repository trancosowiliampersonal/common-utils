package com.dalcim.utils;

import java.lang.reflect.Field;

import com.dalcim.annotations.BindProperty;

public class ObjectConverter {
	
	public static <T> T setFields(Object de, Class<T> para) throws Exception {
		
		Class<?> fieldsAnnotations = de.getClass();
		T objRetorno = para.newInstance();
		
		BindProperty bp;
		String nameProperty = null;
		
		try {
			for (Field f : fieldsAnnotations.getDeclaredFields()) {
				
				bp = f.getAnnotation(BindProperty.class);
				nameProperty = !bp.value().isEmpty() ? bp.value() : f.getName();
				
				if (bp != null) {

					
					Field ft;
					
					ft = para.getDeclaredField( nameProperty );
						
					f.setAccessible(true);
					ft.setAccessible(true);
					
					ft.set(objRetorno, f.get(de));
					
					ft.setAccessible(false);
					f.setAccessible(false);
				}
				
			}
		}catch(Exception e){
			
		}
		
		return objRetorno;
	}
	
}
