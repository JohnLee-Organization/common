package net.lizhaoweb.common.util.http.common;

import net.lizhaoweb.common.util.base.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author arron
 * @version 1.0.0.0.1
 * @notes Created on 2015-11-10 12:49:26 <br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p></p>
 */
public class Utils extends HttpUtil {

    //传入参数特定类型
    public static final String ENTITY_STRING = "$ENTITY_STRING$";
    public static final String ENTITY_FILE = "$ENTITY_FILEE$";
    public static final String ENTITY_BYTES = "$ENTITY_BYTES$";
    public static final String ENTITY_INPUTSTREAM = "$ENTITY_INPUTSTREAM$";
    public static final String ENTITY_SERIALIZABLE = "$ENTITY_SERIALIZABLE$";
    private static final List<String> SPECIAL_ENTITIY = Arrays.asList(ENTITY_STRING, ENTITY_BYTES, ENTITY_FILE, ENTITY_INPUTSTREAM, ENTITY_SERIALIZABLE);

    /**
     * 检测url是否含有参数，如果有，则把参数加到参数列表中
     *
     * @param url  资源地址
     * @param nvps 参数列表
     * @return 返回去掉参数的url
     * @throws UnsupportedEncodingException
     */
    public static String checkHasParas(String url, List<NameValuePair> nvps, String encoding) throws UnsupportedEncodingException {
        // 检测url中是否存在参数
        if (url.contains("?") && url.indexOf("?") < url.indexOf("=")) {
            Map<String, String[]> map = new HashMap<>();
            url = checkHasParas(url, map, encoding);
            map2List(nvps, map, encoding);
        }
        return url;
    }

    /**
     * 参数转换，将map中的参数，转到参数列表中
     *
     * @param nvps 参数列表
     * @param map  参数列表（map）
     * @throws UnsupportedEncodingException
     */
    public static HttpEntity map2List(List<NameValuePair> nvps, Map<String, ?> map, String encoding) throws UnsupportedEncodingException {
        HttpEntity entity = null;
        if (map != null && map.size() > 0) {
            boolean isSpecial = false;
            // 拼接参数
            for (Entry<String, ?> entry : map.entrySet()) {
                String parameterName = entry.getKey();
                Object parameterValue = entry.getValue();
                if (SPECIAL_ENTITIY.contains(parameterName)) {//判断是否在之中
                    isSpecial = true;
                    if (ENTITY_STRING.equals(parameterName)) {//string
                        entity = new StringEntity(String.valueOf(parameterValue), encoding);
                        break;
                    } else if (ENTITY_BYTES.equals(parameterName)) {//file
                        entity = new ByteArrayEntity((byte[]) parameterValue);
                        break;
                    } else if (ENTITY_FILE.equals(parameterName)) {//file
                        //entity = new FileEntity(file)
                        break;
                    } else if (ENTITY_INPUTSTREAM.equals(parameterName)) {//inputstream
//						entity = new InputStreamEntity();
                        break;
                    } else if (ENTITY_SERIALIZABLE.equals(parameterName)) {//serializeable
//						entity = new SerializableEntity()
                        break;
                    } else {
                        commonParameters(nvps, parameterName, parameterValue);
                    }
                } else {
                    commonParameters(nvps, parameterName, parameterValue);
                }
            }
            if (!isSpecial) {
                entity = new UrlEncodedFormEntity(nvps, encoding);
            }
        }
        return entity;
    }

    private static void commonParameters(List<NameValuePair> nvps, String parameterName, Object parameterValue) {
        if (parameterValue instanceof String[]) {
            for (String value : (String[]) parameterValue) {
                nvps.add(new BasicNameValuePair(parameterName, value));
            }
        } else {
            nvps.add(new BasicNameValuePair(parameterName, String.valueOf(parameterValue)));
        }
    }

}
