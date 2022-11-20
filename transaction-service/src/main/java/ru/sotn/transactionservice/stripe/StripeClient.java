package ru.sotn.transactionservice.stripe;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.stereotype.Component;
import ru.sotn.transactionservice.config.AppProps;

import java.util.HashMap;
import java.util.Map;

@Component
public class StripeClient {
    private final AppProps appProps;
    public StripeClient(AppProps appProps){
        this.appProps = appProps;
        Stripe.apiKey  = appProps.getSercretKey();
    }

    public Charge chargeNewCard(String token, double amount) throws Exception {

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}
