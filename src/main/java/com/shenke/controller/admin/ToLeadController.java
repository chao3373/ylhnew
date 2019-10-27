//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.shenke.controller.admin;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.shenke.entity.Log;
import com.shenke.entity.SaleListProduct;
import com.shenke.service.BrandService;
import com.shenke.service.ClientService;
import com.shenke.service.DaoService;
import com.shenke.service.LetterService;
import com.shenke.service.LogService;
import com.shenke.service.PackService;
import com.shenke.service.ProductService;
import com.shenke.service.ToLeadService;
import com.shenke.service.WightService;
import com.shenke.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/admin/toLead"})
public class ToLeadController {
    @Resource
    private ToLeadService toLeadService;
    @Resource
    private LogService logService;
    @Resource
    private ProductService productService;
    @Resource
    private WightService wightService;
    @Resource
    private DaoService daoService;
    @Resource
    private BrandService brandService;
    @Resource
    private PackService packService;
    @Resource
    private LetterService letterService;
    @Resource
    private ClientService clientService;

    public ToLeadController() {
    }

    @RequestMapping({"/import"})
    public Map<String, Object> importt(@RequestParam("fileName") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap();
        String errorInfo = "";
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Map<String, Object>> mapList = reader.readAll();
        List<Map<String, Object>> data = new ArrayList();
        int i = 0;

        while (i < mapList.size()) {
            int j = i + 1;
            Map<String, Object> map1 = (Map) mapList.get(i);
            map1.put("name", map1.remove("产品名称").toString().trim());
            map1.put("model", map1.remove("宽度m").toString().trim());
            map1.put("price", map1.remove("厚度").toString().trim());
            map1.put("length", map1.remove("长度m").toString().trim());
            map1.put("color", map1.remove("颜色").toString().trim());
            map1.put("oneweight", map1.remove("理论重量").toString().trim());
            map1.put("num", map1.remove("件数").toString().trim());
            map1.put("sumwight", map1.remove("总重量").toString().trim());
            map1.put("dao", map1.remove("剖刀设置").toString().trim());
            map1.put("brand", map1.remove("商标设置").toString().trim());
            map1.put("letter", map1.remove("印字设置").toString().trim());
            map1.put("clientname", map1.remove("经销商名称").toString().trim());
            map1.put("code", map1.remove("序号").toString().trim());
            if (map1.get("农户名称") != null) {
                map1.put("peasant", map1.remove("农户名称").toString().trim());
            } else {
                map1.put("peasant", "");
            }

            if (map1.get("实际重量") != null) {
                map1.put("realityweight", map1.remove("实际重量").toString().trim());
            }

            if (map1.get("要求") != null) {
                map1.put("demand", map1.remove("要求").toString().trim());
            }

            if (map1.get("实际厚度mm") != null) {
                map1.put("meter", map1.remove("实际厚度mm").toString().trim());
            }

            if (map1.get("实际幅宽m") != null) {
                map1.put("realitymodel", map1.remove("实际幅宽m").toString().trim());
            }

            if (map1.get("包装") != null) {
                map1.put("pack", map1.remove("包装").toString().trim());
            }

            if (map1.get("name") != null && map1.get("dao") != null && map1.get("brand") != null && map1.get("letter") != null && map1.get("clientname") != null) {
                System.out.println(this.productService.findByName(map1.get("name").toString()));
                System.out.println(this.productService.findByName(map1.get("name").toString()).size());
                if (map1.get("name") != null && this.productService.findByName(map1.get("name").toString()).size() != 0) {
                    if (map1.get("dao") != null && this.daoService.findByName(map1.get("dao").toString()).size() != 0) {
                        if (map1.get("brand") != null && this.brandService.findByName(map1.get("brand").toString()).size() != 0) {
                            if (map1.get("letter") != null && this.letterService.findByName(map1.get("letter").toString()).size() != 0) {
                                if (map1.get("clientname") != null && this.clientService.findByName(map1.get("clientname").toString()).size() != 0) {
                                    if (map1.get("pack")!=null && this.packService.findByName(map1.get("pack").toString()).size()!=0){
                                        data.add(map1);
                                        ++i;
                                        continue;
                                    }
                                    map.put("success", false);
                                    map.put("errorInfo", "第" + j + "行包装有误");
                                    return map;
                                }

                                map.put("success", false);
                                map.put("errorInfo", "第" + j + "行客户姓名有误");
                                return map;
                            }

                            map.put("success", false);
                            System.out.println(this.letterService.findName(map1.get("letter").toString()));
                            map.put("errorInfo", "第" + j + "行印字设置有误");
                            return map;
                        }

                        map.put("success", false);
                        map.put("errorInfo", "第" + j + "行商标设置有误");
                        return map;
                    }

                    map.put("success", false);
                    map.put("errorInfo", "第" + j + "行剖刀设置有误");
                    return map;
                }

                map.put("success", false);
                map.put("errorInfo", "第" + j + "行产品名称有误");
                return map;
            }

            map.put("success", false);
            map.put("errorInfo", "第" + j + "行存在空白单元格");
            return map;
        }

        System.out.println(data);
        map.put("success", true);
        map.put("rows", data);
        return map;
    }

