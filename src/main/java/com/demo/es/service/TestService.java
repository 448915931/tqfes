package com.demo.es.service;

import com.demo.es.domain.Opportunity;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    private static final String INDEX = "certification";
    private static final String TYPE =  "certification";


    //count函数
    public void selectgroup(){
        QueryBuilder query =  QueryBuilders.termQuery("status", 1);
        SearchRequestBuilder requestBuilder = elasticsearchTemplate.getClient().prepareSearch(INDEX).setTypes(TYPE);
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("aaa").size(100000000).field("user_id");
        requestBuilder.setQuery(query).addAggregation(aggregationBuilder).setSize(0).setFrom(0);
        System.out.println(requestBuilder.toString());
        SearchResponse response = requestBuilder.execute().actionGet();
        System.out.println(response);
        Terms aggregation = response.getAggregations().get("aaa");
        for (Terms.Bucket bucket : aggregation.getBuckets()) {
            System.out.println("id=" + bucket.getKey() + ";数量=" + bucket.getDocCount());
        }
    }


    public void selectlsitbycheckid(){
        //query
//        BoolQueryBuilder boolqueryBuilder=QueryBuilders.boolQuery();
//        boolqueryBuilder.must(QueryBuilders.termQuery("status", 1));
        QueryBuilder queryBuilder=QueryBuilders.boolQuery().must(QueryBuilders.termQuery("user_id", 12));
        System.out.println(queryBuilder.toString());
        //排序
        SortBuilder sortBuilder = SortBuilders.fieldSort("check_id")
                .order(SortOrder.DESC);
        Map<String,String> map=new HashMap<String,String>();
        Pageable pageable = PageRequest.of(0, 10);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(INDEX)
                .withTypes(TYPE)
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .withSort(sortBuilder)
                .build();
        List<Opportunity> list=elasticsearchTemplate.queryForList(searchQuery,Opportunity.class);
        long count=elasticsearchTemplate.count(searchQuery);
        System.out.println(list.size());
        System.out.println(count);
    }



}
