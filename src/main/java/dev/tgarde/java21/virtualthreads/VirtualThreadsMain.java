package dev.tgarde.java21.virtualthreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadsMain {

  public static void main(String[] args) {
    PlatformThreads.platformThreadRun();
  }

}


class PlatformThreads {

  public static void platformThreadRun() {
    ExecutorService service = Executors.newFixedThreadPool(130);

    for (int i = 0; i < 1000; i++) {
      service.submit(() -> {
        System.out.println("Running Thread " + Thread.currentThread().getName());
        try {
          Thread.sleep(100);
        } catch (Exception e) {
          e.printStackTrace();
        }

      });
    }

    try {
      Thread.sleep(500005);
    } catch (Exception e) {
      e.printStackTrace();
    }
    service.shutdown();


  }
}
