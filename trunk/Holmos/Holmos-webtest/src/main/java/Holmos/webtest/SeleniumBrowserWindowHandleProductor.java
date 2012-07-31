package Holmos.webtest;
/**selenium窗口handle生产类，由于Selenium1对多窗口的支持有问题<br>
 * 所以这些问题交予holmos框架解决，采用单件模式，每次产生一个唯一的不同<br>
 * 的Handle，实际上只是一个整数转化的字符窜，并且是线程安全的<br>
 * @author 吴银龙(15857164387)
 * */
class SeleniumBrowserWindowHandleProductor{
	private volatile static SeleniumBrowserWindowHandleProductor productor;
	private long handle=0;
	private SeleniumBrowserWindowHandleProductor(){}
	/**获得Selenium引擎的窗口句柄生产者,采用单件<br>
	 * 模式，保证在整个框架的唯一性,这个生产者有一个<br>
	 * productHandle()生产句柄的方法每调用一次，<br>
	 * 产生一个唯一的句柄，其实是一个整数转化为的字符窜*/
	public static SeleniumBrowserWindowHandleProductor getInstance(){
		if(productor==null){
			synchronized (SeleniumBrowserWindowHandleProductor.class) {
				if(productor==null){
					productor=new SeleniumBrowserWindowHandleProductor();
				}
			}
		}return productor;
	}
	/**产生Selenium引擎所控制窗口的句柄，在框架运行期间唯一
	 * @param 生成的句柄*/
	public String productHandle(){
		handle=handle+1;
		return Long.toString(handle);
	}
}