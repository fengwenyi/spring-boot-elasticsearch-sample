package com.fengwenyi.spring_boot_elasticsearch_sample.repository;

import com.fengwenyi.javalib.util.DateTimeUtils;
import com.fengwenyi.spring_boot_elasticsearch_sample.entity.PhoneEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Erwin Feng
 * @since 2020/7/4
 */
@SpringBootTest
public class PhoneRepositoryTests {

    @Autowired
    private PhoneRepository phoneRepository;

    @Test
    public void test() {
        // 测试添加
        PhoneEntity phoneEntity = testAdd();
        Assert.notNull(phoneEntity, "保存失败");

        // 测试查询
        PhoneEntity findEntity = testGet(phoneEntity.getId());
        Assert.notNull(findEntity, "查询失败");
        Assert.isTrue(findEntity.getName().equals(phoneEntity.getName()), "保存对象不相等");

        // 测试修改
        PhoneEntity updateEntity = testUpdate(phoneEntity.setName("华为"));
        Assert.isTrue(updateEntity.getName().equals(phoneEntity.getName()), "修改对象不相等");

        // 测试删除
        testDelete(phoneEntity.getId());
        PhoneEntity findDeleteEntity = testGet(phoneEntity.getId());
        Assert.isNull(findDeleteEntity, "删除失败");

    }

    /**
     * 测试添加数据
     * @return {@link PhoneEntity}
     */
    private PhoneEntity testAdd() {
        LocalDateTime localDateTime = LocalDateTime.now();
        PhoneEntity phoneEntity = new PhoneEntity()
                .setName("小米")
                .setCreateTimeStamp(DateTimeUtils.toMillisecond(localDateTime))
                .setCreateTimeString(DateTimeUtils.format(localDateTime, "yyyy-MM-dd HH:mm:ss"))
                ;
        return phoneRepository.save(phoneEntity);

    }

    /**
     * 测试查询数据
     * @param id ID
     * @return {@link PhoneEntity}
     */
    private PhoneEntity testGet(String id) {
        Optional<PhoneEntity> optionalPhoneEntity =  phoneRepository.findById(id);
        return optionalPhoneEntity.orElse(null);
    }

    /**
     * 测试修改时间
     * @param entity {@link PhoneEntity}
     * @return {@link PhoneEntity}
     */
    private PhoneEntity testUpdate(PhoneEntity entity) {
        return phoneRepository.save(entity);
    }

    /**
     * 测试删除数据
     * @param id ID
     */
    private void testDelete(String id) {
        phoneRepository.deleteById(id);
    }

}
