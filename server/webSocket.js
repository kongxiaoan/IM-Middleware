const WebSocket = require('ws');

// 创建 WebSocket 服务器
const server = new WebSocket.Server({ port: 8080 });

// 监听连接事件
server.on('connection', (socket) => {
  console.log('Client connected');

  // 监听消息事件
  socket.on('message', (data) => {
    console.log(`Received message: ${data}`);

    // 发送消息到客户端
    socket.send(`You sent: ${data}`);
  });

  // 监听断开连接事件
  socket.on('close', () => {
    console.log('Client disconnected');
  });
});

console.log('WebSocket server started');
