package com.hanxiaozhang.common.controller;


import com.hanxiaozhang.common.domain.DictDO;
import com.hanxiaozhang.common.service.DictService;
import com.hanxiaozhang.common.validation.CreateGroupValidation;
import com.hanxiaozhang.common.validation.UpdateGroupValidation;
import com.hanxiaozhang.utils.PageUtil;
import com.hanxiaozhang.utils.Query;
import com.hanxiaozhang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author hanxinghua
 * @email hanxinghua2015@sina.com
 * @date 2020-04-30 09:43:57
 */
@Controller
@RequestMapping("/common/dict")
public class DictController {

    @Autowired
    private DictService dictService;
    private ObjectError x;

    @GetMapping()
    String dict() {
        return "common/dict/dict";
    }

    @ResponseBody
    @GetMapping("/list")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        List<DictDO> dictList = dictService.list(query);
        int total = dictService.count(query);
        PageUtil pageUtil = new PageUtil(dictList, total);
        return pageUtil;
    }

    @GetMapping("/add")
    String add() {
        return "common/dict/add";
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        DictDO dict = dictService.get(id);
        model.addAttribute("dict", dict);
        return "common/dict/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(@Validated(CreateGroupValidation.class) DictDO dict, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            bindingResult.getAllErrors().forEach(x->{
//                System.out.println(x.getObjectName());
//                System.out.println(x.getDefaultMessage());
//            });
            // 正常逻辑返回第一错误
            return R.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        if (dictService.save(dict) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(@Validated(UpdateGroupValidation.class)DictDO dict, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            bindingResult.getAllErrors().forEach(x->{
//                System.out.println(x.getObjectName());
//                System.out.println(x.getDefaultMessage());
//            });
            // 正常逻辑返回第一错误
            return R.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        dictService.update(dict);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        if (dictService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] ids) {
        dictService.batchRemove(ids);
        return R.ok();
    }

    @GetMapping("/type")
    @ResponseBody
    public List<DictDO> listType() {
        return dictService.listType();
    }



    /**
     * 类别已经指定增加
     *
     * @param model
     * @param type
     * @param description
     * @return
     */
    @GetMapping("/add/{type}/{description}")
    String addD(Model model, @PathVariable("type") String type, @PathVariable("description") String description) {
        model.addAttribute("type", type);
        model.addAttribute("description", description);
        return "common/dict/add";
    }

    @ResponseBody
    @GetMapping("/list/{type}")
    public List<DictDO> listByType(@PathVariable("type") String type) {
        // 查询列表数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("type", type);
        List<DictDO> dictList = dictService.list(map);
        return dictList;
    }


    @GetMapping("/typing")
    String typing() {
        return "common/dict/typing";
    }


}
