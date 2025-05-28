package com.eucl.example.meter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeterResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private String timestamp;
    private Object pagination;

    public MeterResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }


    public MeterResponse(T data, String message) {
        this.success = true;
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public MeterResponse(T data, Object pagination) {
        this.success = true;
        this.data = data;
        this.timestamp = LocalDateTime.now().toString();
        this.pagination = pagination;
    }

    public static <T> MeterResponse<T> success(T data) {
        MeterResponse<T> response = new MeterResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setTimestamp(LocalDateTime.now().toString());
        return response;
    }

    public static <T> MeterResponse<T> fail(String message) {
        MeterResponse<T> response = new MeterResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now().toString());
        return response;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
