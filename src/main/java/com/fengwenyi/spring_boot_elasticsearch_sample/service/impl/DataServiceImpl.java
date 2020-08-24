package com.fengwenyi.spring_boot_elasticsearch_sample.service.impl;

import com.fengwenyi.javalib.convert.DateTimeUtils;
import com.fengwenyi.javalib.file.FileUtils;
import com.fengwenyi.javalib.util.StringUtils;
import com.fengwenyi.spring_boot_elasticsearch_sample.entity.PhoneEntity;
import com.fengwenyi.spring_boot_elasticsearch_sample.repository.PhoneRepository;
import com.fengwenyi.spring_boot_elasticsearch_sample.service.DataService;
import com.fengwenyi.spring_boot_elasticsearch_sample.util.HttpUtils;
import com.fengwenyi.spring_boot_elasticsearch_sample.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erwin Feng
 * @since 2020/7/4
 */
@Slf4j
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public void jd() {
        for (int i = 1; i < 100000; i++) {
            String url = "https://list.jd.com/list.html?cat=9987%2C653%2C655&page=" + i + "&s=1&click=0";
            grabJdPhone(url);
        }
    }

    @Override
    public void export() {
        int startPage = 0;
        int endPage = 1000000000;
        long sum = 0;
        long startTime = System.nanoTime();
        writeFile("[");
        for (int i = startPage; i < endPage; i++) {
            if (i > 0) {
                writeFile(",");
            }
            Pageable pageable = PageRequest.of(i, 1000);
            Page<PhoneEntity> phoneEntityPage = phoneRepository.findAll(pageable);
            long totalElements = phoneEntityPage.getNumberOfElements();
            if (totalElements == 0) {
                break;
            }
            sum += totalElements;
            List<PhoneEntity> collect = phoneEntityPage.get().collect(Collectors.toList());
            String json = JsonUtils.coverObject(collect);
            assert json != null;
            String content = json.substring(1, json.length() - 1);
            writeFile(content);
        }
        writeFile("]");
        long endTime = System.nanoTime();
        log.info("共处理 {} 条数据，共耗时：{} 秒", sum, (double) (endTime - startTime) / 1000000000);
        // 共处理 1253670 条数据，共耗时：444.7313754 秒
    }

    String filePath = "E:/Users/phone-info.json";
    Path path = Paths.get(filePath);

    private void writeFile(String content) {
        /*try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            // writer.write(content);
            writer.append(content);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            FileUtils.write(filePath, content, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void grabJdPhone(String url) {
        String result = HttpUtils.get(url);
//        log.info("【抓取数据】抓取JD手机信息：{}", result);

        Document document = Jsoup.parse(result);

        // 2020.7.5 0:35 --- 到这里，下面验证失败

//        Elements elements = document.select("#content-v3-plp #pagehidedata .plphidedata");
        Elements elementsByClass = document.getElementsByClass("gl-item");
        for (Element element : elementsByClass) {

            PhoneEntity phoneEntity = new PhoneEntity();

            LocalDateTime localDateTime = LocalDateTime.now();

//            log.info("--------------------------------------------------------------");

            Elements imgElement = element.select(".p-img img");
            String img = imgElement.attr("src");
            phoneEntity.setImgUrl(img);

//            log.info(img);

            Elements nameElement = element.select(".p-name em");
            String name = nameElement.text();
            phoneEntity.setName(name);

//            log.info(name);

            Elements adElement = element.select(".p-name i");
            String ad = adElement.text();

            phoneEntity.setAd(ad);

//            log.info(ad);

            Elements attrElements = element.select(".p-name .attr b");
            for (Element attrElement : attrElements) {
                String attrElementValue = attrElement.text();
                if (attrElementValue.contains("运存")) {
//                    log.info("内存：" + attrElementValue);
                    phoneEntity.setMemory(attrElementValue);
                }
                if (attrElementValue.contains("GB") && !attrElementValue.contains("运存")) {
//                    log.info("存储：" + attrElementValue);
                    phoneEntity.setStorage(attrElementValue);
                }
                if (attrElementValue.contains("英寸")) {
//                    log.info("屏幕：" + attrElementValue);
                    phoneEntity.setScreen(attrElementValue);
                }
            }

            Elements priceElement = element.select(".p-price i");
            String price = priceElement.text();
//            log.info(price);
            if (StringUtils.isNotEmpty(price)) {
                try {
                    phoneEntity.setPrice(BigDecimal.valueOf(Double.parseDouble(price)));
                } catch (Exception e) {
                    log.error(price);
                }
            }


//            log.info("=======================================================================");

            phoneEntity.setCreateTimeStamp(DateTimeUtils.toMillisecond(localDateTime))
                    .setCreateTimeString(DateTimeUtils.format(localDateTime, "yyyy-MM-dd HH:mm:ss,SSS"));

            phoneRepository.save(phoneEntity);
        }
    }
}