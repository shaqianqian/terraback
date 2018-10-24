package com.terrastation.sha;

import com.terrastation.sha.enums.ResultEnum;

public class ResultUtil {
    public static ResultVO success(Object object) {
        ResultVO result = new ResultVO();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMessage());
        result.setData(object);
        return result;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO result = new ResultVO();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


}
