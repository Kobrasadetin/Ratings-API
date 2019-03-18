package com.majesticbyte.exceptions;

import lombok.Data;
import lombok.NonNull;

@Data
public class ExceptionMessage {
    @NonNull
    private String error;
}
