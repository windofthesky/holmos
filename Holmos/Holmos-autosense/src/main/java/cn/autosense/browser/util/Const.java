package cn.autosense.browser.util;

import org.nutz.lang.Files;

/**
 * 
 * @author MashupEye(mashupeye@gmail.com)<br>
 *         2012-7-11  下午‏‎9:12:05<br>
 * @since 1.0<br>
 * @version 1.0<br>
 *          Remark:<br>
 * 
 */
public interface Const {
    /**
     * 文件路径分隔符
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * 项目根目录
     */
    public static final String USER_DIR = System.getProperty("user.dir");

	/**
	 * xulrunner路径
	 */
    public final static String XUL_RUNNER = USER_DIR + "/xulrunner";

    /**
     * jquery库
     */
    public final static String JQUERY_LIB = Files.read("js/jquery-1.6.2.min.js");

    /**
     * pathFinder库
     */
    public final static String PATH_FINDER_LIB = Files.read("js/pathFinder-1.0.7.min.js");

    /**
     * 高亮显示选择的元素
     */
    public final static String SELECTOR_DIV_SHOW = "return mouseClickSelect();";

    /**
     * 在Browser模式下隐藏加亮显示层
     */
    public final static String SELECTOR_DIV_HIDE = "hideHighLight();";

    /**
     * 
     */
    public final static String[] ELEMENT_TYPE =  {"Unknown", "Button", "Checkbox", "Collection", "Comobobox", "Element", "Frame", "Label", "Link", "Radiobutton", "Richtextfield", "Subpage", "Table", "Textfield", "Image"};

    /**
     * 
     */
    public static final String SKIN_DIR = USER_DIR + FILE_SEP + "skin" + FILE_SEP;

    /**
     * 
     */
    public static final String DATA_DIR = USER_DIR + FILE_SEP + "data" + FILE_SEP;

    /**
     * 
     */
    //public static final String IMAGE_DIR = USER_DIR + FILE_SEP + "images" + FILE_SEP;
    public static final String IMAGE_DIR = Const.class.getResource("/images/").toString().substring(6);

    /**
     * 初始化文件
     */
    public static final String INIT_FILE = USER_DIR + FILE_SEP + "init.properties";

}
