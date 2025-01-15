package app.DTO.response;

import lombok.Data;

@Data
public class BaseResponse {
    private boolean success;
    private Object data;

    @Data
    public static class Message {
        private String message;

        public Message() {};

        public Message(String message) {
            this.message = message;
        }
    }

    public BaseResponse() {}

    protected BaseResponse(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    protected BaseResponse(boolean success, String msg) {
        this.data = new Message(msg);
        this.success = success;
    }
}
