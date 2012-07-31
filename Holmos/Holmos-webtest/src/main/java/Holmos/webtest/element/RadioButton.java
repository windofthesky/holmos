package Holmos.webtest.element;
/**
 * @author 吴银龙(15857164387)
 * */
public class RadioButton extends CheckBox{

	public RadioButton(String comment) {
		super(comment);
	}
	/**检验这个RadioButton多选框是否被选中<br>
	 * @return true 被选中
	 * 		   false 没有被选中*/
	@Override
	public boolean isChecked(){
		return super.isChecked();
	}
	/**根据value的值来设定单选框的状态<br>
	 * @param value true 设置为选中   false  取消选中
	 * */
	@Override
	public void setValue(boolean value){
		super.setValue(value);
	}
	/**校验单选框是否被选中，如果没有被选中，则失败程序退出运行<br>
	 * 如果被选中，程序继续执行，校验成功!*/
	@Override
	public void assertChecked(){
		super.assertChecked();
	}
	/**校验单选框是否被选中，如果没有被选中，则失败程序继续运行<br>
	 * 如果被选中，程序继续执行，校验成功!*/
	@Override
	public void verifyChecked(){
		super.verifyChecked();
	}
	/**等待单选框元素的状态变为选中，默认的等待时间为30s*/
	public void waitForChecked(){
		super.waitForChecked();
	}
	/**等待单选框元素的状态变为未选中，默认的等待时间为30s*/
	public void waitForUnChecked(){
		super.waitForUnChecked();
	}
}
