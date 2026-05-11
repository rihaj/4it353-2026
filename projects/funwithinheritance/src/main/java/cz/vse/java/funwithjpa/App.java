package cz.vse.java.funwithjpa;

import cz.vse.java.funwithjpa.model.onetable.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("punit");
        EntityManager em = EMF.createEntityManager();

        em.getTransaction().begin();

        Sms sms = new Sms();
        sms.setText("SMS notifikace");
        sms.setTimestamp(LocalDateTime.now());
        sms.setPhoneNumber("+420123456789");
        em.persist(sms);

        Email email = new Email();
        email.setText("Emailová notifikace");
        email.setTimestamp(LocalDateTime.now());
        email.setEmailAddress("muf@puf.cz");
        em.persist(email);

        em.getTransaction().commit();

        var result = em.createQuery("from Notification", Notification.class);
        result.getResultList().forEach( n -> System.out.println("***** " + n.getClass() + " " + n));

        em.close();
        EMF.close();
    }
}
