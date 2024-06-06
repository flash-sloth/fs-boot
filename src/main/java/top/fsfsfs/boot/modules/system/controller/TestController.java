package top.fsfsfs.boot.modules.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.cache.redis.CacheResult;
import top.fsfsfs.basic.cache.repository.CacheOps;
import top.fsfsfs.basic.model.cache.CacheKey;
import top.fsfsfs.boot.modules.system.entity.DefGenTestSimple;
import top.fsfsfs.boot.modules.system.service.TestSimpleService;

import java.time.Duration;

@RestController
@RequestMapping("/system/test")
@AllArgsConstructor
@Tag(name = "测试接口")
@Slf4j
public class TestController {

    private final TestSimpleService testSimpleService;
    private final CacheOps cacheOps;

    @PostMapping("/saveCache")
    public R<Object> saveCache(@RequestBody DefGenTestSimple simple) {
        log.info("hello fs");

        CacheKey key = new CacheKey("tangyh:test", Duration.ofHours(1));
        cacheOps.set(key, simple);


        CacheResult<DefGenTestSimple> result = cacheOps.get(key);

        return R.success(result);
    }

    @GetMapping("/list")
    public R<Object> list() {
        return R.success(testSimpleService.list());
    }

    @PostMapping("/save")
    public R<Object> save(@RequestBody DefGenTestSimple simple) {
        return R.success(testSimpleService.save(simple));
    }

    @PostMapping("/updateById")
    public R<Object> updateById(@RequestBody DefGenTestSimple simple) {
        return R.success(testSimpleService.updateById(simple));
    }
}
