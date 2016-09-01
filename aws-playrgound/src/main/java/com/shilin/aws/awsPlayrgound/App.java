package com.shilin.aws.awsPlayrgound;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        
        int i = 0;
        
        while (i < 100){
        	
        	ObjectNode objNode = objectMapper.createObjectNode();
        	objNode.put("中文1", "中文");
        	objNode.put("中文2", "中文");
        	objNode.put("中文3", "中文");
        	objNode.put("中文4", "中文");
        	objNode.put("中文5", "中文");
        	
        	arrayNode.add(objNode);
        	
        	i++;
        	
        }
        
        System.out.println(arrayNode);
        
    }
}
