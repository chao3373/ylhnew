package com.shenke.controller.admin;

import java.util.*;
import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenke.entity.*;
import com.shenke.service.*;
import com.shenke.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/saleListProduct")
public class SaleListProductAdminController {

    @Resource
    private LogService logService;

    @Resource
    private PeiFangShouService peiFangShouService;

    @Resource
    private ClientService clientService;

    @Resource
    private SaleListProductService saleListProductService;

    @Resource
    private StorageService storageService;

    @Resource
    private JiTaiService jiTaiService;

    @Resource
    private PeiFangInfoService peiFangInfoService;

    /**
     * 订单审核通过
     *
     * @return
     */
    @RequestMapping("/passTheAudit")
    public Map<String, Object> passTheAudit(String ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            int id = Integer.parseInt(idArr[i]);

            logService.save(new Log(Log.AUDIT_ACTION, "审核通过"));
            saleListProductService.passTheAudit(id);
        }
        map.put("success", true);
        return map;
    }

    /**
     * 订单审核失败
     *
     * @param ids
     * @return
     */
    @RequestMapping("/auditFailure")
    public Map<String, Object> auditFailure(String ids, String cause) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            int id = Integer.parseInt(idArr[i]);
            logService.save(new Log(Log.AUDIT_ACTION, "审核失败"));
            saleListProductService.auditFailure(id, cause);
        }
        map.put("success", true);
        return map;
    }

    /**
     * 查询所有审核成功的销售商品信息
     *
     * @return
     */
    @RequestMapping("/listProductSucceed")
    public Map<String, Object> listProductSucceed() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", saleListProductService.listProductSucceed());
        return map;
    }

    /**
     * 下拉框模糊查询所有幅宽
     *
     * @return
     */
    @RequestMapping("/breadthList")
    public List<SaleListProduct> breadthList(String q) {
        if (q == null) {
            q = "";
        }
        return saleListProductService.breadthList(q);
    }

    /**
     * 根据条件查询所有订单商品信息
     *
     * @param
     * @param priceSort
     * @param lengthSort
     * @param client
     * @return
     */
    @RequestMapping("/screen")
    public Map<String, Object> screen(Double modeSort1, Double modeSort2, String priceSort, Double lengthSort, String client,
                                      Double oneweight) {

        System.out.println(modeSort1 + "********************" + modeSort2);

        Map<String, Object> map = new HashMap<String, Object>();
        /*Map<String, Object> condition = new HashMap<String, Object>();

        condition.put("modeSort1", modeSort1);
        condition.put("modeSort2", modeSort2);
        condition.put("priceSort", priceSort);
        condition.put("lengthSort", lengthSort);
        condition.put("client", client);
        condition.put("oneweight", oneweight);*/

        SaleListProduct saleListProduct = new SaleListProduct();
        saleListProduct.setModel(modeSort1);
        saleListProduct.setMeter(modeSort2);
        saleListProduct.setPrice(priceSort);
        saleListProduct.setLength(lengthSort);
        saleListProduct.setClientname(client);
        saleListProduct.setOneweight(oneweight);

        List<SaleListProduct> screen = saleListProductService.screen(saleListProduct);
        map.put("success", true);
        map.put("rows", screen);
        return map;
    }

    /**
     * 根据机台id，下发状态，通知单号查询
     *
     * @Description:
     * @Param:
     * @return:
     * @Author: Andy
     * @Date:
     */
    @RequestMapping("/selectByJitaiIdAndIssueStateAndInformNumber")
    public Map<String, Object> selectByJitaiIdAndIssueStateAndInformNumber(Integer jitaiId, String state, String informNumber) {
        Map<String, Object> map = new HashMap<String, Object>();
        Long infLong = null;
        System.out.println(informNumber);
        if (StringUtil.isNotEmpty(informNumber)) {
            infLong = Long.parseLong(informNumber);
        }
        if (saleListProductService.selectByJitaiIdAndIssueStateAndInformNumber(jitaiId, state, infLong) != null) {
            map.put("success", true);
            map.put("rows", saleListProductService.selectByJitaiIdAndIssueStateAndInformNumber(jitaiId, state, infLong));
        } else {
            map.put("success", false);
        }

        return map;
    }

    /**
     * 查询该机台没有所有未完成的生产通知单号
     *
     * @Description:
     * @Param:
     * @return:
     * @Author: Andy
     * @Date:
     */
    @RequestMapping("/selectNoAccomplish")
    public Map<String, Object> selectNoAccomplish(Integer jitaiId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        List<SaleListProduct> saleListProducts = saleListProductService.selectNoAccomplish(jitaiId);
        List<Long> informNumber = new ArrayList<>();
        for (SaleListProduct saleListProduct : saleListProducts) {
            informNumber.add(saleListProduct.getInformNumber());
        }
        map.put("rows", informNumber);
        return map;
    }

    /**
     * 根据订单状态查询订单
     */
    @RequestMapping("/listProductByState")
    public Map<String, Object> listProductByState(String state) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("rows", saleListProductService.listProductByState(state));
        return map;
    }


    /**
     * 添加或修改仓库信息
     */
    @RequestMapping("/saveInfo")
    public Map<String, Object> save(SaleListProduct saleListProduct) {
        System.out.println("****************************************");
        System.out.println(saleListProduct);
        System.out.println("controller");
        Map<String, Object> map = new HashMap<>();
        saleListProductService.save(saleListProduct);
        map.put("success", true);
        return map;
    }

    /***
     * 修改机台
     */
    @RequestMapping("/alertJitai")
    public Map<String, Object> alertJitai(String idsStr, Integer jitai) {
        Map<String, Object> map = new HashMap<>();
        String[] ids = idsStr.split(",");
        for (int i = 0; i < ids.length; i++) {
            saleListProductService.updateJitaiId(Integer.parseInt(ids[i]), jitai);
        }
        map.put("success", true);
        return map;
    }

    /***
     * 下发机台
     * @return
     */
    @RequestMapping("/issue")
    public Map<String, Object> issue(String idStr) {
        Map<String, Object> map = new HashMap<>();
        String[] ids = idStr.split(",");
        for (int i = 0; i < ids.length; i++) {
            SaleListProduct saleListProduct = saleListProductService.findById(Integer.parseInt(ids[i]));
            saleListProductService.updateState("下发机台：" + saleListProduct.getJiTai().getName(), saleListProduct.getId());
            saleListProductService.updateIussueState("下发机台：" + saleListProduct.getJiTai().getName(), saleListProduct.getId());
        }
        map.put("success", true);
        return map;
    }


    @RequestMapping("/findAll")
    public Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<>();
        List<SaleListProduct> list = saleListProductService.fandAll();
        map.put("rows", list);
        System.out.println(map);
        return map;
    }

    @RequestMapping("/findJitaiProduct")
    public Map<String, Object> findJitaiProduct() {
        Map<String, Object> map = new HashMap<>();
        List<SaleListProduct> list = saleListProductService.findJitaiProduct();
        map.put("rows", list);
        return map;
    }

    @RequestMapping("/searchJitai")
    public Map<String, Object> searchLiftMoney(String saleNumber, Integer jitai, String saleDate, String deliveryDate, String allorState, String state) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();

        map1.put("saleNumber", saleNumber);
        map1.put("jitai", jitai);
        map1.put("saleDate", saleDate);
        map1.put("allorState", allorState);
        map1.put("deliveryDate", deliveryDate);
        map1.put("state", state);


        map.put("success", true);
        map.put("rows", saleListProductService.searchJitai(map1));

        System.out.println(map);

        return map;
    }

    /**
     * 修改完成数量
     *
     * @param id
     */
    @RequestMapping("/updateAccomplishNumber")
    public synchronized Map<String, Object> update(Integer id, Integer jiTai, String clerkName, Double realityweight, String type, Double changdu, String groupName) {
        SaleListProduct saleListProduct = saleListProductService.findById(id);
        Integer daBaoShu = saleListProduct.getDaBaoShu();
        System.out.println(jiTai);
        JiTai jiTaiServiceById = jiTaiService.findById(jiTai);
        saleListProductService.findById(id);
        Storage storage = new Storage();
        storage.setJiTai(jiTaiServiceById);
        storage.setSaleListProduct(saleListProduct);
        if (type.equals("快速")) {
            storage.setRealityweight(saleListProduct.getOneweight());
        } else {
            storage.setRealityweight(realityweight);
        }
        Map<String, Object> map = new HashMap<>();
        String result = saleListProductService.updateAccomplishNumber(id);
        if (result.split(":").length > 1) {
            int num = Integer.parseInt(result.split(":")[1]);
            storage.setDabaonum(num);
        }
        if (result.startsWith("生产完成")) {
            if (changdu != null) {
                storageService.add(storage, clerkName, groupName, changdu);
            } else {
                storageService.add(storage, clerkName, groupName);
            }
            map.put("success", true);
            map.put("id", storageService.selectByMaxId().getId());
        } else if (result.startsWith("打包完成")) {
            if (changdu != null) {
                storageService.add(storage, clerkName, groupName, changdu);
            } else {
                storageService.add(storage, clerkName, groupName);
            }
            map.put("success", true);
            map.put("id", storageService.selectByMaxId().getId());
        } else if (result.startsWith("生产已完成")) {
            map.put("success", true);
            map.put("msg", result);
        } else {
            map.put("success", false);
        }
        return map;
    }
