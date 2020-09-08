package com.dstz.base.rest.util;

import com.dstz.base.api.response.impl.OdooResultMsg;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;

import java.util.List;

/*通过接口获取对象
注意配置文件配置好host*/
@Component
@ConfigurationProperties("odoo")
public class OdooUtily {
    private String odooHost;

    public String getOdooHost() {
        return odooHost;
    }

    public void setOdooHost(String odooHost) {
        this.odooHost = odooHost;
    }

//    public <T> T  getOdooObject(String path, java.lang.Class<T> valueType) {
//        Object object = this.getObject(path);
//        if (null == object){
//            return null;
//        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.convertValue(object, valueType);
//
//    }
//    public <T> List<T> getOdooObjects(String path, java.lang.Class<T> valueType){
//        Object object = this.getObject(path);
//        if (null == object){
//            return null;
//        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, valueType);
//        try {
//            return (List<T>) objectMapper.convertValue(object, javaType);
//        }catch (Exception e){
//            return null;
//        }
//
//    }

//    public int postOdooObject(String path, Object o) {
////        todo：创建对象
//        return 0;
//    }
    public Object postObjects(String path,Object object) {
        String actualUrl = odooHost + path;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            String body = objectMapper.writeValueAsString(object);
            HttpEntity httpEntity = new StringEntity(body, "UTF-8");
            String response = HttpClientUtil.httpPost(actualUrl, "UTF-8",60 * 1000,  httpEntity,"application/x-www-form-urlencoded" );
//            ObjectMapper objectMapper = new ObjectMapper();
            OdooResultMsg resultMsg = objectMapper.readValue(response, OdooResultMsg.class);
            if (!resultMsg.isSuccess()){
                return null;
            }
            return resultMsg.getData();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
//        return null;
    }
    public Object getObject(String path){
//        通过http请求数据，转换为object

        String actualUrl = odooHost + path;
        try {
            String response  = HttpClientUtil.httpGet(actualUrl,null);
            ObjectMapper objectMapper = new ObjectMapper();
            OdooResultMsg resultMsg = objectMapper.readValue(response, OdooResultMsg.class);
            if (!resultMsg.isSuccess()){
                return null;
            }
            return resultMsg.getData();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
