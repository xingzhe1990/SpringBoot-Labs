package cn.iocoder.springboot.lab15.springdataelasticsearch.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.net.InetAddress;

@Configuration
public class XPackClientConfig {
    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;
    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;
    @Value("${spring.data.elasticsearch.keystore-path}")
    private String keystorePath;
    @Value("${spring.data.elasticsearch.user}")
    private String user;
    @Value("${spring.data.elasticsearch.password}")
    private String password;
    @Bean(destroyMethod = "close")
    public TransportClient transportClient() throws Exception {
        TransportClient client = new PreBuiltXPackTransportClient(Settings.builder()
                .put("cluster.name", clusterName)
//                .put("client.transport.sniff", true)
//                .put("xpack.security.enabled", true)
                .put("xpack.security.transport.ssl.enabled", true)
                .put("xpack.security.transport.ssl.keystore.path", keystorePath)
                .put("xpack.security.transport.ssl.truststore.path", keystorePath)
                .put("xpack.security.transport.ssl.verification_mode","certificate")
                .put("xpack.security.user", user+':'+password)
                .build());
        String[] nodes = StringUtils.split(clusterNodes,",");
        for(String node : nodes) {
            String ip = node.substring(0,node.indexOf(":"));
            int port = Integer.valueOf(node.substring(node.indexOf(":")+1));
            client.addTransportAddress(new TransportAddress(InetAddress.getByName(ip), port));
        }
        return client;
    }
}
