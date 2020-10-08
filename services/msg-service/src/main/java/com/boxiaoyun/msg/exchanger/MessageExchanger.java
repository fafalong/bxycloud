package com.boxiaoyun.msg.exchanger;


import com.boxiaoyun.msg.client.model.BaseMessage;

/**
 * @author woodev
 */

public interface MessageExchanger {

    boolean support(Object message);

    boolean exchange(BaseMessage message);
}