    @RequestMapping({"/importFile"})
    public Map<String, Object> getExcel(@RequestParam("fileName") MultipartFile file) {
        System.out.println("文件导入");
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        Workbook wb = null;
        if (prefix.equals("xlsx")) {
            try {
                wb = new XSSFWorkbook(file.getInputStream());
            } catch (IOException var22) {
                var22.printStackTrace();
            }
        } else if (prefix.equals("xls")) {
            try {
                wb = new HSSFWorkbook(file.getInputStream());
            } catch (IOException var21) {
                var21.printStackTrace();
            }
        }

        Sheet sheet = ((Workbook) wb).getSheetAt(0);
        Map<String, Object> map = new HashMap();
        DecimalFormat dd = new DecimalFormat("#.00");
        List<SaleListProduct> list = new ArrayList();
        Iterator var9 = sheet.iterator();

        while (true) {
            Row row;
            do {
                do {
                    if (!var9.hasNext()) {
                        this.logService.save(new Log("添加操作", "销售订单导入"));
                        return map;
                    }

                    row = (Row) var9.next();
                    System.out.println("行号：" + row.getRowNum());
                    System.out.println("行数：" + sheet.getPhysicalNumberOfRows());
                } while (row.getRowNum() == 0);
            } while (row.getRowNum() == 1);

            SaleListProduct saleListProduct = new SaleListProduct();
            Iterator var12 = row.iterator();

            while (var12.hasNext()) {
                Cell cell = (Cell) var12.next();
                if (cell.getColumnIndex() != 0 && cell.getRowIndex() != 0 && cell.getRowIndex() != 1) {
                    String name;
                    switch (cell.getColumnIndex()) {
                        case 1:
                            name = getStringCellValue(cell);
                            saleListProduct.setName(getStringCellValue(cell));
                            if (this.productService.findName(name) == null) {
                                map.put("errorInfo", "无该产品！");
                                map.put("success", false);
                                return map;
                            }
                            break;
                        case 2:
                            if (!StringUtil.isEmpty(getStringCellValue(cell))) {
                                saleListProduct.setModel(Double.parseDouble(getStringCellValue(cell)));
                            }
                            break;
                        case 3:
                            if (!StringUtil.isEmpty(getStringCellValue(cell))) {
                                saleListProduct.setPrice(getStringCellValue(cell));
                            }
                            break;
                        case 4:
                            if (!StringUtil.isEmpty(getStringCellValue(cell))) {
                                saleListProduct.setLength(Double.parseDouble(getStringCellValue(cell)));
                            }
                            break;
                        case 5:
                            if (!StringUtil.isEmpty(getStringCellValue(cell))) {
                                saleListProduct.setMeter(Double.parseDouble(dd.format(Double.parseDouble(getStringCellValue(cell)))));
                            }
                            break;
                        case 6:
                            saleListProduct.setColor(getStringCellValue(cell));
                            break;
                        case 7:
                            if (!StringUtil.isEmpty(getStringCellValue(cell))) {
                                saleListProduct.setOneweight(Double.parseDouble(dd.format(Double.parseDouble(getStringCellValue(cell)))));
                            }
                            break;
                        case 8:
                            if (!StringUtil.isEmpty(getStringCellValue(cell))) {
                                saleListProduct.setNum((int) Double.parseDouble(getStringCellValue(cell)));
                            }
                            break;
                        case 9:
                            if (!StringUtil.isEmpty(getStringCellValue(cell))) {
                                saleListProduct.setSumwight(Double.parseDouble(getStringCellValue(cell)));
                            }
                            break;
                        case 10:
                            if (!StringUtil.isEmpty(getStringCellValue(cell))) {
                                saleListProduct.setRealitymodel(Double.parseDouble(getStringCellValue(cell)));
                            }
                            break;
                        case 11:
                            if (!StringUtil.isEmpty(getStringCellValue(cell))) {
                                saleListProduct.setDemand(getStringCellValue(cell));
                            }
                            break;
                        case 12:
                            String weightname = getStringCellValue(cell);
                            saleListProduct.setWightset(getStringCellValue(cell));
                            if (this.wightService.findName(weightname) == null) {
                                map.put("errorInfo", "重量参数有误");
                                map.put("success", false);
                                return map;
                            }
                            break;
                        case 13:
                            String daoname = getStringCellValue(cell);
                            saleListProduct.setDao(getStringCellValue(cell));
                            if (this.daoService.findName(daoname) == null) {
                                map.put("errorInfo", "剖刀设置有误");
                                map.put("success", false);
                                return map;
                            }
                            break;
                        case 14:
                            String biaoname = getStringCellValue(cell);
                            saleListProduct.setBrand(getStringCellValue(cell));
                            if (this.brandService.findName(biaoname) == null) {
                                map.put("errorInfo", "商标设置有误");
                                map.put("scuuess", false);
                                return map;
                            }
                            break;
                        case 15:
                            String packname = getStringCellValue(cell);
                            saleListProduct.setPack(getStringCellValue(cell));
                            if (this.packService.findName(packname) == null) {
                                map.put("errorInfo", "包装设置有误");
                                map.put("success", false);
                                return map;
                            }
                            break;
                        case 16:
                            String lettername = getStringCellValue(cell);
                            saleListProduct.setLetter(getStringCellValue(cell));
                            if (this.letterService.findName(lettername) == null) {
                                map.put("errorInfo", "印字设置有误");
                                map.put("success", false);
                                return map;
                            }
                            break;
                        case 17:
                            saleListProduct.setPeasant(getStringCellValue(cell));
                            break;
                        case 18:
                            String clname = getStringCellValue(cell);
                            System.out.println("客户名称：" + clname);
                            saleListProduct.setClientname(getStringCellValue(cell));
                            if (this.clientService.findName(clname) == null) {
                                map.put("errorInfo", "客户姓名有误");
                                map.put("success", false);
                                return map;
                            }
                            break;
                        case 19:
                            if (!StringUtil.isEmpty(getStringCellValue(cell))) {
                                saleListProduct.setRealityweight(Double.parseDouble(getStringCellValue(cell)));
                            }
                    }

                    name = getStringCellValue(cell);
                }
            }

            map.put("success", true);
            map.put("rows", list);
            list.add(saleListProduct);
        }
    }

