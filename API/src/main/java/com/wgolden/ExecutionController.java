package com.wgolden;

import com.wgolden.SSISDB.Builder.SSISExecutionBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wgolden.SSISDB.Pojo.SSISExecution;

@RestController
public class ExecutionController {

    @GetMapping("/execute")
    public com.wgolden.SSISExecution ssisExecution(@RequestParam(value="folderName") String folderName,
                                                   @RequestParam(value="projectName") String projectName,
                                                   @RequestParam(value="packageName") String packageName) {

       SSISExecution ssisExecution = new SSISExecutionBuilder()
                .build();
        return new com.wgolden.SSISExecution(folderName, projectName, packageName);
    }

}
