package no.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class CommunityDetectorTester {

	@Test
	public void testNeighborhoodOf() {
		
		try {
			
			Class c = Class.forName("CommunityDetector");
			Method method = c.getDeclaredMethod("neighborhoodOf", new Class[] { Integer.class, Integer.class});
			Object instance = c.newInstance();
			Object returned = method.invoke(instance, new Object[] { 1, 2 });
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}	
	}

	private void invokePrivateMethod(Class targetClass, Object targetObject, String methodName,
			Class[] argClasses, Object[] argObjects) {
		
		try {
			Method method = targetClass.getDeclaredMethod(methodName, argClasses);
			method.setAccessible(true);
			method.invoke(targetObject, argObjects);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
