// MessageReceiver.aidl
package com.example.mylibrary.listener;

// Declare any non-default types here with import statements
import com.example.mylibrary.entities.MessageModel;
// 转发服务器消息
interface IMMessageReceiver {
    void onMessageReceived(in MessageModel receiveMessage);
}