package com.wgolden;

import com.fasterxml.jackson.databind.JsonNode;
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

@RestController
public class ExecutionController {
    private final SsisService ssisService;
    public ExecutionController(SsisService ssisService){
        this.ssisService = ssisService;
    }
    public static <T> T convertNode(String fieldName, String dataType, JsonNode node){
        if(node == null){
            return null;
        }
        switch(dataType) {
            case "String": return (T) node.get(fieldName).asText();
            case "Boolean": return (T) (Boolean)node.get(fieldName).asBoolean();
            case "Integer": return (T) (Integer)node.get(fieldName).asInt();
            case "Decimal": return (T) (Double)node.get(fieldName).asDouble();
            default:
                throw new IllegalArgumentException("Unsupported data type: " + dataType);
        }
    }
    @PostMapping("/CreateExecution")
    public <T> long execution(@RequestBody ObjectNode json) throws ClassNotFoundException {
        var executionNode  = json.get("execution");
        Execution ex = new Execution(executionNode.get("folderName").asText(),
                executionNode.get("projectName").asText(),
                executionNode.get("packageName").asText());
        var params = json.get("parameters");
        ArrayList<ExecutionParameter<T>> parameters = new ArrayList<>();
        for(var node: params){
            String dataType = node.get("dataType").asText();
            T value = convertNode("parameterValue", dataType, node);
            ExecutionParameter<T> executionParameter = new ExecutionParameter(
                    node.get("objectType").asInt(),
                    node.get("parameterName").asText(),
                    value
            );
            parameters.add(executionParameter);
        }
        return ssisService.createSSISExecutionWithParams(ex,parameters);
    }
    @GetMapping("/execute")
    public Execution ssisExecution(@RequestParam(value="folderName") String folderName,
                                   @RequestParam(value="projectName") String projectName,
                                   @RequestParam(value="packageName") String packageName) {

        SSISExecutionManager ssisExecutionManager = ssisService.getSsisExecutionManager();
        try {
            SSISExecution ssisExecution = new SSISExecutionBuilder()
                    .ssisExecutionManager(ssisExecutionManager)
                    .dataSource(ssisService.getSqlServerDataSource())
                    .folderName(folderName)
                    .packageName(packageName)
                    .projectName(projectName).build();

            SSISExecutionParameter<String> connectionString = new SSISExecutionParameterBuilder<String>()
                    .ssisExecution(ssisExecution)
                    .parameterValue("Data Source=localhost;Initial Catalog=AdventureWorks;Provider=SQLOLEDB.1;Integrated Security=SSPI;")
                    .objectType(20)
                    .parameterName("ConnectionString")
                    .build();

            ssisExecutionManager.setExecutionParameter(connectionString);
            ssisExecutionManager.startExecution(ssisExecution);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new Execution(folderName, projectName, packageName);
    }

}
