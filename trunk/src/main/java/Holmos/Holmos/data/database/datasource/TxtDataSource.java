package Holmos.Holmos.data.database.datasource;

/**普通文本文件配置的数据源
 * @author 吴银龙(15857164387)
 * */
public class TxtDataSource extends HolmosDataSource{
	
	/**数据库连接url*/
	public String databaseUrl=null;
	/**数据库驱动名字*/
	public String dataBaseDriver=null;
	/**登录数据库的用户名*/
	public String userName=null;
	/**登录数据库的密码*/
	public String password=null;
	
	public void init(){
		
	}
}
