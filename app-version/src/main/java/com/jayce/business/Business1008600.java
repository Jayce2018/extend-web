package com.jayce.business;

import com.alibaba.fastjson.JSONObject;
import com.jayce.DTO.RequestDTO;
import com.jayce.DTO.ReturnDTO;
import com.jayce.common.dao.BookMapper;
import com.jayce.common.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class Business1008600 {
    @Autowired
    private BookMapper bookMapper;

    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public JSONObject trade1000000(RequestDTO requestDTO) throws Exception {
        int count;
        //add
        if (null == requestDTO.getId()) {
            Book book = new Book();
            book.setName(requestDTO.getName());
            book.setAuthor(requestDTO.getAuthor());
            book.setCreateTime(new Date());
            count = bookMapper.insertSelective(book);
        }else{
            //update
            Book bookU = new Book();
            bookU.setId(requestDTO.getId());
            bookU.setName(requestDTO.getName());
            bookU.setAuthor(requestDTO.getAuthor());
            bookU.setCreateTime(new Date());
            count = bookMapper.updateByPrimaryKeySelective(bookU);
            throw new Exception("测试回滚");
        }
        ReturnDTO returnDTO=new ReturnDTO();
        returnDTO.setCode(200);
        returnDTO.setMessage("success");
        returnDTO.setResult(count);

        return (JSONObject) JSONObject.toJSON(returnDTO);
    }

    @ResponseBody
    public JSONObject trade1000001(@RequestParam("input") String input, @RequestParam("input2") Integer input2,HttpServletRequest request) {
        ReturnDTO returnDTO = new ReturnDTO();
        returnDTO.setCode(200);
        returnDTO.setMessage("我的版本分离测试trade1000001");
        returnDTO.setResult(input + input2);
        return (JSONObject) JSONObject.toJSON(returnDTO);
    }

    public JSONObject trade1000003(@RequestParam("pram") String pram, @RequestParam("pram2") Integer pram2) {
        ReturnDTO returnDTO = new ReturnDTO();
        returnDTO.setCode(200);
        returnDTO.setMessage("我的版本分离测试trade1000003");
        returnDTO.setResult(pram + pram2);
        return (JSONObject) JSONObject.toJSON(returnDTO);
    }
}
