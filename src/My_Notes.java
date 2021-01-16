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
	int searchmode=1;//기본적으로 "제목"이 검색 옵션으로 선택되어 있는 상태
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
	String title1;//현재 선택된 리스트의 값(작품의 제목)
	
	
	//리스트의 값이 선택되었을 때의 이벤트
	class MovieSelectListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			it=new Item();
			JList<String> l=(JList)e.getSource();
			//리스트에서 현재 선택된 값의 인덱스
			int index=l.getSelectedIndex();
			//선택된 값이 있을 경우
			if (index != -1) {
				//선택된 제목 title1
				title1=l.getSelectedValue();
				if (title1==null) {
					System.out.println("선택한 값이 없습니다.");
				}
				//title1이 null이 아닌 경우
				else {
					for (int i=0;i<ic.v.size();i++) {
						//벡터에서 해당 아이템 찾은 경우
						if (title1.equals(ic.v.get(i).title)) {
							//벡터의 해당 객체를 아이템객체 it에 저장
							it=ic.v.get(i);
						}
					}
					//기존에 있던 판넬의 자식 컴포넌트 다 지우기
					parentgraphicPanel.removeAll();
					ImageIcon icon=new ImageIcon(it.poster);
					img=icon.getImage();
					//MyPosterPanel 객체 새로 만들기(이미지가 올라갈 판넬)
					posterPanel=new MyPosterPanel();
					posterPanel.setPreferredSize(new Dimension(150,150));
					//posterPanel을 parentgraphicPanel에 올리기
					parentgraphicPanel.add(posterPanel);
					
					/*JLabel booktitle=new JLabel("제목");JLabel bookauthor=new JLabel("저자");
					JLabel bookcompany=new JLabel("출판사");JLabel bookyear=new JLabel("출판년도");
					JLabel bookstar=new JLabel("뱔점");*/
					
					//리스트에서 선택한 객체가 영화인지 책인지 구분 (Movie 와 Book 클래스에서 mode 값을 다르게 주었음)
					if (it.mode==1) {
						//영화라면 Movie객체를 생성하고
						m=(Movie)it;
						//상세보기 화면에 각 정보를 출력
						movietitlech.setText("제목");moviedirect.setText("감독");
						movieactor.setText("배우");moviegenre.setText("장르");
						moviegrade.setText("등급");movieyear.setText("개봉년도");
						moviestar.setText("별점");
						mtitle.setText(it.title);mdirector.setText(m.director);
						mactor.setText(m.actor);mgenre.setText(m.genre);
						mgrade.setText(m.grade);myear.setText(m.year);
						mstar.setText(Integer.toString(m.star));
						
						
					}
					else if(it.mode==2) {
						//책이라면 Book 객체를 생성하고 
						b=(Book)it;
						//상세보기 화면에 각 정보를 출력
						movietitlech.setText("제목");mtitle.setText(b.title);
						moviedirect.setText("저자");mdirector.setText(b.author);
						movieactor.setText("출판사");mactor.setText(b.company);
						charPanel.remove(moviegenre);charPanel.remove(moviegrade);
						charPanel.remove(mgenre);charPanel.remove(mgrade);
						movieyear.setText("출판년도");myear.setText(b.year);
						moviestar.setText("별점");mstar.setText(Integer.toString(b.star));
						
					}
					//줄거리와 감상평 출력
					story.setText(it.story);
					review.setText(it.review);
				}
			}
		}	
	}
	//생성자 정의
	public My_Notes() {
		
		setTitle("JAVA 004 1911093 오새별");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c=getContentPane();
		//컨텐트팬의 배치관리자 설정
		c.setLayout(new BorderLayout());
		
		//메뉴만들기 
		createMenu();
		
		//각 리스트에 이벤트리스너 등록
		moviel.addListSelectionListener(new MovieSelectListener());
		bookl.addListSelectionListener(new MovieSelectListener());
		totall.addListSelectionListener(new MovieSelectListener());
		searchl.addListSelectionListener(new MovieSelectListener());
		
		//제목과 시간 알려주는 North 판넬 만들기 
		JPanel north=new JPanel();
		north.setLayout(new GridLayout(0,2));
		JLabel mynotes=new JLabel("My Notes");
		mynotes.setHorizontalAlignment(SwingConstants.CENTER);
		mynotes.setForeground(Color.blue);
		mynotes.setFont(new Font("TimesRoman", Font.BOLD, 30));
		//MyLabel 클래스 객체인 clockl 생성
		MyLabel clockl=new MyLabel();
		clockl.setHorizontalAlignment(SwingConstants.RIGHT);
		//생성한 라벨과 clockl 객체를 north판넬에 올리기
		north.add(mynotes);north.add(clockl);
		//north판넬을 컨텐트팬의 북쪽에 배치
		c.add(north,BorderLayout.NORTH);
		
		
		// 탭팬 판넬 만들기
		//createTabbedPane() 함수 호출
		JTabbedPane pane=createTabbedPane();
		pane.setLocation(0,50);
		pane.setPreferredSize(new Dimension(200,400));
		//생성한 탭팬 객체를 컨텐트팬의 서쪽에 배치
		add(pane,BorderLayout.WEST);
		
		
		// 이미지와 줄거리를 위한 CENTER 판넬 만들기
		center=new JPanel();
		//center판넬을 상세보기가 감싸도록 createTitleBorder 함수 사용
		Border resultBorder=BorderFactory.createTitledBorder("상세보기");
		center.setBorder(resultBorder);
		//centerPanel의 배치관리자 설정
		center.setLayout(new BorderLayout());
		
		//center 위에 상세정보판넬, 이미지판넬, 줄거리판넬 ,감상평판넬 올리기
		//상세정보를 출력할 charPanel 생성
		charPanel=new JPanel();
		charPanel.setLayout(new GridLayout(0,2));
		//charPanel에 라벨 생성 및 올리기
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
		
		//이미지를 올릴 imagePanel 생성
		imagePanel=new JPanel();
		imageLabel=new JLabel();
		imageLabel.setText("이미지 없음");
		//parentgraphicPanel 생성
		parentgraphicPanel=new JPanel();
		//MyPanel 객체 생성하기(그래픽 그려지는 부분)
		graphicPanel=new MyPanel();
		graphicPanel.setPreferredSize(new Dimension(150,150));
		//graphicPanel에 imageLabel올려 초기 이미지 화면 구상
		graphicPanel.add(imageLabel);
		//graphicPanel을 parentgraphicPanel에 올리기
		parentgraphicPanel.add(graphicPanel);
		//imagePanel에 parentgraphicPanel과 charPanel을 나란히 올리기
		imagePanel.setLayout(new GridLayout(0,2));
		imagePanel.add(parentgraphicPanel);
		imagePanel.add(charPanel);
		
		//줄거리를 위한 result1판넬 생성
		JPanel result1=new JPanel();
		//테두리 생성
		Border result1Border=BorderFactory.createTitledBorder("줄거리");
		result1.setBorder(result1Border);
		//JTextArea 두개 생성
		story=new JTextArea(5,20);review=new JTextArea(5,20);
		//story에는 스크롤바를 붙여 result1 판넬에 올리기
		result1.add(new JScrollPane(story));
		//감상평을 위한 result2판넬 생성하고 테두리 만들어 review 컴포넌트 올리기
		JPanel result2=new JPanel();
		Border result2Border=BorderFactory.createTitledBorder("감상평");
		result2.setBorder(result2Border);
		result2.add(review);
		
		//center 판넬에 상세보기에 들어갈 내용 다 올리고 컨텐트팬의 center에 배치
		center.add(imagePanel,BorderLayout.NORTH);
		center.add(result1,BorderLayout.CENTER);
		center.add(result2,BorderLayout.SOUTH);		
		add(center,BorderLayout.CENTER);
		
		//입력 다이얼로그 생성
		dialog=new MyDialog(this,"입력");
		
		//버튼을 위한 South 판넬
		JPanel south=new JPanel(new GridLayout(0,3));
		//버튼 생성 및 각 버튼마다 해당 이벤트 리스너 등록
		add=new JButton("추가");edit=new JButton("수정");delete=new JButton("삭제");
		add.addActionListener(new myaddActionListener());
		edit.addActionListener(new myeditActionListener());
		delete.addActionListener(new deleteActionListener());
		
		//'추가' 버튼을 위한 addPanel 생성하고 add 버튼 올리기
		JPanel addPanel=new JPanel();
		addPanel.add(add);
		//'수정'버튼을 위한 editPanel 생성하고 edit 버튼 올리기
		FlowLayout f=new FlowLayout();
		//버튼이 판넬의 오른쪽에 오도록 설정
		f.setAlignment(FlowLayout.RIGHT);
		JPanel editPanel=new JPanel(f);
		editPanel.add(edit);
		//'삭제'버튼을 위한 deletPanel 생성하고 delete 버튼 올리기
		FlowLayout fl=new FlowLayout();
		//버튼이 판넬의 왼쪽에 오도록 설정
		fl.setAlignment(FlowLayout.LEFT);
		JPanel deletePanel=new JPanel(fl);
		deletePanel.add(delete);
		//south 판넬에 세 버튼이 있는 판넬 올리고
		south.add(addPanel);
		south.add(editPanel);
		south.add(deletePanel);
		//south 판넬을 컨텐트팬의 남쪽에 배치
		add(south, BorderLayout.SOUTH);
		
		//프레임의 크기 설정 및 화면에 출력
		setSize(700,600);
		setVisible(true);
	}
	//선택한 객체의 이미지를 보여줄 MyPosterPanel 클래스 생성
	class MyPosterPanel extends JPanel{

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			//이미지 객체를 판넬의 크기에 맞게 출력
			g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),this);
		}
	}
	//선택한 객체가 없을 경우 이미지 없음을 나타내기 위한 MyPanel 클래스 생성
	class MyPanel extends JPanel{

		@Override
		protected void paintComponent(Graphics g) {
			//선 그려서 x 표시 만들기
			super.paintComponent(g);
			g.drawLine(0,0, 140,140);
			g.drawLine(140, 0, 0, 140);
		}
	}
	
	//탭팬을 만들기 위한 createTabbedPane함수 생성
	JTabbedPane createTabbedPane() {
		//JTabbedPane 객체 생성하기
		JTabbedPane pane=new JTabbedPane(JTabbedPane.TOP);
		//탭에 각 리스트 달기
		pane.addTab("전체", totall);
		pane.addTab("영화", moviel);
		pane.addTab("도서", bookl);
		//검색 탭에는 리스트 위에 각 컴포넌트들을 갖고 있는 searchPanel 필요
		searchInputPanel=new JPanel(new FlowLayout());
		//콤보박스와 텍스트필드, 버튼 생성하여 searchInputPanel에 올리기
		String searchoption[]= {"제목","별점"};
		searchcb=new JComboBox<>(searchoption);
		searchtf=new JTextField(5);
		searchbtn=new JButton("검색");
		searchbtn.addActionListener(new MySearchListener());
		searchInputPanel.add(searchcb);searchInputPanel.add(searchtf);searchInputPanel.add(searchbtn);
		//콤보박스의 이벤트 리스너
		searchcb.addActionListener(new ActionListener(){
			//콤보박스 값에 따라 searchmode에 차이 주기
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox <String>cb=(JComboBox<String>)e.getSource();
				int index=cb.getSelectedIndex();
				if (index==0) {
					//제목을 선택한 경우
					searchmode=1;
				}
				else if (index==1)
					//별점을 선택한 경우
					searchmode=2;
			}
			
			
		});
		//searchPanel 생성하여 컴포넌트들 올린 searchInputPanel과 리스트 올리기
		searchPanel=new JPanel(new BorderLayout());
		searchPanel.add(searchInputPanel,BorderLayout.NORTH);
		searchPanel.add(searchl,BorderLayout.CENTER);
		//검색 탭에는 searchPanel 올리기
		pane.addTab("검색",searchPanel);
		//완성된 탭팬을 리턴
		return pane;
		
	}
	
	//검색 버튼 눌렀을 때 이벤트 -> 리스트와 연결된 search배열에 값 추가
	class MySearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//searchl 리스트에 올라갈 배열 search 생성
			search=new String[20];
			int n=0;
			int nn=0;
			//검색 옵션(콤보박스 값)에 따라 분류
			if (searchmode==1) {
				//제목으로 검색
				for (int i=0;i<ic.v.size();i++) {
					//totaltitle 배열을 순차적으로 탐색
					if (totaltitle[i].indexOf(searchtf.getText())>-1) {
						//검색어를 포함하는 경우 search배열에 해당 totaltitle배열의 값 대입 
						search[n]=totaltitle[i];
						n++;
					}
				}
				//for문 다 돌았는데도 n이 0 (해당 문자열 포함하는 게 하나도 없는 경우)
				if (n==0) {
					JOptionPane.showMessageDialog(null,"["+searchtf.getText()+"] 검색 결과가 없습니다.","Message", JOptionPane.WARNING_MESSAGE);
				}
				//검색 완료되면 search배열로 searchl 새로고침
				else {
					searchl.setListData(search);
					
				}
			}
			else if(searchmode==2) {
				//별점으로 검색 (입력값 이상의 별점을 가지는 제목을 찾아 search배열에 추가)
				for (int i=0;i<ic.v.size();i++) {
					if (Integer.parseInt(searchtf.getText())<= ic.v.get(i).star) {
						//벡터에서 입력값 이상의 작품을 찾은 경우 해당 벡터값의 title을 search배열에 대입
						search[nn]=ic.v.get(i).title;
						nn++;
					}
				}
				//검색결과가 없는 경우
				if (nn==0)
					JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.","Message", JOptionPane.WARNING_MESSAGE);
				//검색 완료 후 search배열로 searchl 새로 고침
				searchl.setListData(search);
			}
		}
		
	}
	//현재 시간 스레드 라벨을 위한 MyLabel 클래스 생성
	class MyLabel extends JLabel implements Runnable {
		private Thread timerThread = null;
		public MyLabel() {
			setText(makeClockText());
			setFont(new Font("TimesRoman", Font.ITALIC, 10));
			
			timerThread = new Thread(MyLabel.this);
			timerThread.start();
		}
		//Calender 클래스 이용하여 시간 표시
		public String makeClockText() {
			Calendar c = Calendar.getInstance();
			int year=c.get(Calendar.YEAR);
			int month=c.get(Calendar.MONTH);
			int day=c.get(Calendar.DATE);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int min = c.get(Calendar.MINUTE);
			int second = c.get(Calendar.SECOND);
			
			String clockText = Integer.toString(year);
			clockText = clockText.concat("년 ");
			clockText = clockText.concat(Integer.toString(month));
			clockText = clockText.concat("월 ");
			clockText = clockText.concat(Integer.toString(day));
			clockText = clockText.concat("일 ");
			clockText = clockText.concat(Integer.toString(hour));
			clockText = clockText.concat(":");
			clockText = clockText.concat(Integer.toString(min));
			clockText = clockText.concat(":");
			clockText = clockText.concat(Integer.toString(second));
			//시간을 나타내는 문자열을 리턴
			return clockText;
		}
		@Override
		public void run() {
			while(true) {
				//1초마다 시간 바뀌도록 설정해주기
				try {
					Thread.sleep(1000);
				}
				catch(InterruptedException e){return;}
				setText(makeClockText());
			}
		}
	}
	//입력을 위한 다이얼로그 MyDialog 클래스 생성
	class MyDialog extends JDialog{
		
		JLabel [] l=new JLabel[10];
		String genre[]= {"드라마","액션","코미디"};
		String grade[]= {"전체","15세","12세","청불"};
		String year[]= {"2015","2016","2017","2018","2019","2020"};
		JButton open,ok;
		JPanel resultPanel;
		Border resultBorder;
		
		public MyDialog(JFrame frame,String title) {
			super (frame,title);
			//다이얼로그의 배치관리자 설정
			setLayout(new BorderLayout());
			
			//라디오버튼을 위한 판넬
			JPanel rbPanel=new JPanel();
			//버튼 생성하고 그룹에 추가, 각 버튼마다 이벤트리스너 등록하기
			ButtonGroup g=new ButtonGroup();
			rb1=new JRadioButton("Movie",true);rb2=new JRadioButton("Book");
			rb1.addItemListener(new MyItemListener());rb2.addItemListener(new MyItemListener());
			g.add(rb1); g.add(rb2);
			//rbPanel에 버튼 올리기
			rbPanel.add(rb1);rbPanel.add(rb2);
			//rbPanel을 다이얼로그의 북쪽에 배치
			add(rbPanel,BorderLayout.NORTH);
			
			//정보 입력을 위한 판넬
			//배열을 사용하여 라벨 생성하기
			String names[]= {"제목","감독","배우","장르","등급","개봉년도","포스터","별점","줄거리","감상평"};
			for (int i=0;i<names.length;i++) {
				l[i]=new JLabel(names[i]);
			}
			//입력을 위해 필요한 컴포넌트들 생성
			tf1=new JTextField(10);tf2=new JTextField(10);tf3=new JTextField(10);tf4=new JTextField(10);
			cb1=new JComboBox<String>(genre); cb2=new JComboBox<String>(grade); cb3=new JComboBox<String>(year);
			sl=new JSlider(JSlider.HORIZONTAL,0,10,5);
			sl.setPaintLabels(true);sl.setPaintTrack(true);sl.setMajorTickSpacing(1);
			ta1=new JTextArea();ta2=new JTextArea();
			open=new JButton(); open.setText("불러오기");
			//'불러오기' 버튼에 이벤트리스너 등록
			open.addActionListener(new ActionListener() {
				JFileChooser ch=new JFileChooser();
				@Override
				public void actionPerformed(ActionEvent e) {
					//파일 오픈 다이얼로그 출력
					int ret=ch.showOpenDialog(null);
					//파일을 선택하지 않은 경우
					if (ret!=JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
						return;
					}
					//선택한 파일의 경로를 텍스트필드에 출력
					String filePath=ch.getSelectedFile().getPath();
					tf4.setText(filePath);
				}
			});
			//위에서 생성한 컴포넌트들을 올리기 위한 resultPanel 생성
			resultPanel=new JPanel();
			//'영화 정보'로 테두리 만들기
			Border resultBorder=BorderFactory.createTitledBorder("영화 정보");
			resultPanel.setBorder(resultBorder);
			//resultPanel의배치관리자 설정
			GridLayout grid=new GridLayout(0,2);
			grid.setVgap(5);
			resultPanel.setLayout(grid);
			//컴포넌트들 올리기
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
			//resultPanel을 다이얼로그의 center에 배치
			add(resultPanel,BorderLayout.CENTER);
			
			//'OK'버튼을 위한 okPanel 생성
			JPanel okPanel=new JPanel();
			okPanel.setLayout(new FlowLayout());
			//버튼 생성하여 판넬에 올리기
			ok=new JButton();
			ok.setText("OK");
			okPanel.add(ok);
			//okPanel을 다이얼로그의 남쪽에 배치
			add(okPanel,BorderLayout.SOUTH);
			
			//ok 버튼에 이벤트리스너 등록
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//수정 다이얼로그일 경우에는 객체 생성이 아니고 수정만
					if (editmode==1) {
						for (int i=0;i<ic.v.size();i++) {
							if (ic.v.get(i).title.equals(title1)) {
								//선택한 객체의 제목(title1)을 벡터에서 찾은 경우
								Item t=ic.v.get(i);
								if (t.mode==1) {
									//영화라면
									Movie m=new Movie(tf1.getText(),tf2.getText(),tf3.getText(),genre[cb1.getSelectedIndex()],grade[cb2.getSelectedIndex()],
											year[cb3.getSelectedIndex()],tf4.getText(),sl.getValue(),ta1.getText(),ta2.getText());
									//영화 객체 생성하여 Item 객체 mt에 대입
									mt=m;
									ic.v.set(i, mt);//기존 백터의 내용 수정
									
									//수정된 벡터로 배열도 수정
									for (int j=0;j<movietitle.length;j++) {
										if (movietitle[j].equals(title1)) {
											//기존의 movietitle배열에서 리스트에서 선택한 제목을 찾은 경우-> 수정해주기
											movietitle[j]=mt.title;
											break;
										}
									}
									//totatitle배열 수정
									for (int n=0;n<totaltitle.length;n++) {
										if (totaltitle[n].equals(title1)) {
											//기존의 totaltitle배열에서 리스트에서 선택한 제목을 찾은 경우-> 수정해주기
											totaltitle[n]=mt.title;
											break;
										}
									}
									//수정된 배열로 리스트 새로고침
									moviel.setListData(movietitle);
									totall.setListData(totaltitle);
									//수정된 내용으로 상세보기 내용 수정
									mtitle.setText(mt.title);mdirector.setText(m.director);
									mactor.setText(m.actor);mgenre.setText(m.genre);
									mgrade.setText(m.grade);myear.setText(m.year);
									mstar.setText(Integer.toString(mt.star));
									
								}
								//책인 경우
								else if(t.mode==2) {
									//Book객체 새로 생성하여 Item 객체 bt에 대입
									Book b=new Book(tf1.getText(),tf2.getText(),tf3.getText(),
											year[cb3.getSelectedIndex()],tf4.getText(),sl.getValue(),ta1.getText(),ta2.getText());
									bt=b;
									//벡터 수정하기
									ic.v.set(i, bt);
									for (int j=0;j<booktitle.length;j++) {
										if (booktitle[j].equals(title1)) {
											//기존의 booktitle배열에서 리스트에서 선택한 제목을 찾은 경우-> 수정해주기
											booktitle[j]=bt.title;
											break;
										}
									}
									//totatitle배열 수정
									for (int n=0;n<totaltitle.length;n++) {
										if (totaltitle[n].equals(title1)) {
											//기존의 totaltitle배열에서 리스트에서 선택한 제목을 찾은 경우-> 수정해주기
											totaltitle[n]=bt.title;
											break;
										}
									}
									//수정한 배열로 리스트 새로고침
									bookl.setListData(booktitle);
									totall.setListData(totaltitle);
									//상세보기 내용도 수정하기
									mtitle.setText(bt.title);mdirector.setText(b.author);
									mactor.setText(b.company);
									myear.setText(b.year);
									mstar.setText(Integer.toString(bt.star));
								}
							}
							
						}
					}
					//'추가'인 경우
					else{
						//Movie상태라면 영화객체 생성해서 벡터에 추가 
						if (rb1.isSelected()) {
							Movie m=new Movie(tf1.getText(),tf2.getText(),tf3.getText(),genre[cb1.getSelectedIndex()],grade[cb2.getSelectedIndex()],
									year[cb3.getSelectedIndex()],tf4.getText(),sl.getValue(),ta1.getText(),ta2.getText());
							Item it=m;
							//벡터에 추가
							ic.addItem(it);
							//객체의 제목만 movietitle, totaltitle배열에 추가하고 리스트 새로고침
							movietitle[mi]=it.title;
							mi++;
							moviel.setListData(movietitle);
							totaltitle[ti]=it.title;
							ti++;
							totall.setListData(totaltitle);
						}
						//book상태라면 책 객체 생성해서 벡터에 추가
						else if (rb2.isSelected()) {
							Item it=new Book(tf1.getText(),tf2.getText(),tf3.getText(),
									year[cb3.getSelectedIndex()],tf4.getText(),sl.getValue(),ta1.getText(),ta2.getText());
							//벡터에 추가
							ic.addItem(it);
							//객체의 제목만 booktitle, totaltitle배열에 추가하고 리스트 새로고침
							booktitle[bi]=it.title;
							bi++;
							bookl.setListData(booktitle);
							totaltitle[ti]=it.title;
							ti++;
							totall.setListData(totaltitle);
							
						}
					}
					//다이얼로그 창 닫기
					setVisible(false);	
				}
					
			});
			//다이얼로그의 크기
			setSize(300,500);
		}
		//라디오버튼의 이벤트리스너 클래스 생성
		class MyItemListener implements ItemListener{

			@Override
			public void itemStateChanged(ItemEvent e) {
				//아무것도 선택되지 않은 경우
				if(e.getStateChange()==ItemEvent.DESELECTED) {
					return;
				}
				//버튼이 선택된 경우
				//'Movie'가 선택되었다면
				if (rb1.isSelected()) {
					//기존 resultPanel의 자식컴포넌트 다 지우기
					resultPanel.removeAll();
					//테두리 설정
					Border resultBorder=BorderFactory.createTitledBorder("영화 정보");
					resultPanel.setBorder(resultBorder);
					//컴포넌트 생성하고 판넬에 올리기
					l[1].setText("감독");
					l[2].setText("배우");
					l[5].setText("개봉년도");
					l[6].setText("포스터");
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
					//새로 그리기
					revalidate();
				}
				//'Book'이 선택되었다면
				else if (rb2.isSelected()) {
					//테두리 설정
					Border resultBorder=BorderFactory.createTitledBorder("도서 정보");
				    resultPanel.setBorder(resultBorder);
				    //'Movie'와는 다른 컴포넌트 수정 및 삭제
					l[1].setText("저자");
					l[2].setText("출판사");
					resultPanel.remove(l[3]);resultPanel.remove(cb1);
					resultPanel.remove(l[4]);resultPanel.remove(cb2);
					l[5].setText("출판 년도");
					l[6].setText("책이미지");
					
				}
			}
			
		}
		
	}
	//메뉴를 생성하기 위한 함수 정의
	private void createMenu() {
		//JMenuBar,JMenu, JMenuItem 생성 
		JMenuBar mb=new JMenuBar();
		JMenu fileMenu=new JMenu("파일");
		JMenu helpMenu=new JMenu("도움말");
		
		JMenuItem openMenu=new JMenuItem("불러오기");
		JMenuItem saveMenu=new JMenuItem("저장하기");
		JMenuItem quitMenu=new JMenuItem("종료");
		//'종료' 메뉴에 이벤트리스너 등록
		quitMenu.addActionListener(new ActionListener() {
			//종료하기
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//'불러오기'메뉴에 대한 이벤트리스너 등록 ->파일 불러오기
		openMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//파일 오픈 다이얼로그 출력
				JFileChooser openchooser=new JFileChooser();
				int ret=openchooser.showOpenDialog(null);
				if (ret==JFileChooser.APPROVE_OPTION) {
					//선택한 파일 경로로 File 객체 생성
					File file=new File(openchooser.getSelectedFile().getPath());
					try {
						//객체 입력 스트림 사용하기
						ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
						//입력 받은 객체를 벡터로 저장하기
						Vector <Item> v=(Vector<Item>)ois.readObject();
						ic.v=v;
						//불러온 벡터의 title을 totaltitle, movietitle, booktitle 배열에 저장하기
						for (int i=0;i<v.size();i++) {
							totaltitle[i]=v.get(i).title;
							//영화라면
							if (v.get(i).mode==1) {
								movietitle[i]=v.get(i).title;
							}
							//책이라면
							if (v.get(i).mode==2) {
								booktitle[i]=v.get(i).title;
							}
							
						}
						//만들어진 배열로 리스트 새로고침
						totall.setListData(totaltitle);
						moviel.setListData(movietitle);
						bookl.setListData(booktitle);
						//스트림 닫기
						ois.close();
						
					} catch (IOException e1) {
						System.out.println("파일 열기 실패");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
			
		});
		//'저장하기'에 이벤트리스너 등록->파일 저장하기
		saveMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//파일 저장 다이얼로그 출력
				JFileChooser savechooser=new JFileChooser();
				int ret=savechooser.showSaveDialog(null);
				if (ret==JFileChooser.APPROVE_OPTION) {
					//선택한 파일의 경로로 File 객체 생성
					File file=new File(savechooser.getSelectedFile().getPath());
					try {
						//파일 출력 스트림 생성하여 선택한 파일로 벡터를 출력하기
						ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
						oos.writeObject(ic.v);
						oos.close();//스트림 닫기
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("파일 저장 실패");
					}
				}
				
			}
			
		});
		//fileMenu에 각 메뉴 아이템 올리기
		fileMenu.add(openMenu); fileMenu.add(saveMenu);fileMenu.addSeparator();fileMenu.add(quitMenu);
		//'시스템정보' 메뉴 아이템 생성 및 이벤트리스너 등록
		JMenuItem systemMenu=new JMenuItem("시스템 정보");
		helpMenu.add(systemMenu);
		systemMenu.addActionListener(new ActionListener() {
			//다이얼로그 띄우기
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"MyNotes 시스템 v 1.0입니다.","Message", JOptionPane.WARNING_MESSAGE);
			}
			
		});
		//메뉴바에 메뉴올리고 메뉴바 붙이기
		mb.add(fileMenu);
		mb.add(helpMenu);
		setJMenuBar(mb);
	}
	//수정 버튼 선택 시 이벤트 리스너
	class myeditActionListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//'리스트에서 선택한' 객체의 입력 다이얼로그 띄우기(기존 내용 그대로)
			for (int i=0;i<ic.v.size();i++) {
				if (ic.v.get(i).title.equals(title1)) {
					//벡터에서 해당 객체를 찾은 경우
					if (ic.v.get(i).mode==1) {
						//영화인 경우
						//해당 객체의 내용으로 입력 다이얼로그의 내용 채우기
						rb1.setSelected(true);
						Movie m=(Movie)ic.v.get(i);
						tf1.setText(m.title);tf2.setText(m.director);tf3.setText(m.actor);
						cb1.setSelectedItem(m.genre);cb2.setSelectedItem(m.grade);cb3.setSelectedItem(m.year);
						sl.setValue(m.star);
						ta1.setText(m.story);ta2.setText(m.review);
					}
					else if (ic.v.get(i).mode==2) {
						//책인경우
						//해당 객체의 내용으로 입력 다이얼로그의 내용 채우기
						rb2.setSelected(true);
						Book b=(Book)ic.v.get(i);
						tf1.setText(b.title);tf2.setText(b.author);tf3.setText(b.company);
						cb3.setSelectedItem(b.year);
						sl.setValue(b.star);
						ta1.setText(b.story);ta2.setText(b.review);
					}
				}
				
			
			}
			//다이얼로그 출력
			dialog.setVisible(true);
			//'수정'임을 나타내기 위한 변수
			editmode=1;
			
		}
		
	}
	//추가 버튼을 눌렀을 때의 이벤트 리스너
	class myaddActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//새로 다이얼로그 생성해서 띄우기
			dialog=new MyDialog(frame,"입력");
			dialog.setVisible(true);
		}
			
	}
	//삭제 버튼을 눌렀을 때의 이벤트 리스너
	class deleteActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//확인 다이얼로그 띄우기
			int result=JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?","삭제 확인",JOptionPane.YES_NO_OPTION);
			String deletetitle="";
			Item it=new Item();
			if (result==JOptionPane.YES_OPTION) {
				//사용자가 yes를 선택한 경우
				//벡터에서 해당 객체 삭제하기
				for (int i=0;i<ic.v.size();i++) {
					if (ic.v.get(i).title.equals(title1)) {
						//현재 리스트에서 선택된 객체를 벡터에서 찾은 경우
						//삭제 전 해당 객체의 값과 title을 변수에 저장해두기
						it=ic.v.get(i);
						deletetitle=ic.v.get(i).title;
						ic.v.remove(i);//해당 값을 벡터에서 삭제
					}
				}
				
				int index=0;
				//삭제한 객체가 영화였다면 
				if (it.mode==1) {
					//삭제한 영화가 movietitle의 몇번째 원소였는지 알아내기
					index=Arrays.asList(movietitle).indexOf(deletetitle);
					//삭제한 타이틀을 movietitle에서 제거
					int mlength=movietitle.length;
					for (int i=index;i<mlength-1;i++) {
						movietitle[i]=movietitle[i+1];
					}
					//수정된 배열로 리스트 새로고침
					moviel.setListData(movietitle);
				}
				//삭제한 객체가 책이었다면
				if (it.mode==2) {
					//삭제한 영화가 booktitle의 몇번째 원소였는지 알아내기
					index=Arrays.asList(booktitle).indexOf(deletetitle);
					//삭제한 타이틀을 movietitle에서 제거
					int blength=booktitle.length;
					for (int i=index;i<blength-1;i++) {
						booktitle[i]=booktitle[i+1];
					}
					//수정된 배열로 리스트 새로고침
					bookl.setListData(booktitle);
				}
				//totaltitle에서 해당 객체 삭제 및 totall수정
				index=Arrays.asList(totaltitle).indexOf(deletetitle);
				int tlength=totaltitle.length;
				for (int i=index;i<tlength-1;i++) {
					totaltitle[i]=totaltitle[i+1];
				}
				totall.setListData(totaltitle);
				
				//삭제한 후 상세보기 내용 없어지도록 컴포넌트 수정
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
				
				graphicPanel.add(new JLabel("이미지 없음"));
				parentgraphicPanel.add(graphicPanel);
				
			}
			
		}
		
	}
	public static void main(String[] args) throws IOException{
		//생성자 호출
		new My_Notes();
	}

}
