package debug.handlers;

public class MyPair 
{
	MyException exception;  //exception��Ϣ���ο�MyException��
	double probability;     //��ǰexception����method�׳��ĸ��ʣ�ֵΪ1��ȷ������method�׳�����0.5����ȷ���Ƿ�����method�׳���
	MyPair()
	{
		exception = new MyException();
		probability = 0.0;		
	}
}
