package app.DTO.response;

public class SuccessResponse extends BaseResponse {
    public SuccessResponse() {}

    public SuccessResponse(String message) {
        super(true, message);
    }

    public SuccessResponse(Object data) {
        super(true, data);
    }
}