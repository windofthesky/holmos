package Holmos.exceptions;

import Holmos.webtest.tools.HolmosWindow;
import junit.framework.AssertionFailedError;

public class HolmosFailedError extends AssertionFailedError{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Exception e;
	public HolmosFailedError () {
		HolmosWindow.closeAllWindows();
	}
	public HolmosFailedError (String message) {
		super (message);
		HolmosWindow.closeAllWindows();
		dealExpection();
	}
	public HolmosFailedError(String message,Exception e){
		super(message);
		this.e=e;
		dealExpection();
	}
	private void dealExpection(){
		if(this.e!=null)
			this.e.printStackTrace();
	}
}
