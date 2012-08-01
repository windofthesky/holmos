package cn.autosense.browser.exchange.util;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import cn.autosense.plug.psm.NetPageInfo;

/**
 * 创建各种Info返回信息
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-7-22 上午11:24:34<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
@Data
@NoArgsConstructor
public class CreateReturnInfo implements Serializable {

	private static final long	serialVersionUID	= 1L;

	/*private VariableInfo varInfo;
	private PageInfo pageInfo;
	private FolderInfo folderInfo;*/
	private NetPageInfo info;
	private ErrorType type;

	public CreateReturnInfo(ErrorType type) {
		super();
		this.type = type;
	}
	/*public CreateReturnInfo(VariableInfo varInfo) {
		super();
		this.varInfo = varInfo;
		this.type = ErrorType.OK;
	}
	public CreateReturnInfo(PageInfo pageInfo) {
		super();
		this.pageInfo = pageInfo;
		this.type = ErrorType.OK;
	}
	public CreateReturnInfo(FolderInfo folderInfo) {
		super();
		this.folderInfo = folderInfo;
		this.type = ErrorType.OK;
	}*/
	public CreateReturnInfo(NetPageInfo info) {
		super();
		this.info = info;
		this.type = ErrorType.OK;
	}

}
