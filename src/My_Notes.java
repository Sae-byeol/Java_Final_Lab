import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class My_Notes extends JFrame {
	JFrame frame=this;
	JButton add, edit, delete;
	String movietitle[]=new String[10];
	JList <String> moviel=new JList<>(movietitle);
	String booktitle[]=new String[10];
	JList <String> bookl=new JList<>(booktitle);
	String totaltitle[]=new String[20];
	JList <String> totall=new JList<>(totaltitle);
	String search[]=new String[20];
	JList <String> searchl=new JList<>(search);
	JPanel searchPanel,searchInputPanel,searchListPanel,center;
	JComboBox <String>searchcb; JTextField searchtf;JButton searchbtn;
	int searchmode=1;//�⺻������ "����"�� �˻� �ɼ����� ���õǾ� �ִ� ����
	MyDialog dialog;
	int editmode=0;
	int mi=0;
	int bi=0;
	int ti=0;
	Item it,mt,bt;
	Movie m;
	Book b;
	Image img;
	MyPosterPanel posterPanel;
	ItemCollections ic=new ItemCollections();
	JPanel imagePanel,charPanel;
	MyPanel graphicPanel;
	JPanel parentgraphicPanel;
	JLabel defaultimage;
	JLabel imageLabel;
	JTextArea story,review;
	JLabel movietitlech, moviedirect, moviegenre, movieactor, moviegrade,movieyear, moviestar,mtitle,mdirector, mactor,mgenre,mgrade,myear,mstar;
	JTextField tf1,tf2,tf3,tf4;
	JComboBox <String> cb1,cb2,cb3;
	JSlider sl;
	JTextArea ta1,ta2;
	JRadioButton rb1,rb2;
	String title1;//���� ���õ� ����Ʈ�� ��(��ǰ�� ����)
	
	
	//����Ʈ�� ���� ���õǾ��� ���� �̺�Ʈ
	class MovieSelectListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			it=new Item();
			JList<String> l=(JList)e.getSource();
			//����Ʈ���� ���� ���õ� ���� �ε���
			int index=l.getSelectedIndex();
			//���õ� ���� ���� ���
			if (index != -1) {
				//���õ� ���� title1
				title1=l.getSelectedValue();
				if (title1==null) {
					System.out.println("������ ���� �����ϴ�.");
				}
				//title1�� null�� �ƴ� ���
				else {
					for (int i=0;i<ic.v.size();i++) {
						//���Ϳ��� �ش� ������ ã�� ���
						if (title1.equals(ic.v.get(i).title)) {
							//������ �ش� ��ü�� �����۰�ü it�� ����
							it=ic.v.get(i);
						}
					}
					//������ �ִ� �ǳ��� �ڽ� ������Ʈ �� �����
					parentgraphicPanel.removeAll();
					ImageIcon icon=new ImageIcon(it.poster);
					img=icon.getImage();
					//MyPosterPanel ��ü ���� �����(�̹����� �ö� �ǳ�)
					posterPanel=new MyPosterPanel();
					posterPanel.setPreferredSize(new Dimension(150,150));
					//posterPanel�� parentgraphicPanel�� �ø���
					parentgraphicPanel.add(posterPanel);
					
					/*JLabel booktitle=new JLabel("����");JLabel bookauthor=new JLabel("����");
					JLabel bookcompany=new JLabel("���ǻ�");JLabel bookyear=new JLabel("���ǳ⵵");
					JLabel bookstar=new JLabel("�u��");*/
					
					//����Ʈ���� ������ ��ü�� ��ȭ���� å���� ���� (Movie �� Book Ŭ�������� mode ���� �ٸ��� �־���)
					if (it.mode==1) {
						//��ȭ��� Movie��ü�� �����ϰ�
						m=(Movie)it;
						//�󼼺��� ȭ�鿡 �� ������ ���
						movietitlech.setText("����");moviedirect.setText("����");
						movieactor.setText("���");moviegenre.setText("�帣");
						moviegrade.setText("���");movieyear.setText("�����⵵");
						moviestar.setText("����");
						mtitle.setText(it.title);mdirector.setText(m.director);
						mactor.setText(m.actor);mgenre.setText(m.genre);
						mgrade.setText(m.grade);myear.setText(m.year);
						mstar.setText(Integer.toString(m.star));
						
						
					}
					else if(it.mode==2) {
						//å�̶�� Book ��ü�� �����ϰ� 
						b=(Book)it;
						//�󼼺��� ȭ�鿡 �� ������ ���
						movietitlech.setText("����");mtitle.setText(b.title);
						moviedirect.setText("����");mdirector.setText(b.author);
						movieactor.setText("���ǻ�");mactor.setText(b.company);
						charPanel.remove(moviegenre);charPanel.remove(moviegrade);
						charPanel.remove(mgenre);charPanel.remove(mgrade);
						movieyear.setText("���ǳ⵵");myear.setText(b.year);
						moviestar.setText("����");mstar.setText(Integer.toString(b.star));
						
					}
					//�ٰŸ��� ������ ���
					story.setText(it.story);
					review.setText(it.review);
				}
			}
		}	
	}
	//������ ����
	public My_Notes() {
		
		setTitle("JAVA 004 1911093 ������");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c=getContentPane();
		//����Ʈ���� ��ġ������ ����
		c.setLayout(new BorderLayout());
		
		//�޴������ 
		createMenu();
		
		//�� ����Ʈ�� �̺�Ʈ������ ���
		moviel.addListSelectionListener(new MovieSelectListener());
		bookl.addListSelectionListener(new MovieSelectListener());
		totall.addListSelectionListener(new MovieSelectListener());
		searchl.addListSelectionListener(new MovieSelectListener());
		
		//����� �ð� �˷��ִ� North �ǳ� ����� 
		JPanel north=new JPanel();
		north.setLayout(new GridLayout(0,2));
		JLabel mynotes=new JLabel("My Notes");
		mynotes.setHorizontalAlignment(SwingConstants.CENTER);
		mynotes.setForeground(Color.blue);
		mynotes.setFont(new Font("TimesRoman", Font.BOLD, 30));
		//MyLabel Ŭ���� ��ü�� clockl ����
		MyLabel clockl=new MyLabel();
		clockl.setHorizontalAlignment(SwingConstants.RIGHT);
		//������ �󺧰� clockl ��ü�� north�ǳڿ� �ø���
		north.add(mynotes);north.add(clockl);
		//north�ǳ��� ����Ʈ���� ���ʿ� ��ġ
		c.add(north,BorderLayout.NORTH);
		
		
		// ���� �ǳ� �����
		//createTabbedPane() �Լ� ȣ��
		JTabbedPane pane=createTabbedPane();
		pane.setLocation(0,50);
		pane.setPreferredSize(new Dimension(200,400));
		//������ ���� ��ü�� ����Ʈ���� ���ʿ� ��ġ
		add(pane,BorderLayout.WEST);
		
		
		// �̹����� �ٰŸ��� ���� CENTER �ǳ� �����
		center=new JPanel();
		//center�ǳ��� �󼼺��Ⱑ ���ε��� createTitleBorder �Լ� ���
		Border resultBorder=BorderFactory.createTitledBorder("�󼼺���");
		center.setBorder(resultBorder);
		//centerPanel�� ��ġ������ ����
		center.setLayout(new BorderLayout());
		
		//center ���� �������ǳ�, �̹����ǳ�, �ٰŸ��ǳ� ,�������ǳ� �ø���
		//�������� ����� charPanel ����
		charPanel=new JPanel();
		charPanel.setLayout(new GridLayout(0,2));
		//charPanel�� �� ���� �� �ø���
		movietitlech=new JLabel();moviedirect=new JLabel();
		movieactor=new JLabel(); moviegenre=new JLabel();
		moviegrade=new JLabel(); movieyear=new JLabel();
		moviestar=new JLabel();
		mtitle=new JLabel();mdirector=new JLabel();
		mactor=new JLabel();mgenre=new JLabel();
		mgrade=new JLabel();myear=new JLabel();
		mstar=new JLabel();
		
		charPanel.add(movietitlech);charPanel.add(mtitle);
		charPanel.add(moviedirect);charPanel.add(mdirector);
		charPanel.add(movieactor);charPanel.add(mactor);
		charPanel.add(moviegenre);charPanel.add(mgenre);
		charPanel.add(moviegrade);charPanel.add(mgrade);
		charPanel.add(movieyear);charPanel.add(myear);
		charPanel.add(moviestar);charPanel.add(mstar);
		
		//�̹����� �ø� imagePanel ����
		imagePanel=new JPanel();
		imageLabel=new JLabel();
		imageLabel.setText("�̹��� ����");
		//parentgraphicPanel ����
		parentgraphicPanel=new JPanel();
		//MyPanel ��ü �����ϱ�(�׷��� �׷����� �κ�)
		graphicPanel=new MyPanel();
		graphicPanel.setPreferredSize(new Dimension(150,150));
		//graphicPanel�� imageLabel�÷� �ʱ� �̹��� ȭ�� ����
		graphicPanel.add(imageLabel);
		//graphicPanel�� parentgraphicPanel�� �ø���
		parentgraphicPanel.add(graphicPanel);
		//imagePanel�� parentgraphicPanel�� charPanel�� ������ �ø���
		imagePanel.setLayout(new GridLayout(0,2));
		imagePanel.add(parentgraphicPanel);
		imagePanel.add(charPanel);
		
		//�ٰŸ��� ���� result1�ǳ� ����
		JPanel result1=new JPanel();
		//�׵θ� ����
		Border result1Border=BorderFactory.createTitledBorder("�ٰŸ�");
		result1.setBorder(result1Border);
		//JTextArea �ΰ� ����
		story=new JTextArea(5,20);review=new JTextArea(5,20);
		//story���� ��ũ�ѹٸ� �ٿ� result1 �ǳڿ� �ø���
		result1.add(new JScrollPane(story));
		//�������� ���� result2�ǳ� �����ϰ� �׵θ� ����� review ������Ʈ �ø���
		JPanel result2=new JPanel();
		Border result2Border=BorderFactory.createTitledBorder("������");
		result2.setBorder(result2Border);
		result2.add(review);
		
		//center �ǳڿ� �󼼺��⿡ �� ���� �� �ø��� ����Ʈ���� center�� ��ġ
		center.add(imagePanel,BorderLayout.NORTH);
		center.add(result1,BorderLayout.CENTER);
		center.add(result2,BorderLayout.SOUTH);		
		add(center,BorderLayout.CENTER);
		
		//�Է� ���̾�α� ����
		dialog=new MyDialog(this,"�Է�");
		
		//��ư�� ���� South �ǳ�
		JPanel south=new JPanel(new GridLayout(0,3));
		//��ư ���� �� �� ��ư���� �ش� �̺�Ʈ ������ ���
		add=new JButton("�߰�");edit=new JButton("����");delete=new JButton("����");
		add.addActionListener(new myaddActionListener());
		edit.addActionListener(new myeditActionListener());
		delete.addActionListener(new deleteActionListener());
		
		//'�߰�' ��ư�� ���� addPanel �����ϰ� add ��ư �ø���
		JPanel addPanel=new JPanel();
		addPanel.add(add);
		//'����'��ư�� ���� editPanel �����ϰ� edit ��ư �ø���
		FlowLayout f=new FlowLayout();
		//��ư�� �ǳ��� �����ʿ� ������ ����
		f.setAlignment(FlowLayout.RIGHT);
		JPanel editPanel=new JPanel(f);
		editPanel.add(edit);
		//'����'��ư�� ���� deletPanel �����ϰ� delete ��ư �ø���
		FlowLayout fl=new FlowLayout();
		//��ư�� �ǳ��� ���ʿ� ������ ����
		fl.setAlignment(FlowLayout.LEFT);
		JPanel deletePanel=new JPanel(fl);
		deletePanel.add(delete);
		//south �ǳڿ� �� ��ư�� �ִ� �ǳ� �ø���
		south.add(addPanel);
		south.add(editPanel);
		south.add(deletePanel);
		//south �ǳ��� ����Ʈ���� ���ʿ� ��ġ
		add(south, BorderLayout.SOUTH);
		
		//�������� ũ�� ���� �� ȭ�鿡 ���
		setSize(700,600);
		setVisible(true);
	}
	//������ ��ü�� �̹����� ������ MyPosterPanel Ŭ���� ����
	class MyPosterPanel extends JPanel{

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			//�̹��� ��ü�� �ǳ��� ũ�⿡ �°� ���
			g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),this);
		}
	}
	//������ ��ü�� ���� ��� �̹��� ������ ��Ÿ���� ���� MyPanel Ŭ���� ����
	class MyPanel extends JPanel{

		@Override
		protected void paintComponent(Graphics g) {
			//�� �׷��� x ǥ�� �����
			super.paintComponent(g);
			g.drawLine(0,0, 140,140);
			g.drawLine(140, 0, 0, 140);
		}
	}
	
	//������ ����� ���� createTabbedPane�Լ� ����
	JTabbedPane createTabbedPane() {
		//JTabbedPane ��ü �����ϱ�
		JTabbedPane pane=new JTabbedPane(JTabbedPane.TOP);
		//�ǿ� �� ����Ʈ �ޱ�
		pane.addTab("��ü", totall);
		pane.addTab("��ȭ", moviel);
		pane.addTab("����", bookl);
		//�˻� �ǿ��� ����Ʈ ���� �� ������Ʈ���� ���� �ִ� searchPanel �ʿ�
		searchInputPanel=new JPanel(new FlowLayout());
		//�޺��ڽ��� �ؽ�Ʈ�ʵ�, ��ư �����Ͽ� searchInputPanel�� �ø���
		String searchoption[]= {"����","����"};
		searchcb=new JComboBox<>(searchoption);
		searchtf=new JTextField(5);
		searchbtn=new JButton("�˻�");
		searchbtn.addActionListener(new MySearchListener());
		searchInputPanel.add(searchcb);searchInputPanel.add(searchtf);searchInputPanel.add(searchbtn);
		//�޺��ڽ��� �̺�Ʈ ������
		searchcb.addActionListener(new ActionListener(){
			//�޺��ڽ� ���� ���� searchmode�� ���� �ֱ�
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox <String>cb=(JComboBox<String>)e.getSource();
				int index=cb.getSelectedIndex();
				if (index==0) {
					//������ ������ ���
					searchmode=1;
				}
				else if (index==1)
					//������ ������ ���
					searchmode=2;
			}
			
			
		});
		//searchPanel �����Ͽ� ������Ʈ�� �ø� searchInputPanel�� ����Ʈ �ø���
		searchPanel=new JPanel(new BorderLayout());
		searchPanel.add(searchInputPanel,BorderLayout.NORTH);
		searchPanel.add(searchl,BorderLayout.CENTER);
		//�˻� �ǿ��� searchPanel �ø���
		pane.addTab("�˻�",searchPanel);
		//�ϼ��� ������ ����
		return pane;
		
	}
	
	//�˻� ��ư ������ �� �̺�Ʈ -> ����Ʈ�� ����� search�迭�� �� �߰�
	class MySearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//searchl ����Ʈ�� �ö� �迭 search ����
			search=new String[20];
			int n=0;
			int nn=0;
			//�˻� �ɼ�(�޺��ڽ� ��)�� ���� �з�
			if (searchmode==1) {
				//�������� �˻�
				for (int i=0;i<ic.v.size();i++) {
					//totaltitle �迭�� ���������� Ž��
					if (totaltitle[i].indexOf(searchtf.getText())>-1) {
						//�˻�� �����ϴ� ��� search�迭�� �ش� totaltitle�迭�� �� ���� 
						search[n]=totaltitle[i];
						n++;
					}
				}
				//for�� �� ���Ҵµ��� n�� 0 (�ش� ���ڿ� �����ϴ� �� �ϳ��� ���� ���)
				if (n==0) {
					JOptionPane.showMessageDialog(null,"["+searchtf.getText()+"] �˻� ����� �����ϴ�.","Message", JOptionPane.WARNING_MESSAGE);
				}
				//�˻� �Ϸ�Ǹ� search�迭�� searchl ���ΰ�ħ
				else {
					searchl.setListData(search);
					
				}
			}
			else if(searchmode==2) {
				//�������� �˻� (�Է°� �̻��� ������ ������ ������ ã�� search�迭�� �߰�)
				for (int i=0;i<ic.v.size();i++) {
					if (Integer.parseInt(searchtf.getText())<= ic.v.get(i).star) {
						//���Ϳ��� �Է°� �̻��� ��ǰ�� ã�� ��� �ش� ���Ͱ��� title�� search�迭�� ����
						search[nn]=ic.v.get(i).title;
						nn++;
					}
				}
				//�˻������ ���� ���
				if (nn==0)
					JOptionPane.showMessageDialog(null, "�˻� ����� �����ϴ�.","Message", JOptionPane.WARNING_MESSAGE);
				//�˻� �Ϸ� �� search�迭�� searchl ���� ��ħ
				searchl.setListData(search);
			}
		}
		
	}
	//���� �ð� ������ ���� ���� MyLabel Ŭ���� ����
	class MyLabel extends JLabel implements Runnable {
		private Thread timerThread = null;
		public MyLabel() {
			setText(makeClockText());
			setFont(new Font("TimesRoman", Font.ITALIC, 10));
			
			timerThread = new Thread(MyLabel.this);
			timerThread.start();
		}
		//Calender Ŭ���� �̿��Ͽ� �ð� ǥ��
		public String makeClockText() {
			Calendar c = Calendar.getInstance();
			int year=c.get(Calendar.YEAR);
			int month=c.get(Calendar.MONTH);
			int day=c.get(Calendar.DATE);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int min = c.get(Calendar.MINUTE);
			int second = c.get(Calendar.SECOND);
			
			String clockText = Integer.toString(year);
			clockText = clockText.concat("�� ");
			clockText = clockText.concat(Integer.toString(month));
			clockText = clockText.concat("�� ");
			clockText = clockText.concat(Integer.toString(day));
			clockText = clockText.concat("�� ");
			clockText = clockText.concat(Integer.toString(hour));
			clockText = clockText.concat(":");
			clockText = clockText.concat(Integer.toString(min));
			clockText = clockText.concat(":");
			clockText = clockText.concat(Integer.toString(second));
			//�ð��� ��Ÿ���� ���ڿ��� ����
			return clockText;
		}
		@Override
		public void run() {
			while(true) {
				//1�ʸ��� �ð� �ٲ�� �������ֱ�
				try {
					Thread.sleep(1000);
				}
				catch(InterruptedException e){return;}
				setText(makeClockText());
			}
		}
	}
	//�Է��� ���� ���̾�α� MyDialog Ŭ���� ����
	class MyDialog extends JDialog{
		
		JLabel [] l=new JLabel[10];
		String genre[]= {"���","�׼�","�ڹ̵�"};
		String grade[]= {"��ü","15��","12��","û��"};
		String year[]= {"2015","2016","2017","2018","2019","2020"};
		JButton open,ok;
		JPanel resultPanel;
		Border resultBorder;
		
		public MyDialog(JFrame frame,String title) {
			super (frame,title);
			//���̾�α��� ��ġ������ ����
			setLayout(new BorderLayout());
			
			//������ư�� ���� �ǳ�
			JPanel rbPanel=new JPanel();
			//��ư �����ϰ� �׷쿡 �߰�, �� ��ư���� �̺�Ʈ������ ����ϱ�
			ButtonGroup g=new ButtonGroup();
			rb1=new JRadioButton("Movie",true);rb2=new JRadioButton("Book");
			rb1.addItemListener(new MyItemListener());rb2.addItemListener(new MyItemListener());
			g.add(rb1); g.add(rb2);
			//rbPanel�� ��ư �ø���
			rbPanel.add(rb1);rbPanel.add(rb2);
			//rbPanel�� ���̾�α��� ���ʿ� ��ġ
			add(rbPanel,BorderLayout.NORTH);
			
			//���� �Է��� ���� �ǳ�
			//�迭�� ����Ͽ� �� �����ϱ�
			String names[]= {"����","����","���","�帣","���","�����⵵","������","����","�ٰŸ�","������"};
			for (int i=0;i<names.length;i++) {
				l[i]=new JLabel(names[i]);
			}
			//�Է��� ���� �ʿ��� ������Ʈ�� ����
			tf1=new JTextField(10);tf2=new JTextField(10);tf3=new JTextField(10);tf4=new JTextField(10);
			cb1=new JComboBox<String>(genre); cb2=new JComboBox<String>(grade); cb3=new JComboBox<String>(year);
			sl=new JSlider(JSlider.HORIZONTAL,0,10,5);
			sl.setPaintLabels(true);sl.setPaintTrack(true);sl.setMajorTickSpacing(1);
			ta1=new JTextArea();ta2=new JTextArea();
			open=new JButton(); open.setText("�ҷ�����");
			//'�ҷ�����' ��ư�� �̺�Ʈ������ ���
			open.addActionListener(new ActionListener() {
				JFileChooser ch=new JFileChooser();
				@Override
				public void actionPerformed(ActionEvent e) {
					//���� ���� ���̾�α� ���
					int ret=ch.showOpenDialog(null);
					//������ �������� ���� ���
					if (ret!=JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(null, "������ �������� �ʾҽ��ϴ�.","���",JOptionPane.WARNING_MESSAGE);
						return;
					}
					//������ ������ ��θ� �ؽ�Ʈ�ʵ忡 ���
					String filePath=ch.getSelectedFile().getPath();
					tf4.setText(filePath);
				}
			});
			//������ ������ ������Ʈ���� �ø��� ���� resultPanel ����
			resultPanel=new JPanel();
			//'��ȭ ����'�� �׵θ� �����
			Border resultBorder=BorderFactory.createTitledBorder("��ȭ ����");
			resultPanel.setBorder(resultBorder);
			//resultPanel�ǹ�ġ������ ����
			GridLayout grid=new GridLayout(0,2);
			grid.setVgap(5);
			resultPanel.setLayout(grid);
			//������Ʈ�� �ø���
			resultPanel.add(l[0]); resultPanel.add(tf1);
			resultPanel.add(l[1]); resultPanel.add(tf2);
			resultPanel.add(l[2]); resultPanel.add(tf3);
			resultPanel.add(l[3]); resultPanel.add(cb1);
			resultPanel.add(l[4]); resultPanel.add(cb2);
			resultPanel.add(l[5]); resultPanel.add(cb3);
			JPanel openPanel=new JPanel();
			openPanel.setLayout(new GridLayout(0,2));
			openPanel.add(tf4); openPanel.add(open);
			resultPanel.add(l[6]); resultPanel.add(openPanel);
			resultPanel.add(l[7]); resultPanel.add(sl);
			resultPanel.add(l[8]); resultPanel.add(ta1);
			resultPanel.add(l[9]); resultPanel.add(ta2);
			//resultPanel�� ���̾�α��� center�� ��ġ
			add(resultPanel,BorderLayout.CENTER);
			
			//'OK'��ư�� ���� okPanel ����
			JPanel okPanel=new JPanel();
			okPanel.setLayout(new FlowLayout());
			//��ư �����Ͽ� �ǳڿ� �ø���
			ok=new JButton();
			ok.setText("OK");
			okPanel.add(ok);
			//okPanel�� ���̾�α��� ���ʿ� ��ġ
			add(okPanel,BorderLayout.SOUTH);
			
			//ok ��ư�� �̺�Ʈ������ ���
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//���� ���̾�α��� ��쿡�� ��ü ������ �ƴϰ� ������
					if (editmode==1) {
						for (int i=0;i<ic.v.size();i++) {
							if (ic.v.get(i).title.equals(title1)) {
								//������ ��ü�� ����(title1)�� ���Ϳ��� ã�� ���
								Item t=ic.v.get(i);
								if (t.mode==1) {
									//��ȭ���
									Movie m=new Movie(tf1.getText(),tf2.getText(),tf3.getText(),genre[cb1.getSelectedIndex()],grade[cb2.getSelectedIndex()],
											year[cb3.getSelectedIndex()],tf4.getText(),sl.getValue(),ta1.getText(),ta2.getText());
									//��ȭ ��ü �����Ͽ� Item ��ü mt�� ����
									mt=m;
									ic.v.set(i, mt);//���� ������ ���� ����
									
									//������ ���ͷ� �迭�� ����
									for (int j=0;j<movietitle.length;j++) {
										if (movietitle[j].equals(title1)) {
											//������ movietitle�迭���� ����Ʈ���� ������ ������ ã�� ���-> �������ֱ�
											movietitle[j]=mt.title;
											break;
										}
									}
									//totatitle�迭 ����
									for (int n=0;n<totaltitle.length;n++) {
										if (totaltitle[n].equals(title1)) {
											//������ totaltitle�迭���� ����Ʈ���� ������ ������ ã�� ���-> �������ֱ�
											totaltitle[n]=mt.title;
											break;
										}
									}
									//������ �迭�� ����Ʈ ���ΰ�ħ
									moviel.setListData(movietitle);
									totall.setListData(totaltitle);
									//������ �������� �󼼺��� ���� ����
									mtitle.setText(mt.title);mdirector.setText(m.director);
									mactor.setText(m.actor);mgenre.setText(m.genre);
									mgrade.setText(m.grade);myear.setText(m.year);
									mstar.setText(Integer.toString(mt.star));
									
								}
								//å�� ���
								else if(t.mode==2) {
									//Book��ü ���� �����Ͽ� Item ��ü bt�� ����
									Book b=new Book(tf1.getText(),tf2.getText(),tf3.getText(),
											year[cb3.getSelectedIndex()],tf4.getText(),sl.getValue(),ta1.getText(),ta2.getText());
									bt=b;
									//���� �����ϱ�
									ic.v.set(i, bt);
									for (int j=0;j<booktitle.length;j++) {
										if (booktitle[j].equals(title1)) {
											//������ booktitle�迭���� ����Ʈ���� ������ ������ ã�� ���-> �������ֱ�
											booktitle[j]=bt.title;
											break;
										}
									}
									//totatitle�迭 ����
									for (int n=0;n<totaltitle.length;n++) {
										if (totaltitle[n].equals(title1)) {
											//������ totaltitle�迭���� ����Ʈ���� ������ ������ ã�� ���-> �������ֱ�
											totaltitle[n]=bt.title;
											break;
										}
									}
									//������ �迭�� ����Ʈ ���ΰ�ħ
									bookl.setListData(booktitle);
									totall.setListData(totaltitle);
									//�󼼺��� ���뵵 �����ϱ�
									mtitle.setText(bt.title);mdirector.setText(b.author);
									mactor.setText(b.company);
									myear.setText(b.year);
									mstar.setText(Integer.toString(bt.star));
								}
							}
							
						}
					}
					//'�߰�'�� ���
					else{
						//Movie���¶�� ��ȭ��ü �����ؼ� ���Ϳ� �߰� 
						if (rb1.isSelected()) {
							Movie m=new Movie(tf1.getText(),tf2.getText(),tf3.getText(),genre[cb1.getSelectedIndex()],grade[cb2.getSelectedIndex()],
									year[cb3.getSelectedIndex()],tf4.getText(),sl.getValue(),ta1.getText(),ta2.getText());
							Item it=m;
							//���Ϳ� �߰�
							ic.addItem(it);
							//��ü�� ���� movietitle, totaltitle�迭�� �߰��ϰ� ����Ʈ ���ΰ�ħ
							movietitle[mi]=it.title;
							mi++;
							moviel.setListData(movietitle);
							totaltitle[ti]=it.title;
							ti++;
							totall.setListData(totaltitle);
						}
						//book���¶�� å ��ü �����ؼ� ���Ϳ� �߰�
						else if (rb2.isSelected()) {
							Item it=new Book(tf1.getText(),tf2.getText(),tf3.getText(),
									year[cb3.getSelectedIndex()],tf4.getText(),sl.getValue(),ta1.getText(),ta2.getText());
							//���Ϳ� �߰�
							ic.addItem(it);
							//��ü�� ���� booktitle, totaltitle�迭�� �߰��ϰ� ����Ʈ ���ΰ�ħ
							booktitle[bi]=it.title;
							bi++;
							bookl.setListData(booktitle);
							totaltitle[ti]=it.title;
							ti++;
							totall.setListData(totaltitle);
							
						}
					}
					//���̾�α� â �ݱ�
					setVisible(false);	
				}
					
			});
			//���̾�α��� ũ��
			setSize(300,500);
		}
		//������ư�� �̺�Ʈ������ Ŭ���� ����
		class MyItemListener implements ItemListener{

			@Override
			public void itemStateChanged(ItemEvent e) {
				//�ƹ��͵� ���õ��� ���� ���
				if(e.getStateChange()==ItemEvent.DESELECTED) {
					return;
				}
				//��ư�� ���õ� ���
				//'Movie'�� ���õǾ��ٸ�
				if (rb1.isSelected()) {
					//���� resultPanel�� �ڽ�������Ʈ �� �����
					resultPanel.removeAll();
					//�׵θ� ����
					Border resultBorder=BorderFactory.createTitledBorder("��ȭ ����");
					resultPanel.setBorder(resultBorder);
					//������Ʈ �����ϰ� �ǳڿ� �ø���
					l[1].setText("����");
					l[2].setText("���");
					l[5].setText("�����⵵");
					l[6].setText("������");
					resultPanel.add(l[0]); resultPanel.add(tf1);
					resultPanel.add(l[1]); resultPanel.add(tf2);
					resultPanel.add(l[2]); resultPanel.add(tf3);
					resultPanel.add(l[3]); resultPanel.add(cb1);
					resultPanel.add(l[4]); resultPanel.add(cb2);
					resultPanel.add(l[5]); resultPanel.add(cb3);
					JPanel openPanel=new JPanel();
					openPanel.setLayout(new GridLayout(0,2));
					openPanel.add(tf4); openPanel.add(open);
					resultPanel.add(l[6]); resultPanel.add(openPanel);
					resultPanel.add(l[7]); resultPanel.add(sl);
					resultPanel.add(l[8]); resultPanel.add(ta1);
					resultPanel.add(l[9]); resultPanel.add(ta2);
					//���� �׸���
					revalidate();
				}
				//'Book'�� ���õǾ��ٸ�
				else if (rb2.isSelected()) {
					//�׵θ� ����
					Border resultBorder=BorderFactory.createTitledBorder("���� ����");
				    resultPanel.setBorder(resultBorder);
				    //'Movie'�ʹ� �ٸ� ������Ʈ ���� �� ����
					l[1].setText("����");
					l[2].setText("���ǻ�");
					resultPanel.remove(l[3]);resultPanel.remove(cb1);
					resultPanel.remove(l[4]);resultPanel.remove(cb2);
					l[5].setText("���� �⵵");
					l[6].setText("å�̹���");
					
				}
			}
			
		}
		
	}
	//�޴��� �����ϱ� ���� �Լ� ����
	private void createMenu() {
		//JMenuBar,JMenu, JMenuItem ���� 
		JMenuBar mb=new JMenuBar();
		JMenu fileMenu=new JMenu("����");
		JMenu helpMenu=new JMenu("����");
		
		JMenuItem openMenu=new JMenuItem("�ҷ�����");
		JMenuItem saveMenu=new JMenuItem("�����ϱ�");
		JMenuItem quitMenu=new JMenuItem("����");
		//'����' �޴��� �̺�Ʈ������ ���
		quitMenu.addActionListener(new ActionListener() {
			//�����ϱ�
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//'�ҷ�����'�޴��� ���� �̺�Ʈ������ ��� ->���� �ҷ�����
		openMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//���� ���� ���̾�α� ���
				JFileChooser openchooser=new JFileChooser();
				int ret=openchooser.showOpenDialog(null);
				if (ret==JFileChooser.APPROVE_OPTION) {
					//������ ���� ��η� File ��ü ����
					File file=new File(openchooser.getSelectedFile().getPath());
					try {
						//��ü �Է� ��Ʈ�� ����ϱ�
						ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
						//�Է� ���� ��ü�� ���ͷ� �����ϱ�
						Vector <Item> v=(Vector<Item>)ois.readObject();
						ic.v=v;
						//�ҷ��� ������ title�� totaltitle, movietitle, booktitle �迭�� �����ϱ�
						for (int i=0;i<v.size();i++) {
							totaltitle[i]=v.get(i).title;
							//��ȭ���
							if (v.get(i).mode==1) {
								movietitle[i]=v.get(i).title;
							}
							//å�̶��
							if (v.get(i).mode==2) {
								booktitle[i]=v.get(i).title;
							}
							
						}
						//������� �迭�� ����Ʈ ���ΰ�ħ
						totall.setListData(totaltitle);
						moviel.setListData(movietitle);
						bookl.setListData(booktitle);
						//��Ʈ�� �ݱ�
						ois.close();
						
					} catch (IOException e1) {
						System.out.println("���� ���� ����");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
			
		});
		//'�����ϱ�'�� �̺�Ʈ������ ���->���� �����ϱ�
		saveMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//���� ���� ���̾�α� ���
				JFileChooser savechooser=new JFileChooser();
				int ret=savechooser.showSaveDialog(null);
				if (ret==JFileChooser.APPROVE_OPTION) {
					//������ ������ ��η� File ��ü ����
					File file=new File(savechooser.getSelectedFile().getPath());
					try {
						//���� ��� ��Ʈ�� �����Ͽ� ������ ���Ϸ� ���͸� ����ϱ�
						ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
						oos.writeObject(ic.v);
						oos.close();//��Ʈ�� �ݱ�
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("���� ���� ����");
					}
				}
				
			}
			
		});
		//fileMenu�� �� �޴� ������ �ø���
		fileMenu.add(openMenu); fileMenu.add(saveMenu);fileMenu.addSeparator();fileMenu.add(quitMenu);
		//'�ý�������' �޴� ������ ���� �� �̺�Ʈ������ ���
		JMenuItem systemMenu=new JMenuItem("�ý��� ����");
		helpMenu.add(systemMenu);
		systemMenu.addActionListener(new ActionListener() {
			//���̾�α� ����
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"MyNotes �ý��� v 1.0�Դϴ�.","Message", JOptionPane.WARNING_MESSAGE);
			}
			
		});
		//�޴��ٿ� �޴��ø��� �޴��� ���̱�
		mb.add(fileMenu);
		mb.add(helpMenu);
		setJMenuBar(mb);
	}
	//���� ��ư ���� �� �̺�Ʈ ������
	class myeditActionListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//'����Ʈ���� ������' ��ü�� �Է� ���̾�α� ����(���� ���� �״��)
			for (int i=0;i<ic.v.size();i++) {
				if (ic.v.get(i).title.equals(title1)) {
					//���Ϳ��� �ش� ��ü�� ã�� ���
					if (ic.v.get(i).mode==1) {
						//��ȭ�� ���
						//�ش� ��ü�� �������� �Է� ���̾�α��� ���� ä���
						rb1.setSelected(true);
						Movie m=(Movie)ic.v.get(i);
						tf1.setText(m.title);tf2.setText(m.director);tf3.setText(m.actor);
						cb1.setSelectedItem(m.genre);cb2.setSelectedItem(m.grade);cb3.setSelectedItem(m.year);
						sl.setValue(m.star);
						ta1.setText(m.story);ta2.setText(m.review);
					}
					else if (ic.v.get(i).mode==2) {
						//å�ΰ��
						//�ش� ��ü�� �������� �Է� ���̾�α��� ���� ä���
						rb2.setSelected(true);
						Book b=(Book)ic.v.get(i);
						tf1.setText(b.title);tf2.setText(b.author);tf3.setText(b.company);
						cb3.setSelectedItem(b.year);
						sl.setValue(b.star);
						ta1.setText(b.story);ta2.setText(b.review);
					}
				}
				
			
			}
			//���̾�α� ���
			dialog.setVisible(true);
			//'����'���� ��Ÿ���� ���� ����
			editmode=1;
			
		}
		
	}
	//�߰� ��ư�� ������ ���� �̺�Ʈ ������
	class myaddActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//���� ���̾�α� �����ؼ� ����
			dialog=new MyDialog(frame,"�Է�");
			dialog.setVisible(true);
		}
			
	}
	//���� ��ư�� ������ ���� �̺�Ʈ ������
	class deleteActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Ȯ�� ���̾�α� ����
			int result=JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?","���� Ȯ��",JOptionPane.YES_NO_OPTION);
			String deletetitle="";
			Item it=new Item();
			if (result==JOptionPane.YES_OPTION) {
				//����ڰ� yes�� ������ ���
				//���Ϳ��� �ش� ��ü �����ϱ�
				for (int i=0;i<ic.v.size();i++) {
					if (ic.v.get(i).title.equals(title1)) {
						//���� ����Ʈ���� ���õ� ��ü�� ���Ϳ��� ã�� ���
						//���� �� �ش� ��ü�� ���� title�� ������ �����صα�
						it=ic.v.get(i);
						deletetitle=ic.v.get(i).title;
						ic.v.remove(i);//�ش� ���� ���Ϳ��� ����
					}
				}
				
				int index=0;
				//������ ��ü�� ��ȭ���ٸ� 
				if (it.mode==1) {
					//������ ��ȭ�� movietitle�� ���° ���ҿ����� �˾Ƴ���
					index=Arrays.asList(movietitle).indexOf(deletetitle);
					//������ Ÿ��Ʋ�� movietitle���� ����
					int mlength=movietitle.length;
					for (int i=index;i<mlength-1;i++) {
						movietitle[i]=movietitle[i+1];
					}
					//������ �迭�� ����Ʈ ���ΰ�ħ
					moviel.setListData(movietitle);
				}
				//������ ��ü�� å�̾��ٸ�
				if (it.mode==2) {
					//������ ��ȭ�� booktitle�� ���° ���ҿ����� �˾Ƴ���
					index=Arrays.asList(booktitle).indexOf(deletetitle);
					//������ Ÿ��Ʋ�� movietitle���� ����
					int blength=booktitle.length;
					for (int i=index;i<blength-1;i++) {
						booktitle[i]=booktitle[i+1];
					}
					//������ �迭�� ����Ʈ ���ΰ�ħ
					bookl.setListData(booktitle);
				}
				//totaltitle���� �ش� ��ü ���� �� totall����
				index=Arrays.asList(totaltitle).indexOf(deletetitle);
				int tlength=totaltitle.length;
				for (int i=index;i<tlength-1;i++) {
					totaltitle[i]=totaltitle[i+1];
				}
				totall.setListData(totaltitle);
				
				//������ �� �󼼺��� ���� ���������� ������Ʈ ����
				movietitlech.setText("");moviedirect.setText("");
				movieactor.setText("");moviegenre.setText("");
				moviegrade.setText("");movieyear.setText("");
				moviestar.setText("");
				mtitle.setText("");mdirector.setText("");
				mactor.setText("");mgenre.setText("");
				mgrade.setText("");myear.setText("");
				mstar.setText("");
				story.setText("");
				review.setText("");
				parentgraphicPanel.removeAll();
				graphicPanel=new MyPanel();
				graphicPanel.setPreferredSize(new Dimension(150,150));
				
				graphicPanel.add(new JLabel("�̹��� ����"));
				parentgraphicPanel.add(graphicPanel);
				
			}
			
		}
		
	}
	public static void main(String[] args) throws IOException{
		//������ ȣ��
		new My_Notes();
	}

}
