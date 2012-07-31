package back;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-6-20 ����2:04:23<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public class RegexTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a = "����        �й���";
		System.out.println(a.replaceAll("[ ]+", "a"));
	}

}
