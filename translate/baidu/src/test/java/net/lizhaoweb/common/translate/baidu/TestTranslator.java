/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.translate.baidu
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 0:51
 */
package net.lizhaoweb.common.translate.baidu;

import net.lizhaoweb.common.translate.api.ITranslator;
import net.lizhaoweb.common.translate.baidu.model.Language;
import net.lizhaoweb.common.translate.model.TranslationRequest;
import net.lizhaoweb.common.translate.model.TranslationResponse;
import org.junit.Test;

/**
 * <h1>测试 - 百度翻译</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年10月27日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p></p>
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//        "classpath*:/schema/spring/spring-translate-baidu.xml"
//})
public class TestTranslator {

    //    @Autowired
    private ITranslator translator;

    @Test
    public void translate() {
//        TranslationRequest request = new TranslationRequest("apple", "en", "zh");
        TranslationRequest request = new TranslationRequest("签名是为了保证调用安全，使用MD5算法生成的一段字符串，生成的签名长度为 32位，签名中的英文字符均为小写格式", Language.fromKey("zh"), Language.fromKey("en"));
        TranslationResponse response = translator.translate(request);
        System.out.println(response.getTranslation());

        TranslationRequest request2 = new TranslationRequest(response.getTranslation(), Language.fromKey("en"), Language.fromKey("zh"));
        TranslationResponse response2 = translator.translate(request2);
        System.out.println(response2.getTranslation());
    }
}
