package com.tmac2236.spring.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 * 輔助解析JSON  https://jsoneditoronline.org/
 * 筆記: jsonNode = jsonNode.at("/result/records"); ===>(result節點裡面的records節點)
 *
 */
public class JSONUtil {
	//private static Logger logger = LoggerFactory.getLogger(JSONUtil.class);
	private static ObjectMapper mapper = new ObjectMapper().configure(Feature.ALLOW_COMMENTS, true);

	/**
	 * 產生空的ObjectNode
	 * @return
	 */
	public static ObjectNode createObjectNode() {
		return mapper.createObjectNode();
	}
	
	/**
	 * 產生空的ArrayNode
	 * @return
	 */
	public static ArrayNode createArrayNode() {
		return mapper.createArrayNode();
	}
	
	/**
     * 讀取ClassPath底下JSON file, 轉換成指定的Java物件
     * 
     * example: 
     * List<BusData> busDatas = JSONUtil.parseJSON(new TypeReference<List<BusData>>(){}, filePath);
     * 
     * @param type
     * @param filePath
     * @return T
     */
    public static <T> T parseJSONFile(final TypeReference<T> type, final String filePath) {
        T data = null;

        try {
            data = mapper.readValue(JSONUtil.class.getResource(filePath), type);
        } catch (Exception e) {
//            logger.warn("error in parseJSON to object!", e);
        }
        return data;
    }
	
	/**
	 * 將JSON字串轉成指定的Java物件
	 * 
	 * example: 
	 * List<BusData> busDatas = JSONUtil.parseJSON(new TypeReference<List<BusData>>(){}, jsonPacket);
	 * 
	 * @param type
	 * @param jsonPacket
	 * @return T
	 */
	public static <T> T parseJSON(final TypeReference<T> type, final String jsonPacket) {
		T data = null;

		try {
			data = mapper.readValue(jsonPacket, type);
		} catch (Exception e) {
//			logger.warn("error in parseJSON to object!", e);
		    System.out.println(e);
		}
		return data;
	}

	/**
	 * 將JSON字串轉成JsonNode
	 */
	public static JsonNode parseJSON2JsonNode(final String jsonPacket) {
		JsonNode root = null;
		try {
			root = mapper.readTree(jsonPacket);
		} catch (Exception e) {
//			logger.warn("error in parseJSON to JsonNode!", e);
		}

		return root;

	}

	/**
	 * 將JSON字串轉成ObjectNode
	 */
	public static ObjectNode parseJSON2ObjectNode(final String jsonPacket) {
		JsonNode root = null;
		ObjectNode objectNode = null;
		try {
			root = mapper.readTree(jsonPacket);
		} catch (Exception e) {
//			logger.warn("error in parseJSON to ObjectNode!", e);
		}

		if (root != null && root.isObject()) {
			objectNode = (ObjectNode) root;
		} else {
			objectNode = mapper.createObjectNode();
		}

		return objectNode;

	}

	/**
	 * 將JSON字串轉成ArrayNode
	 */
	public static ArrayNode parseJSON2ArrayNode(final String jsonPacket) {
		JsonNode root = null;
		ArrayNode arrayNode = null;
		try {
			root = mapper.readTree(jsonPacket);
		} catch (Exception e) {
//			logger.warn("error in parseJSON to ArrayNode!", e);
		}

		if (root != null && root.isArray()) {
			arrayNode = (ArrayNode) root;
		} else {
			arrayNode = mapper.createArrayNode();
		}

		return arrayNode;

	}	
	
    /**
     * 將Java物件轉成JSON字串
     */
    public static String format2Json(Object bean) {
        String json = "";
        try {
            json = mapper.writeValueAsString(bean);
        } catch (Exception e) {
//            logger.warn("error in format object to JSON!", e);
        }

        return json;
    }
}
