package com.journaldev.spring.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.huxhorn.sulky.ulid.ULID;
import de.huxhorn.sulky.ulid.ULID.Value;

/**
 * UUID產生器
 * 
 * @author 1108013
 *
 */
public class UUIDUtil {

	protected static Logger logger = LoggerFactory.getLogger(UUIDUtil.class);

	private static ULID ulid = new ULID();
    private static Value previousValue = ulid.nextValue();
	
	/**
	 * 取得32長度之UUID
	 */
	public static String getUUID() {
		String uuid = java.util.UUID.randomUUID().toString();
		return uuid;
	}
	
	/**
	 * 產生26長度依時間排序的ULID    <br>
	 * 碰撞機率極低(等同於UUID)    <br>
	 * 比UUID更適合用來當table的primary key
	 */
	public synchronized static String getSeqULID() {
	    previousValue = ulid.nextMonotonicValue(previousValue);
		return previousValue.toString();
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
		    UUIDUtil.getSeqULID();
		}
		long endTime = System.nanoTime();
		System.out.println("產生10萬筆ID,執行時間:" + ((endTime - startTime) / 1000000) + "毫秒");

		System.out.println(UUIDUtil.getSeqULID());
        System.out.println(UUIDUtil.getSeqULID());
        System.out.println(UUIDUtil.getSeqULID());
	}

}
