package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.SecondHandPublishMapper;

@Service
public class SecondHandPublishService {
    SecondHandPublishMapper secondHandPublishMapper;
    @Autowired
    public void setSecondHandPublishMapper(SecondHandPublishMapper secondHandPublishMapper){
    }
}
