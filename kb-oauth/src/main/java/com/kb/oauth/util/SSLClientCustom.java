package com.kb.oauth.util;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * @author wjx
 * @description
 */

public class SSLClientCustom {
    private static X509TrustManager tm = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    public static CloseableHttpClient getCloseableHttpClient() {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] { tm }, null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        HttpClientBuilder builder = HttpClientBuilder.create();
        SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(ctx,
                NoopHostnameVerifier.INSTANCE);
        builder.setSSLSocketFactory(sslConnectionFactory);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("https", sslConnectionFactory).build();

        HttpClientConnectionManager ccm1 = new BasicHttpClientConnectionManager(registry);

        builder.setConnectionManager(ccm1);

        return builder.build();
    }

}


