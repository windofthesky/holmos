package holmos.android.annoatation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Holmos Android测试的测试方法注解标记，加上此注解，框架的Runner会执行该测试方法，其中含有三个参数设置：
 * <li>expected Exception指定抛出的异常，如果不抛磁异常，执行失败</li>
 * <li>timeout超时标记，如果执行时间超过了该设置时间，方法执行失败</li>
 * <li>order顺序信息，设置该方法在测试类中的执行顺序</li>
 * @author 吴银龙(QQ:307087558)
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AndroidTest {
	/**
	 * 默认情况下抛出的异常类——什么都不做
	 */
	static class None extends Throwable {
		private static final long serialVersionUID= 1L;		
		private None() {
		}
	}
	
	/**
	 * 指定测试类的测试方法抛出的异常，如果在此方法执行过程中，没有抛出这个异常，那么Holmos会认为当前方法没有执行通过
	 */
	Class<? extends Throwable> expected() default None.class;
	
	/** 
	 * AndroidTest设置的超时时间，如果超过了这个时间，那么该方法在超过这个时间的时候会失败 
	 */
	long timeout() default 0L;
	
	/**
	 * 执行该方法在该指定测试类里面的执行顺序；执行顺序的方案如下：<br>
	 * 对于没有设置的测试方法，该值默认为-1；先将没有设置该值的方法按照定义放到执行队列里面，然后将<br>
	 * 测试类里面设置顺序的方法按照设置的顺序插入到这个执行队列里面,如果该值设置的为<=0的数，默认为-1，<br>
	 * 如果其中有若干个方法设置的order值相同，那么插入顺序在本身顺序的基础上按照方法定义顺序依次插入<br>
	 * */
	int order() default -1;
}
