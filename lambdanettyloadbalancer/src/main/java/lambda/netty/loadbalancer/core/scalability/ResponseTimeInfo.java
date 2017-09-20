package lambda.netty.loadbalancer.core.scalability;

public class ResponseTimeInfo {

    private long total = 0;
    private int count = 0;


    public long update(long val) {
        count++;
        total += val;
        return total / count;

    }

    public void clear() {
        total = 0;
        count = 0;
    }
}
