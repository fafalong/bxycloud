package com.boxiaoyun.msg.task;

import com.boxiaoyun.msg.client.model.BaseMessage;
import com.boxiaoyun.msg.exchanger.MessageExchanger;

import java.util.concurrent.Callable;

/**
 * @author woodev
 */
public class MessageTask implements Callable<Boolean> {

    private MessageExchanger exchanger;

    private BaseMessage notify;

    public MessageTask(MessageExchanger exchanger, BaseMessage notify){
        this.exchanger = exchanger;
        this.notify = notify;
    }

    @Override
    public Boolean call() throws Exception {
        return exchanger.exchange(notify);
    }
}
