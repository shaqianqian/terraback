package com.terrastation.sha.VO;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResultVOTest {


    @Test
    public void getCode() {
        ResultVO<String>r=new ResultVO<String>();
        r.setCode(10);
        r.setMsg("test");
        r.setData("data");
        assertEquals(new Integer(10),r.getCode());

    }

    @Test
    public void getMsg() {
        ResultVO<String>r=new ResultVO<String>();
        r.setCode(10);
        r.setMsg("test");
        r.setData("data");
        assertEquals("test",r.getMsg());
    }

    @Test
    public void getData() {
        ResultVO<String>r=new ResultVO<String>();
        r.setCode(10);
        r.setMsg("test");
        r.setData("data");
        assertEquals("data",r.getData());
    }
}