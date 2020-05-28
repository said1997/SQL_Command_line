package StructureInMemoryTest;



import org.junit.Test;

import StructureInMemory.TablesInMemory;


public class StructureInMemoryTest {

	@Test
	public void test() throws Exception{
		//TablesInMemory.Create();
		int foo;
		try {
		   foo = Integer.parseInt("1997");
		   System.out.println(foo);
		}
		catch (NumberFormatException e)
		{
		   foo = 0;
		}
		
	}
	
	
}