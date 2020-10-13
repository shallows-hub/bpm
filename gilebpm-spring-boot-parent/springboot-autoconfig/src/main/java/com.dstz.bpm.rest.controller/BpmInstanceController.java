package com.dstz.bpm.rest.controller;

import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.rest.ControllerTools;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.bpm.act.service.ActInstanceService;
import com.dstz.bpm.api.engine.action.cmd.FlowRequestParam;
import com.dstz.bpm.api.engine.data.BpmFlowDataAccessor;
import com.dstz.bpm.api.engine.data.result.BpmFlowInstanceData;
import com.dstz.bpm.api.engine.data.result.FlowData;
import com.dstz.bpm.api.exception.BpmStatusCode;
import com.dstz.bpm.api.model.inst.IBpmInstance;
import com.dstz.bpm.api.service.BpmImageService;
import com.dstz.bpm.core.manager.BpmDefinitionManager;
import com.dstz.bpm.core.manager.BpmInstanceManager;
import com.dstz.bpm.core.manager.BpmTaskOpinionManager;
import com.dstz.bpm.core.model.BpmDefinition;
import com.dstz.bpm.core.model.BpmInstance;
import com.dstz.bpm.core.model.BpmTaskOpinion;
import com.dstz.bpm.engine.action.cmd.DefaultInstanceActionCmd;
import com.dstz.form.api.model.FormType;
import com.dstz.sys.util.ContextUtil;
import com.github.pagehelper.Page;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping({"/bpm/instance"})
@Api(
        description = "流程实例服务接口"
)
public class BpmInstanceController extends ControllerTools {
    @Resource
    BpmInstanceManager bpmInstanceManager;
    @Resource
    BpmFlowDataAccessor bpmFlowDataAccessor;
    @Resource
    BpmTaskOpinionManager bpmTaskOpinionManager;
    @Resource
    BpmImageService bpmImageService;
    @Resource
    BpmDefinitionManager bpmDefinitionMananger;
    @Resource
    ActInstanceService actInstanceService;

    public BpmInstanceController() {
    }

