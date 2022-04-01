package ph.edu.ckc.k8sckcbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Component("assymetricSecurity")
public class AssymetricSecurity {

    private KeyStore keyStore;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/ckc.jks");
            keyStore.load(resourceAsStream, environment.getProperty("JKS_PASSWORD").toCharArray());

        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

     public PrivateKey getPrivateKeys() {
        try {
            return (PrivateKey) keyStore.getKey("ckc", environment.getProperty("JKS_PASSWORD").toCharArray());
        }catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

     public PublicKey getPublicKeys() {
        try {
            return (PublicKey) keyStore.getCertificate("ckc").getPublicKey();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}