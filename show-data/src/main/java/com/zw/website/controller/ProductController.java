package com.zw.website.controller;

import com.zw.framework.controller.dto.BaseJsonResponse;
import com.zw.framework.controller.dto.NameIntValuePair;
import com.zw.framework.utils.DateUtil;
import com.zw.website.controller.res.KindShowJsonResponse;
import com.zw.website.controller.res.MarketShowJsonResponse;
import com.zw.website.controller.res.PriceShowJsonResponse;
import com.zw.website.model.CoKindModel;
import com.zw.website.model.KindStatisticsModel;
import com.zw.website.model.MarketStatisticsModel;
import com.zw.website.model.PriceStatisticsModel;
import com.zw.website.service.product.CoKindService;
import com.zw.website.service.product.KindStatisticsService;
import com.zw.website.service.product.MarketStatisticsService;
import com.zw.website.service.product.PriceStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 农产品数据展示
 * <p>
 * Created by zhangws on 16/9/22.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private MarketStatisticsService marketStatisticsService;

    @Autowired
    private CoKindService coKindService;

    @Autowired
    private KindStatisticsService kindStatisticsService;

    @Autowired
    private PriceStatisticsService priceStatisticsService;

    @RequestMapping("/market")
    public String marketStatistics(Model model) {
        return "product/market";
    }

    @RequestMapping("/market/show")
    @ResponseBody
    public MarketShowJsonResponse marketShow(HttpServletRequest request) {
        List<MarketStatisticsModel> ans = marketStatisticsService.getAll();
        MarketShowJsonResponse response = new MarketShowJsonResponse();
        response.setTitle("统计每个省份的农产品市场总数");
        if (ans != null) {
            int max = 0, min = 0;
            List<NameIntValuePair> list = new ArrayList<>();
            for (MarketStatisticsModel item : ans) {
                min = Math.min(min, item.getMarketCnt());
                max = Math.max(max, item.getMarketCnt());
                list.add(new NameIntValuePair(item.getProvince(), item.getMarketCnt()));
            }
            response.setMax(max);
            response.setMin(min);
            response.setResult(true);
            response.setData(list);
        } else {
            response.setResult(false);
        }
        return response;
    }

    @RequestMapping("/kind")
    public String kindStatistics(Model model) {
        return "product/kind";
    }

    @RequestMapping("/kind/show")
    @ResponseBody
    public KindShowJsonResponse kindShow(HttpServletRequest request) {
        List<KindStatisticsModel> ans = kindStatisticsService.getAll();
        KindShowJsonResponse response = new KindShowJsonResponse();
        response.setTitle("统计每个省农产品种类总数");
        if (ans != null) {
            int max = 0, min = 0;
            List<NameIntValuePair> list = new ArrayList<>();
            for (KindStatisticsModel item : ans) {
                min = Math.min(min, item.getKindCnt());
                max = Math.max(max, item.getKindCnt());
                list.add(new NameIntValuePair(item.getProvince(), item.getKindCnt()));
            }
            response.setMax(max);
            response.setMin(min);
            response.setResult(true);
            response.setData(list);
        } else {
            response.setResult(false);
        }
        return response;
    }

    @RequestMapping("/co-kind")
    public String coKindStatistics(Model model) {
        List<CoKindModel> ans = coKindService.getAll();
        model.addAttribute("all", ans);
        return "product/co-kind";
    }

    @RequestMapping("/co-kind/show")
    @ResponseBody
    public BaseJsonResponse coKindShow(HttpServletRequest request) {
        List<CoKindModel> ans = coKindService.getAll();
        BaseJsonResponse response = new BaseJsonResponse();
        response.setTitle("统计排名前 3 的省份共同拥有的农产品类型");
        if (ans != null) {
            response.setResult(true);
            response.setData(ans);
        } else {
            response.setResult(false);
        }
        return response;
    }

    @RequestMapping("/price")
    public String priceStatistics(Model model) {
        return "product/price";
    }

    @RequestMapping("/price/show")
    @ResponseBody
    public PriceShowJsonResponse priceShow(HttpServletRequest request) {
        List<PriceStatisticsModel> ans = priceStatisticsService.getAll();
        PriceShowJsonResponse response = new PriceShowJsonResponse();
        response.setTitle("山西省的每种农产品的价格波动趋势");
        if (ans != null) {
            String[] legendData = new String[10];
            String[] xAxisData = new String[5];
            List<PriceShowJsonResponse.EntityData> seriesData = new ArrayList<>();
            boolean isNotXAxisSet = true;
            for (int i = 0; i < 50; i=i+5) {
                legendData[i / 5] = ans.get(i).getKind();
                Float[] data = new Float[5];
                for (int j = 0; j < 5; j++) {
                    // 设置横坐标
                    if (isNotXAxisSet) {
                        xAxisData[j] = DateUtil.formatDate(ans.get(i+j).getCollectDate(),
                                DateUtil.YYYY_MM_DD);
                    }
                    data[j] = ans.get(i + j).getPrice();
                }
                seriesData.add(new PriceShowJsonResponse.EntityData(ans.get(i).getKind(), data));
                isNotXAxisSet = false;
            }
            response.setResult(true);
            response.setLegendData(legendData);
            response.setxAxisData(xAxisData);
            response.setData(seriesData);
        } else {
            response.setResult(false);
        }
        return response;
    }
}
