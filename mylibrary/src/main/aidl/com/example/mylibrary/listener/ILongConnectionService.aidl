// ILongConnectionService.aidl
package com.example.mylibrary.listener;
import com.example.mylibrary.entities.IMParams;

// Declare any non-default types here with import statements

interface ILongConnectionService {
     /**
         * 初始化长连接实例
         */
        void initLongConnection();

        /**
         * 连接长连接
         */
        void connect();

        /**
         * 断开长连接
         */
        void disConnect();

        /**
         * 重新连接长连接
         */
        void reConnect();

        /**
         * 登录参数
         */
        void initLoginParams(in IMParams imParams);

        /**
         * 发送消息
         */
        void sendMessage(in byte[] sendMessage);

}