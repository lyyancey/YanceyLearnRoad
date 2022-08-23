package com.yancey.hotelsuggest.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SugService {
    // 定义三种搜索建议的名称
    public static final String chineseSugName = "hotel_chinese_sug";
    public static final String fullPinyinSugName = "hotel_full_pinyin_sug";
    public static final String headPinyinSugName = "hotel_head_pinyin_sug";
    @Autowired
    RestHighLevelClient client;
    public List<String> suggestSearch(String prefixWord){
        // 创建搜索请求，指定搜索名称为hotel_sug
        SearchRequest request = new SearchRequest("hotel_suggest");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        // 创建completion类型的搜索建议
        CompletionSuggestionBuilder chineseSug = SuggestBuilders
                .completionSuggestion("chinese")
                .prefix(prefixWord);
        // 添加搜索建议
        suggestBuilder.addSuggestion(chineseSugName, chineseSug);

        //创建completion类型的搜索建议
        CompletionSuggestionBuilder fullPinyinSug = SuggestBuilders
                .completionSuggestion("full_pinyin")
                .prefix(prefixWord);
        //添加拼音全拼搜索建议
        suggestBuilder.addSuggestion(fullPinyinSugName, fullPinyinSug);

        // 创建completion类型的搜索建议
        CompletionSuggestionBuilder headPinyinSug = SuggestBuilders
                .completionSuggestion("head_pinyin")
                .prefix(prefixWord);
        // 添加拼音首字母搜索建议
        suggestBuilder.addSuggestion(headPinyinSugName, headPinyinSug);

        builder.suggest(suggestBuilder); // 设置建议请求
        request.source(builder); // 设置请求查询
        List<String> sugList = new ArrayList<>();
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            Suggest suggest = response.getSuggest();
            // 获取suggest结果
            CompletionSuggestion  chineseSuggestion = suggest.getSuggestion(chineseSugName);
            // 遍历中文的搜索建议
            for(CompletionSuggestion.Entry.Option option : chineseSuggestion.getOptions()){
                // 获取文档
                Map<String, Object> sourceMap = option.getHit().getSourceAsMap();
                // 获取文档中的中文
                Map<String, Object> chineseText = (Map<String, Object>)sourceMap.get("chinese");
                sugList.add((String)chineseText.get("input"));
            }
             // 获取建议的结果
            CompletionSuggestion fullPinyinSuggestion = suggest.getSuggestion(fullPinyinSugName);
            for(CompletionSuggestion.Entry.Option option : fullPinyinSuggestion.getOptions()){
                //获取文档
                Map<String, Object> sourceMap = option.getHit().getSourceAsMap();
                // 获取文档中的中文
                Map<String, Object> chineseText = (Map<String, Object>)sourceMap.get("chinese");
                sugList.add((String)chineseText.get("input"));
            }
            // 获取suggest结果
            CompletionSuggestion headPinyinSuggestion = suggest.getSuggestion(headPinyinSugName);
            // 遍历拼音首字母建议的结果
            for(CompletionSuggestion.Entry.Option option : headPinyinSuggestion.getOptions()){
                Map<String, Object> sourceMap = option.getHit().getSourceAsMap();
                Map<String, Object> chineseTest = (Map<String, Object>) sourceMap.get("chinese");
                sugList.add((String)chineseTest.get("input"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sugList;

    }
}
