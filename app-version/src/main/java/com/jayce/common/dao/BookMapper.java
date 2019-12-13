package com.jayce.common.dao;

import com.jayce.common.entity.Book;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Component
public interface BookMapper extends Mapper<Book> {
}