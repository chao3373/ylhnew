package com.shenke.repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import com.shenke.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.shenke.entity.JitaiProductionAllot;
import com.shenke.entity.SaleListProduct;

@Transactional
public interface SaleListProductRepository
		extends JpaRepository<SaleListProduct, Integer>, JpaSpecificationExecutor<SaleListProduct> {

	/**
	 * 根据销售单id查询订单中的所有未审核的商品信息
	 *
	 * @param saleListId
	 * @return
	 */
	@Query(value = "SELECT * FROM t_sale_list_product WHERE sale_list_id = ?1", nativeQuery = true)
	public List<SaleListProduct> listBySaleListId(Integer saleListId);

	/**
	 * 根据销售单id删除所有商品信息0
	 * 
	 * @param id
	 */
	@Modifying
	@Query(value = "delete from t_sale_list_product where sale_list_id = ?1", nativeQuery = true)
	public void deleteBySaleListId(Integer id);

	/**
	 * 订单审核通过
	 * 
	 * @param id
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set state = '审核通过' where id = ?1", nativeQuery = true)
	public void passTheAudit(int id);

	/**
	 * 订单审核失败
	 * 
	 * @param id
	 */
	@Modifying
	@DeleteMapping
	@Query(value = "update t_sale_list_product set state = ?2 where id = ?1", nativeQuery = true)
	public void auditFailure(int id, String cause);

	/**
	 * 根据订单审核状态查询所有订单信息
	 * 
	 * @param state
	 */
	@Query(value = "select * from t_sale_list_product where state like %?1%", nativeQuery = true)
	public List<SaleListProduct> 	listProductByState(String state);

	/**
	 * 根据订单审核状态和销售单id查询所有订单信息
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	@Query(value = "select * from t_sale_list_product where state like %?2% and sale_list_id = ?1", nativeQuery = true)
	public List<SaleListProduct> listProductByStateAndId(Integer id, String state);

	/**
	 * 查询所有审核通过的商品信息
	 * 
	 * @return
	 */
	@Query(value = "select * from t_sale_list_product where state like '%审核通过%'", nativeQuery = true)
	public List<SaleListProduct> listProductSucceed();

	/**
	 * 下拉框模糊查询所有幅宽信息
	 * 
	 * @param q
	 * @return
	 */
	@Query(value = "select * from t_sale_list_product where model like ?1", nativeQuery = true)
	public List<SaleListProduct> breadthList(String q);

	/**
	 * 根据订单商品信息修改订单商品状态
	 * 
	 * @param name
	 * @param id
	 */
	@Modifying
	@Query(value = "UPDATE t_sale_list_product SET state = ?1 where id = ?2", nativeQuery = true)
	public void updateState(String name, Integer id);

	/**
	 * 根据机台id和通知单号查询所有销售商品信息
	 * 
	 * @param jitai
	 * @param parseLong
	 * @return
	 */
	@Query(value = "SELECT * FROM t_sale_list_product WHERE id IN (SELECT sale_list_product_id FROM t_jitai_production_allot WHERE jitai_id = ?1 AND inform_number = ?2)", nativeQuery = true)
	public List<SaleListProduct> selectAllByInformAndJitaiId(Integer jitai, Long parseLong);

	/**
	 * 根据id修改实际重量
	 * @param weight
	 * @param saleListProductId
	 */
	@Modifying
	@Query(value = "UPDATE t_sale_list_product SET realityweight = ?1 where id = ?2", nativeQuery = true)
	public void updaterRealityWeightById(Double weight, Integer saleListProductId);


	/**
	 * 修改机台id
	* @Description:
	* @Param:
	* @return:
	* @Author: Andy
	* @Date:
	*/
	@Modifying
	@Query(value = "update t_sale_list_product set jitai_id = ?2 where id = ?1", nativeQuery = true)
	public void updateJitaiId(Integer id, Integer id1);

	/**
	 * 根据id修改通知单号
	* @Description:
	* @Param:
	* @return:
	* @Author: Andy
	* @Date:
	*/
	@Modifying
	@Query(value = "update t_sale_list_product set inform_number = ?1 where id = ?2", nativeQuery = true)
	public void updateInformNumber(Long informNumber, int id);

	/**
	 * 根据id修改下发状态
	* @Description:
	* @Param:
	* @return:
	* @Author: Andy
	* @Date:
	*/
	@Modifying
	@Query(value = "update t_sale_list_product set issue_state = ?1 where id = ?2", nativeQuery = true)
	public void updateIussueState(String issueState, int id);

	/**
	 * 查询该机台上所有未完成的通知单号
	* @Description:
	* @Param:selectNoAccomplish
	* @return:
	* @Author: Andy
	* @Date:
	*/
	@Query(value = "SELECT * FROM t_sale_list_product WHERE id IN (SELECT id FROM t_sale_list_product WHERE state LIKE '%下发机台%') AND jitai_id = ?1 GROUP BY inform_number order by inform_number", nativeQuery = true)
	public List<SaleListProduct> selectNoAccomplish(Integer jitaiId);

	/**
	 * 根据id修改完成数量
	* @Description:
	* @Param:
	* @return:
	* @Author: Andy
	* @Date:
	*/
	@Modifying
	@Query(value = "update t_sale_list_product set accomplish_number = ?1 where id = ?2", nativeQuery = true)
	public void updateAccomplishNumberById(Integer count, Integer producionProcessId);


	/***
	 * 查询最大的通知单号
	 * @return
	 */
	@Query(value = "SELECT MAX(inform_number) FROM t_sale_list_product", nativeQuery = true)
    public Long findMaxInfornNumber();

	/***+
	 * 根据机台id查询
	 * @param jitai
	 * @return
	 */
	@Query(value = "select * from t_sale_list_product where jitai_id = ?1", nativeQuery = true)
    public List<SaleListProduct> findByJitaiId(Integer jitai);

	/***
	 * 修改机台
	 * @param jitai
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set jitai_id = ?1 where id = ?2", nativeQuery = true)
	public void alertJitai(Integer jitai, Integer id);


	/***
	 * 根据机台生产情况进行查询
	 * @param
	 * @return
	 */
	@Query(value = "select * from t_sale_list_product where state like '%生产完成%' or state like'%下发机台%' or state like '%分配机台%'" , nativeQuery = true)
	public List<SaleListProduct> findJitaiProduct();

	/**
	 * 订单加急
	 * @param id
	 * @param jiajidengji
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set level =?2 where sale_list_id =?1", nativeQuery = true)
	public void dingdanjiaji(Integer id,String jiajidengji);


	/**
	 * 产品加急
	 * @param id
	 * @param jiajidengji
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set level =?2 where id =?1", nativeQuery = true)
	public void chanpinjiaji(Integer id,String jiajidengji);

	/***
	 * 修改数量
	 * @param num
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set num = ?1 where id = ?2", nativeQuery = true)
    void updateNum(Integer num, Integer id);

	/***
	 * 根据id更新完成数量
	 * @param id
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set accomplish_number = (select count(*) from t_storage where sale_list_product_id = ?1) where id = ?1", nativeQuery = true)
	void updateAccomplishNumber(Integer id);

	/**
	 * 添加配方
	 * @param informNumber
	 * @param peifangnum
	 * @param id
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set peifangid = ?3, peifangnum = ?2 where inform_number = ?1", nativeQuery = true)
    void addpeifang(Long informNumber, Double peifangnum, Integer id);

	/***
	 * 修改状态
	 * @param idArr
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set state = ?2 where id in ?1", nativeQuery = true)
    void passTheAudits(Integer[] idArr, String state);

	/***
	 * 根据id数组修改状态
	 * @param
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set state = ?1 where id in ?2", nativeQuery = true)
	void updateStateByIds(String state, Integer[] ids);

	/***
	 * 根据id数组修改下发状态
	 * @param
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set issue_state = ?1 where id in ?2", nativeQuery = true)
	void updateIussueStateByIds(String state, Integer[] ids);

	/***
	 * 根据id数组修改机台id
	 * @param
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set jitai_id = ?1 where id in ?2", nativeQuery = true)
	void updateJiTaiIdByIds(Integer jiTaiId, Integer[] ids);

	/***
	 * 根据id数组修改通知单号
	 * @param
	 */
	@Modifying
	@Query(value = "update t_sale_list_product set inform_number = ?1 where id in ?2", nativeQuery = true)
	void updateInformNumberByIds(Long informNumber, Integer[] ids);

	/***
	 * 根据id数组修改状态为下发
	 * @param
	 */
	@Modifying
	@Query(value = "update t_sale_list_product a, t_jitai b set a.state = CONCAT('下发机台：', b.name) where a.jitai_id = b.id and a.id in ?1", nativeQuery = true)
	void xiaFaStateByIds(Integer[] ids);

	/***
	 * 根据id数组修改下发状态为下发
	 * @param
	 */
	@Modifying
	@Query(value = "update t_sale_list_product a, t_jitai b set a.issue_state = CONCAT('下发机台：', b.`name`) where a.jitai_id = b.id and a.id in ?1", nativeQuery = true)
	void xiaFaIussueStateByIds(Integer[] ids);
}
