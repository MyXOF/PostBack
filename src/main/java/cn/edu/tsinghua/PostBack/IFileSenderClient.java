package cn.edu.tsinghua.PostBack;

import java.io.*;
import java.util.*;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class IFileSenderClient {

	private final String uuidPath = "F:\\university\\学\\毕设\\postBack\\uuid.txt";
	private TTransport transport;  //rpc Socket连接
	private String uuid;          
	private IFileReceiverService.Client clientForReceiver;   //rpc服务器端的控制接口
	private Map<String, Integer> fileListFromReceiver;       //接收端的传输列表

	// UUID生成与保存,并获取接收端的文件列表
	public void prepare() throws Exception {
		// 创建UUID并转为String类型
		uuid = UUID.randomUUID().toString().replaceAll("-", "");
		System.out.println(uuid);
		File file = new File(uuidPath);

		// 打开(新建)txt文件并写入UUID
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file);
		out.write(uuid.getBytes());
		out.close();

		// 客户端配置

		// 设置调用的服务地址-端口
		transport = new TSocket("127.0.0.1", 5555);
		// 使用二进制协议
		TProtocol protocol = new TBinaryProtocol(transport);
		// 使用的接口
		clientForReceiver = new IFileReceiverService.Client(protocol);

		// 打开socket
		transport.open();

		// 获得接收端文件传输列表
		fileListFromReceiver = clientForReceiver.prepare(uuid);
	}

	public void postBack() throws Exception {
		prepare(); // UUID生成与保存,并获取接收端的文件列表

	}

	public void closeSocket() {
		// 关闭socket
		transport.close();
	}

	public static void main(String[] args) throws Exception {
		IFileSenderClient client = new IFileSenderClient();
		client.postBack();
	}
}
