package xyz.windback.basesdk.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Class description
 * FastJson的转换器
 *
 * @author WJ
 * @version 1.0, 2018-1-16
 */

public class FastJsonConvertFactory extends Converter.Factory {

    private ParserConfig mParserConfig = ParserConfig.getGlobalInstance();
    private int featureValues = JSON.DEFAULT_PARSER_FEATURE;
    private Feature[] features;

    private SerializeConfig serializeConfig;
    private SerializerFeature[] serializerFeatures = {SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.SkipTransientField,
            SerializerFeature.DisableCircularReferenceDetect};

    public static FastJsonConvertFactory create() {
        return new FastJsonConvertFactory();
    }

    private FastJsonConvertFactory() {
    }


    /**
     * 需要重写父类中responseBodyConverter，该方法用来转换服务器返回数据
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new FastJsonResponseConverter<>(type, mParserConfig, featureValues, features);
    }

    /**
     * 需要重写父类中requestBodyConverter，该方法用来转换发送给服务器的数据
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FastJsonRequestConverter<>(serializeConfig, serializerFeatures);
    }

//    /**
//     * 需要重写父类中requestBodyConverter，该方法用来转换发送给服务器的数据
//     */
//    @Override
//    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        return new FastJsonRequestConverter<>();
//    }
//
//    /**
//     * 需要重写父类中responseBodyConverter，该方法用来转换服务器返回数据
//     */
//    @Override
//    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//        return new FastJsonResponseConverter<>(type);
//    }
}
