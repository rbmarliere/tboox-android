package info.nsupdate.tboox.tboox.utils;

import android.content.Context;
import com.loopj.android.http.*;
import org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import cz.msebera.android.httpclient.conn.ssl.SSLConnectionSocketFactory;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/* Created by rbmarliere on 12/12/16. */

public class TbooxConnector extends AsyncHttpClient
{
    private static final String URL_crt = "http://tboox.nsupdate.info/cert";
    private static final String URL_api = "https://tboox.nsupdate.info/api";
    private Certificate cert = null;
    private SSLConnectionSocketFactory sf = null;

    public enum method
    {
        conn_method_DELETE,
        conn_method_GET,
        conn_method_POST,
        conn_method_PUT
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
                case conn_method_PUT:
                    put(context, URL_api + path, entity, "application/json", handler);
                    break;
            }
        } catch (Exception e) {
            error(e);
        }
    }

    private void set_sf(SSLConnectionSocketFactory sf)
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
    }

    private void load_ssl()
    {
        try {
            init_cert();

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("tboox_ca", this.cert);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext ssl_context = SSLContext.getInstance("TLS");
            ssl_context.init(null, tmf.getTrustManagers(), null);

            set_sf(new SSLConnectionSocketFactory(ssl_context));
        } catch (Exception e) {
            error(e);
        }
    }

    private void init_cert()
    {
        if (this.cert == null) load_cert();
    }

    private void load_cert()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL_crt, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
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
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    private APIException error(Exception e)
    {
        e.printStackTrace();
        return new APIException(e.getMessage());
    }
}
