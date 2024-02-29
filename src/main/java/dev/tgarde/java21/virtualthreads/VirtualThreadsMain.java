package dev.tgarde.java21.virtualthreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

public class VirtualThreadsMain {

  public static void main(String[] args) {
   PlatformThreads.platformThreadRun();
   VirtualThread.fromVirtualThread();
  }

}


class VirtualThread {

  public static void fromVirtualThread(){
    Thread thread = Thread.ofVirtual().start(RunnableInstance.getRunnable());
    try {
      thread.join();
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}


class PlatformThreads {

  public static void platformThreadRun() {
    ExecutorService service = Executors.newFixedThreadPool(10);

    Runnable runnable = RunnableInstance.getRunnable();

    for (int i = 0; i < 100; i++) {
      service.submit(runnable);
    }

    service.shutdown();


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
}
