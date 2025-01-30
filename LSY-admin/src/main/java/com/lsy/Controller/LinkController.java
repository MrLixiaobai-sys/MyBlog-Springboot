package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.LinkListVo;
import com.lsy.domain.dto.LinkAddDTO;
import com.lsy.domain.dto.LinkListDTO;
import com.lsy.domain.entity.Link;
import com.lsy.service.LinkService;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

//    分页查询友链列表(支持模糊查询)
    @PostMapping("/list")
    public ResponseResult list(@RequestBody LinkListDTO linkListDTO) {

        return linkService.pageLink(linkListDTO);
    }

    // 添加友链
    @PostMapping()
    public ResponseResult add(@RequestBody LinkAddDTO linkAddDTO) {

        Link link = BeanCopyUtils.copyBean(linkAddDTO, Link.class);
        return ResponseResult.okResult(linkService.save(link));
    }

    // 根据id查询友联
    @GetMapping("/{id}")
    public ResponseResult getLinkById(Integer id) {
        Link link = linkService.getById(id);
        LinkListVo linkListVo = BeanCopyUtils.copyBean(link, LinkListVo.class);

        return ResponseResult.okResult(linkListVo);
    }


    //修改友链
    @PutMapping()
    public ResponseResult updateLink(@RequestBody LinkListVo linkListVo) {
        Link link = BeanCopyUtils.copyBean(linkListVo, Link.class);
        return ResponseResult.okResult(linkService.updateById(link));
    }

    // 删除友链
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(Integer id) {
        return ResponseResult.okResult(linkService.removeById(id));
    }

}
