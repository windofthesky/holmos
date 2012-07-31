package pagestore.tianya;

import java.util.ArrayList;

import Holmos.webtest.struct.Collection;

public class InfoStructure {
	private String url;
	private int huifu;
	private int fangwen;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getHuifu() {
		return huifu;
	}
	public void setHuifu(int huifu) {
		this.huifu = huifu;
	}
	public int getFangwen() {
		return fangwen;
	}
	public void setFangwen(int fangwen) {
		this.fangwen = fangwen;
	}
	public void outPutInfo(){
		System.out.println("访问量:"+fangwen+" | 回复量:"+huifu);
		System.out.println("连接:"+url);
		System.out.println("************");
	}
	
	public static void sortByFangwen(ArrayList<InfoStructure>infos){
		InfoStructure info;
		int k=0;
		for(int i=0;i<infos.size();i++){
			info=infos.get(i);
			for(int j=i+1;j<infos.size();j++){
				if(info.fangwen<infos.get(j).fangwen){
					info=infos.get(j);
					k=j;
				}
			}
			infos.set(k, infos.get(i));
			infos.set(i, info);
		}
		System.out.println("按照访问量排序如下:");
	}
	public static void sortByHuifu(ArrayList<InfoStructure>infos){
		InfoStructure info;
		int k=0;
		for(int i=0;i<infos.size();i++){
			info=infos.get(i);
			for(int j=i+1;j<infos.size();j++){
				if(info.huifu<infos.get(j).huifu){
					info=infos.get(j);
					k=j;
				}
			}
			infos.set(k, infos.get(i));
			infos.set(i, info);
		}
		System.out.println("按照回复量排序如下:");
	}
	public static void outputInfos(ArrayList<InfoStructure>infos){
		for(int i=0;i<infos.size();i++){
			infos.get(i).outPutInfo();
		}
	}
}
