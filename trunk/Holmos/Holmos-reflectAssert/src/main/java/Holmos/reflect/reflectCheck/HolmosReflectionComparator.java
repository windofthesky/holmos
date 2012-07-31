package Holmos.reflect.reflectCheck;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import Holmos.reflect.reflectCheck.comparator.HolmosComparator;
import Holmos.reflect.reflectCheck.difference.HolmosDifference;

/**比较两个对象实例的反射比较器
 * @author 吴银龙(15857164387)
 * */
public class HolmosReflectionComparator {
	/**
     * 比较器的链条，会循环扫描比较器，来进行两个实例的比较，如果在链条里面找不到合适的比较器，则跑出异常
     */
    protected List<HolmosComparator> comparators;

    /**
     * 比较结果的缓存，这样做的话，就会使两个实例的比较只会进行一次，然后比较结果就可以尽心无限次的使用，这是由于不能进行循环比较<br>
     * 之所以会定义两个变量，主要是因为我们的比较方式有两种，一种是全比较，另外一种是如果发现了第一个子元素不同，就跳出比较<br>
     */
    protected Map<Object, Map<Object, HolmosDifference>> firstDifferenceCachedResults = new IdentityHashMap<Object, Map<Object, HolmosDifference>>();
    protected Map<Object, Map<Object, HolmosDifference>> allDifferencesCachedResults = new IdentityHashMap<Object, Map<Object, HolmosDifference>>();

    /**
     * HolmosreflectionComparator比较器的构造函数，要使用给定的比较器的链条
     *
     * @param comparators 给定的反射比较器的链条, not null
     */
    public HolmosReflectionComparator(List<HolmosComparator> comparators) {
        this.comparators = comparators;
    }


    /**
     * 验证left和right进行反射比较之后是否相同
     *
     * @param left  第一个比较实例
     * @param right 第二个比较实例
     * @return true 两个实例反射比较的结果是相同<br> false 否则的话
     */
    public boolean isEqual(Object left, Object right) {
        HolmosDifference difference = getDifference(left, right, true);
        return difference == null;
    }


    /**
     * 验证left和right进行反射比较之后是否相同
     *
     * @param left  第一个比较实例
     * @param right 第二个比较实例
     * @return  两个实例的比较结果，如果是null的话，代表两个实例相同
     */
    public HolmosDifference getDifference(Object left, Object right) {
        return getDifference(left, right, false);
    }


    /**
     * 验证left和right进行反射比较之后是否相同，获得比较的结果，获得结果是根结果，<br>是树状结构的结果，如果 onlyFirstDifference
     * 是false的话，返回的结果是所有<br>子对象比较之后的所有比较结果，如果为null的话，则代表两个对象反射比较一致
     *
     * @param left                第一个比较实例
     * @param right               第二个比较实例
     * @param onlyFirstDifference 如果为true的话，那么比较到第一个不同的子元素就会返回
     * @return 返回所有的不同的信息，null的话则两个实例反射比较一致
     */
    public HolmosDifference getDifference(Object left, Object right, boolean onlyFirstDifference) {
        // 首先校验cache里面的difference是否有用
        Map<Object, HolmosDifference> cachedResult = getCachedDifference(left, onlyFirstDifference);
        if (cachedResult != null) {
            if (cachedResult.containsKey(right)) {
                // 如果找到了left和right的不同信息，那么将返回这个不同信息
                return cachedResult.get(right);
            }
        } else {//如果找不到对应的差异化信息，那么建立一个差异化信息的变量，并将这个变量存储在cache里面，来存储left的差异化Map列表
            cachedResult = new IdentityHashMap<Object, HolmosDifference>();
            saveResultInCache(left, cachedResult, onlyFirstDifference);
        }
        //存放left和right的结果，还没有进行比较的结果，等于是新建
        cachedResult.put(right, null);

        // 循环扫描各种比较器，遇到合适的比较器则进行比较，并记录比较的结果
        boolean compared = false;
        HolmosDifference result = null;
        for (HolmosComparator comparator : comparators) {
            if (comparator.canCompare(left, right)) {
                result = comparator.compare(left, right, onlyFirstDifference, this);
                compared = true;
                break;
            }
        }

        // 检查一下有没有合适的比较器，如果没有的话，再次抛出异常，不过一般不会，除非Holmos框架没有考虑到位
        if (!compared) {
            //throw new UnitilsException("Could not determine differences. No comparator found that is able to compare the values. Left: " + left + ", right " + right);
        }
        
        // 在cache里面更新结果，并将比较结果返回
        cachedResult.put(right, result);
        return result;
    }
    /**在链条里面保存比较结果
     * @param left 索引值，也是根据这个key来找到他其他对象的比较信息
     * @param cachedResult 当前left对象的比较的结果信息变量
     * @param onlyFirstDifference 指示是否在找到了第一个值就不同就跳出比较过程的变量
     * */
    protected void saveResultInCache(Object left, Map<Object, HolmosDifference> cachedResult, boolean onlyFirstDifference) {
        if (onlyFirstDifference) {
            firstDifferenceCachedResults.put(left, cachedResult);
        } else {
            allDifferencesCachedResults.put(left, cachedResult);
        }
    }
    /**根据参与比较的实例对象left来获得left与其他实例对象的比较的不同信息
     * @param left 参与比较的实例对象
     * @param onlyFirstDifference 指示是否在找到了第一个值就不同就跳出比较过程的变量
     * @return left与其他实例对象的比较的不同信息，null则代表没有找到，需要将新结果纳入比较结果cache里面*/
    protected Map<Object, HolmosDifference> getCachedDifference(Object left, boolean onlyFirstDifference) {
        if (onlyFirstDifference) {
            return firstDifferenceCachedResults.get(left);
        } else {
            return allDifferencesCachedResults.get(left);
        }
    }
}
