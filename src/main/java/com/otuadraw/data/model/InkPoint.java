package com.otuadraw.data.model;

import com.otuadraw.enums.PointEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InkPoint {
    Integer x = null;
    Integer y = null;
    Long time = null;
    PointEnum pointType = null;
}
