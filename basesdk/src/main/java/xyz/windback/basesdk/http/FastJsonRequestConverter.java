package xyz.windback.basesdk.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Class description
 * FastJson 请求转换器
 *
 * @author WJ
 * @version 1.0, 2018-1-16
 */

public class FastJsonRequestConverter<T> implements Converter<T, RequestBody> {


//    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
//    private static final Charset UTF_8 = Charset.forName("UTF-8");
//
//    @Override
//    public RequestBody convert(T value) throws IOException {
//
//        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value));
//    }


    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private SerializeConfig serializeConfig;
    private SerializerFeature[] serializerFeatures;

    FastJsonRequestConverter(SerializeConfig config, SerializerFeature... features) {
        serializeConfig = config;
        serializerFeatures = features;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        byte[] content;
        if (serializeConfig != null) {
            if (serializerFeatures != null) {
                content = JSON.toJSONBytes(value, serializeConfig, serializerFeatures);
            } else {
                content = JSON.toJSONBytes(value, serializeConfig);
            }
        } else {
            if (serializerFeatures != null) {
                content = JSON.toJSONBytes(value, serializerFeatures);
            } else {
                content = JSON.toJSONBytes(value);
            }
        }
        return RequestBody.create(MEDIA_TYPE, content);
    }




}
