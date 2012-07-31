package cn.autosense.browser.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-11 下午2:07:28<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class JsonCreater {
	
	private Map<String, String> map;
	
	public JsonCreater() {
		map = new HashMap<String, String>();
	}

	public void put(String key, String value) {
		map.put(key, value);
	}
	
	public String getJson() {
		return getJson("\"", "\"");
	}
	
	public String getJson(String keySplit, String valueSplit) {
		StringBuffer sb = new StringBuffer("{");
		for (Entry<String, String> entry : map.entrySet()) {
			sb.append(keySplit);
			sb.append(entry.getKey());
			sb.append(keySplit);
			sb.append(":");
			sb.append(valueSplit);
			sb.append(entry.getValue());
			sb.append(valueSplit);
			sb.append(",");
		}
		sb.replace(sb.length()-1, sb.length(), "}");
		return sb.toString();
	}
}
