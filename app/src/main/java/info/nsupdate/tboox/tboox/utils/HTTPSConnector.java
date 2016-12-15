package info.nsupdate.tboox.tboox.utils;

import android.content.Context;
import com.loopj.android.http.*;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import cz.msebera.android.httpclient.conn.ssl.X509HostnameVerifier;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/* Created by rbmarliere on 12/12/16. */

public class HTTPSConnector extends AsyncHttpClient
{
    private static final String URL_crt = "http://tboox.nsupdate.info/tboox.crt";
    private static final String URL_api = "https://tboox.nsupdate.info/api";

    //private static final String URL_crt = "http://192.168.1.17/tboox.crt";
    //private static final String URL_api = "https://192.168.1.17/api";

    private static Certificate cert = null;
    private static SSLSocketFactory sf = null;

    public enum method
    {
        conn_method_DELETE,
        conn_method_GET,
        conn_method_POST
    }

    public void request(
            Context context,
            method method,
            final String path,
            JSONObject parameters,
            ResponseHandlerInterface handler)
    {
        try {
            init_ssl();

            ByteArrayEntity entity = new ByteArrayEntity(parameters.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            switch (method) {
                case conn_method_DELETE:
                    delete(context, URL_api + path, entity, "application/json", handler);
                    break;
                case conn_method_GET:
                    get(context, URL_api + path, entity, "application/json", handler);
                    break;
                case conn_method_POST:
                    post(context, URL_api + path, entity, "application/json", handler);
                    break;
            }
        } catch (Exception e) {
            error(e);
        }
    }

    private void set_sf(SSLSocketFactory sf)
    {
        this.sf = sf;
    }

    private void set_cert(Certificate crt)
    {
        this.cert = crt;
    }

    private void init_ssl()
    {
        if (this.sf == null) load_ssl();
        setSSLSocketFactory(this.sf);
    }

    private void init_cert()
    {
        if (this.cert == null) load_cert();
    }

    private void load_ssl()
    {
        try {
            init_cert();

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // TODO: buy a proper certificate...
            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            X509HostnameVerifier hv = new X509HostnameVerifier()
            {
                @Override
                public void verify(String host, SSLSocket ssl) throws IOException
                {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException
                {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException
                {
                }

                @Override
                public boolean verify(String s, SSLSession sslSession)
                {
                    return true;
                }
            };

            SSLContext ssl_context = SSLContext.getInstance("TLS");
            ssl_context.init(null, new TrustManager[]{ tm }, null);

            SSLSocketFactory sf = new SSLSocketFactory(ssl_context);
            sf.setHostnameVerifier(hv);
            set_sf(sf);
        } catch (Exception e) {
            error(e);
        }
    }

    private void load_cert()
    {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            if (keyStore.isCertificateEntry("tboox_ca")) {
                set_cert(keyStore.getCertificate("tboox_ca"));
                return;
            }
        } catch (Exception e) {
            error(e);
        }

        // if no tboox_ca cert found in the keystore, load it down!
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL_crt, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody)
            {
                try {
                    InputStream caInput = new ByteArrayInputStream(responseBody);

                    CertificateFactory cf = null;
                    cf = CertificateFactory.getInstance("X.509");

                    Certificate crt;
                    crt = cf.generateCertificate(caInput);
                    set_cert(crt);

                    caInput.close();
                } catch (Exception e) {
                    error(e);
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error)
            {
            }
        });

        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("tboox_ca", this.cert);
        } catch (Exception e) {
            error(e);
        }
    }

    private APIException error(Exception e)
    {
        e.printStackTrace();
        return new APIException(e.getMessage());
    }
}
