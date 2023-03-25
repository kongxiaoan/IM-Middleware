// IMessageSender.aidl
package com.example.mylibrary;

// Declare any non-default types here with import statements
import com.example.mylibrary.entities.MessageModel;
import com.example.mylibrary.entities.IMParams;
import com.example.mylibrary.listener.IMMessageReceiver;
import com.example.mylibrary.listener.IMLoginStatusReceiver;
import com.example.mylibrary.listener.ILongConnectionService;

interface IMessageProvider{
    void sendMessage(in byte[] message);

    void sendOrder(int order);

    void login(in IMParams imParams);

    void bindLongConnectionService(ILongConnectionService service);

    void registerMessageReceiveListener(IMMessageReceiver messageReceiver);

    void unRegisterMessageReceiveListener(IMMessageReceiver messageReceiver);

    void registerLoginReceiveListener(IMLoginStatusReceiver loginStatusReceiver);

    void unRegisterLoginReceiveListener(IMLoginStatusReceiver loginStatusReceiver);
}