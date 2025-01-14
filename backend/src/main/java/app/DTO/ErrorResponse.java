package app.DTO;

public class ErrorResponse extends BaseResponse{
    public ErrorResponse() {}

    public ErrorResponse(String error) {
        super(false, error);
    }
}
