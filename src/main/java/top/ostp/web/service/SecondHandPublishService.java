package top.ostp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import top.ostp.web.mapper.SecondHandPublishMapper;
import top.ostp.web.model.SecondHandPublish;
import top.ostp.web.model.common.ApiResponse;
import top.ostp.web.model.common.Responses;

@Service
public class SecondHandPublishService {
    SecondHandPublishMapper secondHandPublishMapper;
    @Autowired
    public void setSecondHandPublishMapper(SecondHandPublishMapper secondHandPublishMapper){
        this.secondHandPublishMapper = secondHandPublishMapper;
    }

    public ApiResponse<Object> insert(SecondHandPublish secondHandPublish){
        try {
            int result = secondHandPublishMapper.insert(secondHandPublish);
            return Responses.ok();
        }catch (DuplicateKeyException e){
            e.printStackTrace();
        }
        return Responses.fail("主键重复");
    }
}
