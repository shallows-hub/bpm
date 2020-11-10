package com.dstz.bpm.rest.controller;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.dao.BaseDao;
import com.dstz.base.rest.ControllerTools;
import com.dstz.bpm.core.dao.BpmBusLinkDao;
import com.dstz.bpm.core.model.BpmBusLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description TODO
 * author hj
 * date 2020/10/20-14:43
 */
@RestController
@RequestMapping("/bpm/BpmBusLink")
public class BpmBusLinkController  extends ControllerTools {
    @Autowired
    private BpmBusLinkDao bpmBusLinkDao;
    @RequestMapping("updateId")
    @CatchErr(value = "获取businessObject异常")
    public ResultMsg<String> UpdateId(@RequestParam Integer BizId, @RequestParam String instanceId){
        List<BpmBusLink> busLinks = bpmBusLinkDao.getByInstanceId(instanceId);
        for (BpmBusLink busLink:busLinks
             ) {
            busLink.setBizCode(BizId.toString());
            bpmBusLinkDao.update(busLink);
        }
        return getSuccessResult("生成成功");
    }

}

