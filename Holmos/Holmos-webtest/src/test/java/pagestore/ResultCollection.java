package pagestore;

import holmos.webtest.element.Element;
import holmos.webtest.element.Link;
import holmos.webtest.struct.Collection;

public class ResultCollection extends Collection{

	public ResultCollection(String comment) {
		super(comment);
	}

	public Element resultImg=new Element("搜索结果图片");
	public Link resultLink=new Link("搜索结果连接");
	public Element resultSort=new Element("结果分类信息");
	public Element otherInfo=new Element("其他结果信息");
	{
		resultImg.addXpathLocator("/tbody/tr/td/div[1]/img");
		resultLink.addXpathLocator("/tbody/tr/td/div[1]/a/span");
		resultSort.addXpathLocator("/tbody/tr/td/div[2]");
		otherInfo.addXpathLocator("/tbody/tr/td/span[2]/span[1]");
	}
}
