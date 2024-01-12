package org.example;

import org.rpc.Instance;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rpc")
public class RpcHttpController {

    private final RpcProcess rpcProcess;

    public RpcHttpController(RpcProcess rpcProcess) {
        this.rpcProcess = rpcProcess;
    }

    @PostMapping
    public String rpc(@RequestBody Instance instance) {
        Object obj = rpcProcess.execute(instance);
        return obj.toString();
    }

}
