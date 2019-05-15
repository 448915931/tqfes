package com.demo.es.domain;


import lombok.Data;

@Data
public class Opportunity {

    private long id;
    private long user_id;
    private String application_remark;
    private long category;
    private long status;
    private long create_time;
    private long update_time;
    private long operator;
    private long check_id;
    private long check_time;



}