    private static String getStringCellValue(Cell cell) {
        String strCell;
        switch (cell.getCellType()) {
            case 0:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case 1:
                strCell = cell.getStringCellValue();
                break;
            case 2:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case 3:
                strCell = "";
                break;
            case 4:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                strCell = "";
        }

        return strCell.equals("") ? "" : strCell;
    }

    @RequestMapping({"/lingshouimport"})
    public Map<String, Object> lingshouimport(@RequestParam("fileName") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap();
        String errorInfo = "";
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Map<String, Object>> mapList = reader.readAll();
        List<Map<String, Object>> data = new ArrayList();

        for (int i = 0; i < mapList.size(); ++i) {
            int j = i + 1;
            Map<String, Object> map1 = (Map) mapList.get(i);
            map1.put("name", map1.remove("品名").toString().trim());
            if (map1.get("重") != null && StringUtil.isNotEmpty(map1.get("重").toString())) {
                map1.put("weight", map1.remove("重").toString().trim());
            } else {
                Double weight = Double.parseDouble(map1.get("长").toString().trim()) * Double.parseDouble(map1.get("宽").toString().trim()) * Double.parseDouble(map1.get("厚").toString().trim()) * 0.93D;
                map1.put("weight", (new DecimalFormat("#.00")).format(weight));
            }

            map1.put("price", map1.remove("厚").toString().trim());
            map1.put("length", map1.remove("长").toString().trim());
            map1.put("model", map1.remove("宽").toString().trim());
            map1.put("num", map1.remove("件数").toString().trim());
            if (map1.get("要求") != null) {
                map1.put("beizhu", map1.remove("要求").toString().trim());
            } else {
                map1.put("beizhu", "");
            }

            map1.put("danjia", map1.remove("单价").toString().trim());
            if (map1.get("农户") != null) {
                map1.put("peasant", map1.remove("农户").toString().trim());
            } else {
                map1.put("peasant", "");
            }

            if (map1.get("name") == null || this.productService.findByName(map1.get("name").toString()).size() == 0) {
                map.put("success", false);
                map.put("errorInfo", "第" + j + "行产品名称有误");
                return map;
            }

            if (map1.get("其他费用") != null) {
                map1.put("qita", map1.remove("其他费用").toString().trim());
            } else {
                map1.put("qita", 0);
            }

            data.add(map1);
        }

        System.out.println(data);
        map.put("success", true);
        map.put("rows", data);
        return map;
    }
}
