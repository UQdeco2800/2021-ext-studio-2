package com.deco2800.game.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * An event pool which runs long-running rendering tasks on a single thread. These expensive
 * tasks are a part of an unbounded queue which executes them sequentially.
 *
 * <pre>{@code
 *  class XYZComponent extends UIComponent{
 *     onCreate(){
 *         // Listen to multiple events coming in at the same time
 *         entity.getEvents("newAchievement", this::renderAchievementCard);
 *
 *         //...
 *     }
 *
 *     private void renderAchievementCard(Achievement achievement){
 *         // Aim: A card should render for at least 5 seconds and then disposed
 *         // Then the next achievement card should be displayed
 *         AsyncTaskQueue.enqueue(() -> {
 *               paintCardUI();
 *               // Display it for 5 seconds
 *               Thread.sleep(5000); // long running code
 *               destroyCardUI();
 *         });
 *     }
 *
 *     dispose(){
 *         // Since the game area is out of focus, cancel all future rendering
 *         // tasks immediately to prevent memory leaks and other nasty bugs.
 *         AsyncTaskQueue.cancelFutureTasksNow();
 *
 *         //...
 *     }
 *  }}</pre>
 */
public class AsyncTaskQueue {
    /**
     * Creates an Executor that uses a single worker thread operating off an unbounded queue.
     * These expensive tasks are a part of an unbounded queue which executes them sequentially.
     * The tasks are executed sequentially on the same thread.
     */
    private static final ExecutorService queue = Executors.newSingleThreadExecutor();

    /**
     * Check if the asynchronous task queue is accepting any tasks
     * @return isShutdown
     */
    public static boolean isShutDown() {
        return queue.isShutdown();
    }

    /**
     * Do not accept any more tasks. Shut down the queue after completing
     * already enqueued tasks.
     */
    public static void cancelFutureTasks() {
        queue.shutdown();
    }

    /**
     * Shut down the task queue immediately and cancel all future tasks.
     */
    public static void cancelFutureTasksNow() {
        queue.shutdownNow();
    }

    /**
     * Dispatch a long-running task to the queue.
     * An event pool which runs long-running rendering tasks on a single thread. These expensive
     * tasks are a part of an unbounded queue which executes them sequentially.
     *
     * @param task Preferably a lambda function with some long-running piece of code.
     */
    public static void enqueueTask(Runnable task){
        try{
            if(!isShutDown()){
                queue.execute(task);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private AsyncTaskQueue() {
        throw new IllegalStateException("Instantiating static util class");
    }
}
