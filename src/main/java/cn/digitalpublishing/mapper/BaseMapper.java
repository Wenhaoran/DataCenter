package cn.digitalpublishing.mapper;

import java.util.List;
import java.util.Map;

import cn.digitalpublishing.util.mybatis.page.PageInfo;

public interface BaseMapper<T, V> {
    /**
     * 添加
     *
     * @param role
     * @return
     */
    int insert(T t);

    /**
     * 根据pageinfo 查询列表
     *
     * @param pageInfo
     * @return
     */
    List<V> findPageCondition(PageInfo pageInfo);

    /**
     * 列表
     *
     * @return
     */
    List<V> findAll();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    V findById(String id);

    /**
     * 更新
     *
     * @param role
     * @return
     */
    int updateById(T t);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteById(String id);  
    
    /**
	 * 通过条件查询集合
	 * @param condition
	 * @return
	 */
    List<T> findListByCondition(Map<String, Object> condition);

}