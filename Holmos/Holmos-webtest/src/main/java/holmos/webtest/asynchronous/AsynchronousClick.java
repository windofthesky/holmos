package holmos.webtest.asynchronous;

import holmos.webtest.element.Element;

public class AsynchronousClick extends Thread{
	private Element element;
	private boolean isGetSucceed=false;
	public AsynchronousClick(Element element){
		this.element=element;
	}
	@Override
	public void run() {
		element.click();
		setGetSucceed(true);
	}
	public boolean isGetSucceed() {
		return isGetSucceed;
	}
	public void setGetSucceed(boolean isGetSucceed) {
		this.isGetSucceed = isGetSucceed;
	}
}
