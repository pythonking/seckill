package com.kaishengit.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * 序列化：就是将一个对象转换为二进制的数据流。
 * 		这样就可以进行传输，或者保存到文件中。
 * 		如果一个类的对象要想实现序列化，就必须实现serializable接口。
 * 		序列化接口：此接口中没有任何的方法，只是作为一个标识，表示本类的对象具备了序列化的能力而已。
 * 反序列化:将二进制数据流转换成相应的对象。
 * 		如果想要完成对象的序列化，则还要依靠ObjectOutputStream和ObjectInputStream,前者属于序列化操作，而后者属于反序列化操作。
 */

/**
 * 序列化对象的工具类
 */
public class SerializationUtil {

	private static Logger log = LoggerFactory
			.getLogger(SerializationUtil.class);

	/**
	 * 序列化对象
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			log.error("{}对象序列化出现异常,{}", object, e);
		}
		return null;
	}

	/**
	 * 反序列化成对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			log.error("反序列化成对象出现异常,{}", e);
		}
		return null;
	}

}