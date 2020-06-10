package com.sgcc.uap.storage.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.baomidou.mybatisplus.core.toolkit.StringPool;

/**
 * 生成ID的工具类，通过mybatis plus抽取
 * @author linbin
 *
 */
public class IdWorker {

	/**
     * 主机和进程的机器码
     */
    private static Sequence WORKER = new Sequence();

    /**
     * 毫秒格式化时间
     */
    public static final DateTimeFormatter MILLISECOND = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public static long getId() {
    	long nextId = WORKER.nextId();
    	if (nextId<=0){
    		//如果生成为0，则再生成一个，并增加一个1到3的随机数
    		nextId = WORKER.nextId() + ThreadLocalRandom.current().nextLong(1, 3); 
    	}
        return nextId;
    }

    public static String getIdStr() {
        //return String.valueOf(WORKER.nextId());
    	return String.valueOf(getId());
    }

    /**
     * 格式化的毫秒时间
     */
    public static String getMillisecond() {
        return LocalDateTime.now().format(MILLISECOND);
    }

    /**
     * 时间 ID = Time + ID
     * <p>例如：可用于商品订单 ID</p>
     */
    public static String getTimeId() {
        return getMillisecond() + getId();
    }

    /**
     * 有参构造器
     *
     * @param workerId     工作机器 ID
     * @param datacenterId 序列号
     */
    public static void initSequence(long workerId, long datacenterId) {
        WORKER = new Sequence(workerId, datacenterId);
    }

    /**
     * 使用ThreadLocalRandom获取UUID获取更优的效果 去掉"-"
     */
    public static String get32UUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace(StringPool.DASH, StringPool.EMPTY);
    }
    
    public static void main(String[] args) {
    	for (int i = 0; i <= 100000; i++) {
    		System.out.println(IdWorker.getTimeId());			
		}
	}
    
}
