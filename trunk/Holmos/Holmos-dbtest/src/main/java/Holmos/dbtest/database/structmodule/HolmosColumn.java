package Holmos.dbtest.database.structmodule;

import java.sql.Types;

import org.dbunit.dataset.datatype.BooleanDataType;
import org.dbunit.dataset.datatype.BytesDataType;
import org.dbunit.dataset.datatype.ClobDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DateDataType;
import org.dbunit.dataset.datatype.DoubleDataType;
import org.dbunit.dataset.datatype.FloatDataType;
import org.dbunit.dataset.datatype.IntegerDataType;
import org.dbunit.dataset.datatype.LongDataType;
import org.dbunit.dataset.datatype.NumberDataType;
import org.dbunit.dataset.datatype.StringDataType;
import org.dbunit.dataset.datatype.TimeDataType;
import org.dbunit.dataset.datatype.TimestampDataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.dbunit.dataset.datatype.UnknownDataType;

import Holmos.dbtest.database.datadifference.HolmosColumnDifference;

/**数据表的一列
 * @author 吴银龙(15857164387)
 * */
public class HolmosColumn {
	/**列名*/
	private String name;
	/**列值*/
	private Object value;
	/**列的类型*/
	private DataType type;
	public HolmosColumn(String name,Object value,DataType type){
		this.name=name;
		this.value=value;
		this.type=type;
	}
	/**@return 列的名字*/
	public String getName() {
		return name;
	}
	/**@return 列的值*/
	public Object getValue() {
		return value;
	}
	/**列的类型*/
	public DataType getType() {
		return type;
	}
	/**将现有的列的值转换为castType指示的类型，
	 * @param castType 要转换成为的类型
	 * @return true 转换成功，那么返回转换后的值<br>
	 * false 转换失败，返回null
	 * */
	public Object getCastedType(DataType castType){
		try{
			return castType.typeCast(value);
		}catch(TypeCastException e){
			e.printStackTrace();
			return null;
		}
	}
	/**当前column对象与给定column对象进行比较，比较分为三个步骤进行<br>
	 * <li>如果value相同，那么返回null</li>
	 * <li>如果value都不为null，那么正常返回差异值</li>
	 * <li>如果转换后的结果相同，那么返回null，不相同，则同第二步</li>
	 * @return 返回比较后的差异信息，如果两个值相同，返回null
	 * */
	public HolmosColumnDifference compare(HolmosColumn column){
		if(value==column.getValue()){
			return null;
		}
		if(value!=null&&column.getValue()!=null){
			return new HolmosColumnDifference(this, column);
		}
		if(!value.equals(column.getCastedType(type))){
			return new HolmosColumnDifference(this, column);
		}return null;
	}
	public String toString(){
		try {
			return name+(strForType())+":"+DataType.asString(value);
		} catch (TypeCastException e) {
			e.printStackTrace();
			return null;
		} 
	}
	/**返回这个列的类型的字符窜表示
	 * @return */
	private String strForType(){
		if(type instanceof UnknownDataType){
			return "UNKNOWN";
		}else if(type instanceof StringDataType){
			if(((StringDataType)type).getSqlType()==Types.CHAR){
				return "CHAR";
			}else if(((StringDataType)type).getSqlType()==Types.VARCHAR){
				return "VARCHAR";
			}else if(((StringDataType)type).getSqlType()==Types.LONGVARCHAR){
				return "LONGVARCHAR";
			}
		}else if(type instanceof ClobDataType){
			return "CLOB";
		}else if(type instanceof NumberDataType){
			if(((NumberDataType)type).getSqlType()==Types.NUMERIC){
				return "NUMERIC";
			}else if(((NumberDataType)type).getSqlType()==Types.DECIMAL){
				return "DECIMAL";
			}
		}else if(type instanceof BooleanDataType){
			return "BOOLEAN";
		}else if(type instanceof IntegerDataType){
			if(((IntegerDataType)type).getSqlType()==Types.INTEGER){
				return "INTEGER";
			}else if(((IntegerDataType)type).getSqlType()==Types.TINYINT){
				return "TINYINT";
			}else if(((IntegerDataType)type).getSqlType()==Types.SMALLINT){
				return "SMALLINT";
			}
		}else if(type instanceof LongDataType){
			return "LONG";
		}else if(type instanceof FloatDataType){
			return "REAL";
		}else if(type instanceof DoubleDataType){
			if(((DoubleDataType)type).getSqlType()==Types.FLOAT){
				return "FLOAT";
			}else if(((DoubleDataType)type).getSqlType()==Types.DOUBLE){
				return "DOUBLE";
			}
		}else if(type instanceof DateDataType){
			return "DATE";
		}else if(type instanceof TimeDataType){
			return "TIME";
		}else if(type instanceof TimestampDataType){
			return "TIMESTAMP";
		}else if(type instanceof BytesDataType){
			if(((BytesDataType)type).getSqlType()==Types.BINARY){
				return "BINARY";
			}else if(((BytesDataType)type).getSqlType()==Types.VARBINARY){
				return "VARBINARY";
			}else if(((BytesDataType)type).getSqlType()==Types.LONGVARBINARY){
				return "LONGVARBINARY";
			}
		}
		return "NULL";
	}
}
