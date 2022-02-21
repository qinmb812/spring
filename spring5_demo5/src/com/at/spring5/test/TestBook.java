package com.at.spring5.test;

import com.at.spring5.entity.Book;
import com.at.spring5.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestBook {
    @Test
    public void testJdbcTemplate() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        BookService bookService = context.getBean("bookService", BookService.class);

        // 添加
//        Book book = new Book();
//        book.setUserId("1");
//        book.setUsername("java");
//        book.setUstatus("a");
//        bookService.addBook(book);

        // 修改
//        Book book = new Book();
//        book.setUserId("1");
//        book.setUsername("javaSE");
//        book.setUstatus("at");
//        bookService.updateBook(book);

        // 删除
//        bookService.deleteBook("1");

        // 查询表记录数
//        int count = bookService.findCount();
//        System.out.println(count);

        // 查询返回对象
//        Book book = bookService.findBook("2");
//        System.out.println(book);

        // 查询返回集合
        List<Book> bookList = bookService.findAllBook();
        System.out.println(bookList);
    }
}
