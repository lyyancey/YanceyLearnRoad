package com.yancey.hotelsuggest.service;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PyProcessService {
    @Autowired
    RestHighLevelClient client;
    // 读取文件内容
    public List<String> getListFromFile(String file){
        List<String> wordList = new ArrayList<>();
        try{
            byte[] bytes;
            ClassPathResource resource = new ClassPathResource(file);
            // 获取文件流
            InputStream inputStream = resource.getInputStream();
            bytes = IOUtils.toByteArray(inputStream);
            inputStream.read(bytes);
            inputStream.close();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            InputStreamReader input = new InputStreamReader(byteArrayInputStream);
            BufferedReader bf = new BufferedReader(input);
            String line = null;
            while((line = bf.readLine()) != null){
                wordList.add(line.trim());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return wordList;
    }
    public static Map<String, Object> processPy(int id, String text, int weight){
        Map<String, Object> dataMap = new HashMap<>();
        // 使用HanLP对中文进行处理
        List<Pinyin> pinyinList = HanLP.convertToPinyinList(text);
        // 定义提示词的拼音全拼为StringBuffer类型
        StringBuffer fullPinyinBuffer = new StringBuffer();
        //定义提示词的拼音首字母为StringBuffer类型
        StringBuffer headPinyinBuffer = new StringBuffer();
        for(Pinyin pinyin : pinyinList){
            fullPinyinBuffer.append(pinyin.getPinyinWithoutTone());
            headPinyinBuffer.append(pinyin.getHead());
        }
        dataMap.put("id", id);

        // 提示词的中文处理
        Map chineseMap = new HashMap();
        chineseMap.put("input", text);
        chineseMap.put("weight", weight);
        dataMap.put("chinese", chineseMap);

        // 提示词的全拼处理
        Map fullPinyinMap = new HashMap();
        fullPinyinMap.put("input", fullPinyinBuffer.toString());
        fullPinyinMap.put("weight", weight);
        dataMap.put("full_pinyin", fullPinyinMap);

        // 提示词的拼音首字母处理
        Map headPinyinMap = new HashMap();
        headPinyinMap.put("input", headPinyinBuffer.toString());
        headPinyinMap.put("weight", weight);
        dataMap.put("head_pinyin", headPinyinMap);

        return dataMap;
    }
    // 批量写入索引
    public void bulkIndexDoc(String indexName, String docIdKey, List<Map<String, Object>> recordMapList){
        // 构建批量操作BulkRequest对象
        BulkRequest bulkRequest = new BulkRequest(indexName);
        for(Map<String, Object> dataMap : recordMapList){ // 遍历数据
            // 获取主键作为ES索引的主键
            String docId = dataMap.get(docIdKey).toString();
            // 构建IndexRequest对象
            IndexRequest indexRequest = new IndexRequest().id(docId).source(dataMap);
            // 添加IndexRequest
            bulkRequest.add(indexRequest);
        }
        bulkRequest.timeout(TimeValue.timeValueSeconds(5));// 设置超时时间
        try{
            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            if(response.hasFailures()){
                System.out.println("bulk fail message:"+response.buildFailureMessage());
            }
        }catch(IOException e){
          e.printStackTrace();
        }

    }
    public void startProcess(){
        // 从文件中获取提示词列表
        List<String> wordList = getListFromFile("suggest_words.csv");
        // 存储所有提示词和提示词对应的拼音形式
        List<Map<String, Object>> recordMapList = new ArrayList<Map<String, Object>>();
        int i=0; // id字段对应的值
        for(String word : wordList){
            i += 1;
            String[] fields = word.split(",");
            String keyword = fields[0];
            int weight = Integer.parseInt(fields[1]); // 获取提示词权重值
            // 得到单条提示词和提示词对应的拼音形式
            Map<String, Object> dataMap = processPy(i, keyword, weight);
            recordMapList.add(dataMap);
        }
        bulkIndexDoc("hotel_suggest", "id", recordMapList);


    }
}
