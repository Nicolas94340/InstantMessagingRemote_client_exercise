package topicmanager;

import apiREST.apiREST_TopicManager;
import util.Subscription_check;
import util.Topic;
import util.Topic_check;
import java.util.List;
import publisher.Publisher;
import publisher.PublisherStub;
import subscriber.Subscriber;
import webSocketService.WebSocketClient;

public class TopicManagerStub implements TopicManager {

  public String user;

  public TopicManagerStub(String user) {
    WebSocketClient.newInstance();
    this.user = user;
  }

  public void close() {
    WebSocketClient.close();
  }

  @Override
  public Publisher addPublisherToTopic(Topic topic) {
    apiREST_TopicManager.addPublisherToTopic(topic);
    return new PublisherStub(topic);
  }

  @Override
  public void removePublisherFromTopic(Topic topic) {
    apiREST_TopicManager.removePublisherFromTopic(topic);
  }

  @Override
  public Topic_check isTopic(Topic topic) {
    Topic_check tc = apiREST_TopicManager.isTopic(topic);
    return tc ; 
  }

  @Override
  public List<Topic> topics() {
    return apiREST_TopicManager.topics();
  }

  @Override
  public Subscription_check subscribe(Topic topic, Subscriber subscriber) {
    Subscription_check sc = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
    if(isTopic(topic).isOpen)
    {
        sc = new Subscription_check(topic, Subscription_check.Result.OKAY);
        WebSocketClient.addSubscriber(topic, subscriber); 
    }
    return sc; 
  }

  @Override
  public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
      Subscription_check sc = new Subscription_check(topic, Subscription_check.Result.NO_SUBSCRIPTION);
    if(isTopic(topic).isOpen)
    {
        sc = new Subscription_check(topic, Subscription_check.Result.OKAY);
        WebSocketClient.removeSubscriber(topic); 
    }
    return sc; 
  }
      

}
