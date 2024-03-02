package dev.tgarde.java21.virtualthreads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VirtualThreadsMain {

  public static void main(String[] args) {
    PlatformThreads.platformThreadRun();
    VirtualThread.fromVirtualThread();
    VirtualThread.fromVirtualThreadBuilder();
    VirtualThread.fromExecutorService();
  }

}


class VirtualThread {

  public static void fromExecutorService(){
    try (ExecutorService myExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
      IntStream.range(0,99)
              .forEach(val -> RunnableInstance.executeFuture(myExecutor.submit(RunnableInstance.getRunnable())));
    }
  }

  /**
   * Creating Thread from Thread.ofVirtual method
   */
  public static void fromVirtualThread(){
    Thread thread = Thread.ofVirtual().start(RunnableInstance.getRunnable());
    try {
      thread.join();
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void fromVirtualThreadBuilder(){
    Thread.Builder builder = Thread.ofVirtual().name("MyThread");
    Thread thread = builder.start(RunnableInstance.getRunnable());
    try {
      thread.join();
    }catch (Exception e){
      e.printStackTrace();
    }

  }
}


class PlatformThreads {

  public static void platformThreadRun() {
    try (ExecutorService service = Executors.newFixedThreadPool(10)) {
      Runnable runnable = RunnableInstance.getRunnable();
      IntStream.range(0,99)
              .forEach(val ->  RunnableInstance.executeFuture(service.submit(runnable)));
    }

  }

}

class RunnableInstance {
  public static Runnable getRunnable(){
    return () -> {
      System.out.println("Running Thread " + Thread.currentThread());
      try {
        Thread.sleep(100);
      } catch (Exception e) {
        e.printStackTrace();
      }
    };
  }


  public static void executeFuture(Future<?> future){
    try {
      future.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
}
