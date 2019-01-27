package com.fcgl.common.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author furg@senthink.com
 * @date 2019/1/24
 */
@Data
public class BatchDeleteRequest {

    /**
     * 唯一id列表
     */
    @NotEmpty
    List<String> ids;
}
