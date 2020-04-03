package pl.agh.kis.edu;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;

@Singleton
@Remote(IRemoteTestBeanCounter.class)
@Local(ILocalTestBeanCounter.class)
public class TestBeanCounter implements ITestBeanCounter {

    long counterNumber = 0;

    public TestBeanCounter() {}

    public void increment() {
        counterNumber++;
    }

    public long getNumber() {
        return counterNumber;
    }
}
