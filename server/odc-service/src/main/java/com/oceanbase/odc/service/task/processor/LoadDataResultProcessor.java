/*
 * Copyright (c) 2023 OceanBase.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oceanbase.odc.service.task.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oceanbase.odc.metadb.task.JobRepository;
import com.oceanbase.odc.service.task.executor.task.TaskDescription;
import com.oceanbase.odc.service.task.executor.task.TaskResult;
import com.oceanbase.odc.service.task.processor.result.ResultProcessor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: liuyizhuo.lyz
 * @date: 2024/10/21
 */
@Slf4j
@Component
public class LoadDataResultProcessor implements ResultProcessor {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public void process(TaskResult result) {
        try {
            jobRepository.updateResultJson(result.getResultJson(), result.getJobIdentity().getId());
            log.info("Refresh loaddata task result successfully, jobIdentity={}", result.getJobIdentity());
        } catch (Exception e) {
            log.error("Refresh loaddata task result failed, jobIdentity={}", result.getJobIdentity(), e);
        }
    }

    @Override
    public boolean interested(String type) {
        return TaskDescription.LOAD_DATA.matched(type);
    }
}