//    @RequestMapping("/updateAccomplishNumber")
//    public String update(Integer id) {
//        Map<String, Object> map = new HashMap<>();
//        String result = saleListProductService.updateAccomplishNumber(id);
//        return result;
//    }

    @RequestMapping("/hebingJian")
    public Map<String, Object> hebingJian(Integer count, Integer id) {
        Map<String, Object> map = new HashMap<>();
        saleListProductService.hebingJian(count, id);
        map.put("success", true);
        return map;
    }

    @RequestMapping("/hebingOne")
    public Map<String, Object> hebingOne(String ids) {
        System.out.println(ids);
        Map<String, Object> map = new HashMap<>();
        String[] idArr = ids.split(",");
        SaleListProduct byId = saleListProductService.findById(Integer.parseInt(idArr[0]));
        double length = byId.getLength();
        StringBuilder hebingLength = new StringBuilder();
        hebingLength.append(length);
        for (int i = 1; i < idArr.length; i++) {
            length += saleListProductService.findById(Integer.parseInt(idArr[0])).getLength();
            hebingLength.append("+" + (int) Math.floor(saleListProductService.findById(Integer.parseInt(idArr[0])).getLength()));
            saleListProductService.deleteById(Integer.parseInt(idArr[i]));
        }
        byId.setLength(length);
        byId.setHebingLength(hebingLength.toString());
        save(byId);
        map.put("success", true);
        System.out.println(map);
        return map;
    }

    /**
     * 产品加急
     *
     * @param
     * @param jiajidengji
     * @return
     */
    @RequestMapping("/chanpinjiaji")
    public Map<String, Object> chanpinjiaji(String idsStr, String jiajidengji) {
        System.out.println("**********");
        System.out.println(idsStr);
        System.out.println("**********");
        Map<String, Object> map = new HashMap<>();
        String[] ids = idsStr.split(",");
        for (int i = 0; i < ids.length; i++) {
            saleListProductService.chanpinjiaji(Integer.parseInt(ids[i]), jiajidengji);
        }
        map.put("success", true);
        return map;
    }

    /***
     * 订单加急
     * @param id
     * @param jiajidengji
     * @return
     */
    @RequestMapping("/dingdanjiaji")
    public Map<String, Object> dingdanjiaji(Integer id, String jiajidengji) {
        Map<String, Object> map = new HashMap<>();
        saleListProductService.dingdanjiaji(id, jiajidengji);
        map.put("success", true);
        return map;
    }

    /***
     * 按条件查询
     * @return
     */
    @RequestMapping("/condition")
    public Map<String, Object> condition(SaleListProduct saleListProduct) {
        System.out.println(saleListProduct);
        Map<String, Object> map = new HashMap<>();
        List<SaleListProduct> condition = saleListProductService.condition(saleListProduct);
        Integer num = 0;
        Double weight = 0.0;

        for (SaleListProduct saleListProduct1 : condition) {
            System.out.println(saleListProduct1);
            Integer wancheng = 0;
            if (saleListProduct1.getAccomplishNumber() != null) {
                wancheng = saleListProduct1.getAccomplishNumber();
            }
            System.out.println("完成数量：" + wancheng);
            Integer num1 = saleListProduct1.getNum() - wancheng;
            num += num1;
            System.out.println("剩余数量：" + num);
            weight += num1 * saleListProduct1.getOneweight();
            System.out.println("剩余总重量：" + weight);
        }

        if (condition.size() != 0) {
            map.put("success", true);
            map.put("num", num);
            map.put("weight", weight);
        } else {
            map.put("success", false);
            map.put("msg", "没有符合条件的商品");
        }
        return map;
    }

    @RequestMapping("/findByJitaiId")
    public Map<String, Object> findByJitaiId(SaleListProduct saleListProduct) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(saleListProduct);
        List<SaleListProduct> byJitaiId = saleListProductService.findByJitaiId(saleListProduct);
        map.put("success", true);
        map.put("rows", byJitaiId);
        return map;
    }

    @RequestMapping("/deleteByIdArr")
    public Map<String, Object> deleteByIdArr(String idArr) {
        Map<String, Object> map = new HashMap<>();
        String[] ids = idArr.split(",");
        for (int i = 0; i < ids.length; i++) {
            saleListProductService.deleteById(Integer.parseInt(ids[i]));
        }
        map.put("success", true);
        return map;
    }

    /***
     * 修改订单数量
     * @param num
     * @return
     */
    @RequestMapping("/updateNum")
    public String updateNum(Integer num, Integer id) {
        return saleListProductService.updateNum(num, id);
    }

    /***
     * 根据id修改完成数量
     * @param id
     * @param wancheng
     * @return
     */
    @RequestMapping("/updateWanCehng")
    public String updateWanCehng(Integer id, Integer wancheng) {
        SaleListProduct saleListProduct = saleListProductService.findById(id);

        Integer num = saleListProduct.getNum();
        Integer count = storageService.findCountBySaleListProductId(saleListProduct.getId());
        System.out.println(count);
        System.out.println(wancheng);
        if (count > wancheng) {
            return "库存中已完成比该数量多的件数，请删除库存！库存中的数量：" + count;
        } else if (count < wancheng) {
            return "库存中改订单的货物完成数量不足，请进行生产！当前库存中的数量：" + count;
        }
        if (wancheng > num) {
            return "完成数量大于总数量无法修改！";
        } else if (wancheng < num) {
            saleListProduct.setAccomplishNumber(wancheng);
            saleListProduct.setState("下发机台：" + saleListProduct.getJiTai().getName());
            saleListProductService.save(saleListProduct);
            return "完成数量小于总数量修改状态为下发机台！";
        } else {
            saleListProduct.setAccomplishNumber(wancheng);
            saleListProduct.setState("生产完成：" + saleListProduct.getJiTai().getName());
            saleListProductService.save(saleListProduct);
            return "完成数量等于总数量修改状态为生产完成！";
        }
    }

    /***
     * 修改打包数量
     * @return
     */
    @RequestMapping("/updateDaBaoShu")
    public boolean updateDaBaoShu(Integer id, Integer dabaoshu) {
        System.out.println(id);
        System.out.println(dabaoshu);
        SaleListProduct byId = saleListProductService.findById(id);
        byId.setDaBaoShu(dabaoshu);
        saleListProductService.save(byId);
        return true;
    }

    /**
     * 加载通知单号
     *
     * @return
     */
    @RequestMapping("/loadInfornum")
    public Long loadInfornum() {
        return saleListProductService.findMaxInfornNumber() + 1;
    }

    @RequestMapping("/find")
    public Map<String, Object> find(SaleListProduct saleListProduct) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(saleListProduct);
        List<SaleListProduct> saleListProducts = saleListProductService.find(saleListProduct);
        Long informNumber = saleListProduct.getInformNumber();
        List<PeiFangShou> peiFangShous = peiFangShouService.findByInfornNumber(informNumber);
        if (peiFangShous != null && peiFangShous.size() != 0) {
            map.put("peifang", peiFangShous);
        }
        map.put("success", true);
        map.put("rows", saleListProducts);
        return map;
    }

    @RequestMapping("/addpeifang")
    public Map<String, Object> addpeifang(SaleListProduct saleListProduct, String peifangjson) {
        System.out.println(peifangjson);
        Long informNumber = saleListProduct.getInformNumber();
        Gson gson = new Gson();
        List<PeiFangShou> peiFangShous = peiFangShouService.findByInfornNumber(informNumber);
        if (peiFangShous != null && peiFangShous.size() > 0) {
            peiFangShouService.deleteList(peiFangShous);
        }
        List<PeiFangShou> plgList = gson.fromJson(peifangjson, new TypeToken<List<PeiFangShou>>() {
        }.getType());
        System.out.println(plgList);
        System.out.println(saleListProduct);
        for (PeiFangShou peiFangShou : plgList) {
            peiFangShou.setInformNumber(informNumber);
        }
        Map<String, Object> map = new HashMap<>();
        peiFangShouService.saveList(plgList);
        map.put("success", true);
        return map;
    }

    /**
     * 通过Id批量删除
     * @param Ids
     * @return
     */
    @RequestMapping("/deleteByIds")
    public Map<String,Object> deleteByIds(Integer[] Ids){
        Map<String,Object> map = new HashMap<>();
        saleListProductService.deleteByIds(Ids);
        map.put("success",true);
        return map;
    }
}
