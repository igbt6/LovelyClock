package com.igbt6.lovelyclock.statemachine;

/**
 * Created by Yuriy on 07.03.2017.
 */
public interface HandlerFactory {
    IHandler create(MessageHandler messageHandler);
}
