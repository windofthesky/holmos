package back;

import java.util.HashMap;
import java.util.Map;

import org.nutz.lang.Files;

import cn.autosense.browser.data.HtmlFiledBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-20 ����9:06:23<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class GsonTest {
	
	public static void main(String[] args) {
		
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("id", "aaa");
		attrs.put("href", "http://www.baidu.com/");
		
		HtmlFiledBean bean = new HtmlFiledBean();
		bean.setXpath("//h3[@class='title']/a");
		bean.setSelector("#hea");
		bean.setAttributes(attrs);
		
		Gson gson = new Gson();

		String sGroupBean = gson.toJson(bean, new TypeToken<HtmlFiledBean>() {}.getType());
		System.out.println(sGroupBean);
		
		String a = Files.read("C:\\Users\\lenovo\\Desktop\\bean.txt");
		System.out.println(a);
		
		HtmlFiledBean bean2 = gson.fromJson(a, new TypeToken<HtmlFiledBean>() {}.getType());
		System.out.println(bean2);
	}
}
