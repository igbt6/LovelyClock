package com.igbt6.lovelyclock;

import com.igbt6.lovelyclock.statemachine.HandlerFactory;
import com.igbt6.lovelyclock.statemachine.IHandler;
import com.igbt6.lovelyclock.statemachine.ImmutableMessage;
import com.igbt6.lovelyclock.statemachine.Message;
import com.igbt6.lovelyclock.statemachine.MessageHandler;

import io.reactivex.Scheduler;

/**
 * Created by Yuriy on 25.06.2017.
 */
class TestHandlerFactory implements HandlerFactory {
    private Scheduler testScheduler;

    public TestHandlerFactory(Scheduler scheduler) {
        this.testScheduler = scheduler;
    }

    @Override
    public IHandler create(final MessageHandler messageHandler) {
        return new IHandler() {
            @Override
            public void sendMessageAtFrontOfQueue(Message message) {
                sendMessage(message);
            }

            @Override
            public void sendMessage(final Message message) {
                testScheduler.scheduleDirect(new Runnable() {
                    @Override
                    public void run() {
                        messageHandler.handleMessage(message);
                    }
                });
            }

            @Override
            public ImmutableMessage obtainMessage(int what, Object obj) {
                return ImmutableMessage.builder().what(what).handler(this).obj(obj).build();
            }

            @Override
            public ImmutableMessage obtainMessage(int what) {
                return ImmutableMessage.builder().what(what).handler(this).build();
            }
        };
    }
}
