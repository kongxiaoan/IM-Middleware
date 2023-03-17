const WebSocket = require('ws');
const wss = new WebSocket.Server({ port: 8080 });

// 存储已验证的客户端
const clients = new Set();

// 发送心跳包
function heartbeat() {
  clients.forEach((client) => {
    if (!client.isAlive) return client.terminate();
    client.isAlive = false;
    client.ping(null, undefined);
  });
}

wss.on('connection', (ws) => {
  console.log('Client connected');
  ws.isAlive = true;
  // 处理收到的消息
  ws.on('message', (message) => {
    if (ws.isAuthorized) {
        if(message == 'pang') {
            // pang 
        } else {
            console.log(`Received message from authorized client: ${message}`);
        }
    
    } else {
      if (message === 'auth') {
        ws.isAuthorized = true;
        clients.add(ws);
        console.log('Client authorized');
        ws.send(`authorized`)
      } else {
        console.log('Client unauthorized');
        ws.terminate();
      }
    }
  });

  ws.on('error', e => {
    console.log(e)
    ws.send(e)
  })

  // 处理连接关闭
  ws.on('close', () => {
    clients.delete(ws);
  });

  // 处理心跳检测
  ws.on('pong', () => {
    ws.isAlive = true;
  });
});

// 启动心跳检测
setInterval(heartbeat, 30000);
