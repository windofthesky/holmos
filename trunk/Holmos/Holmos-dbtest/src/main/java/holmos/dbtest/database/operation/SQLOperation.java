package holmos.dbtest.database.operation;

import java.util.Set;

import javax.sql.DataSource;

public interface SQLOperation {
	 DataSource getDataSource();
	 /**
	  * 执行更新
	  * */
	 int executeUpdate(String sql);
	 /**
	 * 执行查询
	 * */
	 void executeQuery(String sql);
	 /**
	  * 执行更新，并且提交更改，所有的上一次更改和错误回滚造成的更改成为永久的改动
	  * */
	 int executeUpdateAndCommit(String sql);
	 /**
	  * 返回第一行第一列的值，当做long类型返回
	  * */
	 long getItemAsLong(String sql);
	 /**
	  * 返回第一行第一列的值，当做String类型返回
	  * */
   	 String getItemAsString(String sql);
   	 /**
   	  * 返回所有行第一列的值，当做String类型返回
   	  * */
	 Set<String> getItemsAsStringSet(String sql);
	 /**
	  * sql是查询操作，如果有结果 返回true 没有结果返回false
	  * */
	 boolean exists(String sql);
 	 boolean isDryMode();
}
