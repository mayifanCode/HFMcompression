package com.myf.HFMcompression1223;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;



public class HFMcompression {

	public static void main(String[] args)
	{
		HFMcompression hc = new HFMcompression();
		File file  = new File("E:\\workspace\\mayifan\\src\\com\\myf\\HFMcompression1223\\data1.txt");//Դ�ļ���ַ	
		FileOperation fo = new FileOperation();
		int [] a = fo.getArrays(file);		
		System.out.println(Arrays.toString(a)); //��ӡ		
		LinkedList<Node<String>> list = hc.createNodeList(a);//�������Ԫ��תΪ�ڵ㲢��������			
		for(int i=0;i<list.size();i++)
		{
			System.out.println(list.get(i).toString());
		}
		Node<String> root = hc.CreateHFMTree(list); //����		
		System.out.println("��ӡ��������������");
	    hc.inOrder(root); //��ӡ������
	    System.out.println("��ȡҶ�ӽ�����������");
	    HashMap<String,String> map = hc.getAllCode(root);//��ȡ�ַ�����HashMap 
	    System.out.println("��ʼ���ļ���ȡstr��");
	    String str = fo.GetStr(map, file);
	    System.out.println("ת���õ���01�ַ�����"+str);
	    File fileCompress = new File("E:\\workspace\\mayifan\\src\\com\\myf\\HFMcompression1223\\data2.zip");//ѹ���ļ���ַ
	    fo.compressFile(fileCompress,map,str);  //����ѹ���ļ�
	    File fileUncompress = new File("E:\\workspace\\mayifan\\src\\com\\myf\\HFMcompression1223\\data3.txt");//ѹ���ļ���ַ
	    fo.uncompressFile(fileCompress,fileUncompress);//��ѹ�ļ���fileUncompress��
	}
	
	
	

	
	
	/**
	 * �ѻ�õ�����ת��Ϊ�ڵ㲢����������
	 * @param arrays
	 * @return
	 */
	public LinkedList<Node<String>> createNodeList(int[] arrays)
	{
		LinkedList<Node<String>> list = new LinkedList<>();
		for(int i=0;i<arrays.length;i++)
		{
			if(arrays[i]!=0)
			{
				String ch = (char)i+"";
				Node<String> node = new Node<String>(ch,arrays[i]); //�����ڵ㲢�����ַ���Ȩֵ
				list.add(node); //��ӽڵ�
			}
		}

		return list;
	}
	
	
	/**
	 * �������е�Ԫ������
	 * @param list
	 * @return
	 */
	public void sortList(LinkedList<Node<String>> list)
	{
		for(int i=list.size();i>1;i--)
		{
			for(int j=0; j<i-1;j++)
			{
				Node<String> node1 = list.get(j);
				Node<String> node2 = list.get(j+1);
				if(node1.getWeight()>node2.getWeight())
				{
					int temp ;					
					temp = node2.getWeight();
					node2.setWeight(node1.getWeight());
					node1.setWeight(temp);
					String tempChar;
					tempChar = node2.getData();
					node2.setData(node1.getData());
				    node1.setData(tempChar);
				    Node<String> tempNode = new Node<String>(null, 0);
				    tempNode.setLeft(node2.getLeft());
				    tempNode.setRight(node2.getRight());
				    node2.setLeft(node1.getLeft());
				    node2.setRight(node1.getRight());
				    node1.setLeft(tempNode.getLeft());
				    node1.setRight(tempNode.getRight());
				}
			}
			
		}

	}
	
	
	
	
	/**
	 * �����ķ���
	 * @param list
	 */
	public Node<String> CreateHFMTree(LinkedList<Node<String>> list)
	{
		while(list.size()>1)
		{
			  sortList(list); //����ڵ�����
			  Node<String> nodeLeft = list.removeFirst();
			  Node<String> nodeRight = list.removeFirst();
			  Node<String> nodeParent = new Node<String>( null ,nodeLeft.getWeight()+nodeRight.getWeight());			  
			  nodeParent.setLeft(nodeLeft);
			  nodeParent.setRight(nodeRight);
			  list.addFirst(nodeParent);
		}
		System.out.println("���ڵ��Ȩ�أ�"+list.get(0).getWeight());
		return list.get(0);//���ظ��ڵ�
	}
	
	
	public HashMap<String, String> getAllCode(Node<String> root)
	{
		HashMap<String, String> map = new HashMap<>();
		inOrderGetCode("", map, root);
		return map;
	}
	
	/**
	 * ��ѯָ���ַ��Ĺ��������루���������
	 * @param code
	 * @param st
	 * @param root
	 * @return
	 */
	public void inOrderGetCode(String code ,HashMap<String, String> map,Node<String> root)
	{
		if(root!=null)
		{
			inOrderGetCode(code+"0",map,root.getLeft());
						
			if(root.getLeft()==null&&root.getRight()==null)//�洢Ҷ�ӽ��Ĺ���������
			{		
				System.out.println(root.getData());
				System.out.println(code);
				map.put(root.getData(), code);
			}            
			inOrderGetCode(code+"1",map,root.getRight());			
		}				
	}
	
	/**
	 * ����������������
	 * @param root
	 * @return
	 */
	public void inOrder(Node<String> root)
	{
		if(root!=null)
		{
			inOrder(root.getLeft());
			
			if(root.getData()!=null)
            System.out.println(root.getData());
            
			inOrder(root.getRight());			
		}		
		
	}	
}