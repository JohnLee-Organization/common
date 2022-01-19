/**
 * Copyright (c) 2014, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @Project : FTP server
 * @Title : ArrayUtil.java
 * @Package : com.daasbank.util.util
 * @author <a href="http://www.lizhaoweb.net">李召(Jhon.Lee)</a>
 * @Date : 2014-9-11
 * @Time : 下午4:06:22
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package net.lizhaoweb.common.util.base;

import java.util.Arrays;

/**
 * <h3>数组工具</h3>
 * 
 * @author <a href="http://www.lizhaoweb.net">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-8-27<br>
 *        Revision of last commit:$Revision: 1717 $<br>
 *        Author of last commit:$Author: lizhao $<br>
 *        Date of last commit:$Date: 2014-08-27 13:48:18 +0800 (Wed, 27 Aug
 *        2014) $<br>
 *        <p></p>
 */
public class ArrayUtil {

	/**
	 * 删除数组中的元素
	 * 
	 * @param array
	 *            源数组
	 * @param index
	 *            要删除的元素INDEX
	 * @param formalArray
	 *            数组类型
	 * @return 返回删除指定索引元素且指定类型的数组。
	 */
	public static final <T> T[] remove(T[] array, int index, T[] formalArray) {
		int size = array.length;
		// T oldValue = array[index];
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(array, index + 1, array, index, numMoved);
		array = toArray(array, size - 1, formalArray);
		// return oldValue;
		return array;
	}

	/**
	 * 
	 * 指定数组大小
	 * 
	 * @param array
	 *            源数组
	 * @param length
	 *            数组大小
	 * @param formalArray
	 *            数组类型
	 * @return 返回指定大小且指定类型的数组。
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T[] toArray(Object[] array, int length, T[] formalArray) {
		if (array != null && array.length > 0) {
			int size = array.length;
			if (formalArray.length < size) {
				if (length < size) {
					return (T[]) Arrays.copyOf(array, length, formalArray.getClass());
				}
			}
			System.arraycopy(array, 0, formalArray, 0, size);
			if (formalArray.length > size)
				formalArray[size] = null;
			return formalArray;
		}
		return null;
	}

	/**
	 * 增加元素到数组.
	 * 
	 * @param array
	 *            数组
	 * @param t
	 *            元素
	 * @return 返回最终数组.
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T[] add(T[] array, T t) {
		T[] result = null;
		if (array != null) {
			int oldSize = array.length;
			int newSize = oldSize + 1;
			Object[] newArray = new Object[newSize];
			System.arraycopy(array, 0, newArray, 0, oldSize);
			newArray[oldSize] = t;
			result = (T[]) Arrays.copyOf(newArray, newSize, array.getClass());
		} else {
			result = null;
		}
		return result;
	}
}
