import java.util.Random;

/**
 * This class demonstrates very basic multithreading and synchronization. It
 * does not do any significant exception handling to simplify the code.
 *
 * <p><em>
 * Note that this class is designed to illustrate a specific concept, and
 * may not be an example of proper class design outside of this context.
 * </em></p>
 */
public class SleepyRabbitRace {

	private final Random random;
	private boolean done;

	public SleepyRabbitRace() {
		this.random = new Random(System.currentTimeMillis());
		this.done = false;
	}

	/**
	 * Will output the provided message prepended with the name of the thread
	 * that generated that message. A low-tech (unrecommended) alternative to
	 * logging in a multithreaded setting.
	 *
	 * @param message to be output to console
	 */
	public static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + ": " + message);
	}

	private class SleepyRabbit implements Runnable {

		/** Used to determine whether this rabbit completed the race. */
		private int count;

		/**
		 * The run() method cannot take any parameters or return any values,
		 * so you use instance members to setup and store values for later.
		 */
		public SleepyRabbit() {
			// The "this" keyword refers to an instance of SleepyRabbit,
			// not an instance of SleepyRabbitRace!
			this.count = 0;
		}

		/**
		 * The idea here is to sleep for a random amount of time, and then
		 * increment the count. The first thread to reach 100 wins the race.
		 */
		@Override
		public void run() {
			threadMessage("Starting race!");

			try {
				while (count < 100) {
					int sleep = random.nextInt(100);

					/*
					 * Output periodic debug messages so we know our code is
					 * working properly without overwhelming the console.
					 */
					if (count % 20 == 0) {
						threadMessage("Reached " + count + ". Sleeping for " +
								sleep + " milliseconds.");
					}

					Thread.sleep(sleep);
					count = count + 1;
				}

				/*
				 * Are all of the accesses to "count" safe above? Is it a
				 * shared data variable? It is not, this thread has its own
				 * count, and can access it safely.
				 */

				/*
				 * We do not need to wait until all of the rabbits finish the
				 * race, we could indicate that one rabbit finished and the
				 * others can go ahead and stop.
				 */
				// done = true;

				/*
				 * Is this access to the "done" variable safe? Is it a shared
				 * data variable? Yes! Multiple threads may be accessing this
				 * variable. So, we need something that is synchronized.
				 */
				setDone();
			}
			catch (InterruptedException e) {
				/*
				 * If another thread has already won, the remaining threads
				 * will be interrupted before they reach 100.
				 */
				threadMessage("Interrupted while counting.");
			}

			threadMessage("Finished! Reached count " + count + ".");
		}
	}

	/**
	 * Creates three sleepy rabbits, and has them race. When a rabbit finishes
	 * the race, the others are interrupted and told to stop early.
	 *
	 * @throws InterruptedException
	 */
	public void race() throws InterruptedException {
		// Create our runnable sleep rabbits.
		SleepyRabbit rabbit1 = new SleepyRabbit();
		SleepyRabbit rabbit2 = new SleepyRabbit();
		SleepyRabbit rabbit3 = new SleepyRabbit();

		// Use the runnable objects to create actual threads.
		Thread thread1 = new Thread(rabbit1, "Rabbit 1");
		Thread thread2 = new Thread(rabbit2, "Rabbit 2");
		Thread thread3 = new Thread(rabbit3, "Rabbit 3");

		threadMessage("Here we go!");

		thread1.start();
		thread2.start();
		thread3.start();

		// Just run with the above code and the "Race finished" method to
		// see the rabbits running along even if the main thread exited.

		// Then add the join messages, and run the code. Notice how the
		// threads keep going even after the race is done. We can try to
		// finish the race early if we have a winner.

		// Then, add the while loop and the interrupt statements. Start with
		// a non-synchronized getDone() method.

		while (getDone() == false) {

			/*
			 * This is a terrible loop without anything on the inside. It
			 * will eat up CPU time repeatedly checking the done variable.
			 * This "busy-wait" loop should be always avoided.
			 */

			/*
			 * Instead, we will wait() until we a notify() lets us know that
			 * we should check the variable again.
			 */
			try {
				synchronized (this) {
					this.wait();
				}
			}
			catch (InterruptedException ex) {
				threadMessage("Race interrupted!");
			}
		}

		threadMessage("Winner detected!");

		/*
		 * This will make sure any threads that are still running even though
		 * the race has finished get interrupted and exit properly.
		 */
		thread1.interrupt();
		thread2.interrupt();
		thread3.interrupt();

		/*
		 * This will make sure all of the threads have exited by this point,
		 * making sure the main thread waits for the worker threads to finish
		 * before trying to access the final counts.
		 */

		thread1.join();
		thread2.join();
		thread3.join();

		threadMessage("Race finished.");
	}

	/**
	 * This method safely gets the status of the "done" variable.
	 *
	 * We add this after discovering we need to safely check this variable in
	 * the while loop of the {@link #race()} method.
	 *
	 * @return {@code true} if the race is finished
	 */
	private synchronized boolean getDone() {
		return this.done;
	}

	/**
	 * This method safely sets the "done" variable to {@code true} and then
	 * notifies anything waiting to wake up and recheck the status.
	 *
	 * We add this after discovering we need to safely change the value in the
	 * worker threads when a rabbit hits 100.
	 */
	private synchronized void setDone() {
		this.done = true;
		this.notifyAll(); // add later once wait() is setup properly
	}

	public static void main(String[] args) throws Exception {
		SleepyRabbitRace race = new SleepyRabbitRace();
		race.race();
	}
}
