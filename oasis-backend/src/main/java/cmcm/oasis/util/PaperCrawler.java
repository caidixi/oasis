package cmcm.oasis.util;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

@Service
public class PaperCrawler {
    //根据文献代码获取文献Reference并添加到Paper中
    public void setReference(PaperInfo paperInfo){
        String url = "https://ieeexplore.ieee.org/rest/document/"+ paperInfo.link_num+"/references";
        Map<String,String> headers = new HashMap<>();
        headers.put("Connection","close");
        headers.put("Accept","application/json, text/plain, */*");
        headers.put("cache-http-response","true");
        headers.put("Accept-Encoding","gzip, deflate");
        headers.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("Referer","https://ieeexplore.ieee.org/rest/document/"+ paperInfo.link_num+"/references");

        JSONObject res = this.Get(url,headers);
        if(res.get("references")!=null)
            paperInfo.ref = ((JSONArray)res.get("references")).size();  //此处只取references数量，其他详细信息可以在res中酌情添加
    }

    public int getReference(String link_num){
        String url = "https://ieeexplore.ieee.org/rest/document/"+ link_num+"/references";
        Map<String,String> headers = new HashMap<>();
        headers.put("Connection","close");
        headers.put("Accept","application/json, text/plain, */*");
        headers.put("cache-http-response","true");
        headers.put("Accept-Encoding","gzip, deflate");
        headers.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("Referer","https://ieeexplore.ieee.org/rest/document/"+ link_num+"/references");

        JSONObject res = this.Get(url,headers);
        if(res.get("references")!=null) {
            return ((JSONArray) res.get("references")).size();  //此处只取references数量，其他详细信息可以在res中酌情添加
        }else {
            return 0;
        }
    }

    //根据文献代码获取文献Kyewords并添加到Paper中
    private void setKeywords(PaperInfo p){
        String url = "https://ieeexplore.ieee.org/rest/document/"+p.link_num+"/keywords";
        Map<String,String> headers = new HashMap<>();
        headers.put("Connection","close");
        headers.put("Accept","application/json, text/plain, */*");
        headers.put("cache-http-response","true");
        headers.put("Accept-Encoding","gzip, deflate");
        headers.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("Referer","https://ieeexplore.ieee.org/rest/document/"+p.link_num+"/keywords");

        JSONObject res = this.Get(url,headers);
        if(res.get("keywords")!=null){                   //存在部分文献不含Keywords（文献内容可能偏向讨论记录的方向），请酌情考虑是删除该文献或者按照无keywords文献安排
            JSONArray keywords = (JSONArray) this.Get(url,headers).get("keywords");
            for(Object j:keywords){
                JSONObject js = (JSONObject)j;
                if(js.get("type").equals("AuthorInfo Keywords ")){   //三种keywords中选择Author Keywords
                    p.keywords = (List<String>)js.get("kwd");
                }
            }
        }

    }

    public String getKeywords(String link_num){
        StringBuilder result = new StringBuilder();

        String url = "https://ieeexplore.ieee.org/rest/document/"+link_num+"/keywords";
        Map<String,String> headers = new HashMap<>();
        headers.put("Connection","close");
        headers.put("Accept","application/json, text/plain, */*");
        headers.put("cache-http-response","true");
        headers.put("Accept-Encoding","gzip, deflate");
        headers.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("Referer","https://ieeexplore.ieee.org/rest/document/"+link_num+"/keywords");

        JSONObject res = this.Get(url,headers);
        if(res.get("keywords")!=null){                   //存在部分文献不含Keywords（文献内容可能偏向讨论记录的方向），请酌情考虑是删除该文献或者按照无keywords文献安排
            JSONArray keywords = (JSONArray) this.Get(url,headers).get("keywords");
            for(Object j:keywords){
                JSONObject js = (JSONObject)j;
                if(js.get("type").equals("AuthorInfo Keywords ")){   //三种keywords中选择Author Keywords
                    List<String> authorKeywords = (List<String>)js.get("kwd");
                    for (String keyword:authorKeywords){
                        if (result.toString().equals("")){
                            result.append(keyword);
                        }else {
                            result.append(";").append(keyword);
                        }
                    }
                }
            }
            return result.toString();
        }else {
            return result.toString();
        }
    }

