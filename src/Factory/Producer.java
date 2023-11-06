package Factory;

import java.io.Serializable;
import Model.Buffer;
import Model.Item;

public class Producer implements FactoryOperator, Runnable, Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Buffer buffer;
	int sleepTime;
	volatile boolean isRunning;

	public Producer(Buffer buffer, int sleepTime) 
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
				Thread.sleep(sleepTime);
				if(isRunning) 
				{
					buffer.add(new Item(""+(char)((int)(Math.random()*100))));
					buffer.checkBufferSize();
				}				
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
