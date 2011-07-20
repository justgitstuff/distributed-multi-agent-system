package de.hft_stuttgart.sopro.agent.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This code was copied and adapted from:
 * http://www.javaworld.com/javaworld/javatips/jw-javatip76.html?page=2
 * 
 * @author Dave Miller
 * @author Eduard Tudenhoefner - nastra@gmx.net
 */
public class ObjectCloner {
	/**
	 * Default constructor which should not be called from outside.
	 */
	private ObjectCloner() {
	}

	/**
	 * @param oldObj
	 *            The object from which a deep copy should be created.
	 * @return A deep copy of an object.
	 * @throws Exception
	 *             When something went wrong.
	 */
	static public Object deepCopy(Object oldObj) throws Exception {
		ObjectOutputStream objectOutputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteOutputStream);
			// serialize and pass the object
			objectOutputStream.writeObject(oldObj);
			objectOutputStream.flush();
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());
			objectInputStream = new ObjectInputStream(byteInputStream);
			return objectInputStream.readObject();
		} catch (Exception e) {
			System.out.println("Exception in ObjectCloner = " + e);
			throw (e);
		} finally {
			objectOutputStream.close();
			objectInputStream.close();
		}
	}
}
