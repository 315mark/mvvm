package com.bdxh.module_task.repository;

import com.bdxh.librarybase.base.BaseResult;
import com.bdxh.module_task.model.bean.TaskResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TaskApiService {

    @POST("query/task")
    Observable<BaseResult<TaskResult>> getTask(@Body Map<String, Object> params);
}
