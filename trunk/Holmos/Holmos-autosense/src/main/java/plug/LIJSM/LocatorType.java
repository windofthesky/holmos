package Holmos.Holmos.plug.LIJSM;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-5-14 上午10:21:48<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public enum LocatorType {

	CSS("css"),
	XPATH("xpath"),
	TEXT("text");
	
	private String name;

	LocatorType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
