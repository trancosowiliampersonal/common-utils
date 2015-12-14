package com.dalcim.utils;

import java.lang.reflect.Field;

import com.dalcim.annotations.BindClass;
import com.dalcim.annotations.BindProperty;

public class ObjectConverter {
	
	@SuppressWarnings("unchecked")
	public static <T> T setFields(Object de) throws Exception {
		
		Class<?> a = ((BindClass)de.getClass().getAnnotation(BindClass.class)).value();
		Object objRetorno = a.newInstance();
		setFields(de, objRetorno);
		
		return (T)objRetorno;
	}
	
	public static void setFields(Object de, Object objRetorno) throws Exception {
		
		Class<?> fieldsAnnotations = de.getClass();
		
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
						
						classAux = objRetorno.getClass();
						
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
						ft = objRetorno.getClass().getDeclaredField( nameProperty );
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
		
	}
	
}
