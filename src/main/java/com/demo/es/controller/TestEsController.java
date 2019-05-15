package com.demo.es.controller;

import com.demo.es.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testapi")
public class TestEsController {

    @Autowired
    public TestService testService;

    /**
     *  http://localhost:3080/esdemo/testapi/gettestest
     * @return
     */
    @GetMapping("/gettestest")
    public String gettestest(){
        testService.selectlsitbycheckid();
        return "查询列表";
    }
    /**
     *  http://localhost:3080/esdemo/testapi/selectgroup
     * @return
     */
    @GetMapping("/selectgroup")
    public String selectgroup(){
        testService.selectgroup();
        return "查询聚合";
    }
}
