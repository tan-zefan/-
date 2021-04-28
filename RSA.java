import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RSA{
	static int p;
	static int q;
	static int e;
	static int d;
	static int n;
	static int n2;
	
	//返回模M进制的逆序
	static ArrayList<Integer> binary(int x,int m) {
		ArrayList<Integer> at = new ArrayList<Integer>();
		int a = 0;
		while(x>m||x == m) {
				a= x%m;
				at.add(a);
				x = x/m;
		}
		at.add(x);
		return at;
	}
	
	//返回x模m的逆原
	static int euclid(int x ,int m) {
		int s0 = 1,s1 = 0;
		int a = x%m,b = 0;
		while(a>1) {
			a = x%m;
			b = x/m;
			x = m;
			m = a;
			int temp = s1;
			s1 = s0 - b*s1;
			s0 = temp;
		}
		return s1;
	}
	
	//模重复平方
	static int powMod(int x,int v,int h) {
		String s = Integer.toBinaryString(v);
		char[] s1 = s.toCharArray();
		int a = 1;
		int b = x %h;
		for(int i = s1.length-1;i>-1;i--) {
			if(s1[i] == '1') {
				a = (a*b)%h;
				b = (b*b)%h;
			}else {
				b = (b*b)%h;
			}
		}
		return a;
	}
	
	//获取随机素数
	static int get_number() {
		Random random = new Random(); 
		int temp = random.nextInt(20)+3;
		int flag = 1;
		for(int i=2;i<Math.sqrt(temp)+1;i++) {
			if(temp%i==0) {
				flag = 0;
				break;
			}
		}
		if(flag == 1)
		return temp;
		else
			return get_number();
	}
	
	//获取随机p,q的值
	static void get_pq() {
		p = get_number();
		q = get_number();
		while(p == q) {
			q = get_number();
		}
		n = p*q;
		n2 = (p-1)*(q-1);
	}
	
	//获取随机e的值
	static void get_e() {
		Random random = new Random();
		e = random.nextInt(20)+11;
		if(e>n2||e==n2){
			get_e();
		}
	}
	
	//获取d的值
	static void get_d() {
		d = euclid(e,n2);
		if(d<0) {
			get_e();
			get_d();
		}
	}
	
	//整型转字符型
	static char intTochar(int t) {
		char c = (char) t;
		return c;
	}
	
	//字符型转整型
	static int charToint(char t ) {
		int i = t;
		return i;
	}
	
	//加密函数，返回加密后的密文
	static String encrypt(String words){
		char[] temp = words.toCharArray();
		int[] tempint = new int[words.length()];
		for(int i =0;i<temp.length;i++) {
			if(charToint(temp[i])<97) {
				temp[i] = intTochar(charToint(temp[i])+32);
			}
			int g = charToint(temp[i])-97;
			tempint[i] = g;
		}
		
		ArrayList<Integer> a = new ArrayList<Integer>();
		int t = tempint[0];
		int t2 = 1;
		while(t2<tempint.length-1||t2 == tempint.length-1) {
			while (t*26+tempint[t2]<n-1) {
				t = t*26+tempint[t2];
				t2++;
				if(t2>tempint.length-1)
					break;
				}
			a.add(powMod(t,e,n ));
			if(t2<tempint.length) {
				t = tempint[t2];
				t2++;
				if(t2 == tempint.length) {
				a.add(powMod(t,e,n));
			}
		}	
	}
			StringBuffer sb = new StringBuffer();
			ArrayList<Integer> tempList = new ArrayList<Integer>();
			for(int i = 0;i<a.size();i++) {
				tempList = binary(a.get(i),26);
				for(int j = tempList.size()-1;j>-1;j--) {
					sb.append(intTochar(tempList.get(j)+97));
				}
			}
		String result = sb.toString();
		return result;
	}
	
	//解密函数，返回解密后的明文
	static String decode(String encoded) {
		char[] temp = encoded.toCharArray();
		int[] tempint = new int[encoded.length()];
		for(int i =0;i<temp.length;i++) {
			if(charToint(temp[i])<97) {
				temp[i] = intTochar(charToint(temp[i])+32);
			}
			int g = charToint(temp[i])-97;
			tempint[i] = g;
		}
		
		ArrayList<Integer> a = new ArrayList<Integer>();
		int t = tempint[0];
		int t2 = 1;
		while(t2<tempint.length-1||t2 == tempint.length-1) {
			while (t*26+tempint[t2]<n-1) {
				t = t*26+tempint[t2];
				t2++;
				if(t2>tempint.length-1)
					break;
				}
			a.add(powMod(t,d,n ));
			if(t2<tempint.length) {
				t = tempint[t2];
				t2++;
				if(t2 == tempint.length) {
				a.add(powMod(t,d,n));
			}
		}	
	}
			StringBuffer sb = new StringBuffer();
			ArrayList<Integer> tempList = new ArrayList<Integer>();
			for(int i = 0;i<a.size();i++) {
				tempList = binary(a.get(i),26);
				for(int j = tempList.size()-1;j>-1;j--) {
					sb.append(intTochar(tempList.get(j)+97));
				}
			}
		String result = sb.toString();
		return result;
	}
	
	
	
	public static void main(String []args) {
		MyFrame mf = new MyFrame("RSA公钥密码系统");
		mf.setVisible(true);
		mf.setResizable(true);
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
	
	static class MyFrame extends JFrame{
	JButton encoder;
	JButton decoder;
	JTextField cin;
	JTextField publicKey;
	JTextField privateWords;
	JTextField publicWords;
	
	MyFrame(String string){
		super(string);
		init();
		encodeListener el =new encodeListener();
		decoderListener dl = new decoderListener();
		encoder.addActionListener(el);
		decoder.addActionListener(dl);
	}
	void init() {
		getContentPane().setLayout(new FlowLayout());
		setSize(800,300);
		setLocation(400,100);
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		p1.add(new JLabel("明文："));
		cin = new JTextField(65);
		p1.add(cin);
		p2.add(new JLabel("公钥e："));
		publicKey = new JTextField(20);
		p2.add(publicKey);
		p3.add(new JLabel("加密后："));
		privateWords = new JTextField(55);
		p3.add(privateWords);
		
		p4.add(new JLabel("解密后："));
		publicWords = new JTextField(55);
		p4.add(publicWords);
		encoder = new JButton("加密发送");
		p5.add(encoder);
		decoder = new JButton("解密");
		p5.add(decoder);
		getContentPane().add(p1);
		getContentPane().add(p2);
		getContentPane().add(p3);
		getContentPane().add(p4);
		getContentPane().add(p5);
	}
	
	//点击加密按钮
	class encodeListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e1) {
			get_pq();
			get_e();
			get_d();
			publicKey.setText(String.valueOf(e));
			String s = "";
			s = encrypt(cin.getText()); 
			privateWords.setText(s);
		}
	}
	
	//点击解密按钮
	class decoderListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String s = "";
			s = decode(privateWords.getText());
			publicWords.setText(s);
		}
	}
  }
}