package xyz.windback.basesdk.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Class description
 * FastJson 响应转换器
 *
 * @author WJ
 * @version 1.0, 2018-1-16
 */

public class FastJsonResponseConverter<T> implements Converter<ResponseBody, T> {
//    private final Type type;
//
//    public FastJsonResponseConverter(Type type) {
//        this.type = type;
//    }
//
//    @Override
//    public T convert(ResponseBody value) throws IOException {
//        BufferedSource buffer = Okio.buffer(value.source());
//        String s = buffer.readUtf8();
//        buffer.close();
//        return JSON.parseObject(s, type);
//    }


    private static final Feature[] EMPTY_SERIALIZER_FEATURES = new Feature[0];

    private Type mType;

    private ParserConfig config;
    private int featureValues;
    private Feature[] features;

    FastJsonResponseConverter(Type type, ParserConfig config, int featureValues,
                                  Feature... features) {
        mType = type;
        this.config = config;
        this.featureValues = featureValues;
        this.features = features;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T convert(ResponseBody value) throws IOException {
        try {
            if (mType == String.class) {
                return (T) value.string();
            }
            return JSON.parseObject(value.string(), mType, config, featureValues,
                    features != null ? features : EMPTY_SERIALIZER_FEATURES);

        } finally {
            value.close();
        }
    }


}
