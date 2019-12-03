package com.shenke.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shenke.entity.SaleListProduct;

public interface SaleListProductService {

	/**
	 * 根据销售单id查询所有销售单商品
	 *
	 * @param saleListId
	 * @return
	 */
	public List<SaleListProduct> listBySaleListId(Integer saleListId);

	/**
	 * 根据SaleListProductId删除该订单的所有商品信息
	 *
	 * @param id
	 */
	public void deleteBySaleListId(Integer id);

	/**
	 * 订单审核通过
	 *
	 * @param id
	 */
	public void passTheAudit(int id);

	/**
	 * 根据id修改订单状态
	 *
	 * @param id
	 */
	public void auditFailure(int id, String cause);

	/**
	 * 查询所有销售商品信息
	 *
	 * @return
	 */
	public List<SaleListProduct> fandAll();

	/**
	 * 根据id列表获取订单商品信息
	 *
	 * @param ids
	 * @return
	 */
	public List<SaleListProduct> fandAll(Iterable<Integer> ids);

	/**
	 * 查询所有的审核成功的销售商品信息
	 */
	public List<SaleListProduct> listProductSucceed();

	/**
	 * 下拉框模糊查询所有幅宽信息
	 *
	 * @param q
	 * @return
	 */
	public List<SaleListProduct> breadthList(String q);

	/**
	 * 根据条件查询所有订单商品信息
	 *
	 * @param
	 * @return
	 */
	public List<SaleListProduct> screen(SaleListProduct saleListProduct);

	/**
	 * 根据订单商品信息id修改订单商品信息状态
	 *
	 * @param name
	 * @param id
	 */
	public void updateState(String name, Integer id);

	public void saveList(List<SaleListProduct> plgList);

	/**
	 * 修改机台id
	 *
	 * @Description:
	 * @Param:
	 * @return:
	 * @Author: Andy
	 * @Date:
	 */
	public void updateJitaiId(Integer id, Integer id1);

	/**
	 * 根据机台id和下发状态和通知单号查询
	 *
	 * @Description:
	 * @Param:
	 * @return:
	 * @Author: Andy
	 * @Date:
	 */
	public List<SaleListProduct> selectByJitaiIdAndIssueStateAndInformNumber(Integer jitaiId, String state, Long infLong);

	/**
	 * 根据id修改通知单号
	 *
	 * @Description:
	 * @Param:
	 * @return:
	 * @Author: Andy
	 * @Date:
	 */
	public void updateInformNumber(Long informNumber, int id);

	/**
	 * 根据id修改下发状态
	 *
	 * @Description:
	 * @Param:
	 * @return:
	 * @Author: Andy
	 * @Date:
	 */
	public void updateIussueState(String issueState, int id);

	/**
	 * 查询该机台上所有未完成的生产通知单号
	 *
	 * @Description:
	 * @Param:
	 * @return:
	 * @Author: Andy
	 * @Date:
	 */
	public List<SaleListProduct> selectNoAccomplish(Integer jitaiId);

	/***
	 * 根据订单状态查询订单
	 * @param state
	 * @return
	 */
	public List<SaleListProduct> listProductByState(String state);

	/***
	 * 获取最大的通知单号
	 * @return
	 */
	public Long findMaxInfornNumber();

	/***
	 * 保存修改的信息
	 * @param saleListProduct
	 * @return
	 */
	public void save(SaleListProduct saleListProduct);

	SaleListProduct findById(int parseInt);

	/***
	 * 根据机台生产查询
	 * @return
	 */
	public  List<SaleListProduct> findJitaiProduct();

	List<SaleListProduct> searchJitai(Map<String, Object> map);


	String updateAccomplishNumber(Integer id);

    void hebingJian(Integer count, Integer id);

	void deleteById(int parseInt);

	/***
	 * 订单加急
	 * @param id
	 * @param jiajidengji
	 */
	public void dingdanjiaji(Integer id, String jiajidengji);


	/***
	 * 产品加急
	 * @param id
	 * @param jiajidengji
	 */
	public void chanpinjiaji(Integer id,String jiajidengji);

    List<SaleListProduct> condition(SaleListProduct saleListProduct);

	List<SaleListProduct> findByJitaiId(SaleListProduct saleListProduct);

    String updateNum(Integer num, Integer id);

    List<SaleListProduct> find(SaleListProduct saleListProduct);

	void addpeifang(Long informNumber, Double peifangnum, Integer id);

	void deleteByIds(Integer[] ids);

	void passTheAudits(Integer[] idArr, String state);

	void fenPeiJiTai(Integer[] ids, String state, Integer jiTaiId, Long informNumber, String xiaFaZhuangTai);

	void xiaFaJiTai(Integer[] ids);
}