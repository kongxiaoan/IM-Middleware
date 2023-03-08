// IMessageSender.aidl
package com.example.mylibrary;

// Declare any non-default types here with import statements
import com.example.mylibrary.entities.MessageModel;
import com.example.mylibrary.listener.IMMessageReceiver;
import com.example.mylibrary.listener.IMLoginStatusReceiver;

interface IMessageProvider{
    void sendMessage(String message);

    void sendOrder(int order);

    void registerMessageReceiveListener(IMMessageReceiver messageReceiver);

    void unRegisterMessageReceiveListener(IMMessageReceiver messageReceiver);

    void registerLoginReceiveListener(IMLoginStatusReceiver loginStatusReceiver);

    void unRegisterLoginReceiveListener(IMLoginStatusReceiver loginStatusReceiver);
}