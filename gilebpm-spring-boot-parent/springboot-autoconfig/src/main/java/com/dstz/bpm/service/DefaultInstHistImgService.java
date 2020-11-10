//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dstz.bpm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.core.util.ThreadMapUtil;
import com.dstz.bpm.act.img.BpmProcessDiagramGenerator;
import com.dstz.bpm.api.constant.ActionType;
import com.dstz.bpm.api.constant.OpinionStatus;
import com.dstz.bpm.api.exception.BpmStatusCode;
import com.dstz.bpm.api.service.BpmImageService;
import com.dstz.bpm.core.manager.BpmInstanceManager;
import com.dstz.bpm.core.manager.BpmTaskStackManager;
import com.dstz.bpm.core.model.BpmInstance;
import com.dstz.bpm.core.model.BpmTaskOpinion;
import com.dstz.bpm.core.model.BpmTaskStack;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;

@Service
public class DefaultInstHistImgService implements BpmImageService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    private BpmInstanceManager f;
    @Autowired
    private BpmTaskStackManager k;
    private Color ch = new Color(26, 179, 148);
    private Color ci = new Color(28, 132, 198);
    private Color cj = new Color(237, 85, 101);

    public DefaultInstHistImgService() {
    }

    public InputStream draw(String actDefId, String actInstId) throws Exception {
        if (StringUtil.isEmpty(actDefId)) {
            throw new BusinessException("流程定义actDefId不能缺失", BpmStatusCode.PARAM_ILLEGAL);
        } else if (StringUtil.isEmpty(actInstId)) {
            return this.j(actDefId);
        } else {
            Map<String, Paint> nodeMap = new HashMap();
            Map<String, Paint> flowMap = new HashMap();
            Map<String, Paint> gateMap = new HashMap();
            BpmInstance instance = this.f.getByActInstId(actInstId);
            List<BpmTaskStack> stacks = this.k.getByInstanceId(instance.getId());
            Map<String, BpmTaskStack> stackMap = new HashMap();
            stacks.forEach((statck) -> {
                stackMap.put(statck.getId(), statck);
            });
            Iterator var9 = stacks.iterator();

            while (var9.hasNext()) {
                BpmTaskStack stack = (BpmTaskStack) var9.next();
                if ("sequenceFlow".equals(stack.getNodeType())) {
                    String actionName = stack.getActionName();
                    if (actionName.equals(ActionType.CREATE.getKey())) {
                        actionName = ActionType.START.getKey();
                    }

                    flowMap.put(stack.getNodeId(), this.i(actionName));
                } else if ("exclusiveGateway".equals(stack.getNodeType())) {
                    gateMap.put(stack.getNodeId(), this.i(stack.getActionName()));
                } else {
                    nodeMap.put(stack.getNodeId(), this.i(stack.getActionName()));
                }
            }

            ThreadMapUtil.put("DefaultInstHistImgService_nodeMap", nodeMap);
            ThreadMapUtil.put("DefaultInstHistImgService_flowMap", flowMap);
            ThreadMapUtil.put("DefaultInstHistImgService_gateMap", gateMap);
            InputStream imageStream = null;

            try {
                BpmnModel bpmnModel = this.repositoryService.getBpmnModel(actDefId);
                BpmProcessDiagramGenerator diagramGenerator = new BpmProcessDiagramGenerator();
//                List<FlowNode> flowNodes = gatherAllFlowNodes(bpmnModel);
//                Iterator var24 = flowNodes.iterator();
//                while (var24.hasNext()) {
//                    FlowNode flowNode = (FlowNode) var24.next();
//                    String test = "";
//                }


                imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", nodeMap, flowMap);
            } finally {
                IOUtils.closeQuietly(imageStream);
            }

            return imageStream;
        }
    }

    public JSONObject getFlowInfoToListJsonObject(String actDefId, String actInstId, List<BpmTaskOpinion> bpmTaskOpinion) throws Exception {
//        List<JSONObject> flowInfos = new ArrayList<>();
        JSONObject flowInfos = new JSONObject();
//        start=发起流程；awaiting_check=待审批；agree=同意；against=反对；return=驳回；abandon=弃权；retrieve=追回
        BpmnModel bpmnModel = this.repositoryService.getBpmnModel(actDefId);
        BpmInstance instance = this.f.getByActInstId(actInstId);
        List<BpmTaskStack> stacks = this.k.getByInstanceId(instance.getId());
        List<FlowNode> flowNodes = gatherAllFlowNodes(bpmnModel);
        for (FlowNode flowNode : flowNodes) {
            JSONObject flowJson = new JSONObject();
            String flowNodeId = flowNode.getId();
            flowJson.put("user", "");
            flowJson.put("userId", "");
            flowJson.put("status", "");
            flowJson.put("candidate", "");
            for (BpmTaskOpinion taskOpinion : bpmTaskOpinion) {
                if (flowNodeId.equals(taskOpinion.getTaskKey())) {
                    String candidate = taskOpinion.getAssignInfo();
                    flowJson.put("candidate", candidate != null ? candidate : "");
                    flowJson.put("user", taskOpinion.getApproverName());
                    flowJson.put("userId", taskOpinion.getApprover());
                    flowJson.put("status", OpinionStatus.fromKey(taskOpinion.getStatus()).getValue());
                    break;
                }
            }
            flowJson.put("id", flowNode.getId());
            flowJson.put("name", flowNode.getName());
            JSONArray inArr = new JSONArray();
            JSONArray ouArr = new JSONArray();
            for (SequenceFlow sequenceFlow : flowNode.getIncomingFlows()) {
                JSONObject inJsonObject = new JSONObject();
                inJsonObject.put("sr", sequenceFlow.getSourceRef());
                inJsonObject.put("tr", sequenceFlow.getTargetRef());
//                inJsonObject.put("info", sequenceFlow.toString());
                inArr.add(inJsonObject);
            }
            for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
                JSONObject outJsonObject = new JSONObject();
                outJsonObject.put("tr", sequenceFlow.getTargetRef());
                outJsonObject.put("sr", sequenceFlow.getSourceRef());
//                outJsonObject.put("info", sequenceFlow.toString());
                ouArr.add(outJsonObject);
            }
            flowJson.put("in", inArr);
            flowJson.put("out", ouArr);
            flowInfos.put(flowNode.getId(),flowJson);
        }
        return flowInfos;
    }

    static List<FlowNode> gatherAllFlowNodes(BpmnModel bpmnModel) {
        List<FlowNode> flowNodes = new ArrayList();
        Iterator var2 = bpmnModel.getProcesses().iterator();

        while (var2.hasNext()) {
            Process process = (Process) var2.next();
            flowNodes.addAll(gatherAllFlowNodes((FlowElementsContainer) process));
        }

        return flowNodes;
    }

    static List<FlowNode> gatherAllFlowNodes(FlowElementsContainer flowElementsContainer) {
        List<FlowNode> flowNodes = new ArrayList();
        Iterator var2 = flowElementsContainer.getFlowElements().iterator();

        while (var2.hasNext()) {
            FlowElement flowElement = (FlowElement) var2.next();
            if (flowElement instanceof FlowNode) {
                flowNodes.add((FlowNode) flowElement);
            }

            if (flowElement instanceof FlowElementsContainer) {
                flowNodes.addAll(gatherAllFlowNodes((FlowElementsContainer) flowElement));
            }
        }

        return flowNodes;
    }

    private Paint i(String action) {
        if (StringUtil.isEmpty(action)) {
            return Color.GREEN;
        } else {
            ActionType status = ActionType.fromKey(action);
            if (status == null) {
                return Color.GREEN;
            } else {
                switch (status) {
                    case START:
                        return this.ch;
                    case AGREE:
                        return this.ch;
                    case SIGNAGREE:
                        return this.ch;
                    case OPPOSE:
                        return Color.PINK;
                    case SIGNOPPOSE:
                        return Color.PINK;
                    case REJECT:
                        return Color.PINK;
                    case REJECT2START:
                        return Color.PINK;
                    case RECOVER:
                        return Color.PINK;
                    case DISPENDSE:
                        return Color.BLUE;
                    case MANUALEND:
                        return Color.DARK_GRAY;
                    case WITHDRAW:
                        return Color.DARK_GRAY;
                    case CREATE:
                        return this.cj;
                    case END:
                        return this.ch;
                    case TASKCANCELLED:
                        return this.ci;
                    default:
                        return Color.RED;
                }
            }
        }
    }

    private InputStream j(String actDefId) {
        BpmnModel bpmnModel = this.repositoryService.getBpmnModel(actDefId);
        return this.processEngineConfiguration.getProcessDiagramGenerator().generateDiagram(bpmnModel, "png", this.processEngineConfiguration.getActivityFontName(), this.processEngineConfiguration.getLabelFontName(), this.processEngineConfiguration.getAnnotationFontName(), this.processEngineConfiguration.getClassLoader());
    }
}
