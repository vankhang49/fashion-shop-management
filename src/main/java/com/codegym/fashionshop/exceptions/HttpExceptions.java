package com.codegym.fashionshop.exceptions;

public class HttpExceptions {

    /**
     * Exception cho lỗi HTTP 400 Bad Request.
     * Điều này cho biết rằng yêu cầu của client không hợp lệ
     * hoặc không thể hiểu được bởi server do cú pháp không đúng,
     * thiếu tham số, hoặc giá trị không hợp lệ.
     */
    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }

    /**
     * Exception cho lỗi HTTP 401 Unauthorized.
     * Điều này có nghĩa là client đã cố gắng truy cập vào
     * một tài nguyên mà không có thông tin xác thực hợp lệ.
     */
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }

    /**
     * Exception cho lỗi HTTP 403 Forbidden.
     * Client đã được xác thực nhưng không có quyền truy cập
     * vào tài nguyên yêu cầu.
     */
    public static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String message) {
            super(message);
        }
    }

    /**
     * Exception cho lỗi HTTP 404 Not Found.
     * Tài nguyên yêu cầu không tồn tại trên server.
     */
    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {
            super(message);
        }
    }

    /**
     * Exception cho lỗi HTTP 405 Method Not Allowed.
     * Phương thức HTTP sử dụng trong yêu cầu (vd: GET, POST, PUT)
     * không được hỗ trợ cho tài nguyên yêu cầu.
     */
    public static class MethodNotAllowedException extends RuntimeException {
        public MethodNotAllowedException(String message) {
            super(message);
        }
    }

    /**
     * Exception cho lỗi HTTP 408 Request Timeout.
     * Server đã mất quá nhiều thời gian để đáp ứng yêu cầu của client,
     * vượt quá giới hạn thời gian chờ.
     */
    public static class RequestTimeoutException extends RuntimeException {
        public RequestTimeoutException(String message) {
            super(message);
        }
    }

    /**
     * Exception cho lỗi HTTP 500 Internal Server Error.
     * Một lỗi không mong muốn xảy ra trên server,
     * ngăn cản server thực hiện yêu cầu.
     */
    public static class InternalServerErrorException extends RuntimeException {
        public InternalServerErrorException(String message) {
            super(message);
        }
    }

    /**
     * Exception cho lỗi HTTP 503 Service Unavailable.
     * Server hiện không khả dụng hoặc quá tải và không thể xử lý yêu cầu.
     */
    public static class ServiceUnavailableException extends RuntimeException {
        public ServiceUnavailableException(String message) {
            super(message);
        }
    }
    /**
     * Exception cho lỗi Format không đúng.
     * Ví dụ: định dạng dữ liệu không phù hợp.
     */
    public static class FormatException extends RuntimeException {
        public FormatException(String message) {
            super(message);
        }
    }

    /**
     * Exception cho lỗi Number không đúng.
     */
    public static class NumberFormatException extends RuntimeException {
        public NumberFormatException(String message) {
            super(message);
        }
    }

    /**
     * Exception cho lỗi NullPointerException.
     * Được ném khi một tham chiếu null được sử dụng.
     */
    public static class NullPointerException extends RuntimeException {
        public NullPointerException(String message) {
            super(message);
        }
    }
}
