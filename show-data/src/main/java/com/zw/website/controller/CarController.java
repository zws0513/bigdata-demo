package com.zw.website.controller;

import com.zw.framework.controller.dto.BaseJsonResponse;
import com.zw.framework.controller.dto.NameFloatValuePair;
import com.zw.framework.controller.dto.NameIntValuePair;
import com.zw.website.controller.res.car.CityDistrictSaleShowJsonResponse;
import com.zw.website.controller.res.car.DiffBrandMonthSaleShowJsonResponse;
import com.zw.website.controller.res.car.MapSeriesData;
import com.zw.website.controller.res.car.UsageShowJsonResponse;
import com.zw.website.model.car.*;
import com.zw.website.service.car.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangws on 16/9/24.
 */
@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarsUsageAmountDistService carsUsageAmountDistService;

    @Autowired
    private CarsSaleScaleByMonthService carsSaleScaleByMonthService;

    @Autowired
    private CityDistrictSaleDistService cityDistrictSaleDistService;

    @Autowired
    private GenderScaleService genderScaleService;

    @Autowired
    private DiffBrandMonthSaleDistService diffBrandMonthSaleDistService;

    @Autowired
    private BrandEngineFuelService brandEngineFuelService;

    @Autowired
    private OwnerTypeBrandService ownerTypeBrandService;

    @RequestMapping("/usage")
    public String usage(Model model) {
        return "car/usage";
    }

    @RequestMapping("/usage/show")
    @ResponseBody
    public UsageShowJsonResponse usageShow(HttpServletRequest request) {
        List<CarsUsageAmountDist> ans = carsUsageAmountDistService.getAll("公交客运");
        UsageShowJsonResponse response = new UsageShowJsonResponse();
        response.setTitle("山西");
        if (ans != null) {
            int max = 0, min = 0;
            List<MapSeriesData> mapSeriesDatalist = new ArrayList<>();

            String name = "";
            List<NameIntValuePair> list = new ArrayList<>();
            for (CarsUsageAmountDist item : ans) {
                if (!name.equals(item.getUsages())) {
                    if (!name.equals("")) {
                        MapSeriesData mapSeriesData = new MapSeriesData();
                        mapSeriesData.setMapType("山西");
                        mapSeriesData.setName(name);
                        mapSeriesData.setData(list);
                        mapSeriesDatalist.add(mapSeriesData);
                    }
                    name = item.getUsages();
                    list.clear();
                }
                min = Math.min(min, item.getAmount());
                max = Math.max(max, item.getAmount());
                list.add(new NameIntValuePair(item.getCity(), item.getAmount()));
            }
            MapSeriesData mapSeriesData = new MapSeriesData();
            mapSeriesData.setMapType("山西");
            mapSeriesData.setName(name);
            mapSeriesData.setData(list);
            mapSeriesDatalist.add(mapSeriesData);

            String[] legendData = new String[mapSeriesDatalist.size()];

            for (int i = 0; i < mapSeriesDatalist.size(); i++) {
                legendData[i] = mapSeriesDatalist.get(i).getName();
            }

            response.setMax(max);
            response.setMin(min);
            response.setLegendData(legendData);
            response.setResult(true);
            response.setData(mapSeriesDatalist);
        } else {
            response.setResult(false);
        }
        return response;
    }

    @RequestMapping("/month-sale")
    public String monthSale(Model model) {
        return "car/month-sale";
    }

    @RequestMapping("/month-sale/show")
    @ResponseBody
    public BaseJsonResponse monthSaleShow(HttpServletRequest request) {
        List<CarsSaleScaleByMonth> ans = carsSaleScaleByMonthService.getAll();
        BaseJsonResponse response = new BaseJsonResponse();
        response.setTitle("统计山西省 2013 年每个月的汽车销售数量的比例");
        if (ans != null) {
            List<NameFloatValuePair> list = new ArrayList<>();
            for (CarsSaleScaleByMonth item : ans) {
                list.add(new NameFloatValuePair(String.format("%02d", item.getMonth()),
                        Float.valueOf(item.getScale().replace("%", ""))));
            }
            response.setResult(true);
            response.setData(list);
        } else {
            response.setResult(false);
        }
        return response;
    }

    @RequestMapping("/brand-engine-fuel")
    public String brandEngineFuel(Model model) {
        List<BrandEngineFuel> ans = brandEngineFuelService.getAll();
        model.addAttribute("all", ans);
        return "car/brand-engine-fuel";
    }

    @RequestMapping("/brand-engine-fuel/show")
    @ResponseBody
    public BaseJsonResponse brandEngineFuelShow(HttpServletRequest request) {
        List<BrandEngineFuel> ans = brandEngineFuelService.getAll();
        BaseJsonResponse response = new BaseJsonResponse();
        response.setTitle("统计发动机型号和燃料种类");
        if (ans != null) {
            response.setResult(true);
            response.setData(ans);
        } else {
            response.setResult(false);
        }
        return response;
    }

    @RequestMapping("/city-district-sale")
    public String cityDistrictSale(Model model) {
        return "car/city-district-sale";
    }

    @RequestMapping("/city-district-sale/show")
    @ResponseBody
    public CityDistrictSaleShowJsonResponse cityDistrictSaleShow(@RequestParam(value = "type", defaultValue = "province") String type,
                                                                 @RequestParam(value = "province", defaultValue = "") String province,
                                                                 HttpServletRequest request) {

        CityDistrictSaleShowJsonResponse response = new CityDistrictSaleShowJsonResponse();
        response.setTitle("各市、区县的汽车销售的分布");
        List<CityDistrictSaleDist> ans = null;
        switch (type) {
            case "city":
                ans = cityDistrictSaleDistService.getCityData(province);
                if (ans != null) {
                    int max = 0, min = 0;
                    List<NameIntValuePair> list = new ArrayList<>();
                    for (CityDistrictSaleDist item : ans) {
                        min = Math.min(min, item.getAmount());
                        max = Math.max(max, item.getAmount());
                        list.add(new NameIntValuePair(item.getCity(), item.getAmount()));
                    }
                    response.setMax(max);
                    response.setMin(min);
                    response.setResult(true);
                    response.setData(list);
                } else {
                    response.setResult(false);
                }
                break;
            case "district":
                ans = cityDistrictSaleDistService.getDistrictData(province);
                if (ans != null) {
                    int max = 0, min = 0;
                    List<NameIntValuePair> list = new ArrayList<>();
                    for (CityDistrictSaleDist item : ans) {
                        min = Math.min(min, item.getAmount());
                        max = Math.max(max, item.getAmount());
                        list.add(new NameIntValuePair(item.getDistrict(), item.getAmount()));
                    }
                    response.setMax(max);
                    response.setMin(min);
                    response.setResult(true);
                    response.setData(list);
                } else {
                    response.setResult(false);
                }
                break;
            default:
                ans = cityDistrictSaleDistService.getProvinceData();
                if (ans != null) {
                    int max = 0, min = 0;
                    List<NameIntValuePair> list = new ArrayList<>();
                    for (CityDistrictSaleDist item : ans) {
                        min = Math.min(min, item.getAmount());
                        max = Math.max(max, item.getAmount());
                        list.add(new NameIntValuePair(item.getProvince(), item.getAmount()));
                    }
                    response.setMax(max);
                    response.setMin(min);
                    response.setResult(true);
                    response.setData(list);
                } else {
                    response.setResult(false);
                }
                break;
        }

        return response;
    }

    @RequestMapping("/diff-brand-month-sale")
    public String diffBrandMonthSale(Model model) {
        model.addAttribute("brandAll", diffBrandMonthSaleDistService.getBrand());
        return "car/diff-brand-month-sale";
    }

    @RequestMapping("/diff-brand-month-sale/show")
    @ResponseBody
    public DiffBrandMonthSaleShowJsonResponse diffBrandMonthSaleShow(@RequestParam(value = "brand") String brand,
                                                   HttpServletRequest request) {
        List<DiffBrandMonthSaleDist> ans = diffBrandMonthSaleDistService.getBrandData(brand);
        DiffBrandMonthSaleShowJsonResponse response = new DiffBrandMonthSaleShowJsonResponse();
        response.setTitle(brand + " 在每个月的销售量分布");
        if (ans != null) {
            String[] xAxisData = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
            int[] amount = new int[xAxisData.length];
            ans.stream().forEach(param -> {
                int i = Integer.valueOf(param.getMonth());
                amount[i - 1] = param.getAmount();
            });
            response.setResult(true);
            response.setName("销售量");
            response.setxAxisData(xAxisData);
            response.setData(amount);
        } else {
            response.setResult(false);
        }
        return response;
    }

    @RequestMapping("/gender-scale")
    public String genderScale(Model model) {
        return "car/gender-scale";
    }

    @RequestMapping("/gender-scale/show")
    @ResponseBody
    public BaseJsonResponse genderScaleShow(HttpServletRequest request) {
        List<GenderScale> ans = genderScaleService.getAll();
        BaseJsonResponse response = new BaseJsonResponse();
        response.setTitle("统计买车的男女比例");
        if (ans != null) {
            List<NameFloatValuePair> list = new ArrayList<>();
            for (GenderScale item : ans) {
                list.add(new NameFloatValuePair(item.getGender(),
                        Float.valueOf(item.getScale().replace("%", ""))));
            }
            response.setResult(true);
            response.setData(list);
        } else {
            response.setResult(false);
        }
        return response;
    }

    @RequestMapping("/owner-type")
    public String ownerType(Model model) {
        List<OwnerTypeBrand> ans = ownerTypeBrandService.getAll();
        model.addAttribute("all", ans);
        return "car/owner-type";
    }

    @RequestMapping("/owner-type/show")
    @ResponseBody
    public BaseJsonResponse ownerTypeShow(HttpServletRequest request) {
        List<OwnerTypeBrand> ans = ownerTypeBrandService.getAll();
        BaseJsonResponse response = new BaseJsonResponse();
        response.setTitle("所有权、车辆类型和品牌的分布");
        if (ans != null) {
            response.setResult(true);
            response.setData(ans);
        } else {
            response.setResult(false);
        }
        return response;
    }
}
