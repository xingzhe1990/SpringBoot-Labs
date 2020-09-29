package cn.iocoder.springboot.lab15.springdataelasticsearch.controller;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private Client client;

    @GetMapping("indexMapping")
    public Object getIndexMapping(){
        String indexName = "user_log_index20200908193234";
        String indexType = "user_log_type";
        Map<String, Object> mapping =  elasticsearchTemplate.getMapping(indexName,indexType);

        return mapping;

    }
}
