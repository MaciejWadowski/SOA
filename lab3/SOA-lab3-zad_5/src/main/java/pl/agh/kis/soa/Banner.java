package pl.agh.kis.soa;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "banner")
@ApplicationScoped
public class Banner {
    private static final String[] BANNER_LABELS = {
            "THE PRICE HAS NEVER BEEN LOWER FOR PAPER TOILET",
            "IF YOU CLICK YOU WON'T REGRET",
            "SOME FANCY BANNER",
            "JEANS 110% OFF",
            "DO YOU WANT SOME COFFEE MACHINE?"
    };

    private Long clicks = 0L;

    public String getRandomAdvertise() {
        int rand  = (int)(Math.random()*BANNER_LABELS.length);
        return BANNER_LABELS[rand];
    }

    public Long getClicks() {
        return clicks;
    }

    public void increment() {
        clicks++;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }
}
