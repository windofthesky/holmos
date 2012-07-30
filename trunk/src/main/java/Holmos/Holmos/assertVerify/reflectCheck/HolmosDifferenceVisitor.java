package Holmos.Holmos.assertVerify.reflectCheck;

import Holmos.Holmos.assertVerify.reflectCheck.difference.HolmosClassDifference;
import Holmos.Holmos.assertVerify.reflectCheck.difference.HolmosCollectionDifference;
import Holmos.Holmos.assertVerify.reflectCheck.difference.HolmosCollectionIgnoreOrderDifference;
import Holmos.Holmos.assertVerify.reflectCheck.difference.HolmosDifference;
import Holmos.Holmos.assertVerify.reflectCheck.difference.HolmosMapDifference;
import Holmos.Holmos.assertVerify.reflectCheck.difference.HolmosObjectDifference;
/**
 * @author 吴银龙(15857164387)
 * */
public interface HolmosDifferenceVisitor<T,A> {

    /**
     * 调用对象为普通的对象，访问这个普通或者抽象的对象
     *
     * @param difference 对象的不同的存储地
     * @param argument   一个配置的访问规则
     * @return 访问的结果
     */
    T visit(HolmosDifference difference, A argument);


    /**
     * 调动对象为Object对象
     *
     *@param difference 对象的不同的存储地
     * @param argument   一个配置的访问规则
     * @return 访问的结果
     */
    T visit(HolmosObjectDifference objectDifference, A argument);


    /**
     * 调用对象为Class对象.
     *
     * @param difference 对象的不同的存储地
     * @param argument   一个配置的访问规则
     * @return 访问的结果
     */
    T visit(HolmosClassDifference classDifference, A argument);


    /**
     * 调用对象为Map对象
     *
     *@param difference 对象的不同的存储地
     * @param argument   一个配置的访问规则
     * @return 访问的结果
     */
    T visit(HolmosMapDifference mapDifference, A argument);


    /**
     * 调用对象为Collection对象或者Array对象
     *
     * @param difference 对象的不同的存储地
     * @param argument   一个配置的访问规则
     * @return 访问的结果
     */
    T visit(HolmosCollectionDifference collectionDifference, A argument);


    /**
     * 调用对象为无序集合对象
     *
     *@param difference 对象的不同的存储地
     * @param argument   一个配置的访问规则
     * @return 访问的结果
     */
    T visit(HolmosCollectionIgnoreOrderDifference unorderedCollectionDifference, A argument);
}
