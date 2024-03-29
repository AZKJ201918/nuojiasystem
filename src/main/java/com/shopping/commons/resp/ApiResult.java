package com.shopping.commons.resp;




import com.shopping.commons.constans.Constants;
import com.shopping.commons.exception.SuperMarketException;
import lombok.Data;


@Data
public class ApiResult<T> {

    private int code = Constants.RESP_STATUS_OK;

    private String message;

    private T data;


}
