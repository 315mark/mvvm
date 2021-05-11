package com.bdxh.librarybase.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

public class FastJsonResponseConverter<T> implements Converter<ResponseBody,T> {

    private final Type type;

    public FastJsonResponseConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource buffer = Okio.buffer(value.source());
        String utf8 = buffer.readUtf8();
        buffer.close();
        return JSON.parseObject(utf8,type, Feature.InitStringFieldAsEmpty);
    }
}
