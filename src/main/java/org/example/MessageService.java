package org.example;

public class MessageService {

  private String message;

  private MessageProvider provider;

  public MessageService() {
    System.out.println("MessageService: 我被Spring通过无参构造函数创建了！");
  }

  public String getMessage() {
    return message.concat(provider.getMessage());
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public MessageProvider getMessageProvider() {
    return provider;
  }

  public void setProvider(MessageProvider messageProvider) {
    this.provider = messageProvider;
  }
}
