package edu.hqu.lease.web.admin.controller.apartment;


import edu.hqu.lease.common.result.Result;
import edu.hqu.lease.model.entity.AttrKey;
import edu.hqu.lease.model.entity.AttrValue;
import edu.hqu.lease.web.admin.service.AttrKeyService;
import edu.hqu.lease.web.admin.service.AttrValueService;
import edu.hqu.lease.web.admin.vo.attr.AttrKeyVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Tag(name = "房间属性管理")
@RestController
@RequestMapping("/admin/attr")
public class AttrController {
    @Autowired
    AttrKeyService attrKeyService;
    @Autowired
    AttrValueService attrValueService;
    @Operation(summary = "新增或更新属性名称")
    @PostMapping("key/saveOrUpdate")
    public Result saveOrUpdateAttrKey(@RequestBody AttrKey attrKey) {
        attrKeyService.saveOrUpdate(attrKey);
        return Result.ok();
    }

    @Operation(summary = "新增或更新属性值")
    @PostMapping("value/saveOrUpdate")
    public Result saveOrUpdateAttrValue(@RequestBody AttrValue attrValue) {
        attrValueService.saveOrUpdate(attrValue);
        return Result.ok();
    }


    @Operation(summary = "查询全部属性名称和属性值列表")
    @GetMapping("list")
    public Result<List<AttrKeyVo>> listAttrInfo() {
        List<AttrKeyVo> ans=new ArrayList<>();
        List<AttrKey> list = attrKeyService.list();
        for (AttrKey attrKey : list) {
            AttrKeyVo attrKeyVo = new AttrKeyVo();
            attrKeyVo.setId(attrKey.getId());
            attrKeyVo.setName(attrKey.getName());
            attrKeyVo.setIsDeleted(attrKey.getIsDeleted());
            LambdaQueryWrapper<AttrValue> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AttrValue::getAttrKeyId, attrKey.getId());
            List<AttrValue> list1 = attrValueService.list(queryWrapper);
            attrKeyVo.setAttrValueList(list1);
            ans.add(attrKeyVo);
        }
        return Result.ok(ans);
    }

    @Operation(summary = "根据id删除属性名称")
    @DeleteMapping("key/deleteById")
    public Result removeAttrKeyById(@RequestParam Long attrKeyId) {
        attrKeyService.removeById(attrKeyId);
        LambdaQueryWrapper<AttrValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AttrValue::getAttrKeyId, attrKeyId);
        attrValueService.remove(queryWrapper);
        return Result.ok();
    }

    @Operation(summary = "根据id删除属性值")
    @DeleteMapping("value/deleteById")
    public Result removeAttrValueById(@RequestParam Long id) {
        attrValueService.removeById(id);
        return Result.ok();
    }

}
