//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dstz.bpm.rest.controller;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.rest.ControllerTools;
import com.dstz.bpm.api.constant.InstanceStatus;
import com.dstz.bpm.core.manager.BpmDefinitionManager;
import com.dstz.bpm.core.manager.BpmInstanceManager;
import com.dstz.bpm.core.manager.BpmTaskManager;
import com.dstz.bpm.core.model.BpmDefinition;
import com.dstz.bpm.core.model.BpmInstance;
import com.dstz.bpm.core.model.BpmTask;
import com.dstz.bpm.core.model.BpmTaskApprove;
import com.dstz.sys.util.ContextUtil;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping({"/bpm/my"})
@Api(
        description = "个人办公服务接口"
)
public class BpmMyTaskController extends ControllerTools {
    @Resource
    BpmTaskManager bpmTaskManager;
    @Resource
    BpmInstanceManager bpmInstanceManager;
    @Resource
    BpmDefinitionManager bpmDefinitionManager;

    public BpmMyTaskController() {
    }

    @RequestMapping(
            value = {"todoTaskList"},
            method = {RequestMethod.POST}
    )
    @ApiOperation(
            value = "我的待办",
            notes = "根据当前用户获取个人所有待办，支持对subject_,name_等Task字段过滤，“filter”为数据库过滤字段名,“V”一位代表字段类型,“EQ/IN/LK/..”代表查询类型“等于/in/小于/..”"
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
            value = "分页大小，默认20条"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "sort",
            value = "排序字段"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "order",
            value = "排序，默认升序",
            defaultValue = "ASC"
    ), @ApiImplicitParam(
            paramType = "form",
            dataType = "String",
            name = "filter$VEQ",
            value = "过滤参数"
    )})
    public PageResult<BpmTask> todoTaskList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        QueryFilter queryFilter = this.getQueryFilter(request);
        String userId = ContextUtil.getCurrentUserId();
        Page<BpmTask> bpmTaskList = (Page)this.bpmTaskManager.getTodoList(userId, queryFilter);
        return new PageResult(bpmTaskList);
    }

    @RequestMapping(
            value = {"applyTaskList"},
            method = {RequestMethod.POST}
    )
    @ApiOperation(
            value = "我的申请",
            notes = "获取我发起过的流程申请，可以查看自己发起的流程执行情况，可查阅自己发起过的所有流程审批"
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
            value = "其他过滤参数，“filter”为数据库过滤字段名，“V”一位代表字段类型，“EQ/IN/LK/..”代表查询类型“等于/in/小于/..”"
    )})
    public PageResult<BpmInstance> applyTaskList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        QueryFilter queryFilter = this.getQueryFilter(request);
        String userId = ContextUtil.getCurrentUserId();
        Page<BpmInstance> bpmTaskList = (Page)this.bpmInstanceManager.getApplyList(userId, queryFilter);
        return new PageResult(bpmTaskList);
    }

    @ApiOperation(
            value = "发起申请",
            notes = "获取自己拥有发起权限的流程列表，用于流程发起。其他过滤参数，“filter”为数据库过滤字段名，“V”一位代表字段类型，“EQ/IN/LK/..”代表查询类型“等于/in/小于/..”"
    )
    @RequestMapping(
            value = {"definitionList"},
            method = {RequestMethod.POST}
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
    public PageResult<BpmDefinition> definitionList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        QueryFilter queryFilter = this.getQueryFilter(request);
        String userId = ContextUtil.getCurrentUserId();
        List<BpmDefinition> list = this.bpmDefinitionManager.getMyDefinitionList(userId, queryFilter);
        return new PageResult(list);
    }

    @ApiOperation(
            value = "我的审批",
            notes = "获取历史审批过的流程任务 。会展示所有自己办理过的流程、若是候选人未办理则不会展示。 其他过滤参数：“filter”为数据库过滤字段名，“V”一位代表字段类型，“EQ/IN/LK/..”代表查询类型“等于/in/小于/..”"
    )
    @RequestMapping(
            value = {"approveList"},
            method = {RequestMethod.POST}
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
    public PageResult<BpmTaskApprove> approveList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        QueryFilter queryFilter = this.getQueryFilter(request);
        String userId = ContextUtil.getCurrentUserId();
        List<BpmTaskApprove> bpmTaskList = this.bpmInstanceManager.getApproveHistoryList(userId, queryFilter);
        return new PageResult(bpmTaskList);
    }

    @ApiOperation(
            value = "我的草稿",
            notes = "获取我的草稿，在流程发起页面保存的，且并未提交的流程事项。其他过滤参数：“filter”为数据库过滤字段名，支持subject_,name_，等字段过滤“V”一位代表字段类型，“EQ/IN/LK/..”代表查询类型“等于/in/小于/..”"
    )
    @RequestMapping(
            value = {"draftList"},
            method = {RequestMethod.POST}
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
    public PageResult<BpmInstance> draftList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
        QueryFilter queryFilter = this.getQueryFilter(request);
        String userId = ContextUtil.getCurrentUserId();
        queryFilter.addFilter("inst.status_", InstanceStatus.STATUS_DRAFT.getKey(), QueryOP.EQUAL);
        List<BpmInstance> instanceList = this.bpmInstanceManager.getApplyList(userId, queryFilter);
        return new PageResult(instanceList);
    }
}
