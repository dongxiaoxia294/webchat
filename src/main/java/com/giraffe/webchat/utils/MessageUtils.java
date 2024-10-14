package com.giraffe.webchat.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giraffe.webchat.domain.ResultMessage;

/**
 * 分装消息的工具类
 */
public class MessageUtils {

    /**
     * 返回ResultMessage的json字符串
     * @param isSystemMessage
     * @param fromName
     * @param message
     * @return
     */
    public static String getMessage(boolean isSystemMessage,String fromName,Object message){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setSystem(isSystemMessage);
        resultMessage.setMessage(message);
        if (fromName!=null) {
            resultMessage.setFromName(fromName);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(resultMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
