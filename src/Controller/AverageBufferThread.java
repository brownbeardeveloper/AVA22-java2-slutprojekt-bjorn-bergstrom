package Controller;

import Model.Buffer;

public class AverageBufferThread implements Runnable 
{
	Buffer buffer;
	boolean runRountineTask = true;
    int totalSize = 0;
    int count = 0;
	
	public AverageBufferThread(Buffer buffer) 
	{
		this.buffer = buffer;
	}
	
	public void stop() 
	{
		runRountineTask = false;
	}

	@Override
	public void run() 
	{		
		RegulatorController controller = RegulatorController.getInstance();
		while(runRountineTask) 
		{
	        try 
	        {
	            int prevSize = buffer.getBufferSize();
				Thread.sleep(10000);
	            int currentSize = buffer.getBufferSize();
                double averageSize = (double) (currentSize + prevSize) / 2;
                controller.addLogWithTime("buffer's average size in 10 seconds: " + averageSize);
			} 
	        catch (InterruptedException e) 
	        {
				e.printStackTrace();
			}

		}
	}
}