    @RequestMapping(
            value = {"listJson"},
            method = {RequestMethod.POST}
    )
    @ApiOperation(
            value = "流程实例列表",
            notes = "获取流程实例管理列表，用于超管管理流程实例，可以用来干预任务实例"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "offset",
            value = "offset"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "limit",
            value = "分页大小"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "sort",
            value = "排序字段"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "order",
            value = "order",
            defaultValue = "ASC"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "filter$VEQ",
            value = "其他过滤参数"
    )})
    public PageResult<BpmInstance> listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        QueryFilter queryFilter = this.getQueryFilter(request);
        Page<BpmInstance> bpmInstanceList = (Page)this.bpmInstanceManager.query(queryFilter);
        return new PageResult(bpmInstanceList);
    }

    @RequestMapping(
            value = {"listJson_currentOrg"},
            method = {RequestMethod.POST}
    )
    @ApiOperation(
            value = "流程实例列表-当前组织",
            notes = "获取流程实例列表-当前组织，用于部门负责人管理本部门下的所有流程实例，可以查看任务情况、干预任务实例"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "offset",
            value = "offset"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "limit",
            value = "分页大小"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "sort",
            value = "排序字段"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "order",
            value = "order",
            defaultValue = "ASC"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "filter$VEQ",
            value = "其他过滤参数"
    )})
    public PageResult<BpmInstance> listJson_currentOrg(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        QueryFilter queryFilter = this.getQueryFilter(request);
        String orgId = ContextUtil.getCurrentGroupId();
        if (StringUtil.isEmpty(orgId)) {
            return new PageResult();
        } else {
            queryFilter.addFilter("create_org_id_", ContextUtil.getCurrentGroupId(), QueryOP.EQUAL);
            Page<BpmInstance> bpmInstanceList = (Page)this.bpmInstanceManager.query(queryFilter);
            return new PageResult(bpmInstanceList);
        }
    }

    @RequestMapping(
            value = {"getById"},
            method = {RequestMethod.POST}
    )
    @CatchErr
    @ApiOperation(
            value = "流程实例",
            notes = "获取流程实例详情信息"
    )
    public ResultMsg<IBpmInstance> getBpmInstance(@RequestParam @ApiParam("ID") String id) throws Exception {
        IBpmInstance bpmInstance = null;
        if (StringUtil.isNotEmpty(id)) {
            bpmInstance = (IBpmInstance)this.bpmInstanceManager.get(id);
        }

        return this.getSuccessResult(bpmInstance);
    }

    @RequestMapping(
            value = {"getInstanceData"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    @CatchErr
    @ApiOperation(
            value = "流程实例数据",
            notes = "获取流程实例相关数据，包含实例信息，业务数据，表单权限、表单数据、表单内容等"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "instanceId",
            value = "流程实例ID"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "readonly",
            value = "是否只读实例",
            defaultValue = "false"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "defId",
            value = "流程定义ID，启动时使用"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "flowKey",
            value = "流程定义Key，启动时使用,与DefId二选一"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "formType",
            value = "表单类型",
            defaultValue = "pc"
    )})
    public ResultMsg<FlowData> getInstanceData(HttpServletRequest request) throws Exception {
        String instanceId = request.getParameter("instanceId");
        Boolean readonly = RequestUtil.getBoolean(request, "readonly", false);
        String defId = request.getParameter("defId");
        String flowKey = RequestUtil.getString(request, "flowKey");
        String nodeId = RequestUtil.getString(request, "nodeId");
        if (StringUtil.isEmpty(defId) && StringUtil.isNotEmpty(flowKey)) {
            BpmDefinition def = this.bpmDefinitionMananger.getByKey(flowKey);
            if (def == null) {
                throw new BusinessException("流程定义查找失败！ flowKey： " + flowKey, BpmStatusCode.DEF_LOST);
            }

            defId = def.getId();
        }

        String formType = RequestUtil.getString(request, "formType", FormType.PC.value());
        if (StringUtil.isNotEmpty(nodeId)) {
            BpmFlowInstanceData instanceData = this.bpmFlowDataAccessor.getInstanceData(instanceId, FormType.fromValue(formType), nodeId);
            return this.getSuccessResult(instanceData);
        } else {
            FlowData data = this.bpmFlowDataAccessor.getStartFlowData(defId, instanceId, FormType.fromValue(formType), readonly);
            return this.getSuccessResult(data);
        }
    }

    @RequestMapping(
            value = {"doAction"},
            method = {RequestMethod.POST}
    )
    @CatchErr
    @ApiOperation(
            value = "执行流程实例相关动作",
            notes = "流程启动，流程保存草稿，草稿启动，催办，人工终止等流程实例相关的动作请求入口"
    )
    public ResultMsg<String> doAction(@RequestBody FlowRequestParam flowParam) throws Exception {
        DefaultInstanceActionCmd instanceCmd = new DefaultInstanceActionCmd(flowParam);
//        Authentication auth = SecurityUtil.login(request, "hj", "", true);
//        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
//        SecurityContextHolder.getContext().setAuthentication(auth);
        String actionName = instanceCmd.executeCmd();
        return this.getSuccessResult(instanceCmd.getInstanceId(), actionName + "成功");
    }

    @RequestMapping(
            value = {"getInstanceOpinion"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    @CatchErr
    @ApiOperation(
            value = "获取流程意见",
            notes = "通过流程实例ID 获取该流程实例下所有的审批意见、并按处理时间排序"
    )
    public ResultMsg<List<BpmTaskOpinion>> getInstanceOpinion(@RequestParam @ApiParam("流程实例ID") String instId) throws Exception {
        List<BpmTaskOpinion> taskOpinion = this.bpmTaskOpinionManager.getByInstId(instId);
        return this.getSuccessResult(taskOpinion, "获取流程意见成功");
    }

    @RequestMapping(
            value = {"flowImage"},
            method = {RequestMethod.GET}
    )
    @ApiOperation(
            value = "获取流程图流文件",
            notes = "获取流程实例的流程图，以流的形式返回png图片"
    )
    public void flowImage(@RequestParam(required = false) @ApiParam("流程实例ID") String instId, @RequestParam(required = false) @ApiParam("流程定义ID，流程未启动时使用") String defId, HttpServletResponse response,HttpServletRequest request) throws Exception {
//        Authentication auth = SecurityUtil.login(request, "hj", "", true);
//        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
//        SecurityContextHolder.getContext().setAuthentication(auth);
        String actInstId = null;
        String actDefId;
        if (StringUtil.isNotEmpty(instId)) {
            BpmInstance inst = (BpmInstance)this.bpmInstanceManager.get(instId);
            actInstId = inst.getActInstId();
            actDefId = inst.getActDefId();
        } else {
            BpmDefinition def = (BpmDefinition)this.bpmDefinitionMananger.get(defId);
            actDefId = def.getActDefId();
        }

        response.setContentType("image/png");
        IOUtils.copy(this.bpmImageService.draw(actDefId, actInstId), response.getOutputStream());
    }

    @RequestMapping(
            value = {"toForbidden"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    @CatchErr("操作失败")
    @ApiOperation(
            value = "流程实例禁用/启用",
            notes = "流程实例禁用启用接口"
    )
    public ResultMsg<String> toForbidden(@RequestParam @ApiParam("流程实例ID") String id, @RequestParam @ApiParam("禁用/启用") Boolean forbidden) throws Exception {
        this.bpmInstanceManager.toForbidden(id, forbidden);
        return this.getSuccessResult(BooleanUtils.toString(forbidden, "禁用成功", "取消禁用成功"));
    }

    @RequestMapping({"startTest"})
    @CatchErr
    public ResultMsg<String> startTest() throws Exception {
        this.actInstanceService.startProcessInstance("tset:1:410210125441138689", "test", (Map)null);
        return this.getSuccessResult("成功");
    }

    @PostMapping({"delete"})
    @CatchErr
    @ApiOperation(
            value = "流程实例批量删除",
            notes = "实例id以逗号分隔，删除流程实例会删除相关的所有任务数据，也会级联删除业务数据"
    )
    public ResultMsg<String> delete(@RequestParam @ApiParam("流程实例ID") String id) throws Exception {
        this.bpmInstanceManager.delete(id);
        return this.getSuccessResult("删除实例成功");
    }

    @RequestMapping(
            value = {"getInstanceAndChildren"},
            method = {RequestMethod.POST}
    )
    @CatchErr
    @ApiOperation(
            value = "流程实例",
            hidden = true
    )
    public ResultMsg<JSONObject> getInstanceAndChildren(@RequestParam @ApiParam("ID") String id) throws Exception {
        JSONObject json = new JSONObject();
        IBpmInstance bpmInstance = (IBpmInstance)this.bpmInstanceManager.get(id);
        json.put("bpmInstance", bpmInstance);
        List<BpmInstance> instanceList = this.bpmInstanceManager.getByParentId(id);
        json.put("bpmInstanceChildren", instanceList);
        return this.getSuccessResult(json);
    }
}