    //根据作者id获取作者机构
    public String getInstitute(String id){
        String url = "https://ieeexplore.ieee.org/rest/author/"+id;
        Map<String,String> headers = new HashMap<>();
        headers.put("Connection","close");
        headers.put("Accept","application/json, text/plain, */*");
        headers.put("cache-http-response","true");
        headers.put("Accept-Encoding","gzip, deflate");
        headers.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("Referer","https://ieeexplore.ieee.org/rest/author/"+id);

        JSONObject res =this.Get(url,headers);
        if(res!=null&&res.get("currentAffiliation")!=null){
            return (String) res.get("currentAffiliation");
        }else{
            return null;
        }
    }

    //输入检索信息conference,开始年份start,结束年份end，获取所有符合要求的论文信息——年份0为不筛选
    public void getPapers(String conference,int start,int end){
        if(end==0) end=2050;
        List<PaperInfo> paperInfos = new ArrayList<>();

        String url = "https://ieeexplore.ieee.org/rest/search";

        Map<String,String> headers = new HashMap<>();
        headers.put("HOST","ieeexplore.ieee.org");
        headers.put("Origin","https://ieeexplore.ieee.org");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contentType","conferences");
        jsonObject.put("queryText", conference);
        jsonObject.put("rowsPerPage", "10");
        jsonObject.put("pageNumber", "1");

        JSONObject json = this.Post(url,headers,jsonObject);
        int totalPages = json.getInt("totalPages");       //获取结果页数，10条文献为一页

        for(int i=1;i<=10;i++){     //读取全部页
            json = this.Post(url,headers,jsonObject);

            JSONArray records = (JSONArray) json.get("records");
            for(Object o:records){
                System.out.println("get record!");
                JSONObject j = (JSONObject) o;
                int publicationYear = Integer.parseInt((String)j.get("publicationYear"));
                if(publicationYear>=start&&publicationYear<=end&&j.get("authors")!=null){     //填充文献信息，若信息不全可以从输出的JSON数据中添加
                    PaperInfo p = new PaperInfo();
                    p.title = (String)j.get("articleTitle");
                    p.abstract_ = (String)j.get("abstract");
                    p.publication = (String)j.get("publicationTitle");
                    p.doi = (String)j.get("doi");
                    p.link_num = (String)j.get("articleNumber");
                    List<AuthorInfo> authorInfos = new ArrayList<>();
                    for(Object ao:(JSONArray) j.get("authors")){
                        JSONObject ja = (JSONObject)ao;
                        AuthorInfo authorInfo = new AuthorInfo();
                        authorInfo.name = (String)ja.get("searchablePreferredName");
                        if(ja.get("id")!=null)
                            authorInfo.institute = this.getInstitute(String.valueOf(ja.get("id")));
                        authorInfos.add(authorInfo);
                    }
                    p.authorInfos = authorInfos;

                    setKeywords(p);
                    setReference(p);
                    System.out.println("record: "+JSON.toJSONString(p));    //此处只进行了文献结果输出，数据库持久化操作待完成
                }
            }

            jsonObject.put("pageNumber", String.valueOf(i+1));
        }
    }

    //构建Post请求并获取返回Json数据
    public JSONObject Post(String url, Map<String,String> header, JSONObject json){
        JSONObject resp = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        for(String s:header.keySet()){
            httpPost.setHeader(s,header.get(s));
        }

        try{
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httpPost.setEntity(s);

            HttpResponse res = httpClient.execute(httpPost);

            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                resp = JSONObject.fromObject(result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resp;
    }

    //构建Get请求并获取返回Json数据
    public JSONObject Get(String url, Map<String,String> header){
        JSONObject resp = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        for(String s:header.keySet()){
            httpGet.setHeader(s,header.get(s));
        }

        try{
            HttpResponse res = httpClient.execute(httpGet);

            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                if(result.charAt(0)=='[')            //部分返回页面带中括号
                    result = result.substring(1,result.length()-1);
                resp = JSONObject.fromObject(result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resp;
    }
}
