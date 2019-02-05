package com.terrastation.sha.Util;

import com.terrastation.sha.Enums.ResultEnum;
import com.terrastation.sha.VO.ResultVO;
import org.junit.Test;

import static com.terrastation.sha.Util.ResultUtil.error;
import static com.terrastation.sha.Util.ResultUtil.success;
import static org.junit.Assert.*;

public class ResultUtilTest {

    @Test
    public void successTest() {


        ResultVO result = new ResultVO();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMessage());
        result.setData("bonjour");
        assertEquals( success("bonjour"),result);

    }

    @Test
    public void errorTest() {
        ResultVO result = new ResultVO();
        result.setCode(1);
        result.setMsg("testError");
        assertEquals( error(1,"testError"),result);


    }
}