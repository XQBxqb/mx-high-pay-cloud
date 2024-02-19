package cn.high.mx.module.mission.mq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class GoodsMsgDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;

    private Long goodId;

    private Long orderId;

    public GoodsMsgDto() {
    }
}
