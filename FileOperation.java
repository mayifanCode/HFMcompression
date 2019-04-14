package com.myf.HFMcompression1223;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class FileOperation {

	FileOutputStream fos;//�����ļ����������
	FileInputStream fis; //�����ļ�д��������
	
	
	/**
	 * ͨ���ļ���ȡ����ķ���
	 * @param str
	 */
	public int[] getArrays(File file)
	{
		int[] arrays = new int[256];
		try{
			FileInputStream fis = new FileInputStream(file);
			int ascii=0;
			while((ascii=fis.read())!=-1)
			{
				arrays[ascii]++;
			}
		    fis.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return arrays;
	}	
	
	/**
	 * ��ȡ�ļ���ȡ01��
	 */
	public String GetStr(HashMap map,File file)
	{
		String str="";   //�����ַ�������01��
		try{						
			FileInputStream fis = new FileInputStream(file);
			int value=0;	
			while((value=fis.read())!=-1)
			{
				str+=map.get((char)value+"");  //ȡ���ַ���Ӧ��01�룬�ۼӵ��ַ�����
			}			
			fis.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return str;
	}
	
	
	
	
	/**
	 * дHashMap���ļ���д��������+��һ��key+��һ��value��ռ�ֽ���+value���һ���ֽڵĲ�0���+��һ��value�������ֽ�+��һ��key+����������
	 */
    public void writeHashMap(HashMap<String, String> map ,File file)
    {
    	int size = map.size(); //��ȡ����ĸ�������HashMap�еļ�ֵ�Ը���
    	String temp=""; //�����ʱ8λ01�ַ���
    	int value=0; //���01�ַ���ת���õ���ASCIIֵ
    	try{
	    	fos = new FileOutputStream(file);
	    	fos.write(size);  //дHashMap����
	    	System.out.println("HashMap���ȣ�"+size);
            Set<String> keySet = map.keySet(); //��ȡHashMap���key������
            java.util.Iterator<String> it = keySet.iterator();//ͨ��������ȡ������
	    	while(it.hasNext()) //�����жϣ�����һ��key
	    	{
	    		String key = it.next(); //ȡ����һ��key
	    		String code = map.get(key); //ȡ��code
	    		System.out.println("key:"+key+"   code:"+code);
	    		fos.write(key.charAt(0)); //дkeyֵ
	    		System.out.println("дkeyֵ��"+key.charAt(0));
	    		int a = code.length()/8;//�ܴ������ֽ���
	    		System.out.println("�ܴ������ֽ�����"+a);
	    		int b = code.length()%8;//ʣ���λ��
	    		System.out.println("ʣ���λ����"+b);
	    		int c =1; //ֵ��Ӧ�Ĵ洢���ֽ���
	    		if(b==0) //��ʣ��λ
	    		{
	    			System.out.println("��ʣ��λ");
	    			c=a;
	    			fos.write(c);  //дcode���ֽ���
	    			System.out.println("дcode�ֽ���:"+c);
	    			fos.write(0);  //д��0����Ϊ0��
	    			System.out.println("д��0����+0");
	    		    for(int i=0;i<a;i++) //дcodeֵ
	    		    {
	    		    	temp="";
	    		    	for(int j=0;j<8;j++) 
	    		    	{
	    		    		temp+=code.charAt(i*8+j);
	    		    	}
	    		    	value=StringToInt(temp);
	    		    	fos.write(value); //��һ��code��ÿһλд��ȥ
	    		    	System.out.println("дvalue��"+value);
	    		    }
	    		}
	    		else 
	    		{
	    			System.out.println("��ʣ��λ");
	    		    c=a+1;
	    		    fos.write(c); //дcode���ֽ���
	    		    System.out.println("дcode�ֽ�����"+c);
	    		    fos.write(8-b); //д��0��
	    		    System.out.println("д��0����"+(8-b));
	    		    for(int i=0;i<8-b;i++) //��0
	    		    {
	    		    	code+="0";
	    		    }
	    		    System.out.println("��0���code��"+code);
	    		    for(int i=0;i<c;i++)
	    		    {
	    		    	temp="";
	    		    	for(int j=0;j<8;j++)
	    		    	{
	    		    		temp+=code.charAt(8*i+j);
	    		    	}
	    		    	System.out.println("temp��"+temp);
	    		    	value=StringToInt(temp);
	    		    	fos.write(value); //��һдcode����������0
	    		    	System.out.println("дvalue��"+value);
	    		    }	    	
	    		}	    			    		
	    	}
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
	
    /**
     * ���ĵ�ת��Ϊ��HFM����д���ļ�
     */
	public void writeHFMcode(String HFMcode)
	{
		int len = HFMcode.length();  //��ȡHFMcode����
		int a = len/8;   //����������ֽڵ���Ŀ
		System.out.println("�������ֽ���Ŀ��"+a);
		int b = len%8;   //���ʣ���λ��
		System.out.println("��0����Ŀ��"+b);
		String temp = ""; //��ʱ���8λ����
		int value = 0; //���8λ01ת���õ���ֵ
		try
		{
			if(b==0)  //�޲����λ�Ĳ��֣�����Ҫ��0
			{
				fos.write(0); //д��0��
				for(int i=0;i<a;i++)
				{
					temp="";
					for(int j=0;j<8;j++)
					{								
						temp+=HFMcode.charAt(i*8+j);						
					}
					value=StringToInt(temp);
					fos.write(value); //дHFMcode
				}
			}
			else   //��Ҫ��0
			{
				int c = 8-b; //���㲹0��
				fos.write(c); //д��0��
				for(int i=0;i<c;i++) //��0
				{
					HFMcode+="0";
				}
				for(int i=0;i<a+1;i++) 
				{
					temp="";
					for(int j=0;j<8;j++)
					{
						temp+=HFMcode.charAt(i*8+j);
					}
					value=StringToInt(temp);
					fos.write(value); //дHFMcode
				}
			}
			fos.close(); //д��ر���Դ
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}		
	}
	
	/**
	 * ��01�ַ���ת��ΪASCII��
	 * @param temp
	 * @return
	 */
	public int StringToInt(String temp)
	{
		int value=0;
		for(int i=0;i<8;i++)
		{
			int x = temp.charAt(i)-48;
			if(x==1)    //Ϊ1���ۼ���value
			{
//				System.out.println("x==1ʱ��i��"+i+"�����ۼӣ�"+Math.pow(2,7-i ));
				value+=Math.pow(2,7-i);  //��ʾ2��(7-i)�η�
			}
		}
		return value;
	}
	
	/**
	 * ����ֵת��Ϊ01�ַ���
	 * @param value
	 */
	public String IntToString(int value)
	{
		String temp1=""; //��ŷ����ַ���
		String temp="";  //��������ַ���
		while(value>0) //��ȡ������������λ�����ַ���Ϊ�����
		{
			temp1+=value%2;
			value=value/2;
		}		
		for(int i=temp1.length()-1;i>=0;i--)
		{
			temp+=temp1.charAt(i);
		}
		return temp;
	}
	
	/**
	 * ����ֵת��Ϊ01�ַ�������ֵ��Χ��0~255,01��������8λ
	 * @param value
	 */
	public String IntToStringEight(int value)
	{
		String temp1=""; //��ŷ����ַ���
		String temp="";  //��������ַ���
		int add=0;
		while(value>0) //��ȡ������������λ�����ַ���Ϊ�����
		{
			add++;
			temp1+=value%2;
			value=value/2;
		}	
		add=8-add;
		for(int i=0;i<add;i++)//��0��8λ
		{
			temp1+="0";
		}
		for(int i=temp1.length()-1;i>=0;i--) //������ַ�����ȡ������ַ���
		{
			temp+=temp1.charAt(i);
		}
		return temp;
	}	
	
	
	
	
	
	/**
	 * ���ⲿ�Ľӿڣ�ʵ�ְ�ѹ��������ݺ���Ϣд��ѹ���ļ�
	 * @param fileCompress
	 */
	public void compressFile(File fileCompress,HashMap<String, String> map,String HFMcode)
	{
		writeHashMap(map, fileCompress);  //дHashMap������
		writeHFMcode(HFMcode); //����дHFMcode 01�ַ���
	}
	
	/**
	 * ��ѹ��ȡHashMap
	 * @param fileCompress
	 */
	public HashMap<String, String> readHashMap(File fileCompress)
	{
		HashMap<String, String> mapGet = new HashMap<>();
		try
		{
			fis=new FileInputStream(fileCompress); 
			int keyNumber = fis.read(); //��ȡkey������
			System.out.println("��ȡ��key��������"+keyNumber);
			String key = ""; //HashMap�ļ�ֵ��
			String code= ""; //δȥ0���ַ���
			String codeRZ="";//ȥ0���ַ���
			int length=0; //��ʾ��ԭ����ַ��������۳��ȣ�����ַ���ǰ���0������
			int byteNum=1; //��ǰcodeռ�˼����ֽ�
			int addZero=0; //��0��
			int value=0; //��ʱ��ֵ
			int zeroLength=0;//codeû��1��ʱ����ַ�������
		    for(int i=0;i<keyNumber;i++)
		    {
		    	key = (char)fis.read()+""; //��ȡkeyֵ
		    	byteNum=fis.read(); //��ȡcode���ֽ���
		    	System.out.println("��ȡ�ֽ�����"+byteNum);
		    	addZero=fis.read(); //��ȡ��0����
		    	System.out.println("��ȡ��0������"+addZero);
		    	if(addZero==0) //û�в�0�������ֽ���
		    	{
		    		for(int k=byteNum-1;k>=0;k--)
		    		{
		    			value+=fis.read()*(Math.pow(2, k*8));
		    			System.out.println("��ȡvalueֵ��"+value);
		    		}
		    		code=IntToString(value);//����ֵתΪ01code
		    		value=0;//����
		    		length=8*byteNum-code.length();//������ǰ��Ҫ������0
		    		if(code.length()==0)  //��code�����ֶ�Ϊ0��ֻҪȥ��β������
		    		{
		    			zeroLength=length-addZero;  //�����ж��ٸ�0
		    			for(int k=0;k<zeroLength;k++)
		    			{
		    				codeRZ+="0";
		    			}
		    		}
		    		else    //codeֵ��Ϊ0������ǰ���0��ȥ�������0
		    		{
			    		for(int k=0;k<length;k++)
			    		{
			    			codeRZ+="0";
			    		}
			    		for(int k=0;k<code.length()-addZero;k++)
			    		{
			    			codeRZ+=code.charAt(k);
			    		}	
		    		}
		    	}
		    	else  //�в�0
		    	{
		    		for(int k=byteNum-1;k>=0;k--)
		    		{
		    			value+=fis.read()*(Math.pow(2, k*8));
		    			System.out.println("��ȡvalueֵ��"+value);
		    		}
		    		code=IntToString(value);//����ֵתΪ01code
		    		value=0;//��0
		    		System.out.println("ת���õ���code��"+code);
		    		length=8*byteNum-code.length();//������ǰ��Ҫ������0	    		
		    		if(code.length()==0)  //��code�����ֶ�Ϊ0��ֻҪȥ��β������
		    		{
		    			zeroLength=length-addZero;  //�����ж��ٸ�0
		    			for(int k=0;k<zeroLength;k++)
		    			{
		    				codeRZ+="0";
		    			}
		    		}
		    		else   //codeֵ��Ϊ0������ǰ���0��ȥ�������0
		    		{
			    		for(int k=0;k<length;k++)
			    		{
			    			codeRZ+="0";
			    		}
			    		for(int k=0;k<code.length()-addZero;k++) //��Ҫ�����0
			    		{
			    			codeRZ+=code.charAt(k);
			    		}
		    		}
		    		
		    	}
		    	
		    	System.out.println("��ȡ��keyֵ��"+key+"   ��ȡ��codeֵ:"+codeRZ);
		    	mapGet.put(codeRZ , key ); //�Ѷ�ȡ���ļ�ֵ�Դ��봴����HashMap
		    	codeRZ=""; //���
		    }
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return mapGet;
	}
	
	/**
	 * ��ȡѹ���ļ��е����ݣ���ԭ����������01��
	 */
	public String readHFMStr()
	{
		String str1=""; //��Ż�ȡ����ֱ�ӵ�01�ַ���
		String str=""; //���ȥ����0���ַ���
		int value=0;
		String temp="";
		try{
			int addZero = fis.read(); //��ȡ�����ļ��Ĳ�0����			
			while((value=fis.read())!=-1)
			{
				temp=IntToStringEight(value); //��ÿ���ֽڵ�����ת��Ϊ��λ��01
				str1+=temp;       
			}
			if(addZero!=0) //�в�0����ȡ��0ǰ���ַ���
			{
				for(int i=0;i<str1.length()-addZero;i++) //��0�Ĳ��ֲ���ֵ
					str+=str1.charAt(i)+""; 
				return str;  
			}
			fis.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}	
		return str1;
	}
	
	/**
	 * д���ļ��ı���·����д�ļ���
	 * @param str
	 * @param mapGet
	 * @param fileCompress
	 */
	public void writeFile(String str , HashMap<String, String> mapGet,File fileCompress)
	{
		try
		{
			fos = new FileOutputStream(fileCompress); //��ȡ�ļ������
			int len = str.length();//��ȡ01���ĳ���
			System.out.println("׼�������01�볤�ȣ�"+len);
			String temp=""; //��ʱ��Ŷε�01�ַ���
			for(int i=0;i<len;i++)
			{
				temp+=str.charAt(i);
				//System.out.println("tempֵ��"+temp);
				if(mapGet.containsKey(temp))
				{
					fos.write(mapGet.get(temp).charAt(0)); //һ���ַ����ַ���ת�ַ�Ȼ��д��
					System.out.println("��ѹ��һ���ַ�:"+mapGet.get(temp).charAt(0)+"   ����01ԭ���ǣ�"+temp+"  ��ʱ��iΪ��"+i);
					temp="";					
				}
			}
			fos.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * ���ⲿ�Ľӿڣ�ʵ�ֽ�ѹ�ļ�����ȡHashMap���ļ�����
	 * @param fileCompress,ѹ���ļ�Ŀ¼
	 * @param fileUncompress����ѹ����Ŀ¼
	 */
	public void uncompressFile(File fileCompress,File fileUncompress)
	{
		HashMap<String, String> mapGet = readHashMap(fileCompress); //��ȡ��ϣ��
//		Set<String> set = mapGet.keySet();
//		Iterator<String> it = set.iterator();
//		while(it.hasNext())
//		{
//			String key = it.next();
//			System.out.println("key"+key+"code"+mapGet.get(key));
//		}
		String str = readHFMStr();  //��ȡ01�ַ���
		System.out.println("��ȡ���Ĺ�����01����"+str);
		writeFile(str,mapGet,fileUncompress);  //д�ļ�������·��
	}
	
	
	
	
}
