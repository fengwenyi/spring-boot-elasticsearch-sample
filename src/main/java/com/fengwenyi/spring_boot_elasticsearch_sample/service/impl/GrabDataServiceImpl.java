package com.fengwenyi.spring_boot_elasticsearch_sample.service.impl;

import com.fengwenyi.spring_boot_elasticsearch_sample.bean.HuaweiPhoneBean;
import com.fengwenyi.spring_boot_elasticsearch_sample.constant.Constants;
import com.fengwenyi.spring_boot_elasticsearch_sample.service.GrabDataService;
import com.fengwenyi.spring_boot_elasticsearch_sample.util.HttpUtils;
import com.fengwenyi.spring_boot_elasticsearch_sample.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Erwin Feng
 * @since 2020/7/4
 */
@Slf4j
@Service
public class GrabDataServiceImpl implements GrabDataService {

    @Override
    public void huawei() {

        String result = HttpUtils.get(Constants.URL_PHONE_HUAWEI);
        log.info("【抓取数据】抓取华为手机信息：{}", result);

        Document document = Jsoup.parse(result);

        // 2020.7.5 0:35 --- 到这里，下面验证失败

        Elements elements = document.select("#content-v3-plp #pagehidedata .plphidedata");
        for (Element element : elements) {
            String content = element.text();
            log.info("【抓取数据】解析华为手机数据：{}", content);
            List<HuaweiPhoneBean> list = JsonUtils.coverObject(result, List.class);
            //List<HuaWeiPhoneBean> list = JSON.parseArray(jsonStr, HuaWeiPhoneBean.class);
            for (HuaweiPhoneBean bean : list) {
                String productName = bean.getProductName();
//                List<ColorModeBean> colorsItemModeList = bean.getColorsItemMode();
//
//                StringBuilder colors = new StringBuilder();
//                for (ColorModeBean colorModeBean : colorsItemModeList) {
//                    String colorName = colorModeBean.getColorName();
//                    colors.append(colorName).append(";");
//                }
//
//                List<String> sellingPointList = bean.getSellingPoints();
//                StringBuilder sellingPoints = new StringBuilder();
//                for (String sellingPoint : sellingPointList) {
//                    sellingPoints.append(sellingPoint).append(";");
//                }

//                System.out.println("产品名：" + productName);
//                System.out.println("颜  色：" + color);
//                System.out.println("买  点：" + sellingPoint);
//                System.out.println("-----------------------------------");
//                PhoneModel phoneModel = new PhoneModel()
//                        .setName(productName)
//                        .setColors(colors.substring(0, colors.length() - 1))
//                        .setSellingPoints(sellingPoints.substring(0, sellingPoints.length() - 1))
//                        .setCreateTime(new Date());
//                phoneRepository.save(phoneModel);
            }
        }
    }

    @Override
    public void xiaomi() {

    }

    @Override
    public void apple() {

    }
}
