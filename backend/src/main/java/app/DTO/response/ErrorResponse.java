package app.DTO.response;

public class ErrorResponse extends BaseResponse{
    public ErrorResponse() {}

    public ErrorResponse(String error) {
        super(false, error);
    }

    public ErrorResponse(Object data) {
        super(false, data);
    }
}
