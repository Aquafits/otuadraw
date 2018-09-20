package com.otuadraw.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InkPoint {
    Integer x = null;
    Integer y = null;
    //TODO find out the what type to use about time
    String t = null;
}
