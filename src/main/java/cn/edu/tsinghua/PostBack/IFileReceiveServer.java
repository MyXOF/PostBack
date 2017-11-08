package cn.edu.tsinghua.PostBack;


import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class IFileReceiveServer {

    public static void main(String[] args) {
        try {
            // 设置服务器端口为5555
            TServerSocket serverTransport = new TServerSocket(5555);
            
            // 设置二进制协议
            Factory protocolFactory = new TBinaryProtocol.Factory();
            
            //处理器关联业务实现
            TProcessor processor = new IFileReceiverService.Processor(new IFileReceiveImp());
            
            // 使用线程池服务模型
            TThreadPoolServer.Args poolArgs = new TThreadPoolServer.Args(serverTransport);
            poolArgs.processor(processor);
            poolArgs.protocolFactory(protocolFactory);
            TServer poolServer = new TThreadPoolServer(poolArgs);
            poolServer.serve();
            
                       
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

}
