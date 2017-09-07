package core.utilities;

import java.util.concurrent.*;

public class CustomFuture implements Future<Boolean>{
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private Boolean result;
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Boolean get() throws InterruptedException, ExecutionException {
        countDownLatch.await();
        return result;
    }

    @Override
    public Boolean get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if(countDownLatch.await(timeout,unit)){
            return result;
        }else {
            throw new TimeoutException("Couldn't send the multipart");
        }
    }
    public void changeResult(Boolean result){
        this.result = result;
        countDownLatch.countDown();
    }
}
