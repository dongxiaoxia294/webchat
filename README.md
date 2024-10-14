使用WebSocket建立聊天室
客户端--js  
 //创建webSocket对象
var ws = new WebSocket("ws://localhost/chat");
//绑定ws onopen  onmessage onclose 方法监听服务端事件
//ws.send("xinxi") 发送信息到服务端

//服务端 使用 javax.websocket.Endpoint 管理和客户端链接，为每一个客户端创建一个 Endpoint对象
@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {
}
