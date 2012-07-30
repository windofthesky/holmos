package Holmos.Holmos.pagestore.taobao2;

import Holmos.Holmos.struct.*;
import Holmos.Holmos.element.*;

public class Taobao2 extends Page{
    
    public Taobao2(){
        super();
        this.comment="taobao2_comment";
        init();
    }
    
    /** 类型:Element  说明: */
    public Element span_20124125110714= new Element ("");
    {
        span_20124125110714.addXpathLocator("//ul[@id='J_ListView']/li[@class='list-item list-ibox list-item-flag quicklook']/ul[@class='attribute']/li[@class='place']/span[@class='seat']");
        span_20124125110714.addCSSLocator("ul[id='J_ListView']>li[class='list-item list-ibox list-item-flag quicklook']>ul[class='attribute']>li[class='place']>span[class='seat']");
        span_20124125110714.addAttributeLocator("class","seat");
    }
    /** 类型:Element  说明: */
    public Element a_20124125110715= new Element ("");
    {
        a_20124125110715.addXpathLocator("//ul[@id='J_ListView']/li[@class='list-item list-ibox list-item-flag quicklook']/h3[@class='title']/a");
        a_20124125110715.addCSSLocator("ul[id='J_ListView']>li[class='list-item list-ibox list-item-flag quicklook']>h3[class='title']>a");
        a_20124125110715.addAttributeLocator("target","_blank");
        a_20124125110715.addAttributeLocator("data-spm","d11");
        a_20124125110715.addAttributeLocator("onclick","return beacon_b2c('1','1')");
        a_20124125110715.addAttributeLocator("data-spm-anchor-id","a2106.m872.1000384.d11");
        a_20124125110715.addAttributeLocator("href","http://detail.tmall.com/item.htm?spm=a2106.m872.1000384.d11&id=15138067017&source=dou");
    }
}
