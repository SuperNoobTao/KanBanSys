package cc.superliar.util;

import cc.superliar.Application;
import cc.superliar.component.Transformer;
import cc.superliar.constant.ResultConstant;
import cc.superliar.domain.DataDomain;
import cc.superliar.domain.StyleDomain;
import cc.superliar.po.Device;
import cc.superliar.po.Url;
import cc.superliar.vo.DataVO;
import cc.superliar.vo.StyleVO;
import cc.superliar.vo.UrlVO;
import net.sf.json.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class Server {





public static void main(String[] args) throws Exception {



    String topic        = "android1";
    String content      = "{'code': '200','message': 'Successful operation','id': '111','name': '安卓电视8','location': 'B车间','screenSize': '28','screenNum': '4','styleVO': {'code': '200','message': 'Successful operation','id': 41,'name': '无','speed': '0','mode': '无','description': '无动作'},'description': '备注:小米电视'}";
    int qos             = 2;
    String broker       = "tcp://iot.eclipse.org:1883";
    String clientId     = "JavaSample";
    MemoryPersistence persistence = new MemoryPersistence();



    try {

        MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        System.out.println("Connecting to broker: "+broker);
        sampleClient.connect(connOpts);
        System.out.println("Connected");
        System.out.println("Publishing message: "+content);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);
        sampleClient.publish(topic, message);
        System.out.println("Message published");
        sampleClient.disconnect();
        System.out.println("Disconnected");
        System.exit(0);
    } catch(MqttException me) {
        System.out.println("reason "+me.getReasonCode());
        System.out.println("msg "+me.getMessage());
        System.out.println("loc "+me.getLocalizedMessage());
        System.out.println("cause "+me.getCause());
        System.out.println("excep "+me);
        me.printStackTrace();
    }
}
}
