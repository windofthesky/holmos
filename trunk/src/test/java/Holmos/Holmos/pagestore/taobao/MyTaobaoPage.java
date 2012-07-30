package Holmos.Holmos.pagestore.taobao;

import Holmos.Holmos.element.Button;
import Holmos.Holmos.element.Element;
import Holmos.Holmos.element.TextField;
import Holmos.Holmos.struct.Page;

public class MyTaobaoPage extends Page{
	public MyTaobaoPage(){
		super();
		this.comment="我的淘宝";
		init();
	}
	public Element prior=new Element("输入前的状态");
	public Button upLoadPic=new Button("上传图片按钮");
	public TextField input=new TextField("叽歪输入框");
	{
		input.addXpathLocator(".//*[@id='mblogEditor']/div/form/div[1]/div/textarea");
		upLoadPic.addXpathLocator(".//*[@id='mblogEditor']/div/form/div[2]/span[1]/span[3]");
		prior.addXpathLocator(".//*[@id='mblogEditor']/div/div[2]/div");
	}
}
