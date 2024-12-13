package eu.possiblex.portal.application.configuration;

import eu.possiblex.portal.business.control.DidWebServiceApiClient;
import eu.possiblex.portal.business.control.OmejdnConnectorApiClient;
import eu.possiblex.portal.business.control.SdCreationWizardApiClient;
import eu.possiblex.portal.business.control.TechnicalFhCatalogClient;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
public class AppConfigurer {

    private static final int EXCHANGE_STRATEGY_SIZE = 16 * 1024 * 1024;

    private static final ExchangeStrategies EXCHANGE_STRATEGIES = ExchangeStrategies.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(EXCHANGE_STRATEGY_SIZE)).build();

    @Value("${sd-creation-wizard-api.base-url}")
    private String sdCreationWizardApiBaseUri;

    @Value("${daps-server.url.internal}")
    private String dapsServerInternalUri;

    @Value("${did-web-service.base-url}")
    private String didWebServiceBaseUri;

    @Value("${did-web-service.ignore-ssl}")
    private boolean didWebServiceIgnoreSsl;

    @Value("${fh.catalog.url}")
    private String fhCatalogUrl;

    @Value("${fh.catalog.secret-key}")
    private String fhCatalogSecretKey;

    @Bean
    public SdCreationWizardApiClient sdCreationWizardApiClient() {

        WebClient webClient = WebClient.builder().exchangeStrategies(EXCHANGE_STRATEGIES)
            .baseUrl(sdCreationWizardApiBaseUri).build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(
            WebClientAdapter.create(webClient)).build();
        return httpServiceProxyFactory.createClient(SdCreationWizardApiClient.class);
    }

    @Bean
    public OmejdnConnectorApiClient dapsConnectorApiClient() {

        WebClient webClient = WebClient.builder().baseUrl(dapsServerInternalUri + "/api/v1/connectors").build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder()
            .exchangeAdapter(WebClientAdapter.create(webClient)).build();
        return httpServiceProxyFactory.createClient(OmejdnConnectorApiClient.class);
    }

    @Bean
    public DidWebServiceApiClient didWebServiceApiClient() throws SSLException {

        WebClient webClient;
        if (didWebServiceIgnoreSsl) {
            SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
            HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

            webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(didWebServiceBaseUri).build();
        } else {
            webClient = WebClient.builder().baseUrl(didWebServiceBaseUri).build();
        }

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder()
            .exchangeAdapter(WebClientAdapter.create(webClient)).build();
        return httpServiceProxyFactory.createClient(DidWebServiceApiClient.class);
    }

    @Bean
    public TechnicalFhCatalogClient fhCatalogClient() {

        WebClient webClient = WebClient.builder().baseUrl(fhCatalogUrl).defaultHeaders(httpHeaders -> {
            httpHeaders.set("Content-Type", "application/json");
            httpHeaders.set("Authorization", "Bearer " + fhCatalogSecretKey);
        }).build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builder()
            .exchangeAdapter(WebClientAdapter.create(webClient)).build();
        return httpServiceProxyFactory.createClient(TechnicalFhCatalogClient.class);
    }
}
