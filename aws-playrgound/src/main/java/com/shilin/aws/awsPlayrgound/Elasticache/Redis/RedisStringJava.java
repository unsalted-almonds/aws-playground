package com.shilin.aws.awsPlayrgound.Elasticache.Redis;

import redis.clients.jedis.Jedis;

public class RedisStringJava {
	public static void main(String[] args) {
		// Connecting to Redis server on localhost
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server sucessfully");
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println("Get Value: " + jedis.ping());
		// check whether server is running or not
		System.out.println("Server is running: " + jedis.ping());
	}
}