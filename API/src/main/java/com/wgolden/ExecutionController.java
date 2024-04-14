package com.wgolden;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wgolden.SSISDB.Builder.SSISExecutionBuilder;
import com.wgolden.SSISDB.Builder.SSISExecutionParameterBuilder;
import com.wgolden.SSISDB.Manager.SSISExecutionManager;
import com.wgolden.SSISDB.Pojo.SSISExecution;
import com.wgolden.SSISDB.Pojo.SSISExecutionParameter;
import com.wgolden.services.SsisService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.wgolden.utils.JsonNodeConverter.convertNode;

@RestController
public class ExecutionController {
    private final SsisService ssisService;
    public ExecutionController(SsisService ssisService){
        this.ssisService = ssisService;
    }

    @PostMapping("/CreateExecution")
    @ResponseBody
    public <T> SSISExecution execution(@RequestBody ObjectNode json) {
        var executionNode  = json.get("execution");
        Execution ex = new Execution(executionNode.get("folderName").asText(),
                executionNode.get("projectName").asText(),
                executionNode.get("packageName").asText());
        var params = json.get("parameters");
        ArrayList<ExecutionParameter<T>> parameters = new ArrayList<>();
        for(var node: params){
            String dataType = node.get("dataType").asText();
            T value = convertNode("parameterValue", dataType, node);
            ExecutionParameter<T> executionParameter = new ExecutionParameter<>(
                    node.get("objectType").asInt(),
                    node.get("parameterName").asText(),
                    value
            );
            parameters.add(executionParameter);
        }
        return ssisService.createSSISExecutionWithParams(ex,parameters);
    }
    @PostMapping("/StartExecution")
    public void startExecution(@RequestBody ObjectNode json) {
        long executionId = json.get("executionId").asLong();
        int retryCount = json.get("retryCount").asInt();
        ssisService.startExecution(executionId, retryCount);
    }

}
