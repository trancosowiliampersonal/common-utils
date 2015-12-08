package com.dalcim.utils;

import java.lang.reflect.Field;

import com.dalcim.annotations.BindProperty;

public class ObjectConverter {
	
	public static <T> T setFields(Object de, Class<T> para) throws Exception {
		
		Class<?> fieldsAnnotations = de.getClass();
		T objRetorno = para.newInstance();
		
		BindProperty bp;
		String nameProperty = null;
		Class<?> classAux = null;
		
		Field ft;
		Object aux;
		
		try {
			for (Field field : fieldsAnnotations.getDeclaredFields()) {
				
				bp = field.getAnnotation(BindProperty.class);
				nameProperty = !bp.value().isEmpty() ? bp.value() : field.getName();
				
				if (bp != null) {

					if (nameProperty.contains(".")) {
						
						classAux = para;
						
						String[] names = nameProperty.split("\\.");
						
						Field[] fields = new Field[names.length];
	
						aux = objRetorno;
						for ( int i = 0; i < fields.length; i++ ) {
							fields[i] = classAux.getDeclaredField(names[i] );
							if(i < fields.length - 1){
								fields[i].setAccessible(true);
								
								if (fields[i].get(aux) == null) {
									fields[i].set(aux, fields[i].getType().newInstance());
									aux = fields[i].get(aux);
								}
								fields[i].setAccessible(false);
							}
							classAux = fields[i].getType();
						}
						
						ft = fields[fields.length - 1];
						
					} else{
						ft = para.getDeclaredField( nameProperty );
						aux = objRetorno;
					}
					
					field.setAccessible(true);
					ft.setAccessible(true);
					
					ft.set(aux, field.get(de));
					
					ft.setAccessible(false);
					field.setAccessible(false);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return objRetorno;
	}
	
}
