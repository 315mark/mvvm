package com.bdxh.librarybase.fastjson;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

public class FastJsonRequestConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(mediaType, JSON.toJSONBytes(value));
    }
}
