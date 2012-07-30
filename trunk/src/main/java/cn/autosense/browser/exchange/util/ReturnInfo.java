package cn.autosense.browser.exchange.util;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import Holmos.Holmos.plug.PSM.container.CollectionInfo;
import Holmos.Holmos.plug.PSM.container.FrameInfo;
import Holmos.Holmos.plug.PSM.container.PageInfo;
import Holmos.Holmos.plug.PSM.container.SubPageInfo;
import Holmos.Holmos.plug.PSM.folder.FolderInfo;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-7-29 下午6:40:07<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnInfo implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private List<FolderInfo> folderInfos;
	private List<PageInfo> pageInfos;
	private List<FrameInfo> frameInfos;
	private List<CollectionInfo> collectionInfos;
	private List<SubPageInfo> subPageInfos;

	public ReturnInfo(List<FolderInfo> folderInfos, List<PageInfo> pageInfos) {
		super();
		this.folderInfos = folderInfos;
		this.pageInfos = pageInfos;
	}
	public ReturnInfo(List<FrameInfo> frameInfos, List<CollectionInfo> collectionInfos,
			List<SubPageInfo> subPageInfos) {
		super();
		this.frameInfos = frameInfos;
		this.collectionInfos = collectionInfos;
		this.subPageInfos = subPageInfos;
	}
	/*public ReturnInfo(List<FolderInfo> folderInfos, List<PageInfo> pageInfos,
			List<FrameInfo> frameInfos, List<CollectionInfo> collectionInfos,
			List<SubPageInfo> subPageInfos) {
		super();
		this.folderInfos = folderInfos;
		this.pageInfos = pageInfos;
		this.frameInfos = frameInfos;
		this.collectionInfos = collectionInfos;
		this.subPageInfos = subPageInfos;
	}*/
	
	
}
