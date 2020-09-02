package com.dstz.base.core.util;

import com.dstz.base.api.response.impl.OdooResultMsg;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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

    public <T> T  getOdooObject(String path, java.lang.Class<T> valueType) {
        Object object = this.getOdooObject(path);
        if (null == object){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object, valueType);

    }
    public <T> List<T> getOdooObjects(String path, java.lang.Class<T> valueType){
        Object object = this.getOdooObject(path);
        if (null == object){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, valueType);
        try {
            return (List<T>) objectMapper.convertValue(object, javaType);
        }catch (Exception e){
            return null;
        }

    }

    public int postOdooObject(Object o, String path) {
//        todo：创建对象
        return 0;
    }
    private Object getOdooObject(String path){
//        通过http请求数据，判断code是否正确，然后转换为object
        String actualUrl = odooHost + path;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getMeth = new HttpGet(actualUrl);
        try {
            HttpResponse response  = client.execute(getMeth);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            OdooResultMsg resultMsg = objectMapper.readValue(EntityUtils.toString(response.getEntity()), OdooResultMsg.class);
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
