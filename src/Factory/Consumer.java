package Factory;

import java.io.Serializable;
import Model.Buffer;

public class Consumer implements FactoryOperator, Runnable, Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Buffer buffer;
	int sleepTime;
	volatile boolean isRunning;

	public Consumer(Buffer buffer, int sleepTime) 
	{
		this.buffer = buffer;
		this.sleepTime = sleepTime;
		this.isRunning = true;
	}
	
	public void stop() 
	{
		isRunning = false;
	}

	@Override
	public void run() 
	{
		while (isRunning) 
		{
			try 
			{
				System.out.println("Consumed: " + buffer.remove());
				buffer.checkBufferSize();
				Thread.sleep(sleepTime);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public int getSleepTime() 
	{
		return this.sleepTime;
	}
}
