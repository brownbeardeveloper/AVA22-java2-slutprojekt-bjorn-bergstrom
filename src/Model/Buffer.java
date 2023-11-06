package Model;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import Controller.RegulatorController;
import Properties.FactoryProperties;

public class Buffer implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Queue<Item> buffer = new LinkedBlockingDeque<Item>(100);
	
	public int getBufferSize() 
	{	
		return buffer.size();
	}
	
	public void checkBufferSize() 
	{	
		double fillPercentage = (((double)buffer.size()/FactoryProperties.stockSize)*100);	
		RegulatorController controller = RegulatorController.getInstance();
		controller.updateProgressBar(fillPercentage);
	}
	
	public synchronized void add(Item item) 
	{
		buffer.add(item);
		System.out.println(buffer);
		notify();
	}
	
	public synchronized Item remove() 
	{
		if(buffer.isEmpty())
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return buffer.remove();
	}
	
	public void clear()
	{
		this.buffer.clear();
	}
}