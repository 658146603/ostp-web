package top.ostp.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ostp.web.mapper.SecondHandFindMapper;
import top.ostp.web.model.Book;
import top.ostp.web.model.SecondHandFind;

import java.util.List;

@SpringBootTest
public class SecondHandFindTests {
    SecondHandFindMapper secondHandFindMapper;

    @Autowired
    public void setSecondHandFindMapper(SecondHandFindMapper secondHandFindMapper) {
        this.secondHandFindMapper = secondHandFindMapper;
    }

    @Test
    public void insert() {
        SecondHandFind secondHandFind = new SecondHandFind();
        //TODO
        List<SecondHandFind> b =  secondHandFindMapper.selectBookByStudentId("201806060231");
        for (SecondHandFind b1:b
             ) {
            System.out.println(b1.toString());

        }
    }
}
