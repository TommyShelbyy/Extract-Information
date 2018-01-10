package debug.handlers;

public class MyPair 
{
	MyException exception;  //exception信息，参考MyException类
	double probability;     //当前exception属于method抛出的概率，值为1（确定属于method抛出），0.5（不确定是否属于method抛出）
	MyPair()
	{
		exception = new MyException();
		probability = 0.0;		
	}
}
