package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.concurrent.TimeUnit;

public class Emissor {

  private final static String QUEUE_NAME = "minha-fila";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("100.27.10.131"); // Alterar
    factory.setUsername("admin"); // Alterar
    factory.setPassword("rabbitmq2022.2"); // Alterar
    factory.setVirtualHost("/");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

                      //(queue-name, durable, exclusive, auto-delete, params); 
    channel.queueDeclare(QUEUE_NAME, false,   false,     false,       null);

    Integer messageNumber = 0;
    
    while(true){
      String message = "TESTE 0 - my server " + messageNumber++;
      
                            //  (exchange, routingKey, props, message-body             ); 
      channel.basicPublish("",       QUEUE_NAME, null,  message.getBytes("UTF-8"));
      System.out.println(" [x] Mensagem enviada: '" + message + "'");
      TimeUnit.SECONDS.sleep(4);
      
      if(messageNumber == 50){
        break;
      }
    }

    channel.close();
    connection.close();
  }
}