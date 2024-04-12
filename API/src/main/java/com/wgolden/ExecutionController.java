package com.wgolden;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.wgolden.SSISDB.Builder.SSISExecutionBuilder;
import com.wgolden.SSISDB.Builder.SSISExecutionParameterBuilder;
import com.wgolden.SSISDB.Manager.SSISExecutionManager;
import com.wgolden.SSISDB.Pojo.SSISExecution;
import com.wgolden.SSISDB.Pojo.SSISExecutionParameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class ExecutionController {
    private final SQLServerDataSource sqlServerDataSource;
    public ExecutionController(SQLServerDataSource sqlServerDataSource){
        this.sqlServerDataSource = sqlServerDataSource;
    }
    @GetMapping("/execute")
    public Execution ssisExecution(@RequestParam(value="folderName") String folderName,
                                   @RequestParam(value="projectName") String projectName,
                                   @RequestParam(value="packageName") String packageName) {

        SSISExecutionManager ssisExecutionManager = SSISExecutionManager.getInstance();
        try {
            SSISExecution ssisExecution = new SSISExecutionBuilder()
                    .ssisExecutionManager(ssisExecutionManager)
                    .dataSource(sqlServerDataSource)
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
