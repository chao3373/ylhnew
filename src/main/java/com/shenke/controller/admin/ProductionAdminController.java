package com.shenke.controller.admin;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shenke.entity.JiTai;
import com.shenke.entity.JitaiProductionAllot;
import com.shenke.entity.Log;
import com.shenke.entity.SaleListProduct;
import com.shenke.service.JiTaiService;
import com.shenke.service.JitaiProductionAllotService;
import com.shenke.service.LogService;
import com.shenke.service.SaleListProductService;

/**
 * 生产订单Controller
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/production")
public class ProductionAdminController {

	@Resource
	private JiTaiService jiTaiService;

	@Resource
	private SaleListProductService saleListProductService;

	@Resource
	private LogService logService;

	@Resource
	private JitaiProductionAllotService jitaiProductionAllotService;

	/**
	 * 分配机台信息
	 * 
	 * @return
	 */
	@RequestMapping("/jitaiAllocation")
	public Map<String, Object> jitaiAllocation(Integer[] ids, Integer jitai) {
		Map<String, Object> map = new HashMap<String, Object>();

		JiTai jitai1 = jiTaiService.findById(jitai);
		logService.save(new Log(Log.UPDATE_ACTION, "分配机台"));
		Long informNumber = this.getInformNumber();

//		saleListProductService.auditFailure(id, "分配机台：" + jitai1.getName());
//		saleListProductService.updateJitaiId(id, jitai1.getId());
//		saleListProductService.updateInformNumber(informNumber, id);
//		saleListProductService.updateIussueState("未下发", id);
		saleListProductService.fenPeiJiTai(ids, "分配机台：" + jitai1.getName(), jitai1.getId(), informNumber, "未下发");
		map.put("success", true);
		return map;
	}

	/**
	 * 生成通知单号
	 * 
	 * @return
	 */
	public Long getInformNumber() {
		if (saleListProductService.findMaxInfornNumber() != null) {
			return saleListProductService.findMaxInfornNumber() + 1;
		} else {
			return 1L;
		}
	}
}
