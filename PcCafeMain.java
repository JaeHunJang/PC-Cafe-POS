import java.util.Vector;
import java.util.Date;
import java.io.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JTable.*;
import javax.swing.table.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.GroupLayout.Alignment;
// 메인 메뉴
public class PcCafeMain extends JFrame implements ActionListener, ListSelectionListener, ItemListener, MouseListener{
	Container ct;
	JPanel tablePan, buttonPan;
	JButton  order, findID, changeWorker, btn1, btn2, btn3, btn4;
	JPanel[] user_jp = new JPanel[5];
	String[] s1 = {"ID","NAME","AGE","H.P","TIME"};
	Object d[][] = new Object[5][];
	JTable IDTable;
	DefaultTableModel ID_Dm, model;
	JLabel ID,seatYN, time;
	JTextField searchID, findUser;
	String Fuser = "", Fid= "", Ftime="";
	String[] s = {"seatNO","seatNO","seatNO","seatNO","seatNO","seatNO","seatNO","seatNO","seatNO","seatNO"};
	String[][] o = {{"a01","a02","a03","a04","a05","a06","a07","a08","a09","a10"},{"b01","b02","b03","b04","b05","b06","b07","b08","b09","b10"},
					{"c01","c02","c03","c04","c05","c06","c07","c08","c09","c10"},{"d01","d02","d03","d04","d05","d06","d07","d08","d09","d10"},
					{"e01","e02","e03","e04","e05","e06","e07","e08","e09","e10"},{"f01","f02","f03","f04","f05","f06","f07","f08","f09","f10"},
					{"g01","g02","g03","g04","g05","g06","g07","g08","g09","g10"},{"h01","h02","h03","h04","h05","h06","h07","h08","h09","h10"},
					{"i01","i02","i03","i04","i05","i06","i07","i08","i09","i10"},{"j01","j02","j03","j04","j05","j06","j07","j08","j09","j10"}	
	};
	JTable jtable;
	int now = 0, play_time = 0, CEO=0;
	JPopupMenu popup;
	JMenuItem m1,m2,m3,m4;
	public PcCafeMain(int CeoCheck){
		ImageIcon image = new ImageIcon("images/pcCafe.jpeg");
		Image original = image.getImage();
		Image newimage = original.getScaledInstance(242,115,Image.SCALE_SMOOTH);
		ImageIcon Icon = new ImageIcon(newimage);
		CEO = CeoCheck;
		Timer t = new Timer(1000, this); 
		t.start(); 
		setTitle("PcCafePosSystem(PCPP)");
		ct = getContentPane();
		ct.setLayout(null);
		for(int i = 0; i<5;i++) {
			user_jp[i]=new JPanel();
		}
		ID_Dm = new DefaultTableModel(d,s1) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};//아이디 정보 테이블
		tablePan = new JPanel();
		tablePan.setLayout(new GridLayout(2,1));
		buttonPan = new JPanel();
		buttonPan.setLayout(null);
		
		tablePan.setBounds(50,50,870,670);
		buttonPan.setBounds(970,50,270,670);
			
		model = new DefaultTableModel(o,s) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		}; //좌석테이블
		jtable = new JTable(model);
		for(int i=0; i < 10; i++) {
			jtable.getColumn(s[i]).setPreferredWidth(70);
		}
		jtable.setRowHeight(70);
		
		JScrollPane sc = new JScrollPane(jtable);
		sc.setPreferredSize(new Dimension(840,380));
		ID = new JLabel("찾을 회원번호    :   ");
		searchID = new JTextField(12);
		seatYN = new JLabel(now+"/50",SwingConstants.CENTER);
		seatYN.setFont(new Font("굴림", 1, 48));
		seatYN.setVerticalAlignment(SwingConstants.CENTER);
		time = new JLabel(Icon,JLabel.CENTER);
		//time.setFont(new Font("굴림", 1, 15));
		order = new JButton("음식 주문");
		order.setFont(new Font("굴림", 1, 30));
		order.addActionListener(this);
		findID = new JButton("ID 찾기");
		findID.setFont(new Font("굴림", 1, 30));
		findID.addActionListener(this);
		changeWorker = new JButton("근무자 전환");
		changeWorker.setFont(new Font("굴림", 1, 30));
		changeWorker.addActionListener(this);
		
		ct.add(tablePan);
		tablePan.add(sc);
		ct.add(buttonPan);
		ID.setBounds(14,12,120,30);
		buttonPan.add(ID);
		searchID.setBounds(125,12,132,30);
		buttonPan.add(searchID);
		seatYN.setBounds(14,50,242,90);
		buttonPan.add(seatYN);
		time.setBounds(14,145,242,115);
		buttonPan.add(time);
		order.setBounds(14,273,242,115);
		buttonPan.add(order);
		findID.setBounds(14,401,242,115);
		buttonPan.add(findID);
		changeWorker.setBounds(14,529,242,115);
		buttonPan.add(changeWorker);
		createMenu();
		popup = new JPopupMenu();
		JMenuItem m1 = new JMenuItem("사용");
		JMenuItem m2 = new JMenuItem("중지");
		JMenuItem m3 = new JMenuItem("주문");
		JMenuItem m4 = new JMenuItem("시간 추가");
		popup.add(m1);
		popup.add(m2);
		popup.addSeparator();
		popup.add(m3);
		popup.add(m4);
		m1.addActionListener(this);
		m2.addActionListener(this);
		m3.addActionListener(this);
		m4.addActionListener(this);
		jtable.add(popup);
		jtable.addMouseListener(this);
		IDTable = new JTable(ID_Dm);
		JScrollPane sc1 = new JScrollPane(IDTable);
		user_jp[4].setLayout(new GridLayout(1,4));
		user_jp[3].setLayout(new BorderLayout());
		user_jp[2].setLayout(new BorderLayout());
		user_jp[1].setLayout(new GridLayout(1,2));
		user_jp[0].setLayout(new BorderLayout());
		findUser = new JTextField();
		btn1 = new JButton("User ID 찾기");
		btn2 = new JButton("음식 주문");
		btn3 = new JButton("시간 추가");
		btn4 = new JButton("초기화");
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		tablePan.add(user_jp[0]);
		user_jp[0].add(user_jp[1], BorderLayout.NORTH);
		user_jp[0].add(user_jp[2], BorderLayout.CENTER);
		user_jp[1].add(user_jp[3]);
		user_jp[1].add(user_jp[4]);
		user_jp[2].add(sc1, BorderLayout.CENTER);
		user_jp[3].add(findUser);
		user_jp[4].add(btn1);
		user_jp[4].add(btn2);
		user_jp[4].add(btn3);
		user_jp[4].add(btn4);
	}
	
	void ordered(String id) // 주문 화면
	{
		OrderForm of = new OrderForm(id);
		of.show();
	}
	void menuEdited() // 메뉴수정 화면
	{
		MenuEditForm mef = new MenuEditForm();
		mef.show();
	}
	void timeOrdered(String id) //시간추가 화면
	{
		TimeOrderForm tof = new TimeOrderForm(id);
		tof.show();
	}
	void tradeInquery() //거래내역조회
	{
		TradeInqueryForm tif = new TradeInqueryForm();
		tif.show();
	}
	void refundInquery() //환불내역조회
	{
		RefundInqueryForm rif = new RefundInqueryForm();
		rif.show();
	}
	void tradeDetailInquery() //거래내역상세조회
	{
		TradeDetailInqueryForm tdif = new TradeDetailInqueryForm();
		tdif.show();
	}
	void refundDetailInquery() //환불내역상세조회
	{
		RefundDetailInqueryForm rdif = new RefundDetailInqueryForm();
		rdif.show();
	}
	void typeInquery() //매출 조회
	{
		TypeInqueryForm tyif = new TypeInqueryForm();
		tyif.show();
	}
	public void actionPerformed(ActionEvent e){
		String str = e.getActionCommand();
		if(str.equals("시간 추가")) {
			timeOrdered(Fid);
		}else if(str.equals("음식 주문")) {
			ordered(Fid);
		}else if(str.equals("거래내역")) {
			tradeInquery();
		}else if(str.equals("거래내역상세보기")) {
			tradeDetailInquery();
		}else if(str.equals("환불내역")) {
			refundInquery();
		}else if(str.equals("환불내역상세보기")) {
			refundDetailInquery();
		}else if(str.equals("매출보기")) {
			typeInquery();
		}else if(str.equals("메뉴수정")) {
			menuEdited();
		}else if(str.equals("ID 찾기")) {
			IDfind ID_window = new IDfind();
			ID_window.setLocation(50,70);
			ID_window.setSize(400,600);
			ID_window.setVisible(true);
		}else if(str.equals("근무자 전환")) {
			LoginMain lg = new LoginMain();
			lg.setSize(800,600);
			lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			lg.setVisible(true);
			dispose();
		}else if(str.equals("User ID 찾기")) {
			String strSql;
			String find_user;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.err.println("JDBC-ODBC Driver connect complete");
			} catch(ClassNotFoundException c) {
				System.err.println("driver load failed");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcpp?serverTimezone=UTC","root","1234");
				System.out.println("db connetion complete");
				Statement dbSt = con.createStatement();
				System.out.println("JDBC driver 정상적 연결");
				find_user = findUser.getText().toString();
				ID_Dm.setRowCount(0);
				strSql = "SELECT * FROM user WHERE id='"+find_user+"';";
				ResultSet result = dbSt.executeQuery(strSql);
				while(result.next()) {
					ID_Dm.addRow(new String[]{result.getString("id"),result.getString("name"),result.getString("age"),result.getString("tel_number"),result.getString("time")});
					Fuser = result.getString("name");
					Ftime = result.getString("time");
					Fid = result.getString("id");
				}
				dbSt.close();
				con.close();
			} catch (SQLException sq) {
				System.out.println("SQLException : " + sq.getMessage());
			} //아이디 테이블에 맞는 아이디에 대한 정보 출력
		}else if(str == "사용") {
			if(!(Fuser.equals(""))) {
				int row = jtable.getSelectedRow();
				int col = jtable.getSelectedColumn();
				Object value = jtable.getValueAt(row, col);
				model.setValueAt(value + " : " + Fuser + "\n 시간 : ", row, col);
				findUser.setText("");
				seatYN.setText(++now+"/50");
				ID_Dm.setRowCount(0);
				Fuser = "";
			} // 아이디 테이블에서 정한것을 우클릭으로 좌석에 시작
		}else if( str == "중지") {
			int row = jtable.getSelectedRow();
			int col = jtable.getSelectedColumn();
			Object value = jtable.getValueAt(row, col);
				if(value.toString().length() > 3) {
				model.setValueAt(o[row][col] + " ", row, col);
				seatYN.setText(--now+"/50");
				ID_Dm.setRowCount(0);
			} // 정해진 자리 좌석 정지
		} else if(str == "초기화") {
			findUser.setText("");
			ID_Dm.setRowCount(0);
		}//아이디입력칸을 비운다
		
	}
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3) 
			popup.show(jtable, e.getX(), e.getY());
	} //우클릭 팝업 메뉴 창

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	//위에 메뉴바 생성
	public void createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenuItem tradeInfMenuItem,tradeMenuItem, refundMenuItem, refundInfMenuItem, salesInfMenuItem;
		JMenuItem changeMenuItem;
		JMenuItem userMenuItem, workMenuItem, newWorkerItem;
		JMenu orderMenu = new JMenu("거래");
		JMenu itemMenu = new JMenu("상품");
		JMenu workMenu = new JMenu("회원 & 근무");
		JMenu totalMenu = new JMenu("환경설정");
		
		tradeMenuItem = new JMenuItem("거래내역");
		tradeInfMenuItem = new JMenuItem("거래내역상세보기");
		refundMenuItem = new JMenuItem("환불내역");
		refundInfMenuItem = new JMenuItem("환불내역상세보기");
		tradeInfMenuItem.addActionListener(this);
		tradeMenuItem.addActionListener(this);
		refundMenuItem.addActionListener(this);
		refundInfMenuItem.addActionListener(this);

		orderMenu.add(tradeMenuItem);
		orderMenu.add(tradeInfMenuItem);
		orderMenu.add(refundMenuItem);
		orderMenu.add(refundInfMenuItem);
		
		mb.add(orderMenu);
		
		changeMenuItem = new JMenuItem("메뉴수정");
		salesInfMenuItem = new JMenuItem("매출보기");
		salesInfMenuItem.addActionListener(this);
		changeMenuItem.addActionListener(this);
		itemMenu.add(salesInfMenuItem);
		itemMenu.add(changeMenuItem);
		mb.add(itemMenu);
		
		userMenuItem = new JMenuItem("회원정보수정");
		workMenuItem = new JMenuItem("근무자 조회");
		newWorkerItem = new JMenuItem("관리자 정보 수정");
		userMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userChangeInf change_window = new userChangeInf(); 
					change_window.setLocation(50,70);
					change_window.setSize(400,600);
					change_window.setVisible(true);
			}//내부클래스로 회원정보 수정 창이 실행됨
		});
		workMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				workReport work_Window = new workReport(CEO);
				work_Window.setLocation(50,70);
				work_Window.setSize(600,400);
				work_Window.setVisible(true);
			}
		});// 내부클래스로 근무자 조회 창이 실행
		newWorkerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				managerChangeInf maChange_window = new managerChangeInf(); 
				maChange_window.setLocation(50,70);
				maChange_window.setSize(400,600);
				maChange_window.setVisible(true);
			}
		}); // 관리자 정보 수정이 가능한 창이 실행
		
		workMenu.add(userMenuItem);
		workMenu.add(workMenuItem);
		workMenu.add(newWorkerItem);
		mb.add(workMenu);
		mb.add(totalMenu);
		setJMenuBar(mb);
	}
	
	public void valueChanged(ListSelectionEvent lse) {
	
	}
	
	public void itemStateChanged(ItemEvent e) {

	}
	
	public static void main(String[] args){
		LoginMain lg = new LoginMain();
		lg.setSize(800,600);
		lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lg.setVisible(true);
	}
}
//관리자 정보 수정창
class managerChangeInf extends JFrame implements ActionListener, ItemListener{
	JButton Ok, Cancel, overLap, PassWdCheck;
	JCheckBox sure;
	JTextField newID, newName, newAge, newHP, newAddress;
	JPasswordField newPassWd, rePassWd, changePassWd;
	JLabel IDInf,passWdInf;
	int IDCheck = 0, passWdCheck = 0, IDRule=0, passWdRule=0;
	int selected;
	managerChangeInf(){
		setTitle("관리자정보수정");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		JPanel Top = new JPanel();
		JPanel Bottom = new JPanel();
		JPanel Center = new JPanel();
		JLabel membership = new JLabel("관리자정보수정");
		ct.add(Top, BorderLayout.NORTH);
		Top.add(membership);
		
		ct.add(Center, BorderLayout.CENTER);
		Center.setLayout(new GridLayout(8,1));
		JPanel[] jp = new JPanel[8];
		
		for(int i = 0; i< 8; i ++) {
			jp[i] = new JPanel();
			jp[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			jp[i].setFont(new Font("",0,20));
			Center.add(jp[i]);
		}

		newID = new JTextField(12);
		newID.addActionListener(this);
		overLap = new JButton("ID확인");
		jp[0].add(new JLabel("ID                         : "));
		overLap.setFont(new Font("",0,15));
		overLap.addActionListener(this);
		newID.setFont(new Font("",0,17));
		jp[0].add(newID);
		newPassWd = new JPasswordField(12);
		newPassWd.setFont(new Font("",0,17));
		jp[1].add(new JLabel("PassWd              : "));
		jp[1].add(newPassWd);
		jp[1].add(overLap, FlowLayout.RIGHT);
		
		changePassWd = new JPasswordField(12);
		changePassWd.setFont(new Font("",0,17));
		jp[2].add(new JLabel("ChangePassWd : "));
		jp[2].add(changePassWd);
		
		rePassWd = new JPasswordField(10);
		rePassWd.setFont(new Font("",0,20));
		rePassWd.addActionListener(this);
		PassWdCheck = new JButton("PW변경");
		PassWdCheck.setFont(new Font("",0,15));
		PassWdCheck.addActionListener(this);
		jp[3].add(new JLabel("PassWd_Check : "));
		jp[3].add(rePassWd);
		jp[3].add(PassWdCheck);
		
		newName =new JTextField(10);
		newName.setFont(new Font("",0,20));
		jp[4].add(new JLabel("Name                  : "));
		jp[4].add(newName);
		
		newAge = new JTextField(10);
		newAge.setFont(new Font("",0,20));
		jp[5].add(new JLabel("age                      : "));
		jp[5].add(newAge);
		
		newHP = new JTextField(10);
		newHP.setFont(new Font("",0,20));
		jp[6].add(new JLabel("H.P                      : "));
		jp[6].add(newHP); 
		
		sure = new JCheckBox("수정 하시겠습니까?");
		sure.addItemListener(this);
		jp[7].add(sure);
		
		ct.add(Bottom, BorderLayout.SOUTH);
		Bottom.setLayout(new FlowLayout());
		Ok = new JButton("확인");
		Cancel = new JButton("취소");
		Ok.addActionListener(this);
		Cancel.addActionListener(this);
		Bottom.add(Ok);
		Bottom.add(Cancel);
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED)
			selected = 1;
		else
			selected = -1;
	}//동의 여부 확인
	
	public void actionPerformed(ActionEvent e){
		String btn = e.getActionCommand();
		String t_id = "", t_passwd = "", t_name = "", t_age = "", t_HP = "", t_rePassWd = "", t_changepasswd = "";
		String strSql;
		t_id = newID.getText();
		t_passwd=newPassWd.getText();
		t_changepasswd = changePassWd.getText();
		t_name = newName.getText();
		t_age = newAge.getText();
		t_HP = newHP.getText();
		t_rePassWd = rePassWd.getText();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC Driver connect complete");
		} catch(ClassNotFoundException c) {
			System.err.println("driver load failed");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcpp?serverTimezone=UTC","root","1234");
			System.out.println("db connetion complete");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC driver 정상적 연결");
			if(btn == "ID확인" || btn.equals(newID.getText().toString())) {
				t_id = newID.getText(); t_passwd = newPassWd.getText();
				strSql = "SELECT*FROM worker_info WHERE id='"+t_id+"'and passwd ='"+t_passwd+"';";
				ResultSet result = dbSt.executeQuery(strSql);
				if (result.next()) {
					System.out.println(result.getString("name"));
					newName.setText(result.getString("name"));	
					newAge.setText(result.getString("age"));
					newHP.setText(result.getString("tel_number"));
				}
			} else if (btn == "PW변경" || btn.equals(rePassWd.getText().toString())) {
				if (t_changepasswd.equals(t_rePassWd)) {
					MessageDialog msg = new MessageDialog(this,"일치여부",true, "사용가능합니다.");
					msg.setLocation(250, 380);
					msg.show();
					passWdCheck = 1;
				} else {
					MessageDialog msg = new MessageDialog(this,"일치여부",true, "일치하지 않습니다.");
					msg.setLocation(250, 380);
					msg.show();
					changePassWd.setText("");
					rePassWd.setText("");
				}
			}else if(btn == "확인") {
				String time = "00:00";
				int age = Integer.parseInt(t_age);
				if(passWdCheck == 1) {
					if(selected == 1) {
						strSql = "UPDATE worker_info SET passwd='"+t_changepasswd+"',name='"+t_name+"',age='"+t_age+"',tel_number='"+t_HP+"'WHERE id='"+t_id+"';";
						dbSt.executeUpdate(strSql);
						MessageDialog msg = new MessageDialog(this,"회원정보수정",true, "수정이 완료 되었습니다.");
						msg.setLocation(250, 380);
						msg.show();
						dispose();
					} else {
						MessageDialog msg = new MessageDialog(this,"회원가입실패",true, "동의여부 확인 부탁드립니다.");
						msg.setLocation(250, 380);
						msg.show();
					}
				}else {
					MessageDialog msg = new MessageDialog(this,"회원가입실패",true, "비밀번호 확인 부탁드립니다.");
					msg.setLocation(250, 380);
					msg.show();
				}
			} else if(btn == "취소") {
				newID.setText("");
				newPassWd.setText("");
				newName.setText("");
				newAge.setText("");
				newHP.setText("");
				rePassWd.setText("");
			}
			dbSt.close();
			con.close();
		} catch (SQLException sq) {
			System.out.println("SQLException : " + sq.getMessage());
		}
	}
} // 아이디 비밀번호를입력하여 밑에 정보가 출력  비밀번호 변경하고 바뀐정보를 db에 저장함 동의를 안할 경우 실행이 안됨
//회원 정보 수정 메뉴
class userChangeInf extends JFrame implements ActionListener, ItemListener{
	JButton Ok, Cancel, overLap, PassWdCheck;
	JCheckBox sure;
	JTextField newID, newName, newAge, newHP, newAddress;
	JPasswordField newPassWd, rePassWd, changePassWd;
	JLabel IDInf,passWdInf;
	int IDCheck = 0, passWdCheck = 0, IDRule=0, passWdRule=0;
	int selected;
	userChangeInf(){
		setTitle("회원정보수정");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		JPanel Top = new JPanel();
		JPanel Bottom = new JPanel();
		JPanel Center = new JPanel();
		JLabel membership = new JLabel("회원정보수정");
		ct.add(Top, BorderLayout.NORTH);
		Top.add(membership);
		
		ct.add(Center, BorderLayout.CENTER);
		Center.setLayout(new GridLayout(8,1));
		
		JPanel[] jp = new JPanel[8];
		
		for(int i = 0; i< 8; i ++) {
			jp[i] = new JPanel();
			jp[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			jp[i].setFont(new Font("",0,20));
			Center.add(jp[i]);
		}

		newID = new JTextField(12);
		newID.addActionListener(this);
		overLap = new JButton("ID확인");
		jp[0].add(new JLabel("ID                         : "));
		overLap.setFont(new Font("",0,15));
		overLap.addActionListener(this);
		newID.setFont(new Font("",0,17));
		jp[0].add(newID);
		newPassWd = new JPasswordField(12);
		newPassWd.setFont(new Font("",0,17));
		jp[1].add(new JLabel("PassWd              : "));
		jp[1].add(newPassWd);
		jp[1].add(overLap, FlowLayout.RIGHT);
		
		changePassWd = new JPasswordField(12);
		changePassWd.setFont(new Font("",0,17));
		jp[2].add(new JLabel("ChangePassWd : "));
		jp[2].add(changePassWd);
		jp[2].add(passWdInf = new JLabel("PassWd는 영어,숫자 혼합 최소 6글자 최대 10글자입니다."));
		
		rePassWd = new JPasswordField(10);
		rePassWd.setFont(new Font("",0,20));
		rePassWd.addActionListener(this);
		PassWdCheck = new JButton("PW변경");
		PassWdCheck.setFont(new Font("",0,15));
		PassWdCheck.addActionListener(this);
		jp[3].add(new JLabel("PassWd_Check : "));
		jp[3].add(rePassWd);
		jp[3].add(PassWdCheck);
		
		newName =new JTextField(10);
		newName.setFont(new Font("",0,20));
		jp[4].add(new JLabel("Name                  : "));
		jp[4].add(newName);
		
		newAge = new JTextField(10);
		newAge.setFont(new Font("",0,20));
		jp[5].add(new JLabel("age                      : "));
		jp[5].add(newAge);
		
		newHP = new JTextField(10);
		newHP.setFont(new Font("",0,20));
		jp[6].add(new JLabel("H.P                      : "));
		jp[6].add(newHP); 
		

		sure = new JCheckBox("수정 하시겠습니까?");
		sure.addItemListener(this);
		jp[7].add(sure);
		
		ct.add(Bottom, BorderLayout.SOUTH);
		Bottom.setLayout(new FlowLayout());
		Ok = new JButton("확인");
		Cancel = new JButton("취소");
		Ok.addActionListener(this);
		Cancel.addActionListener(this);
		Bottom.add(Ok);
		Bottom.add(Cancel);
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED)
			selected = 1;
		else
			selected = -1;
	}
	
	public void actionPerformed(ActionEvent e){
		String btn = e.getActionCommand();
		String t_id = "", t_passwd = "", t_name = "", t_age = "", t_HP = "", t_rePassWd = "", t_changepasswd = "";
		String strSql;
		t_id = newID.getText();
		t_passwd=newPassWd.getText();
		t_changepasswd = changePassWd.getText();
		t_name = newName.getText();
		t_age = newAge.getText();
		t_HP = newHP.getText();
		t_rePassWd = rePassWd.getText();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC Driver connect complete");
		} catch(ClassNotFoundException c) {
			System.err.println("driver load failed");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcpp?serverTimezone=UTC","root","1234");
			System.out.println("db connetion complete");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC driver 정상적 연결");
			if(btn == "ID확인" || btn.equals(newID.getText().toString())) {
				t_id = newID.getText(); t_passwd = newPassWd.getText();
				strSql = "SELECT*FROM user WHERE id='"+t_id+"'and passwd ='"+t_passwd+"';";
				ResultSet result = dbSt.executeQuery(strSql);
				if (result.next()) {
					System.out.println(result.getString("name"));
					newName.setText(result.getString("name"));	
					newAge.setText(result.getString("age"));
					newHP.setText(result.getString("tel_number"));
				}
			} else if (btn == "PW변경" || btn.equals(rePassWd.getText().toString())) {
				if(rePassWd.getText().length() >= 6 && rePassWd.getText().length() <= 10) {
					System.out.println(rePassWd.getText().length()+rePassWd.getText());
					if(englishCheck(rePassWd.getText()) || englishCheck(changePassWd.getText())) {
						passWdInf.setForeground(Color.black);
						System.out.println(newPassWd.getText().length()+newPassWd.getText());
						passWdRule=1;
						if (t_changepasswd.equals(t_rePassWd)) {
							MessageDialog msg = new MessageDialog(this,"일치여부",true, "사용가능합니다.");
							msg.setLocation(250, 380);
							msg.show();
							passWdCheck = 1;
						} else {
							MessageDialog msg = new MessageDialog(this,"일치여부",true, "일치하지 않습니다.");
							msg.setLocation(250, 380);
							msg.show();
							changePassWd.setText("");
							rePassWd.setText("");
						}
					}else {
						passWdInf.setForeground(Color.red);
					}
				}else {
					passWdInf.setForeground(Color.red);
				}
			} else if(btn == "확인") {
				String time = "00:00";
				int age = Integer.parseInt(t_age);
				if(passWdCheck == 1 || passWdRule == 1) {
					if(selected == 1) {
						strSql = "UPDATE user SET passwd='"+t_changepasswd+"',name='"+t_name+"',age='"+t_age+"',tel_number='"+t_HP+"',time='00:00' WHERE id='"+t_id+"';";
						dbSt.executeUpdate(strSql);
						MessageDialog msg = new MessageDialog(this,"회원정보수정",true, "수정이 완료 되었습니다.");
						msg.setLocation(250, 380);
						msg.show();
						dispose();
					} else {
						MessageDialog msg = new MessageDialog(this,"수정실패",true, "동의여부 확인 부탁드립니다.");
						msg.setLocation(250, 380);
						msg.show();
					}
				}
				else {
					MessageDialog msg = new MessageDialog(this,"수정실패",true, "비밀번호 확인 부탁드립니다.");
					msg.setLocation(250, 380);
					msg.show();
					}
				}else if(btn == "취소") {
				newID.setText("");
				newPassWd.setText("");
				newName.setText("");
				newAge.setText("");
				newHP.setText("");
				rePassWd.setText("");
			}
			dbSt.close();
			con.close();
		} catch (SQLException sq) {
			System.out.println("SQLException : " + sq.getMessage());
		}
	}
	boolean englishCheck(String str){
		int English = 0, Number = 0;
		for(int i =0; i< str.length();i++) {
			char c = str.charAt(i);
			boolean b = Character.isDigit(c);
			if(b== true) {
				English++;
			}else {
				Number++;
			}
		}
		if(English == 0 || Number == 0) {
			return false;
		}else
			return true;
	}
}
//관리 정보 수정과 비슷하게 하지만 아이디 비밀번호에 규제가 심함 아이디는 변경불가며 비밀번호는 6-10자에 영숫자가 합쳐야한다. 중복확인도 해야하며 동의도 해야한다 하나라도 안지켜질시 회원가입이 불가능하다
//로그인 메인화면
class LoginMain extends JFrame implements ActionListener{
	JLabel IDLabel, PasswdLabel;
	JButton Login, findId, newPeo;
	JPanel Ljp, IDjp;
	JTextField ID;
	JPasswordField Password;
	int i = 0;
	LoginMain(){
		Container ct = getContentPane();
		ct.setLayout(null);
		Ljp = new JPanel();
		IDjp = new JPanel();
		
		JLabel log = new JLabel("Pc Cafe P.O.S Program");
		JLabel PCPP = new JLabel("PCPP");
		IDLabel = new JLabel("ID : ");
		PasswdLabel = new JLabel("PassWord : ");
		ID = new JTextField();
		Password = new JPasswordField(8);
		Login = new JButton("LOG IN");
		findId = new JButton("아이디 찾기");
		newPeo = new JButton("회원가입");
		
		IDLabel.setHorizontalAlignment(IDLabel.RIGHT);
		PasswdLabel.setHorizontalAlignment(PasswdLabel.RIGHT);
		log.setHorizontalAlignment(log.CENTER);
		PCPP.setFont(new Font(" ",0,100));
		PCPP.setHorizontalAlignment(PCPP.CENTER);
		Ljp.setBounds(212, 110, 410, 150);
		Ljp.setLayout(new BorderLayout());
		Ljp.add(log, BorderLayout.NORTH);
		Ljp.add(PCPP, BorderLayout.CENTER);
		
		IDjp.setBounds(90,331, 620, 170);
		IDjp.setLayout(null);
		
		IDLabel.setBounds(40,12,90,40);
		IDLabel.setFont(new Font(" ",0,15));
		IDjp.add(IDLabel);
		
		PasswdLabel.setBounds(40,70,90,40);
		PasswdLabel.setFont(new Font(" ",0,15));
		IDjp.add(PasswdLabel);
		
		ID.setBounds(145,12, 245, 40);
		ID.setFont(new Font(" ",0,15));
		IDjp.add(ID);
		
		Password.setBounds(145,70, 245, 40);
		Password.setFont(new Font(" ",0,15));
		IDjp.add(Password);
		
		Login.setBounds(428,12, 160, 143);
		Login.setHorizontalAlignment(log.CENTER);
		Login.setFont(new Font(" ",Font.BOLD,30));
		IDjp.add(Login);
		
		findId.setBounds(145,128, 120, 25);
		findId.setHorizontalAlignment(log.CENTER);
		IDjp.add(findId);
		
		newPeo.setBounds(270,128, 120, 25);
		findId.setHorizontalAlignment(log.CENTER);
		IDjp.add(newPeo);
		
		ct.add(Ljp);
		ct.add(IDjp);

		Login.addActionListener(this);
		newPeo.addActionListener(this);
		findId.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if(s == "LOG IN") {
			LogInComPlete();
		} else if(s == "아이디 찾기") {
			IDfind ID_window = new IDfind();
			ID_window.setLocation(50,70);
			ID_window.setSize(400,600);
			ID_window.setVisible(true);
		} else if(s == "회원가입") {
			newMember New_window = new newMember();
			New_window.setLocation(50,70);
			New_window.setSize(400,600);
			New_window.setVisible(true);
		}
	}
	//로그인 기능
	public void LogInComPlete() {
		String t_id = "", t_passwd = "", t_name = "";
		String strSql, strSql2;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC Driver connect complete");
		} catch(ClassNotFoundException c) {
			System.err.println("driver load failed");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcpp?serverTimezone=UTC","root","1234");
			System.out.println("db connetion complete");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC driver 정상적 연결");
			
			t_id = ID.getText(); t_passwd = Password.getText();
			strSql2 = "SELECT*FROM worker_info WHERE id='"+t_id+"'and passwd ='"+t_passwd+"';";
			ResultSet result2 = dbSt.executeQuery(strSql2);
			if (result2.next()) {
				i = 1;
				MessageDialog md = new MessageDialog(this, "WELCOME!",true,"관리자님 환영합니다!");
				md.setLocation(250,380);
				md.show();
				PcCafeMain jcbt = new PcCafeMain(i);
				jcbt.setSize(1280,960);
				jcbt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jcbt.setVisible(true);
				dispose();
//			} else {
//				strSql = "SELECT*FROM user WHERE id='"+t_id+"'and passwd ='"+t_passwd+"';";
//				ResultSet result = dbSt.executeQuery(strSql);
//				if (result.next()) {
//					PcCafeMain jcbt = new PcCafeMain(i);
//					jcbt.setSize(1280,960);
//					jcbt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//					jcbt.setVisible(true);
//					dispose();
				} else {
					MessageDialog md = new MessageDialog(this, "log in",true,"ID or Password 를 확인해주세요.");
					md.setLocation(250,380);
					md.show();
//				}
//			}
			}
			dbSt.close();
			con.close();
			
		} catch (SQLException sq) {
			System.out.println("SQLException : " + sq.getMessage());
		}
	}
}
//아이디 찾는클래스
class IDfind extends JFrame implements ActionListener{
	JTextField FindID;
	JButton Find, Reset;
	JTable jtable;
	JPanel jp3;
	DefaultTableModel model;
	String[] s = {"ID","이름","전화번호"};
	String[][] n;
	IDfind(){
		Container ct = getContentPane();
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		jp3 = new JPanel();
		ct.setLayout(new BorderLayout());
		jp1.setLayout(new BorderLayout());
		jp2.setLayout(new FlowLayout());
		jp3.setLayout(new BorderLayout());
		ct.add(new JLabel("ID 찾기"), BorderLayout.NORTH);
		ct.add(jp1, BorderLayout.CENTER);
		jp1.add(jp2, BorderLayout.NORTH);
		jp1.add(jp3, BorderLayout.CENTER);
		
		FindID = new JTextField(10);
		Find = new JButton("검색");
		Reset = new JButton("초기화");
		
		FindID.setFont(new Font("",0,20));
		jp2.add(FindID);
		Find.addActionListener(this);
		Find.setFont(new Font("",0,20));
		jp2.add(Find);
		Reset.addActionListener(this);
		Reset.setFont(new Font("",0,20));
		jp2.add(Reset);
		
		model = new DefaultTableModel(n,s) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		jtable = new JTable(model);
		JScrollPane sc = new JScrollPane(jtable);
		jp3.add(sc,BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e){
		String text = e.getActionCommand();
		String t_Find = FindID.getText();
		String strSql;
		String initPassWd = "abcd1234";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC Driver connect complete");
		} catch(ClassNotFoundException c) {
			System.err.println("driver load failed");
		} try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcpp?serverTimezone=UTC","root","1234");
			System.out.println("db connetion complete");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC driver 정상적 연결");
			if(text.equals("검색")) {
				model.setRowCount(0);
				strSql = "SELECT id, name, tel_number FROM user WHERE id='"+t_Find+"' or name='"+t_Find+"';";
				ResultSet result = dbSt.executeQuery(strSql);
				while(result.next()) {
					model.addRow(new String[] {result.getString("id"),result.getString("name"),result.getString("tel_number")});
				}
				
			}else if(text.equals("초기화")) {
				int row = jtable.getSelectedRow();
				Object value = jtable.getValueAt(row, 0);
				strSql = "UPDATE user SET passwd='"+initPassWd+"' WHERE ID='"+value.toString()+"';";
				dbSt.executeUpdate(strSql);
				MessageDialog msg = new MessageDialog(this,"비밀번호 초기화",true, "비밀번호가 초기화 되었습니다.");
				msg.setLocation(250, 380);
				msg.show();
				model.setRowCount(0);
			}
			dbSt.close();
			con.close();
		}catch (SQLException sq) {
			System.out.println("SQLException : " + sq.getMessage());
		} 
	}
}//이름으로 아이디를 찾고 초기화를 통해 초기화 비번 abcd1234로 바뀐다
//근무자 조회 기능
class workReport extends JFrame implements ActionListener, ItemListener{
	int CEO = 0,click = 1;;
	JPanel[] jp = new JPanel[6];
	JButton workFind, workChange, workInsert;
	JLabel Date, Name;
	JTextField DateInput, NameInput;
	JTable WorkTable;
	String[] Dates = {"years", "month", "date"};
	String[] workbar = {"근무번호","이름","년","월","일", "근무시작", "근무종료"};
	String Select_time = "years";
	Object [][] workS = new Object[5][];
	JComboBox select_date;
	DefaultTableModel Work_Dm;
	workReport(int CeoCheck){
		CEO = CeoCheck;
		setTitle("근무자조회");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		Work_Dm = new DefaultTableModel(workS,workbar) {
			public boolean isCellEditable(int i, int c) {
				if(CEO == 0) {
					return false;
				}else {
					return true;
				}
			}
		};
		WorkTable = new JTable(Work_Dm);
		JScrollPane sc2 = new JScrollPane(WorkTable);
		for(int i = 0; i<6;i++) {
			jp[i] = new JPanel();
		}
		select_date = new JComboBox(Dates);
		select_date.addItemListener(this);
		workInsert = new JButton("삽입");
		workInsert.addActionListener(this);
		workFind = new JButton("조회");
		workFind.addActionListener(this);
		workChange = new JButton("수정");
		workChange.addActionListener(this);
		Date = new JLabel("날짜");
		Name = new JLabel("이름");
		DateInput = new JTextField(8);
		NameInput = new JTextField(8);
		
		jp[0].setLayout(new BorderLayout());
		jp[1].setLayout(new GridLayout(1,2));
		jp[2].setLayout(new GridLayout(1,2));
		jp[3].setLayout(new GridLayout(1,3));
		jp[4].setLayout(new BorderLayout());
		jp[5].setLayout(new BorderLayout());
		
		jp[4].add(Date, BorderLayout.WEST);
		jp[5].add(Name, BorderLayout.WEST);
		jp[4].add(DateInput, BorderLayout.CENTER);
		jp[4].add(select_date, BorderLayout.EAST);
		jp[5].add(NameInput, BorderLayout.CENTER);
		jp[2].add(jp[4]);
		jp[2].add(jp[5]);
		jp[1].add(jp[2]);
		jp[1].add(jp[3]);
		jp[3].add(workInsert);
		jp[3].add(workFind);
		jp[3].add(workChange);
		jp[0].add(sc2, BorderLayout.CENTER);
		ct.add(jp[1], BorderLayout.NORTH);
		ct.add(jp[0], BorderLayout.CENTER);
	}
	public void actionPerformed(ActionEvent e){
		String s = e.getActionCommand();
		String t_name = NameInput.getText().toString();
		String strSql;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC Driver connect complete");
		} catch(ClassNotFoundException c) {
			System.err.println("driver load failed");
		} try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcpp?serverTimezone=UTC","root","1234");
			System.out.println("db connetion complete");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC driver 정상적 연결");
			if(s.equals("조회")) {
				if(Select_time.equals("years")) {
					Work_Dm.setRowCount(0);
					String select_years = DateInput.getText();
					strSql = "SELECT work_num, name, year,month,day,start_time, finish_time FROM work_info, worker_info WHERE worker_info.name='"+t_name+"'and year ='"+select_years+"'and work_info.id = worker_info.id;";
					ResultSet result = dbSt.executeQuery(strSql);
					while(result.next()) {
						Work_Dm.addRow(new String[]{result.getString("work_num"),result.getString("name"),result.getString("year"),result.getString("month"),result.getString("day"),result.getString("start_time"),result.getString("finish_time")});
					}
				}else if(Select_time.equals("month")) {
					Work_Dm.setRowCount(0);
					String select_month = DateInput.getText();
					strSql = "SELECT work_num,name, year,month,day, start_time, finish_time FROM work_info, worker_info WHERE worker_info.name='"+t_name+"'and month ='"+select_month+"' and work_info.id = worker_info.id;";
					ResultSet result = dbSt.executeQuery(strSql);
					while(result.next()) {
						Work_Dm.addRow(new String[]{result.getString("work_num"),result.getString("name"),result.getString("year"),result.getString("month"),result.getString("day"),result.getString("start_time"),result.getString("finish_time")});
					}
				}else if(Select_time.equals("date")) {
					Work_Dm.setRowCount(0);
					String select_day = DateInput.getText();
					strSql = "SELECT work_num, name, year,month,day, start_time, finish_time FROM work_info, worker_info WHERE worker_info.name='"+t_name+"'and day ='"+select_day+"'and work_info.id = worker_info.id;";
					ResultSet result = dbSt.executeQuery(strSql);
					while(result.next()) {
						Work_Dm.addRow(new String[]{result.getString("work_num"),result.getString("name"),result.getString("year"),result.getString("month"),result.getString("day"),result.getString("start_time"),result.getString("finish_time")});
					}	
				}	
			} else if(s.equals("수정")) {
				if(CEO == 1) {
					int row = WorkTable.getSelectedRow();
					Object value = WorkTable.getValueAt(row,0);
					System.out.print(WorkTable.getValueAt(row,1));
					strSql = "UPDATE work_info SET id=(select id from worker_info where name='"+WorkTable.getValueAt(row,1)+"'),year ='"+WorkTable.getValueAt(row,2)+"',month='"+WorkTable.getValueAt(row,3)+"', day ='"+WorkTable.getValueAt(row,4)+"',start_time='"+WorkTable.getValueAt(row,5)+"',finish_time='"+WorkTable.getValueAt(row,6)+"'WHERE work_num='"+WorkTable.getValueAt(row,0)+"';";
					dbSt.executeUpdate(strSql);
				}
			} else if(s.equals("삽입")) {
				if(CEO == 1) {
					if(click == 1) {
						System.out.println(click++);
						Work_Dm.setRowCount(0);
						strSql = "SELECT MAX(work_num)+1 FROM work_info;";
						ResultSet result = dbSt.executeQuery(strSql);
						if(result.next()) {
						Work_Dm.addRow(new String[]{result.getString("MAX(work_num)+1"),"","","","","",""});
						}
					}else if(click == 2) {
						click = 1;
						Object value = WorkTable.getValueAt(0,0);
						strSql = "INSERT INTO work_info(work_num,id,year,month,day,start_time,finish_time) values('"+WorkTable.getValueAt(0,0)+"',(select id from worker_info where name='"+WorkTable.getValueAt(0,1)+"'),'"+WorkTable.getValueAt(0,2)+"','"+WorkTable.getValueAt(0,3)+"','"+WorkTable.getValueAt(0,4)+"','"+WorkTable.getValueAt(0,5)+"','"+WorkTable.getValueAt(0,6)+"');";
						dbSt.executeUpdate(strSql);
					}
				}
			}
			dbSt.close();
			con.close();
		}catch (SQLException sq) {
			System.out.println("SQLException : " + sq.getMessage());
		} 
	}

	public void itemStateChanged(ItemEvent e) {
		Object s = e.getItem();
		Select_time = s.toString();
	}
}//근무자 조회가 가능하며 수정 삽입 도 가능하다. 수정 삽입은 메니져만 가능한 기능이다.
//회원가입 메뉴
class newMember extends JFrame implements ActionListener, ItemListener{
	JButton Ok, Cancel, overLap, PassWdCheck;
	JCheckBox sure;
	JTextField newID, newName, newAge, newHP, newAddress;
	JPasswordField newPassWd, rePassWd;
	JLabel IDInf,passWdInf;
	int IDCheck = 0, passWdCheck = 0, IDRule=0, passWdRule=0;
	int selected;
	newMember(){
		setTitle("회원가입");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		JPanel Top = new JPanel();
		JPanel Bottom = new JPanel();
		JPanel Center = new JPanel();
		JLabel membership = new JLabel("회원가입");
		IDInf = new JLabel("ID는  최소 6글자 최대 10글자입니다.");
		ct.add(Top, BorderLayout.NORTH);
		Top.add(membership);
		
		ct.add(Center, BorderLayout.CENTER);
		Center.setLayout(new GridLayout(8,1));
		
		JPanel[] jp = new JPanel[8];
		
		for(int i = 0; i< 8; i ++) {
			jp[i] = new JPanel();
			jp[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			jp[i].setFont(new Font("",0,20));
			Center.add(jp[i]);
		}

		newID = new JTextField(10);
		newID.addActionListener(this);
		overLap = new JButton("ID확인");
		jp[0].add(new JLabel("ID                         : "));
		overLap.setFont(new Font("",0,15));
		overLap.addActionListener(this);
		newID.setFont(new Font("",0,20));
		jp[0].add(newID);
		jp[0].add(overLap, FlowLayout.RIGHT);
		jp[0].add(IDInf);
		
		newPassWd = new JPasswordField(10);
		newPassWd.setFont(new Font("",0,20));
		
		jp[1].add(new JLabel("PassWd              : "));
		jp[1].add(newPassWd);
		jp[1].add(passWdInf = new JLabel("PassWd는 영어,숫자 혼합 최소 6글자 최대 10글자입니다."));
		
		rePassWd = new JPasswordField(10);
		rePassWd.setFont(new Font("",0,20));
		rePassWd.addActionListener(this);
		PassWdCheck = new JButton("PW확인");
		PassWdCheck.setFont(new Font("",0,15));
		PassWdCheck.addActionListener(this);
		jp[2].add(new JLabel("PassWd_Check : "));
		jp[2].add(rePassWd);
		jp[2].add(PassWdCheck);
		
		newName =new JTextField(10);
		newName.setFont(new Font("",0,20));
		jp[3].add(new JLabel("Name                  : "));
		jp[3].add(newName);
		
		newAge = new JTextField(10);
		newAge.setFont(new Font("",0,20));
		jp[4].add(new JLabel("age                      : "));
		jp[4].add(newAge);
		
		newHP = new JTextField(10);
		newHP.setFont(new Font("",0,20));
		jp[5].add(new JLabel("H.P                      : "));
		jp[5].add(newHP); 
		
		TextArea terms = new TextArea();
		jp[6].add(terms);
		
		sure = new JCheckBox("동의 하시겠습니까?");
		sure.addItemListener(this);
		jp[7].add(sure);
		
		ct.add(Bottom, BorderLayout.SOUTH);
		Bottom.setLayout(new FlowLayout());
		Ok = new JButton("확인");
		Cancel = new JButton("취소");
		Ok.addActionListener(this);
		Cancel.addActionListener(this);
		Bottom.add(Ok);
		Bottom.add(Cancel);
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED)
			selected = 1;
		else
			selected = -1;
	}
	
	public void actionPerformed(ActionEvent e){
		
		String btn = e.getActionCommand();
		String t_id = "", t_passwd = "", t_name = "", t_age = "", t_HP = "", t_rePassWd = "" ;
		String strSql;
		t_id = newID.getText();
		t_passwd=newPassWd.getText();
		t_name = newName.getText();
		t_age = newAge.getText();
		t_HP = newHP.getText();
		t_rePassWd = rePassWd.getText();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC Driver connect complete");
		} catch(ClassNotFoundException c) {
			System.err.println("driver load failed");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcpp?serverTimezone=UTC","root","1234");
			System.out.println("db connetion complete");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC driver 정상적 연결");
			if(btn == "ID확인" || btn.equals(newID.getText().toString())) {
				if(newID.getText().length() >= 6 && newID.getText().length()<=10) {
					IDInf.setForeground(Color.BLACK);
					IDRule = 1;
					strSql = "SELECT * FROM user WHERE id='"+t_id+"';";
					ResultSet result = dbSt.executeQuery(strSql);
					if(result.next()) {
						MessageDialog msg = new MessageDialog(this,"중복확인",true, "중복입니다.");
						msg.setLocation(250, 380);
						msg.show();
						newID.setText("");
					}else {
						IDCheck = 1;
						MessageDialog msg = new MessageDialog(this,"중복확인",true, "사용가능합니다.");
						msg.setLocation(250, 380);
						msg.show();
					}
				}else {
					IDInf.setForeground(Color.RED);
				}
			} else if (btn == "PW확인" || btn.equals(rePassWd.getText().toString())) {
				if(rePassWd.getText().length() >= 6 && rePassWd.getText().length() <= 10) {
					System.out.println(newPassWd.getText().length()+newPassWd.getText());
					if(englishCheck(newPassWd.getText())) {
						passWdInf.setForeground(Color.black);
						System.out.println(newPassWd.getText().length()+newPassWd.getText());
						passWdRule=1;
						if (t_passwd.equals(t_rePassWd)) {
							MessageDialog msg = new MessageDialog(this,"일치여부",true, "사용가능합니다.");
							msg.setLocation(250, 380);
							msg.show();
							passWdCheck = 1;
						} else {
							MessageDialog msg = new MessageDialog(this,"일치여부",true, "일치하지 않습니다.");
							msg.setLocation(250, 380);
							msg.show();
							newPassWd.setText("");
							rePassWd.setText("");
						}
					}else {
						passWdInf.setForeground(Color.red);
					}
				}else {
					passWdInf.setForeground(Color.red);
				}
			} else if(btn == "확인") {
				String time = "00:00";
				int age = Integer.parseInt(t_age);
				if(IDCheck == 1 || IDRule == 1) {
					if(passWdCheck == 1 || passWdRule == 1) {
						if(selected == 1) {
							strSql = "INSERT INTO user VALUES('"+t_id+"','"+t_passwd+"','"+t_name+"','"+age+"','"+t_HP+"','00:00');";
							dbSt.executeUpdate(strSql);
							MessageDialog msg = new MessageDialog(this,"회원가입완료",true, "가입이 완료 되었습니다.");
							msg.setLocation(250, 380);
							msg.show();
							dispose();
						} else {
							MessageDialog msg = new MessageDialog(this,"회원가입실패",true, "동의여부 확인 부탁드립니다.");
							msg.setLocation(250, 380);
							msg.show();
						}
					}
					else {
						MessageDialog msg = new MessageDialog(this,"회원가입실패",true, "비밀번호 확인 부탁드립니다.");
						msg.setLocation(250, 380);
						msg.show();
					}
				}else {
					MessageDialog msg = new MessageDialog(this,"회원가입실패",true, "아이디 확인 부탁드립니다.");
					msg.setLocation(250, 380);
					msg.show();
				}
			} else if(btn == "취소") {
				newID.setText("");
				newPassWd.setText("");
				newName.setText("");
				newAge.setText("");
				newHP.setText("");
				rePassWd.setText("");
			}
			dbSt.close();
			con.close();
		} catch (SQLException sq) {
			System.out.println("SQLException : " + sq.getMessage());
		}
	}
	boolean englishCheck(String str){
		int English = 0, Number = 0;
		for(int i =0; i< str.length();i++) {
			char c = str.charAt(i);
			boolean b = Character.isDigit(c);
			if(b== true) {
				English++;
			}else {
				Number++;
			}
		}
		if(English == 0 || Number == 0) {
			return false;
		}else
			return true;
	}
}
// 새로운 회원 가입 메뉴이다 회원가입시 아이디 중복체크와 비밀번호 일치여부 제약 성립 확인 동의여부를 판단후 가입시킨다.
class MessageDialog extends JDialog implements ActionListener {
	Button OK;
	MessageDialog(JFrame parent, String title, boolean mode, String msg){
		super(parent,title,mode);
		JPanel pc = new JPanel();
		JLabel label = new JLabel(msg);
		pc.add(label);
		add(pc, BorderLayout.CENTER);
		JPanel ps = new JPanel();
		OK = new Button("ok");
		OK.addActionListener(this);
		ps.add(OK);
		add(ps, BorderLayout.SOUTH);
		pack();
	}
	public void actionPerformed(ActionEvent ae) {
		dispose();
	}
}
// 메세지 다일로그 경고 및 알람을 해준다
/******************************************************************/
/* -------------------- 기본 ---------------------------------*/
class UseDB //데이터베이스 조회 클래스
{
	Statement dbSt;
	Connection con;
	void updateDB(String strSql) throws SQLException,ClassNotFoundException //DB 수정 메소드
	{
		Class.forName("com.mysql.jdbc.Driver");  // mysql의 jdbc Driver 연결
		System.err.println("MYSQL JDBC 드라이버를 정상적으로 로드함");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/PCPP?serverTimezone=UTC", "root", "1234");
		System.out.println("DB 연결 완료."); 
		dbSt = con.createStatement();
		System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
		dbSt.executeUpdate(strSql); // sql 질의어 실행
	}
	ResultSet queryDB(String strSql) throws SQLException,ClassNotFoundException //DB 조회 메소드
	{
		Class.forName("com.mysql.jdbc.Driver");  // mysql의 jdbc Driver 연결
		System.err.println("MYSQL JDBC 드라이버를 정상적으로 로드함");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/PCPP?serverTimezone=UTC", "root", "1234");
		System.out.println("DB 연결 완료."); 
		dbSt = con.createStatement();
		System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
		ResultSet result = dbSt.executeQuery(strSql);
		return result;
	}
	void closeDB() throws SQLException //DB 종료 메소드
	{
		dbSt.close();       
		con.close();   
		System.out.println("DB 종료");
	}
}
class Overlap extends JDialog implements ActionListener //알림창 클래스
{
	JButton ok;
	Overlap(Frame parent, String title, boolean flag, String msg){
		super(parent,title,flag);
		JPanel p = new JPanel();
		JLabel l1 = new JLabel(msg);
		ok = new JButton("OK");
		ok.addActionListener(this);
		p.add(l1);
		p.add(ok);
		add(p,BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
	}
	public void actionPerformed(ActionEvent ae) {
		this.dispose();
	}
}
/* --------------------- 기본 ------------------------------- */
/* ----------------------메뉴 수정 시작------------------------------- */
class MenuEditForm extends JFrame implements ListSelectionListener, ActionListener //메뉴 수정 화면 클래스
{
	JList jlst_menu;
	JTable jtab_menuSelect;
	String[] header = {"번호", "상품명", "가격", "재고"};
	String select; //메뉴구분이 선택되면 저장할 변수
	int lselect; //메뉴구분의 인덱스를 저장할 변수
	DefaultTableModel model; //메뉴테이블을 구성할 모델
	DefaultListModel lmodel;  //메뉴구분리스트를 구성할 모델
	MenuEditForm()
	{
		select = "";
		model = new DefaultTableModel(header,0) //사용자가 수정할수 없게 만듬
		{
			public boolean isCellEditable(int row, int col)
			{
				return false;
			}
		};
		lmodel = new DefaultListModel();

		Font f2 = new Font("돋움", Font.BOLD, 20);
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());

		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel center = new JPanel();
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane sc;
		center.setLayout(null);

		JLabel l1 = new JLabel("          메뉴구분                                        메   뉴      ");
		JLabel l2 = new JLabel("수정결과를 확인하려면 갱신을 눌러야합니다.");
		JButton jbtn_reNew = new JButton("갱신");

		JButton jbtn_sectionEdit = new JButton("수정");
		JButton jbtn_sectionAdd = new JButton("추가");
		JButton jbtn_sectionDelete = new JButton("삭제");
		JButton jbtn_menuEdit = new JButton("메뉴수정");
		JButton jbtn_menuAdd = new JButton("메뉴추가");
		JButton jbtn_menuDelete = new JButton("메뉴삭제");

		try
		{
			reNewSection();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
	
		jlst_menu = new JList();
		jlst_menu.setModel(lmodel);
		jlst_menu.addListSelectionListener(this);
		jtab_menuSelect = new JTable(model);

		jlst_menu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //하나만 선택하게함
		jtab_menuSelect.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sc = new JScrollPane(jtab_menuSelect);

		jbtn_reNew.addActionListener(this);
		jbtn_sectionEdit.addActionListener(this);
		jbtn_sectionAdd.addActionListener(this);
		jbtn_sectionDelete.addActionListener(this);
		jbtn_menuEdit.addActionListener(this);
		jbtn_menuAdd.addActionListener(this);
		jbtn_menuDelete.addActionListener(this);

		top.add(l1);
		top.add(jbtn_reNew);
		center.add(l2);
		center.add(jlst_menu);
		center.add(sc);
		bottom.add(jbtn_sectionEdit);
		bottom.add(jbtn_sectionAdd);
		bottom.add(jbtn_sectionDelete);
		bottom.add(new JLabel("                      "));
		bottom.add(jbtn_menuEdit);
		bottom.add(jbtn_menuAdd);
		bottom.add(jbtn_menuDelete);
			
		l2.setBounds(200,20,300,20);
		jlst_menu.setBounds(70,50,100,400);
		sc.setBounds(300,50,400,400);
		l1.setFont(f2);
		jbtn_sectionEdit.setFont(f2);
		jbtn_sectionAdd.setFont(f2);
		jbtn_sectionDelete.setFont(f2);
		jbtn_menuEdit.setFont(f2);
		jbtn_menuAdd.setFont(f2);
		jbtn_menuDelete.setFont(f2);

		ct.add(top, BorderLayout.NORTH);
		ct.add(center, BorderLayout.CENTER);
		ct.add(bottom, BorderLayout.SOUTH);

		setTitle("메뉴 수정");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
	}

	void reNewSection() throws SQLException,ClassNotFoundException //메뉴구분을 갱신하는 메소드
	{
		UseDB db = new UseDB();
		lmodel.removeAllElements();
		String strSql = "select * from menu_section order by number;";
		ResultSet result = db.queryDB(strSql);
		while(result.next())
		{
			lmodel.addElement(result.getString("section"));
		}
		db.closeDB();
	}

	void reNewMenu() throws SQLException,ClassNotFoundException//메뉴를 갱신하는 메소드
	{
		UseDB db = new UseDB();
		model.setNumRows(0); //model의 rows를 0으로 만듦으로서 기존 데이터 초기화
		String strSql = "select * from menu where section='"+select+"' order by price;";
		ResultSet result = db.queryDB(strSql);
		String t_name, t_price, t_stock;
		int k = 1; //테이블에 출력할 번호
		Object t[] = new Object[4];
		while(result.next())
		{
			int i = 0;
			t[i] = k++;
			t[++i] = result.getString("name");
			t[++i] = result.getString("price");
			t[++i] = result.getString("stock");
			model.addRow(t);
		}
		db.closeDB();
	}
	public void valueChanged(ListSelectionEvent lse)  //리스트 (메뉴구분) 이벤트
	{
		if (!lse.getValueIsAdjusting()) return; //ListSelectionEvent가 2번 실행되지 않도록 함
		if (lse.getSource() == jlst_menu)
		{
			select = jlst_menu.getSelectedValue().toString(); //메뉴 선택값을 가져옴
			lselect = jlst_menu.getSelectedIndex(); //선택한 메뉴의 인덱스를 가져옴
			try
			{
				reNewMenu();
			}
			catch(ClassNotFoundException e) 
			{
				System.err.println("드라이버 로드에 실패했습니다."); 
			}
			catch (SQLException e)
			{
				System.out.println("SQLException : " + e.getMessage()); 
			}
		}
	}
	public void actionPerformed(ActionEvent ae) //액션(버튼) 이벤트
	{
		String s = ae.getActionCommand(); //버튼의 이름을 가져옴
		try
		{
			if (s.contains("갱신")) //갱신버튼 이벤트 실행
			{
				reNewSection();
				jlst_menu.setSelectedIndex(lselect);
				reNewMenu();
			}
			else if (s.contains("메뉴")) //메뉴의 수정, 삭제, 추가 이벤트 실행
			{
				if (s.contains("수정"))
				{
					MenuEdit edit = new MenuEdit(s,jlst_menu.getSelectedValue().toString(), jtab_menuSelect.getSelectedRow(), jtab_menuSelect.getModel());
					edit.show();
				}
				else if (s.contains("삭제"))
				{
					MenuEdit edit = new MenuEdit(s,jlst_menu.getSelectedValue().toString(), jtab_menuSelect.getSelectedRow(), jtab_menuSelect.getModel());
					edit.show();
				}
				else if (s.contains("추가"))
				{
					MenuEdit edit = new MenuEdit(s);
					edit.show();
				}
			}
			else //메뉴구분의 수정, 삭제, 추가 이벤트 실행
			{
				if (s.contains("수정")) 
				{
					SectionEdit edit = new SectionEdit(s,jlst_menu.getSelectedValue().toString());
					edit.show();
				}
				else if (s.contains("삭제"))
				{
					SectionEdit edit = new SectionEdit(s,jlst_menu.getSelectedValue().toString());
					edit.show();
				}
				else if (s.contains("추가"))
				{
					SectionEdit edit = new SectionEdit(s,"");
					edit.show();
				}
			}
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (NullPointerException e) //선택을 하지않고 버튼을 눌렀을 경우 예외처리
		{
			alert("Warning","메뉴 혹은 메뉴와 메뉴구분을 선택하고 버튼을 누르세요.");
		}
		catch (ArrayIndexOutOfBoundsException e) //선택을 하지않고 버튼을 눌렀을 경우 예외처리
		{
			alert("Warning","메뉴 혹은 메뉴와 메뉴구분을 선택하고 버튼을 누르세요.");
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
	}
	void alert(String title, String str){
		Overlap ol = new Overlap(this,title,true,str);
		ol.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);            
		ol.show();
	}
}
class MenuEdit extends JFrame implements ActionListener //메뉴를 수정하는 클래스
{
	Container ct;
	String[] values;
	String title,select;
	JLabel[] jlb_values;
	JTextField[] jtf_edit;
	MenuEdit(String title, String section, int row, TableModel table){ //메뉴를 수정하거나 생성할때 사용되는 생성자
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();

		title = title;
		select = (String)table.getValueAt(row,1);
		values = new String[4];
		values[0] = section;
		values[1] = select;
		values[2] = table.getValueAt(row,2).toString();
		values[3] = table.getValueAt(row,3).toString();
		
		jlb_values = new JLabel[4];
		jtf_edit = new JTextField[4];
		jlb_values[0] = new JLabel("                             구분 : " + values[0] + "          ");
		jtf_edit[0] = new JTextField(8);
		jlb_values[1] = new JLabel("이름 : " + values[1] + "          ");
		jtf_edit[1] = new JTextField(10);
		jlb_values[2] = new JLabel("가격 : " + values[2] + "              ");
		jtf_edit[2] = new JTextField(8);
		jlb_values[3] = new JLabel("재고 : " + values[3] + "              ");
		jtf_edit[3] = new JTextField(8);
		JButton jbtn_edit = new JButton(title);

		p2.add(new JLabel(title + " 내용 : "));
		for (int i = 0; i < jlb_values.length ; i++ )
		{
			jtf_edit[i].setText(values[i]);
			p1.add(jlb_values[i]);
			p2.add(jtf_edit[i]);
		}
		p3.add(new JLabel(title + "할 내용을 입력하세요"));
		p3.add(jbtn_edit);
		ct.add(p1);
		ct.add(p2);
		ct.add(p3);

		jbtn_edit.addActionListener(this);
		setTitle(title);
		setSize(570,150);
		setLocationRelativeTo(null);
	}
	MenuEdit(String title){ //메뉴를 삭제할때 사용되는 생성자
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();

		title = title;
		values = new String[4];
		jlb_values = new JLabel[4];
		jtf_edit = new JTextField[4];
		jlb_values[0] = new JLabel("                             구분 :           ");
		jtf_edit[0] = new JTextField(8);
		jlb_values[1] = new JLabel("이름 :           ");
		jtf_edit[1] = new JTextField(10);
		jlb_values[2] = new JLabel("가격 :               ");
		jtf_edit[2] = new JTextField(8);
		jlb_values[3] = new JLabel("재고 :               ");
		jtf_edit[3] = new JTextField(8);
		JButton jbtn_edit = new JButton(title);

		p2.add(new JLabel(title + " 내용 : "));
		for (int i = 0; i < jlb_values.length ; i++ )
		{
			p1.add(jlb_values[i]);
			p2.add(jtf_edit[i]);
		}
		p3.add(new JLabel(title + "할 내용을 입력하세요"));
		p3.add(jbtn_edit);
		ct.add(p1);
		ct.add(p2);
		ct.add(p3);

		jbtn_edit.addActionListener(this);
		setTitle(title);
		setSize(570,150);
		setLocationRelativeTo(null);
	}
	public void actionPerformed(ActionEvent ae) //버튼의 이벤트
	{
		String btn = ae.getActionCommand()+"";
		System.out.println(btn + values[0]);
		if (!btn.contains("추가"))
		{
			if (values[0].equals("삭제목록") && !btn.contains("수정"))
			{
				alert("Warning","삭제목록은 수정이 불가능합니다.");
				return;
			}
		}
		try
		{
			if (btn.contains("수정"))
				update();
			else if (btn.contains("삭제"))
				delete();
			else if (btn.contains("추가"))
				insert();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage());
		}
		catch (NumberFormatException e) //insert 메소드의 예외처리
		{
			alert("Warning","가격과 재고가 숫자가 아니면 추가가 불가능합니다.");
		}
		
	}
	void update() //메뉴 수정
	{
		UseDB db = new UseDB();
		for (int i = 0; i < values.length; i++ )
		{
			values[i] = jtf_edit[i].getText();
			if (values[i].isEmpty())
			{
				alert("Warning","메뉴정보를 전부 입력해야 수정이 가능합니다.");
				return;
			}
		}
		
		try
		{
			String strSql = "update menu set section='" + values[0] + "', name='" + values[1] + "', price=" 
				+ Integer.parseInt(values[2])+ ", stock=" + Integer.parseInt(values[3]) + " where name='"+select+"';"; //검색한 것을 수정함
		db.updateDB(strSql);
		db.closeDB();
		dispose();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage());
			alert("Warning","구분이 존재하지 않거나, 이름이 중복되면 수정이 불가능합니다.");
		}
	}
	void insert() throws SQLException, NumberFormatException,ClassNotFoundException //메뉴 추가
	{
		UseDB db = new UseDB();
		for (int i = 0; i < values.length ; i++ )
		{
			values[i] = jtf_edit[i].getText();
		}
		boolean row = false;
		String strSql = "select * from menu_section where section='"+ values[0] +"';";
		ResultSet result = db.queryDB(strSql); // sql 질의어 실행
		while(result.next()) //메뉴 구분이 정확한지 확인하는 과정
		{
			row = true;
		}
		strSql = "select * from menu where name='"+ values[1] +"' order by price;";
		result = db.queryDB(strSql); // sql 질의어 실행
		while(result.next()) //중복된 메뉴 이름이 있는지 확인하는 과정
		{
			row = false;
		}
		if (!row) //메뉴구분이 틀리거나, 메뉴이름이 존재하는 경우 예외발생
		{
			alert("Warning","입력한 이름이 있거나, 메뉴구분이 없으면 추가가 불가능합니다.");
			return;
		}
		if (values[2].isEmpty() || values[3].isEmpty())
		{
			alert("Warning","가격과 재고가 숫자가 아니면 추가가 불가능합니다.");
		}
		else 
		{
			Integer.parseInt(values[2]);
			Integer.parseInt(values[3]);
		}
		strSql = "insert ignore into menu values ('" + values[0] +"', '" + values[1] + "', "+ values[2] +", "+ values[3] +");"; //검색한 것을 수정함
		db.updateDB(strSql); // sql 질의어 실행
		db.closeDB();
		dispose();
	}
	void delete() throws SQLException,ClassNotFoundException //메뉴 삭제
	{
		UseDB db = new UseDB();
		boolean deletion = false; //정보를 정확하게 입력했는지 확인할 변수, 다르면 삭제 불가
		boolean exist = false; //거래내역이 존재하는지 확인할 변수, 존재하면 삭제 불가
		String tuple[] = new String[4];
		for (int i = 0; i < jtf_edit.length ; i++ )
		{
			tuple[i] = (String)jtf_edit[i].getText();
			if (tuple[i].isEmpty() || !tuple[i].equals(values[i]))
			{
				deletion = true;
			}
		}
		String strSql = "select * from trade_detail where goods='"+ select +"';";
		ResultSet result = db.queryDB(strSql); // sql 질의어 실행
		if (result.next())
			exist = true;
		if (deletion)
		{
			alert("Warning","메뉴정보를 정확하게 입력해야 삭제가 가능합니다.");
			return;
		}
		if (exist)
		{
			alert("Warning","거래정보가 존재하면 삭제할 수 없습니다.");
			return;
		}
		strSql = "delete from menu where name='" + tuple[1] + "';"; //검색한 것을 삭제함
		db.updateDB(strSql); // sql 질의어 실행
		db.closeDB();
		dispose();
	}
	void alert(String title, String str){
		Overlap ol = new Overlap(this,title,true,str);
		ol.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);            
		ol.show();
	}
}
class SectionEdit extends JFrame implements ActionListener //메뉴구분을 수정하는 클래스
{
	Container ct;
	String title;
	JLabel jlb_select;
	JTextField jtf_edit;

	SectionEdit(String title, String item){ 
		this.title = title;
		setTitle(title);
		setSize(400,100);
		setLocationRelativeTo(null);

		ct = getContentPane();
		ct.setLayout(new FlowLayout());

		jlb_select = new JLabel(item);
		jtf_edit = new JTextField(item,10);
		JButton jbtn_edit = new JButton(title);

		ct.add(new JLabel(title + " 내용 : "));
		jbtn_edit.addActionListener(this);
		jtf_edit.addActionListener(this);
		ct.add(jlb_select);
		ct.add(jtf_edit);
		ct.add(jbtn_edit);
		ct.add(new JLabel(title + "할 내용을 입력하세요"));
	}
	public void actionPerformed(ActionEvent ae){ //버튼의 이벤트
		if (jlb_select.getText().equals("삭제목록"))
		{
			alert("Warning","삭제목록은 수정이 불가능합니다.");
			return;
		}
		try
		{
			if (title.contains("수정"))
				update();
			else if (title.contains("삭제"))
				delete();
			else if (title.contains("추가"))
				insert();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage());
		}
	}
	void update() throws SQLException,ClassNotFoundException //메뉴구분 수정
	{
		String select = jlb_select.getText();
		String edit = jtf_edit.getText();
		UseDB db = new UseDB();
		if (edit.isEmpty())//입력값을 확인하고 입력값으로 값을 변경함
		{
			alert("Warning","수정할 내용을 입력해야합니다.");
			return;
		}
		String strSql = "select * from menu_section order by number;";
		ResultSet result = db.queryDB(strSql);
		while(result.next())
		{
			if ((result.getString("section")+"").equals(edit))
			{
				alert("Warning","기존 이름과 중복되면 수정할 수 없습니다.");
				return;
			}
		}
		strSql = strSql = "update menu_section set section='" + edit + "' where section='"+select+"' order by number;";
		db.updateDB(strSql); // sql 질의어 실행
		db.closeDB();
		dispose();
	}
	void insert() throws SQLException,ClassNotFoundException //메뉴구분 추가
	{
		String edit = jtf_edit.getText();
		UseDB db = new UseDB();
		Vector section = new Vector(); //db 테이블의 데이터를 저장할 배열
		Vector number = new Vector();
		
		String strSql = "select * from menu_section where number!='0' order by number;";
		ResultSet result = db.queryDB(strSql);
		while(result.next())
		{
			section.add(result.getString("section")); 
			number.add(result.getString("number"));
		}
		//입력값이 없거나, 이미 DB 테이블에 존재하거나, 튜플이 9개가 존재할 경우 예외발생
		if (edit.isEmpty()) 
		{
			alert("Warning","입력이 없으면 추가가 불가능합니다.");
			return;
		}
		else if (number.size() >= 9)
		{
			alert("Warning","추가는 9개를 넘을 수 없습니다.");
			return;
		}
		for (int i = 0; i < section.size(); i++ )
		{
			if (edit.equals(section.get(i)))
			{
				alert("Warning","입력한 이름이 있으면 추가가 불가능합니다.");
				return;
			}
		}
		int row = 1; //1부터 9까지만 메뉴에 추가 가능, 추가할 튜플의 기본키가 될 값
		for (int i = 0; i < number.size(); i++ )
		{
			int t = Integer.parseInt(number.get(i)+"");
			if ( t != row)	//기본키가 있으면 row를 하나 증가, 아니라면 해당 row로 튜플 추가
				break;
			else
				row++;
		}
		strSql = "insert ignore into menu_section values ('" + row +"', '" + edit + "');"; //검색한 것을 수정함
		db.updateDB(strSql); // sql 질의어 실행
		db.closeDB();
		dispose();
	}
	void delete() throws SQLException,ClassNotFoundException //메뉴구분 삭제
	{
		String select = jlb_select.getText();
		String edit = jtf_edit.getText();
		UseDB db = new UseDB();
		int row = 0; //해당 튜플과 참조된 메뉴가 있는지 확인할 변수
		
		String strSql = "select * from menu where section='"+select+"';";
		ResultSet result = db.queryDB(strSql);
		while (result.next())
			row++;
	
		if (row != 0 || !select.equals(edit)) //참조되는 튜플이 존재하거나, 삭제할 이름이 정확하지 않으면 예외발생
		{
			alert("Warning","상품이 없거나 정확하게 입력해야 삭제가 가능합니다.");
			return;
		}
		strSql = "delete from menu_section where section='" + select + "';";
		db.updateDB(strSql);
		db.closeDB();
		dispose();
	}
	void alert(String title, String str){
		Overlap ol = new Overlap(this,title,true,str);
		ol.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);            
		ol.show();
	}
}
/* ----------------------메뉴 수정 끝--------------------------------- */
/* ------------------------------- 주문 시작 ------------------------------- */
class OrderForm extends JFrame implements ListSelectionListener, ActionListener //주문 화면 클래스
{
	JButton jbtn_goodsSearch;
    JButton jbtn_cashPay;
    JButton jbtn_creditPay;
	JButton jbtn_select;
	JButton jbtn_cancel;
	JButton jbtn_reset;

    JLabel jlb_id;
    JLabel jlb_num;
    JLabel jlb_pay;

    JList jlst_menu;
	JTable jtab_menuSelect;
    DefaultTableModel model;
	DefaultTableModel model2;
	String[] header = {"상품명", "가격",  "재고"};
	String[] header2 = {"상품명", "수량",  "가격"};

    JTable jtab_list;
    JTextField jtf_goods;

	int payment; //결제 금액을 저장할 변수
	String search; //검색한 상품의 이름을 저장할 변수
	String id; //회원의 정보
	
	DefaultListModel section; 
	//메뉴의 숫자가 변경될 수 있으므로 Vector 사용
	OrderForm(String user_id)
	{
		id = user_id;
		payment = 0;
		search = "";
		Font f1 = new Font("돋움", Font.BOLD, 15);
		Font f2 = new Font("돋움", Font.BOLD, 20);
		Font f3 = new Font("돋움", 0, 15);
		Container ct = getContentPane();
		ct.setLayout(null);

		section = new DefaultListModel();

		model = new DefaultTableModel(header,0)
		{
			public boolean isCellEditable(int row, int col)
			{
				return false;
			}
		};
		model2 = new DefaultTableModel(header2,0)
		{
			public boolean isCellEditable(int row, int col)
			{
				return false;
			}
		};
		
		{//최초에 메뉴구분 DB에서 가져오기
			try
			{
				UseDB db = new UseDB();
				String strSql = "select * from menu_section where section!='시간' and section!='삭제목록' order by number;";
				ResultSet result = db.queryDB(strSql); // sql 질의어 실행
				while(result.next())
				{
					section.addElement(result.getString("section"));
				}
				db.closeDB();
			}
			catch(ClassNotFoundException e) 
			{
				System.err.println("드라이버 로드에 실패했습니다."); 
			}
			catch (SQLException e)
			{
				System.out.println("SQLException : " + e.getMessage()); 
			}
		}
		//컴포넌트 생성
			jlb_id = new JLabel("ID : " + id);
			JLabel l1 = new JLabel("메뉴");
			JLabel l2 = new JLabel("메뉴 선택");
			JLabel l3 = new JLabel("주문내역");
			jlst_menu = new JList(section);
			jtab_menuSelect = new JTable(model);
			JScrollPane sc= new JScrollPane(jtab_menuSelect);
			jbtn_select = new JButton("추가>>");
			jbtn_cancel = new JButton("<<삭제");
			jtab_list = new JTable(model2);
			JScrollPane sc2= new JScrollPane(jtab_list);
			JLabel l4 = new JLabel("결제 금액   \\");
			jlb_pay = new JLabel();
			jtf_goods = new JTextField();
			jbtn_goodsSearch = new JButton("상품검색");
			jbtn_reset = new JButton("취소");
			jbtn_cashPay = new JButton("결제");
		{//컨테이너에 추가
			ct.add(jlb_id);
			ct.add(l1);
			ct.add(l2);
			ct.add(l3);
			ct.add(jlst_menu);
			ct.add(sc);
			ct.add(jbtn_select);
			ct.add(jbtn_cancel);
			ct.add(sc2);
			ct.add(l4);
			ct.add(jlb_pay);
			ct.add(jtf_goods);
			ct.add(jbtn_goodsSearch);
			ct.add(jbtn_reset);
			ct.add(jbtn_cashPay);
		}
		{//위치,크기 설정
			jlb_id.setBounds(20, 20, 200, 20);
			l1.setBounds(20, 55, 130, 20);
			l2.setBounds(200, 55, 130, 20);
			l3.setBounds(490, 55, 130, 20);
			jlst_menu.setBounds(20, 80, 100, 280);
			sc.setBounds(140, 80, 250, 280);
			jbtn_select.setBounds(395, 140, 90, 40);
			jbtn_cancel.setBounds(395, 190, 90, 40);
			sc2.setBounds(490, 80, 270, 320);
			l4.setBounds(490, 410, 170, 30);
			jlb_pay.setBounds(660, 410, 170, 30);
			jtf_goods.setBounds(40, 430, 150, 30);
			jbtn_goodsSearch.setBounds(230, 410, 100, 70);
			jbtn_reset.setBounds(490, 440, 100, 80);
			jbtn_cashPay.setBounds(660, 440, 100, 80);
		}
		{//글자 스타일 설정
			jlb_id.setFont(f2);
			l1.setFont(f2);
			l2.setFont(f2);
			l3.setFont(f2);
			jlst_menu.setFont(f3);
			jtab_menuSelect.setFont(f3);
			l4.setFont(f2);
			jlb_pay.setFont(f2);
			jtf_goods.setFont(f2);
			jbtn_goodsSearch.setFont(f1);
			jbtn_reset.setFont(f1);
			jbtn_cashPay.setFont(f1);
		}
		
		jtf_goods.setText("");
		jlst_menu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlst_menu.addListSelectionListener(this);
		jbtn_select.addActionListener(this);
		jbtn_cancel.addActionListener(this);
		jbtn_reset.addActionListener(this);
		jbtn_goodsSearch.addActionListener(this);
		jtf_goods.addActionListener(this);
		jbtn_cashPay.addActionListener(this);
		
		totalPay();
		setTitle("주문");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
	}

	public void valueChanged(ListSelectionEvent lse)  //리스트 이벤트
	{
		if (!lse.getValueIsAdjusting()) return; //ListSelectionEvent가 2번 실행되지 않도록 함
		try
		{
			if (lse.getSource() == jlst_menu)
				sectionSelect(jlst_menu.getSelectedValue().toString());
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
	}
	public void actionPerformed(ActionEvent ae) //버튼 이벤트
	{
		try
		{
			if (ae.getActionCommand().contains("결제"))
				payments();
			else if (ae.getActionCommand().contains("추가"))
				menuSelect();
			else if (ae.getActionCommand().contains("취소"))
				model2.setRowCount(0); //선택 테이블의 row값을 0을 주어 초기화
			else if (ae.getActionCommand().contains("삭제"))
				menuCancel();
			else if (ae.getActionCommand().contains("검색") || ae.getSource() == jtf_goods)
				searchGoods();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
		totalPay();
	}
	void payments() throws ClassNotFoundException, SQLException //결제버튼의 이벤트 처리
	{
		UseDB db = new UseDB();
		payment = 0;
		DefaultTableModel table = (DefaultTableModel)jtab_list.getModel();
		if (table.getRowCount() == 0) return;
		for (int i = 0; i < table.getRowCount() ; i++ )
		{
			payment += Integer.parseInt(table.getValueAt(i,2)+"");
		}
		jlb_pay.setText(String.format("%,d",payment));
		String strSql = "select date_format(now(), '%Y-%m-%d %H:%i:%s') as 'ex_time';";
		ResultSet result = db.queryDB(strSql);
		result.next();
		String cur_time = result.getString("ex_time");
		strSql = "insert into trade (date, id, payment) values ('" + cur_time +"', '" + id + "'," + payment +");";
		db.updateDB(strSql);
		
		strSql = "select number from trade where date='"+ cur_time +"';";
		result = db.queryDB(strSql);
		result.next();
		int num = result.getInt("number");

		for (int i = 0; i < table.getRowCount() ; i++ )
		{
			String iname = "";
			strSql = "insert into trade_detail values (" + num +", '" + table.getValueAt(i,0) + "'," + table.getValueAt(i,1) +"," + table.getValueAt(i,2) +");";
			db.updateDB(strSql); 
			strSql = "update menu set stock=stock-" + table.getValueAt(i,1) + " where name='" + table.getValueAt(i,0) + "';";
			db.updateDB(strSql); 
		}
		db.closeDB();
		model2.setRowCount(0);
		sectionSelect(jlst_menu.getSelectedValue().toString());
	}
	void sectionSelect(String select) throws ClassNotFoundException, SQLException //메뉴구분을 선택했을 때 이벤트 처리
	{
		UseDB db = new UseDB();
		Vector menu_name, menu_price, menu_stock;
		menu_name = new Vector();
		menu_price = new Vector();
		menu_stock = new Vector();
		String strSql = "select * from menu where section='"+select+"' order by price;";
		ResultSet result = db.queryDB(strSql); // sql 질의어 실행
		while(result.next())
		{
			menu_name.add(result.getString("name"));
			menu_price.add(result.getString("price"));
			menu_stock.add(result.getString("stock"));
		}
		db.closeDB();
		model.setNumRows(0); //model의 rows를 0으로 만듦으로서 기존 데이터 초기화
		Object t[] = new Object[3];
		for(int i = 0, j = 0; i < menu_name.size(); i++)
		{
			t[j++] = menu_name.get(i);
			t[j++] = menu_price.get(i);
			t[j++] = menu_stock.get(i);
			model.addRow(t);
			j = 0;
		}
	}
	void menuSelect() //메뉴를 선택하고 추가했을 때 이벤트 처리
	{
		boolean flag = true;
		int row[] = jtab_menuSelect.getSelectedRows(); //선택된 메뉴의 인덱스를 가져옴
		DefaultTableModel table = (DefaultTableModel)jtab_menuSelect.getModel(); 
		DefaultTableModel ltable = (DefaultTableModel)jtab_list.getModel();
		Vector name, price, stock, lname, index;
		name = new Vector();
		price = new Vector();
		stock = new Vector();
		lname = new Vector();
		index = new Vector();
		for ( int i = 0; i < ltable.getRowCount(); i++ )
		{
			lname.add(ltable.getValueAt(i,0));
			index.add(i);
		}
		for ( int i = 0, j = 0; i < row.length ; i++ )
		{
			j = 0;
			name.add(table.getValueAt(row[i],j++));
			price.add(table.getValueAt(row[i],j++));
			stock.add(table.getValueAt(row[i],j++));
			for (int k = 0; k < lname.size() ; k++ )
			{
				if (lname.get(k) == name.get(i))
				{
					String t_name;
					int t_price, t_cnt;
					t_name = ltable.getValueAt((int)index.get(k),0)+"";
					t_cnt = Integer.parseInt(ltable.getValueAt((int)index.get(k),1)+"")+1;
					t_price = Integer.parseInt(ltable.getValueAt((int)index.get(k),2)+"") / (t_cnt-1);
					t_price *= t_cnt;
					if (t_cnt > Integer.parseInt(stock.get(i)+""))
					{
						flag = false;
						break;
					}
					model2.setValueAt(t_cnt,k,1);
					model2.setValueAt(t_price,k,2);
					flag = false;
				}
			}
			if(flag)
			{
				Vector t = new Vector();
				t.add(name.get(i));
				t.add(1);
				t.add(price.get(i));
				model2.addRow(t);
			}
			flag =true;
		}
	}
	void menuCancel() //메뉴를 선택하고 삭제했을 때 이벤트 처리
	{
		int row[] = jtab_list.getSelectedRows();
		DefaultTableModel table = (DefaultTableModel)jtab_list.getModel();
		for ( int i = row.length-1; i >= 0 ; i-- )
			table.removeRow(row[i]);
	}
	void totalPay() //메뉴가 변동되었을 때마다 결제금액을 갱신
	{
		payment = 0;
		DefaultTableModel table = (DefaultTableModel)jtab_list.getModel();
		for (int i = 0; i < table.getRowCount() ; i++ )
		{
			payment += Integer.parseInt(table.getValueAt(i,2)+"");
		}
		jlb_pay.setText(String.format("%,10d",payment));
	}
	void searchGoods() //메뉴이름을 검색했을 때 이벤트 처리
	{
		String s = jtf_goods.getText();
		SearchMenu sm = new SearchMenu(s);
		sm.show();
	}
	class SearchMenu extends JFrame implements ActionListener //검색한 메뉴 이름을 처리하고 새창으로 띄어주는 클래스
	{
		String goods;
		JTable jtab_menuSearch;
		JTextField jtf_search;
		DefaultTableModel model;
		String[] header = {"구분", "상품명", "가격", "재고"};
		JButton jbtn_chk, jbtn_search;
		String section, name;
		SearchMenu(String item)
		{
			goods = item;
			section = "";
			name = "";
			model = new DefaultTableModel(header,0)
			{
				public boolean isCellEditable(int row, int col)
				{
					return false;
				}
			};
			jtab_menuSearch = new JTable(model);
			JScrollPane sc= new JScrollPane(jtab_menuSearch);
			jbtn_chk = new JButton("선택");
			jtf_search = new JTextField(10);
			jbtn_search = new JButton("검색");

			Container ct = getContentPane();
			JPanel p1 = new JPanel();
			p1.add(new JLabel("검색 결과"));
			p1.add(jtf_search);
			p1.add(jbtn_search);
			ct.add(p1,BorderLayout.NORTH);
			ct.add(sc,BorderLayout.CENTER);
			ct.add(jbtn_chk,BorderLayout.SOUTH);

			jtab_menuSearch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//하나만 선택하게함

			jtf_search.addActionListener(this);
			jbtn_search.addActionListener(this);
			jbtn_chk.addActionListener(this);

			setSize(400,400);
			setTitle("메뉴 검색");
			setLocationRelativeTo(null);
			try
			{
				search(goods);
			}
			catch(ClassNotFoundException e) 
			{
				System.err.println("드라이버 로드에 실패했습니다."); 
			}
			catch (SQLException e)
			{
				System.out.println("SQLException : " + e.getMessage()); 
			}
		}
		public void actionPerformed(ActionEvent ae) //텍스트, 버튼 이벤트
		{
			try
			{
				if (ae.getSource() == jtf_search || ae.getSource() == jbtn_search)
					search(jtf_search.getText());
				else
					select();
			}
			catch(ClassNotFoundException e) 
			{
				System.err.println("드라이버 로드에 실패했습니다."); 
			}
			catch (SQLException e)
			{
				System.out.println("SQLException : " + e.getMessage()); 
			}
		}
		void search(String goods) throws ClassNotFoundException, SQLException //메뉴 검색 메소드
		{
			UseDB db = new UseDB();
			model.setRowCount(0);
			String strSql = " select * from menu where name like '%" + goods + "%' and section != '시간';";
			ResultSet result = db.queryDB(strSql); // sql 질의어 실행
			while(result.next())
			{
				Vector t = new Vector();
				t.add(result.getString("section"));
				t.add(result.getString("name"));
				t.add(result.getString("price"));
				t.add(result.getString("stock"));
				model.addRow(t);
			}
			db.closeDB();
		}
		void select() throws ClassNotFoundException, SQLException //검색결과 선택버튼 이벤트 처리
		{
			int t = -1;//초기화
			t = jtab_menuSearch.getSelectedRow();
			if (t == -1)
			{
				alert("Warning","메뉴를 선택해주세요.");
				return;
			}
			section = jtab_menuSearch.getValueAt(t,0)+"";
			name = jtab_menuSearch.getValueAt(t,1)+"";
			searchSelect();
			dispose();
		}
		void searchSelect() throws ClassNotFoundException, SQLException // 선택한 메뉴로 주문메뉴에서 선택
		{
			int sectionIndex = -1;
			DefaultListModel list = (DefaultListModel)jlst_menu.getModel();
			for ( int i = 0 ; i < list.getSize() ; i++ )
			{
				if ((list.getElementAt(i)+"").equals(section))
				{
					sectionIndex = i;
					break;
				}
			}
			jlst_menu.setSelectedIndex(sectionIndex);
			sectionSelect(jlst_menu.getSelectedValue().toString());
			sectionIndex = -1;
			DefaultTableModel table = (DefaultTableModel)jtab_menuSelect.getModel();
			for ( int i = 0 ; i < table.getRowCount() ; i++ )
			{
				if ((table.getValueAt(i,0)+"").equals(name))
				{
					sectionIndex = i;
					break;
				}
			}
			jtab_menuSelect.setRowSelectionInterval(sectionIndex,sectionIndex);
		}
		void alert(String title, String str)
		{
			Overlap ol = new Overlap(this,title,true,str);
			ol.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);            
			ol.show();
		}
	}
	void alert(String title, String str) //경고창을 띄어줄 메소드
	{
		Overlap ol = new Overlap(this,title,true,str);
		ol.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);            
		ol.show();
	}
}
/* ------------------------- 주문 끝 --------------------------------------------- */
/* ------------------------- 시간 추가 시작 --------------------------------------------- */
class TimeOrderForm extends JFrame implements ActionListener //시간 추가 화면 클래스
{
	int hour, minute, addHour, addMinute, payment;
	Vector name, price;
	String id;
	boolean flag;

	JLabel jlb_id;
	JLabel jlb_restTime;
	JLabel jlb_addTime;
	JLabel jlb_totalPay;

	TimeOrderForm(String user_id)
	{
		hour = 0; minute = 0; id = user_id; flag=true;//초기화
		addHour = 0; addMinute = 0; payment = 0;
		name = new Vector();
		price = new Vector();
		Font f1 = new Font("돋움", Font.BOLD, 15);
		Font f2 = new Font("돋움", Font.BOLD, 20);
		Container ct = getContentPane();
		JPanel left = new JPanel();
		JScrollPane sc = new JScrollPane(left);
		JPanel right = new JPanel();
		ct.setLayout(null);
		ct.add(sc);
		ct.add(right);
		sc.setBounds(50,70,300,450);
		right.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		right.setBounds(450,200,300,300);

		jlb_restTime = new JLabel();
		inquiryTime();

		JLabel jlb_title = new JLabel("시간 충전");
		jlb_id = new JLabel("   I  D    : "+id);
		
		jlb_addTime = new JLabel("추가시간 :  00 : 00");
		jlb_totalPay = new JLabel("합계요금 \\");

		JButton jbtn_cancel = new JButton("        취     소        ");
		JButton jbtn_cashPay = new JButton("        결     제        ");

		JButton[] jbtn_timeMenus;

		jlb_title.setBounds(50,10,150,50);
		jlb_title.setFont(f2);
		jlb_id.setFont(f2);
		jlb_restTime.setFont(f2);
		jlb_addTime.setFont(f2);
		jlb_totalPay.setFont(f2);
		jbtn_cancel.setFont(f2);
		jbtn_cashPay.setFont(f2);

		ct.add(jlb_title);
		right.add(jlb_id);
		right.add(jlb_restTime);
		right.add(jlb_addTime);
		right.add(jlb_totalPay);
		right.add(jbtn_cancel);
		right.add(jbtn_cashPay);

		jbtn_cancel.addActionListener(this);
		jbtn_cashPay.addActionListener(this);
		
		{ //시간의 메뉴 정보를 DB에서 가져와 버튼에 적용시키는 과정
			Vector menu_ = new Vector();
			try
			{
				UseDB db = new UseDB();
				String strSql = "select name,price from menu where section='시간' order by price;";
				ResultSet result = db.queryDB(strSql); // sql 질의어 실행
				String t_name, t_price, t_menu;
				while(result.next())
				{
					t_name = result.getString("name");
					name.add(t_name);
					price.add(result.getInt("price"));
					t_price = String.format("%,d",result.getInt("price"));
					t_menu = String.format("<html>%s<br>%s원</html>",t_name,t_price);
					menu_.add(t_menu);
				}
				db.closeDB();
			} 
			catch(ClassNotFoundException e) 
			{
				System.err.println("드라이버 로드에 실패했습니다."); 
			}
			catch (SQLException e)
			{
				System.out.println("SQLException : " + e.getMessage()); 
			}
			jbtn_timeMenus = new JButton[menu_.size()];
			left.setLayout(new GridLayout((menu_.size()/2+1),2,20,20));
			for(int i = 0; i < menu_.size(); i++) { //메뉴에 없는 버튼들
				jbtn_timeMenus[i] = new JButton();
				jbtn_timeMenus[i].setText(menu_.get(i)+"");
				jbtn_timeMenus[i].setFont(f1);
				jbtn_timeMenus[i].addActionListener(this);
				left.add(jbtn_timeMenus[i]);
			}
		}
		
		setTitle("시간 충전");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent ae) //시간 메뉴 버튼들 이벤트처리
	{
		String menus = ae.getActionCommand();
		String t_name,t_hour,t_minute = "";
		if (flag && menus.contains("시간"))
		{
			if (menus.isEmpty()) return;
			menus = menus.replace("<html>","");
			t_name = menus.split("<br>")[0];
			menus = menus.replace("원</html>","");
			menus = menus.replace(",","");
			if (menus.contains("분"))
			{
				String temp = menus.split("<br>")[0];
				t_hour = temp.split("시간")[0];
				temp = temp.split("시간")[1];
				t_minute = temp.split("분")[0];
				addHour =  Integer.parseInt(t_hour);
				addMinute +=  Integer.parseInt(t_minute);
			}
			else
			{
				t_hour = menus.split("시간<br>")[0];
				addHour +=  Integer.parseInt(t_hour);
			}

			if (t_minute.isEmpty())
				payment = Integer.parseInt(menus.split("시간<br>")[1]);
			else
				payment = Integer.parseInt(menus.split("분<br>")[1]);

			flag = false;
		}
		else if (ae.getActionCommand().replace(" ","").contains("취소"))
		{
			addHour = 0;
			addMinute = 0;
			payment = 0;
			flag = true;
		}
		else if (ae.getActionCommand().replace(" ","").contains("결제")) //결제 처리
		{
			UseDB db = new UseDB();
			try
			{
				String strSql = "select date_format(now(), '%Y-%m-%d %H:%i:%s') as 'ex_time';";
				ResultSet result = db.queryDB(strSql);
				result.next();
				String cur_time = result.getString("ex_time");

				strSql = "insert into trade (date, id, payment) values ('" + cur_time +"', '" + id + "'," + payment +");";
				db.updateDB(strSql);

				strSql = "select number from trade where date='"+ cur_time +"';";
				result = db.queryDB(strSql);
				result.next();
				int num = result.getInt("number");
				String iname = "";
				for (int row = 0; row < price.size(); row++ )
				{
					if ((int)price.get(row) == payment)
					{
						iname = name.get(row)+"";
						break;
					}
				}
				strSql = "insert into trade_detail values (" + num +", '" + iname + "', 1,"+ payment + ");";
				db.updateDB(strSql); 
				hour += addHour;
				minute += addMinute;
				if (minute >= 60)
				{
					hour += minute / 60;
					minute = minute % 60;
				}
				
				strSql = "update user set time='" +String.format("%02d",hour) + ":"+ String.format("%02d",minute) + "' where id='"+id+"';";
				db.updateDB(strSql); 
				db.closeDB();
			} 
			catch(ClassNotFoundException e) 
			{
				System.err.println("드라이버 로드에 실패했습니다."); 
			}
			catch (SQLException e)
			{
				System.out.println("SQLException : " + e.getMessage()); 
			}
			flag = true;
			addHour = 0;
			addMinute = 0;
			payment = 0;
		}
		inquiryTime();
		jlb_addTime.setText("추가시간 :  " + String.format("%02d",addHour) + " : "+ String.format("%02d",addMinute));
		jlb_totalPay.setText("합계요금 \\ " + String.format("%,6d",payment));
	}
	void inquiryTime() //회원의 id를 통해 남은 시간을 조회하는 과정
	{ 
		UseDB db = new UseDB();
		try
		{
			String strSql = "select id,time from user where id='" + id + "';";
			ResultSet result = db.queryDB(strSql); // sql 질의어 실행
			String t_time;
			while(result.next())
			{
				t_time = result.getString("time");
				if (t_time.equals("null"))
				{
					hour=0;
					minute=0;
				}
				else
				{
					hour = Integer.parseInt(t_time.split(":")[0]);
					minute = Integer.parseInt(t_time.split(":")[1]);
				}
			}
			db.closeDB();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
		jlb_restTime.setText("남은시간 :  "+ String.format("%02d",hour) + " : "+ String.format("%02d",minute));
	}
}
/* ------------------------- 시간 추가 끝 --------------------------------------------- */
/* ------------------------- 환불 조회 시작 --------------------------------------------- */
class RefundInqueryForm extends JFrame implements ActionListener //환불조회화면 클래스
{
	JComboBox jcb_start[], jcb_end[];
	Date d = new Date();
	int symd[], eymd[];
	JTextField jtf_id, jtf_num;
	JLabel jlb_todaySales;
	JButton jbtn_search, jbtn_detail, jbtn_delete, jbtn_cancel;
	JTable jtab_list;
	DefaultTableModel model;
	String[] header = {"환불번호", "거래일시", "환불일시", "ID", "금액"};
	String id, num;
	RefundInqueryForm()
	{
		Container ct = getContentPane();

		model = new DefaultTableModel(header,0)
		{
			public boolean isCellEditable(int row, int col)
			{
				return false;
			}
		};
		symd = new int[3];
		eymd = new int[3];
		symd[0] = d.getYear()+1900;
		symd[1] = d.getMonth()+1;
		symd[2] = d.getDate();
		eymd[0] = symd[0];
		eymd[1] = symd[1];
		eymd[2] = symd[2];
		id = "";
		num = "";
		jcb_start = new JComboBox[3];
		jcb_end = new JComboBox[3];
		Vector t_year = new Vector();
		Vector t_month = new Vector();
		Vector t_day = new Vector();
		for (int i = d.getYear()+1900; i > (d.getYear()+1900-30) ; i--)
			t_year.add(i);
		for (int i = 1; i <= (d.getMonth()+1) ; i++)
			t_month.add(i);
		for (int i = 1; i <= 31 ; i++)
			t_day.add(i);
		jcb_start[0] = new JComboBox(t_year);
		jcb_start[1] = new JComboBox(t_month);
		jcb_start[2] = new JComboBox(t_day);
		jcb_end[0] = new JComboBox(t_year);
		jcb_end[1] = new JComboBox(t_month);
		jcb_end[2] = new JComboBox(t_day);

		jtf_id = new JTextField(8);
		jtf_num = new JTextField(8);
		jlb_todaySales = new JLabel("\\");
		jbtn_search = new JButton("검색");
		jbtn_cancel = new JButton("취소");
		jbtn_detail = new JButton("환불상세");
		jbtn_delete = new JButton("삭제");
		jtab_list = new JTable(model);
		JScrollPane sc = new JScrollPane(jtab_list);
		JLabel l2 = new JLabel("    총 환불액");

		JPanel top = new JPanel();
		JPanel center = new JPanel();
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));//
		JPanel right = new JPanel();
		top.add(new JLabel("시작일자 :"));
		for (int i = 0; i < jcb_start.length ; i++ )
		{
			top.add(jcb_start[i]);
			jcb_start[i].setSelectedItem(symd[i]);
		}
		top.add(new JLabel("종료일자 :"));
		for (int i = 0; i < jcb_end.length ; i++ )
		{
			top.add(jcb_end[i]);
			jcb_end[i].setSelectedItem(eymd[i]);
		}
		top.add(new JLabel("ID :"));
		top.add(jtf_id);
		top.add(new JLabel("환불번호 : "));
		top.add(jtf_num);
		top.add(jbtn_search);
		
		center.add(sc);
		right.add(jbtn_detail);
		right.add(jbtn_delete);
		right.add(jbtn_cancel);
		bottom.add(l2);
		bottom.add(jlb_todaySales);

		jbtn_cancel.addActionListener(this);
		jbtn_search.addActionListener(this);
		jbtn_detail.addActionListener(this);
		jbtn_delete.addActionListener(this);
		jtf_id.addActionListener(this);
		jtf_num.addActionListener(this);
		
		ct.add(top,BorderLayout.NORTH);
		ct.add(center,BorderLayout.CENTER);
		ct.add(bottom,BorderLayout.SOUTH);
		ct.add(right,BorderLayout.EAST);

		Font f2 = new Font("돋움", Font.BOLD, 20);
		l2.setFont(f2);
		jlb_todaySales.setFont(f2);
		
		setTitle("환불내역");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
	}
	public void actionPerformed(ActionEvent ae) //버튼 이벤트 처리
	{
		try
		{
			if (ae.getActionCommand().toString().equals("취소"))
			{
				jtf_id.setText("");
				jtf_num.setText("");
			}
			else if (ae.getActionCommand().toString().equals("검색") || ae.getSource() == jtf_id || ae.getSource() == jtf_num )
			{
				for (int i = 0; i < symd.length ; i++ )
				{
					symd[i] = (int)jcb_start[i].getSelectedItem();
					eymd[i] = (int)jcb_end[i].getSelectedItem();
				}
				search();
			}
			else if (ae.getActionCommand().toString().contains("상세"))
			{
				int row = -1; //초기화
				row = jtab_list.getSelectedRow(); //선택값이 있으면 row에 저장
				RefundDetailInqueryForm rif;
				if (row == -1)
					rif = new RefundDetailInqueryForm(); //선택값이 없을때
				else
					rif = new RefundDetailInqueryForm(model.getValueAt(jtab_list.getSelectedRow(),0)+""); //선택값이 있을때
				rif.show();
			}
			else if (ae.getActionCommand().toString().contains("삭제"))
			{
				delete();
				search();
			}
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
	}
	void delete() throws SQLException,ClassNotFoundException //환불을 취소하는 메소드
	{
		UseDB db = new UseDB();
		String number = model.getValueAt(jtab_list.getSelectedRow(),0)+"";
		String strSql = "delete from refund_detail where number='" + number + "';";
		db.updateDB(strSql); // sql 질의어 실행
		strSql = "delete from refund where number='" + number + "';";
		db.updateDB(strSql);
		db.closeDB();
	}
	void search() throws SQLException,ClassNotFoundException //환불내역을 검색하는 메소드
	{
		id = jtf_id.getText();
		num = jtf_num.getText();
		if (id.isEmpty())
			id = "";

		model.setRowCount(0);
		UseDB db = new UseDB();
		String strSql = " select refund.number, date_format(trade.date, '%Y-%m-%d') as tradeDate, date_format(refund.date, '%Y-%m-%d') as refundDate, refund.id, refund from refund, trade "
			+"where trade.number=refund.number and date_format(refund.date, '%Y-%m-%d')between '"
			+ String.format("%04d-%02d-%02d",symd[0],symd[1],symd[2]) +"' and '"
			+ String.format("%04d-%02d-%02d",eymd[0],eymd[1],eymd[2]) +"' and refund.id like '%"+id+"%'";
		if (num.isEmpty())
			strSql += " order by refund.number, tradeDate, refundDate, refund.id;";
		else
			strSql += "and refund.number='"+num+"' order by refund.number, tradeDate, refundDate, refund.id;";

		ResultSet result = db.queryDB(strSql); // sql 질의어 실행

		Object[] t = new Object[5];
		while(result.next())
		{
			int i = 0;
			t[i++] = (result.getString("refund.number"));
			t[i++] = (result.getString("tradeDate"));
			t[i++] = (result.getString("refundDate"));
			t[i++] = (result.getString("refund.id"));
			t[i++] = (result.getString("refund"));
			model.addRow(t);
		}
		db.closeDB();
		int sales = 0;
		for (int i = 0; i < model.getRowCount() ; i++ )
		{
			sales += Integer.parseInt(model.getValueAt(i,4)+"");
		}

		jlb_todaySales.setText(String.format("\\ %,d",sales));
	}
}
class RefundDetailInqueryForm extends JFrame implements ActionListener //환불 상세보기화면 클래스
{
	JTextField jtf_num;
	JButton jbtn_search;
	JLabel jlb_Sales;
	JTable jtab_list;
	DefaultTableModel model;
	String[] header = {"환불번호", "메뉴", "수량", "금액"};
	String number;
	RefundDetailInqueryForm(String num) //선택값이 있을때
	{
		number = num;
		constructor();
		
	}
	RefundDetailInqueryForm() //선택값이 없을때
	{
		number = "";
		constructor();
		
	}
	private void constructor() //공통적인 생성 부분
	{
		Container ct = getContentPane();
		model = new DefaultTableModel(header,0)
		{
			public boolean isCellEditable(int row, int col)
			{
				return false;
			}
		};
		jtf_num = new JTextField(8);
		jbtn_search = new JButton("검색");
		jtab_list = new JTable(model);
		JScrollPane sc = new JScrollPane(jtab_list);
		JLabel l1 = new JLabel("총 환불액");
		jlb_Sales = new JLabel("\\");

		JPanel top = new JPanel();
		JPanel center = new JPanel();
		JPanel bottom = new JPanel();
		top.add(new JLabel("환불번호 : "));
		top.add(jtf_num);
		top.add(jbtn_search);
		center.add(sc);
		bottom.add(l1);
		bottom.add(jlb_Sales);

		ct.add(top,BorderLayout.NORTH);
		ct.add(center,BorderLayout.CENTER);
		ct.add(bottom,BorderLayout.SOUTH);

		Font f2 = new Font("돋움", Font.BOLD, 20);
		l1.setFont(f2);
		jlb_Sales.setFont(f2);

		jbtn_search.addActionListener(this);
		jtf_num.addActionListener(this);
		try
		{
			search();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
		
		setTitle("환불상품내역");
		setSize(600,600);
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent ae) //검색버튼 이벤트
	{
		try
		{
			number = jtf_num.getText();
			search();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
	}
	
	void search() throws ClassNotFoundException, SQLException //환불번호 검색
	{
		model.setRowCount(0);
		UseDB db = new UseDB();
		String strSql = "select * from refund_detail where number='" + number + "';";
		ResultSet result = db.queryDB(strSql); // sql 질의어 실행
		Object[] t = new Object[4];
		while(result.next())
		{
			int i = 0;
			t[i++] = (result.getString("number"));
			t[i++] = (result.getString("goods"));
			t[i++] = (result.getString("amount"));
			t[i++] = (result.getString("refund"));
			model.addRow(t);
		}
		db.closeDB();
		int resales = 0;
		for (int i = 0; i < model.getRowCount() ; i++ )
		{
			resales += Integer.parseInt(model.getValueAt(i,3)+"");
		}

		jlb_Sales.setText(String.format("\\ %,d",resales));
	}	
}
/* ------------------------- 환불 조회 끝 --------------------------------------------- */
/* ------------------------- 거래 조회 시작 --------------------------------------------- */
class TradeInqueryForm extends JFrame implements ActionListener //거래조회화면 클래스
{
	JTextField jtf_id, jtf_num;
	JLabel jlb_todaySales;
	JButton jbtn_search, jbtn_detail, jbtn_refund, jbtn_inquery,jbtn_cancel,jbtn_refundShow;
	JTable jtab_list;
	DefaultTableModel model;
	String[] header = {"거래번호", "거래일시", "ID", "금액"};
	String id, num;
	JComboBox jcb_start[], jcb_end[];
	Date d;
	int symd[], eymd[];
	TradeInqueryForm()
	{
		Container ct = getContentPane();
		model = new DefaultTableModel(header,0)
		{
			public boolean isCellEditable(int row, int col)
			{
				return false;
			}
		};
		d = new Date();
		symd = new int[3];
		eymd = new int[3];
		symd[0] = d.getYear()+1900;
		symd[1] = d.getMonth()+1;
		symd[2] = d.getDate();
		eymd[0] = symd[0];
		eymd[1] = symd[1];
		eymd[2] = symd[2];
		id = "";
		num = "";

		jcb_start = new JComboBox[3];
		jcb_end = new JComboBox[3];
		Vector t_year = new Vector();
		Vector t_month = new Vector();
		Vector t_day = new Vector();
		for (int i = d.getYear()+1900; i > (d.getYear()+1900-30) ; i--)
			t_year.add(i);
		for (int i = 1; i <= (d.getMonth()+1) ; i++)
			t_month.add(i);
		for (int i = 1; i <= 31 ; i++)
			t_day.add(i);
		jcb_start[0] = new JComboBox(t_year);
		jcb_start[1] = new JComboBox(t_month);
		jcb_start[2] = new JComboBox(t_day);
		jcb_end[0] = new JComboBox(t_year);
		jcb_end[1] = new JComboBox(t_month);
		jcb_end[2] = new JComboBox(t_day);
		jtf_id = new JTextField(8);
		jtf_num = new JTextField(8);
		jlb_todaySales = new JLabel("\\");
		jbtn_search = new JButton("검색");
		jbtn_cancel = new JButton("취소");
		jbtn_detail = new JButton("거래상세");
		jbtn_refund = new JButton("환불");
		jbtn_refundShow = new JButton("환불조회");
		jtab_list = new JTable(model);
		JScrollPane sc = new JScrollPane(jtab_list);
		JLabel l2 = new JLabel("    총 매출액");

		JPanel top = new JPanel();
		JPanel center = new JPanel();
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));//
		JPanel right = new JPanel();
		top.add(new JLabel("시작일자 :"));
		for (int i = 0; i < jcb_start.length ; i++ )
		{
			top.add(jcb_start[i]);
			jcb_start[i].setSelectedItem(symd[i]);
		}
		top.add(new JLabel("종료일자 :"));
		for (int i = 0; i < jcb_end.length ; i++ )
		{
			top.add(jcb_end[i]);
			jcb_end[i].setSelectedItem(eymd[i]);
		}
		top.add(new JLabel("ID :"));
		top.add(jtf_id);
		top.add(new JLabel("거래번호 : "));
		top.add(jtf_num);
		top.add(jbtn_search);
		
		center.add(sc);
		right.add(jbtn_detail);
		right.add(jbtn_refund);
		right.add(jbtn_refundShow);
		right.add(jbtn_cancel);
		bottom.add(l2);
		bottom.add(jlb_todaySales);

		jbtn_cancel.addActionListener(this);
		jbtn_search.addActionListener(this);
		jbtn_detail.addActionListener(this);
		jbtn_refundShow.addActionListener(this);
		jbtn_refund.addActionListener(this);
		jtf_id.addActionListener(this);
		jtf_num.addActionListener(this);
		
		ct.add(top,BorderLayout.NORTH);
		ct.add(center,BorderLayout.CENTER);
		ct.add(bottom,BorderLayout.SOUTH);
		ct.add(right,BorderLayout.EAST);

		Font f2 = new Font("돋움", Font.BOLD, 20);
		l2.setFont(f2);
		jlb_todaySales.setFont(f2);
		
		setTitle("거래내역");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
	}
	public void actionPerformed(ActionEvent ae) //버튼 이벤트 처리
	{
		try
		{
			if (ae.getActionCommand().toString().equals("환불조회"))
			{
				RefundInqueryForm rif = new RefundInqueryForm();
				rif.show();
			}
			else if (ae.getActionCommand().toString().equals("취소"))
			{
				jtf_id.setText("");
				jtf_num.setText("");
			}
			else if (ae.getActionCommand().toString().equals("검색") || ae.getSource() == jtf_id || ae.getSource() == jtf_num)
			{
				for (int i = 0; i < symd.length ; i++ )
				{
					symd[i] = (int)jcb_start[i].getSelectedItem();
					eymd[i] = (int)jcb_end[i].getSelectedItem();
				}
				search();
			}
			else if (ae.getActionCommand().toString().contains("상세"))
			{
				TradeDetailInqueryForm tif;
				int row = -1;
				row = jtab_list.getSelectedRow();
				if ( row == -1 )
					tif = new TradeDetailInqueryForm();
				else
					tif = new TradeDetailInqueryForm((model.getValueAt(jtab_list.getSelectedRow(),0)+""));
				tif.show();
			}
			else if (ae.getActionCommand().toString().contains("환불"))
			{
				int row = -1;
				row = jtab_list.getSelectedRow();
				if (row != -1)
				{
					alert("Warning","환불이 되면 환불내역에서 수정이 가능합니다.");
					refund();
					search();
				}
				else
					alert("Warning","내역을 선택후 환불이 가능합니다.");
			}
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
	}
	void search() throws ClassNotFoundException, SQLException //거래 내역 검색 메소드
	{
		id = jtf_id.getText();
		num = jtf_num.getText();

		if (id.isEmpty())
			id = "";

		UseDB db = new UseDB();
		String strSql = "select number, date_format(date, '%Y-%m-%d') as date, id, payment from trade"
			+ " where number not in(select number from refund) and "
			+ "date_format(date, '%Y-%m-%d')between '"
			+ String.format("%04d-%02d-%02d",symd[0],symd[1],symd[2]) +"' and '"
			+ String.format("%04d-%02d-%02d",eymd[0],eymd[1],eymd[2]) +"' and "
			+ "id like '%"+id+"%'";

		if (num.isEmpty())
			strSql += "order by number, date, id;";
		else
			strSql += "and number='"+num+"' order by number, date, id;";
		
		model.setRowCount(0);

		ResultSet result = db.queryDB(strSql); // sql 질의어 실행
		Object[] t = new Object[4];
		while(result.next())
		{
			int i = 0;
			t[i++] = (result.getString("number"));
			t[i++] = (result.getString("date"));
			t[i++] = (result.getString("id"));
			t[i++] = (result.getString("payment"));
			model.addRow(t);
		}
		db.closeDB();

		int sales = 0;
		for (int i = 0; i < model.getRowCount() ; i++ )
		{
			sales += Integer.parseInt(model.getValueAt(i,3)+"");
		}

		jlb_todaySales.setText(String.format("\\ %,d",sales));
	}
	void refund() throws ClassNotFoundException, SQLException
	{
		UseDB db = new UseDB();
		String number = model.getValueAt(jtab_list.getSelectedRow(),0)+"";
		String strSql = "select date_format(now(), '%Y-%m-%d %H:%i:%s') as 'ex_time';";
		ResultSet result = db.queryDB(strSql);
		result.next();
		String cur_time = result.getString("ex_time");
		strSql = "insert into refund(number, date, id, refund) select number,date_format(now(), '%Y-%m-%d %H:%i:%s'),id,payment from trade where number='" + number +"';";
		db.updateDB(strSql); 
		strSql = "insert into refund_detail select * from trade_detail where number='" + number +"';";
		db.updateDB(strSql); 
	}
	void alert(String title, String str) //경고창을 띄어줄 메소드
	{
		Overlap ol = new Overlap(this,title,true,str);
		ol.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);            
		ol.show();
	}
}
class TradeDetailInqueryForm extends JFrame implements ActionListener //거래상세조회 화면 클래스
{
	JTextField jtf_num;
	JButton jbtn_search;
	JLabel jlb_Sales;
	JTable jtab_list;
	DefaultTableModel model;
	String[] header = {"거래번호", "메뉴", "수량", "금액"};
	String number;
	TradeDetailInqueryForm(String num)
	{
		number = num;
		constructor();
	}
	TradeDetailInqueryForm()
	{
		number = "";
		constructor();
	}
	private void constructor()
	{
		Container ct = getContentPane();
		model = new DefaultTableModel(header,0)
		{
			public boolean isCellEditable(int row, int col)
			{
				return false;
			}
		};
		jtf_num = new JTextField(8);
		jbtn_search = new JButton("검색");
		jtab_list = new JTable(model);
		JScrollPane sc = new JScrollPane(jtab_list);
		JLabel l1 = new JLabel("총 매출액");
		jlb_Sales = new JLabel("\\");

		JPanel top = new JPanel();
		JPanel center = new JPanel();
		JPanel bottom = new JPanel();
		top.add(new JLabel("거래번호 : "));
		top.add(jtf_num);
		top.add(jbtn_search);
		center.add(sc);
		bottom.add(l1);
		bottom.add(jlb_Sales);

		ct.add(top,BorderLayout.NORTH);
		ct.add(center,BorderLayout.CENTER);
		ct.add(bottom,BorderLayout.SOUTH);

		Font f2 = new Font("돋움", Font.BOLD, 20);
		l1.setFont(f2);
		jlb_Sales.setFont(f2);

		jbtn_search.addActionListener(this);
		try
		{
			search();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
		
		setTitle("거래상품내역");
		setSize(600,600);
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent ae)  //버튼 이벤트 처리
	{
		try
		{
			number = jtf_num.getText();
			search();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
	}
	
	void search() throws ClassNotFoundException,SQLException //거래번호 검색
	{
		model.setRowCount(0);
		UseDB db = new UseDB();
		String strSql = "select * from trade_detail where number='" + number + "';";
		ResultSet result = db.queryDB(strSql); 
		Object[] t = new Object[4];
		while(result.next())
		{
			int i = 0;
			t[i++] = (result.getString("number"));
			t[i++] = (result.getString("goods"));
			t[i++] = (result.getString("amount"));
			t[i++] = (result.getString("payment"));
			model.addRow(t);
		}
		db.closeDB();
		int sales = 0;
		for (int i = 0; i < model.getRowCount() ; i++ )
		{
			sales += Integer.parseInt(model.getValueAt(i,3)+"");
		}

		jlb_Sales.setText(String.format("\\ %,d",sales));
	}
}
/* ------------------------- 거래 조회 끝 --------------------------------------------- */
/*  ---------------------------- 매출 조회 시작 ------------------------------------ */
class TypeInqueryForm extends JFrame implements ActionListener
{
	DefaultTableModel model;
	DateInquery di;
	UserInquery ui;
	MenuInquery mi;
	TypeInqueryForm()
	{
		JTabbedPane tp = new JTabbedPane();
		di = new DateInquery();
		ui = new UserInquery();
		mi = new MenuInquery();
		tp.addTab("날짜별", di);
		tp.addTab("메뉴별", mi);
		tp.addTab("고객별", ui);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("파일");
		JMenuItem saveDate = new JMenuItem("날짜별파일저장");
		JMenuItem saveUser = new JMenuItem("회원별파일저장");
		JMenuItem saveMenu = new JMenuItem("메뉴별파일저장");
		fileMenu.add(saveDate);
		fileMenu.add(saveUser);
		fileMenu.add(saveMenu);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		saveDate.addActionListener(this);
		saveUser.addActionListener(this);
		saveMenu.addActionListener(this);

		Container ct = getContentPane();
		ct.add(tp);

		setTitle("매출조회");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
	}
	public void actionPerformed(ActionEvent ae) //메뉴바 이벤트 처리 (파일 출력)
	{
		PrintWriter pw = null;
		String fileName = "";
		if (ae.getActionCommand().contains("날짜"))
		{
			model = di.getModel();
			fileName = "_Date.txt";
		}
		else if (ae.getActionCommand().contains("회원"))
		{
			model = ui.getModel();
			fileName = "_User.txt";
		}
		else if (ae.getActionCommand().contains("메뉴"))
		{
			model = mi.getModel();
			fileName = "_Menu.txt";
		}
		try
		{
			UseDB db = new UseDB();
			String strSql = "select date_format(now(), '%Y-%m-%d_%H-%i-%s') as 'ex_time';";
			ResultSet result = db.queryDB(strSql);
			result.next();
			String filePath = "C:\\lib\\"+result.getString("ex_time")+fileName;
			db.closeDB();
			pw = new PrintWriter(filePath);
			for (int i = 0; i < model.getRowCount() ; i++ )
			{
				String t="";
				if (fileName.equals("_Menu.txt"))
					t = String.format("%10s %10s %10s %10s",model.getValueAt(i,0),model.getValueAt(i,1),model.getValueAt(i,2),model.getValueAt(i,3));
				else
					t = String.format("%10s %10s",model.getValueAt(i,0),model.getValueAt(i,1));
				pw.println(t);
			}
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}
		catch (IOException e)
		{
			System.err.println(e);
		}
		finally
		{
			pw.close();
		}
	}
}
class DateInquery extends JPanel implements ActionListener, ItemListener //날짜별 검색 탭화면
{
	JComboBox jcb_start[], jcb_end[];
	JRadioButton jrb_section[];
	JButton jbtn_search;
	JTable jtab_list;
	DefaultTableModel model;
	String[] header = {"날짜", "매출액"};
	String[] sect = {"연도별","월별","일별"};
	Date d = new Date();
	int symd[], eymd[];
	int rbChk;
	DateInquery()
	{
		rbChk = 0;
		symd = new int[3];
		eymd = new int[3];
		symd[0] = d.getYear()+1900;
		symd[1] = d.getMonth()+1;
		symd[2] = d.getDate();
		eymd[0] = symd[0];
		eymd[1] = symd[1];
		eymd[2] = symd[2];
		{ //ct1
			model = new DefaultTableModel(header,0)
			{
				public boolean isCellEditable(int row, int col)
				{
					return false;
				}
			};

			jcb_start = new JComboBox[3];
			jcb_end = new JComboBox[3];
			Vector t_year = new Vector();
			Vector t_month = new Vector();
			Vector t_day = new Vector();
			for (int i = d.getYear()+1900; i > (d.getYear()+1900-30) ; i--)
				t_year.add(i);
			for (int i = 1; i <= (d.getMonth()+1) ; i++)
				t_month.add(i);
			for (int i = 1; i <= 31 ; i++)
				t_day.add(i);
			jcb_start[0] = new JComboBox(t_year);
			jcb_start[1] = new JComboBox(t_month);
			jcb_start[2] = new JComboBox(t_day);
			jcb_end[0] = new JComboBox(t_year);
			jcb_end[1] = new JComboBox(t_month);
			jcb_end[2] = new JComboBox(t_day);
			jbtn_search = new JButton("검색");
			jtab_list = new JTable(model);
			JScrollPane sc = new JScrollPane(jtab_list);

			JPanel top = new JPanel();
			JPanel center = new JPanel();
			JPanel bottom = new JPanel();//
			top.add(new JLabel("시작일자 :"));
			for (int i = 0; i < jcb_start.length ; i++ )
			{
				top.add(jcb_start[i]);
				jcb_start[i].setSelectedItem(symd[i]);
				jcb_start[i].addItemListener(this);
				jcb_start[i].addActionListener(this);
			}
			top.add(new JLabel("종료일자 :"));
			for (int i = 0; i < jcb_end.length ; i++ )
			{
				top.add(jcb_end[i]);
				jcb_end[i].setSelectedItem(eymd[i]);
				jcb_end[i].addItemListener(this);
				jcb_end[i].addActionListener(this);
			}
			top.add(new JLabel("기준 :"));

			ButtonGroup g = new ButtonGroup();
			jrb_section = new JRadioButton[3];
			for (int i = 0; i < sect.length ; i++ )
			{
				jrb_section[i] = new JRadioButton(sect[i],true);
				jrb_section[i].addItemListener(this);
				jrb_section[i].addActionListener(this);
				g.add(jrb_section[i]);
				top.add(jrb_section[i]);
			}
			top.add(jbtn_search);
			center.add(sc);
			
			add(top);
			add(center);

			jbtn_search.addActionListener(this);
			search();
		}
		
	}
	public void actionPerformed(ActionEvent ae) //버튼, 콤보박스, 라디오버튼 이벤트 처리
	{
		model.setRowCount(0);
		search();
	}
	void search() //기준별 검색
	{
		try
		{
			UseDB db = new UseDB();
			String strSql = "";
			if (rbChk == 0)
			{
				strSql = "select date_format(date, '%Y') as date, sum(payment) as payment "
							+ "from trade "
							+ "where number not in(select number from refund)"
							+ "and date_format(date, '%Y-%m-%d') between '"+String.format("%d-%02d-%02d",symd[0],symd[1],symd[2])
							+"' and '"+String.format("%d-%02d-%02d",eymd[0],eymd[1],eymd[2])+"' "
							+ "group by date_format(date, '%Y')"
							+ "order by date desc;";
			}
			else if (rbChk == 1)
			{
				strSql = "select date_format(date, '%Y-%m') as date, sum(payment) as payment "
							+ "from trade "
							+ "where number not in(select number from refund)"
							+ "and date_format(date, '%Y-%m-%d') between '"+String.format("%d-%02d-%02d",symd[0],symd[1],symd[2])
							+"' and '"+String.format("%d-%02d-%02d",eymd[0],eymd[1],eymd[2])+"' "
							+ "group by date_format(date, '%Y-%m')"
							+ "order by date desc;";
			} 
			else if (rbChk == 2)
			{
				strSql = "select date_format(date, '%Y-%m-%d') as date, sum(payment) as payment "
							+ "from trade "
							+ "where number not in(select number from refund)"
							+ "and date_format(date, '%Y-%m-%d') between '"+String.format("%d-%02d-%02d",symd[0],symd[1],symd[2])
							+"' and '"+String.format("%d-%02d-%02d",eymd[0],eymd[1],eymd[2])+"' "
							+ "group by date_format(date, '%Y-%m-%d')"
							+ "order by date desc;";
			} 
			ResultSet result = db.queryDB(strSql);
			while (result.next())
			{
				Vector t = new Vector();
				t.add(result.getString("date")+"");
				t.add(String.format("%,d",result.getInt("payment")));
				model.addRow(t);
			}
			db.closeDB();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}	
	}
	public void itemStateChanged(ItemEvent ie) //날짜를 변경하면 날짜를 저장하는 이벤트
	{	
		if (ie.getStateChange() == 1)  //item이벤트 1번만 실행
		{
			for (int i = 0; i < jcb_start.length ; i++ )
			{
				if (ie.getSource() == jcb_start[i])
					symd[i] = (int)jcb_start[i].getSelectedItem();
				if (ie.getSource() == jcb_end[i])
					eymd[i] = (int)jcb_end[i].getSelectedItem();
			}
		}
		if ( ie.getStateChange() == ItemEvent.DESELECTED ) // 라디오버튼이 선택 해제된 경우 리턴 1번 실행
			return; 
		for (int i = 0; i < jrb_section.length ; i++ )
		{
			if ( jrb_section[i].isSelected() ) 
			{
				rbChk = i; break;
			}
			
		}
	}
	DefaultTableModel getModel(){ return model;} //파일 출력을 위한 테이블 모델 얻어오기
}
class MenuInquery extends JPanel implements ActionListener, ItemListener, ListSelectionListener //메뉴별 검색 탭화면
{
	JComboBox jcb_start[], jcb_end[];
	JCheckBox jchk_section[];
	JButton jbtn_search,jbtn_cancel;
	JTable jtab_list;
	JList jlst_section, jlst_menu;
	DefaultTableModel model;
	String[] header = {"구분","메뉴", "매출액", "판매수량"};
	String[] sect = {"메뉴구분","메뉴"};
	Date d = new Date();
	int symd[], eymd[];
	int rbChk;
	Object section[], menu[];
	MenuInquery()
	{
		rbChk = 2;
		symd = new int[3];
		eymd = new int[3];
		symd[0] = d.getYear()+1900;
		symd[1] = d.getMonth()+1;
		symd[2] = d.getDate();
		eymd[0] = symd[0];
		eymd[1] = symd[1];
		eymd[2] = symd[2];
		setLayout(new BorderLayout());
		{
			model = new DefaultTableModel(header,0)
			{
				public boolean isCellEditable(int row, int col)
				{
					return false;
				}
			};
			jcb_start = new JComboBox[3];
			jcb_end = new JComboBox[3];
			Vector t_year = new Vector();
			Vector t_month = new Vector();
			Vector t_day = new Vector();
			for (int i = d.getYear()+1900; i > (d.getYear()+1900-30) ; i--)
				t_year.add(i);
			for (int i = 1; i <= (d.getMonth()+1) ; i++)
				t_month.add(i);
			for (int i = 1; i <= 31 ; i++)
				t_day.add(i);
			jcb_start[0] = new JComboBox(t_year);
			jcb_start[1] = new JComboBox(t_month);
			jcb_start[2] = new JComboBox(t_day);
			jcb_end[0] = new JComboBox(t_year);
			jcb_end[1] = new JComboBox(t_month);
			jcb_end[2] = new JComboBox(t_day);
			jbtn_search = new JButton("검색");
			jbtn_cancel = new JButton("취소");
			jtab_list = new JTable(model);
			JScrollPane sc = new JScrollPane(jtab_list);

			JPanel top = new JPanel();
			JPanel center = new JPanel();
			JPanel bottom = new JPanel();
			top.add(new JLabel("시작일자 :"));
			for (int i = 0; i < jcb_start.length ; i++ )
			{
				top.add(jcb_start[i]);
				jcb_start[i].setSelectedItem(symd[i]);
				jcb_start[i].addItemListener(this);
				jcb_start[i].addActionListener(this);
			}
			top.add(new JLabel("종료일자 :"));
			for (int i = 0; i < jcb_end.length ; i++ )
			{
				top.add(jcb_end[i]);
				jcb_end[i].setSelectedItem(eymd[i]);
				jcb_end[i].addItemListener(this);
			}
			top.add(new JLabel("기준 :"));

			jchk_section = new JCheckBox[3];
			for (int i = 0; i < sect.length ; i++ )
			{
				jchk_section[i] = new JCheckBox(sect[i],true);
				jchk_section[i].addItemListener(this);
				top.add(jchk_section[i]);
			}
			top.add(jbtn_search);
			top.add(jbtn_cancel);
			center.add(sc);

			JPanel p = new JPanel();
			
			try
			{
				UseDB db = new UseDB();
				String strSql = "select section from menu_section order by number;";
				ResultSet result = db.queryDB(strSql);
				Vector ts = new Vector();
				while (result.next())
				{
					ts.add(result.getString("section"));
				}
				jlst_section = new JList(ts);
				
				strSql = "select name from menu, menu_section where menu.section=menu_section.section order by number, name;";
				result = db.queryDB(strSql);
				Vector tm = new Vector();
				while (result.next())
				{
					tm.add(result.getString("name"));
				}
				jlst_menu = new JList(tm);
				
				db.closeDB();
			}
			catch(ClassNotFoundException e) 
			{
				System.err.println("드라이버 로드에 실패했습니다."); 
			}
			catch (SQLException e)
			{
				System.out.println("SQLException : " + e.getMessage()); 
			}
			p.add(jlst_section);
			p.add(jlst_menu);

			add(top, BorderLayout.NORTH);
			add(p, BorderLayout.EAST);
			add(center, BorderLayout.CENTER);
			add(new JLabel("선택한 기준에 맞게 검색목록을 선택해야 결과가 보입니다"), BorderLayout.SOUTH);

			jbtn_search.addActionListener(this);
			jbtn_cancel.addActionListener(this);
			jlst_menu.addListSelectionListener(this);
			jlst_section.addListSelectionListener(this);
		}
		
	}
	public void actionPerformed(ActionEvent ae) //버튼 이벤트 처리
	{
		model.setRowCount(0);
		search();
		if (ae.getActionCommand().contains("취소"))
		{
			jlst_section.clearSelection();
			jlst_menu.clearSelection();
		}
	}
	void search() //검색 결과 출력
	{
		if (rbChk == -1) //기준이 없으면 실행하지않음
			return;
		try
		{
			UseDB db = new UseDB();
			String strSql = "";
			if (rbChk == 0 && jlst_section.getSelectedIndices().length > 0) //메뉴구분
			{
				strSql = "select section, sum(trade_detail.payment) as payment, sum(trade_detail.amount) as amount "
						+ "from trade,trade_detail,menu "
						+ "where trade.number=trade_detail.number and trade_detail.goods=menu.name "
						+ "and trade.number not in(select number from refund) "
						+ "and date_format(date, '%Y-%m-%d') between '"+String.format("%d-%02d-%02d",symd[0],symd[1],symd[2])
						+ "' and '"+String.format("%d-%02d-%02d",eymd[0],eymd[1],eymd[2])+"' "
						+ "and (";
				String t = "";
				for (int i = 0; i < section.length ; i++ )
					t += "section='"+section[i]+"' ";
				if (section.length > 1)
					t = t.replace("' s", "' or s");
				strSql += t;
				strSql += ") group by section order by payment desc;";
			}
			else if (rbChk == 1 && jlst_menu.getSelectedIndices().length > 0) //메뉴
			{
				strSql = "select goods, sum(trade_detail.payment) as payment, sum(trade_detail.amount) as amount "
						+ "from trade,trade_detail "
						+ "where trade.number=trade_detail.number "
						+ "and trade.number not in(select number from refund) "
						+ "and date_format(date, '%Y-%m-%d') between '"+String.format("%d-%02d-%02d",symd[0],symd[1],symd[2])
						+ "' and '"+String.format("%d-%02d-%02d",eymd[0],eymd[1],eymd[2])+"' "
						+ "and (";
				String t = "";
				for (int i = 0; i < menu.length ; i++ )
					t += "goods='"+menu[i]+"' ";
				if (menu.length > 1)
					t = t.replace("' g", "' or g");
				strSql += t;
				strSql += ")group by goods order by payment desc;";
			} 
			else if (rbChk == 2 && jlst_menu.getSelectedIndices().length > 0 && jlst_section.getSelectedIndices().length > 0) //메뉴구분&메뉴
			{
				strSql = "select section, goods, sum(trade_detail.payment) as payment, sum(trade_detail.amount) as amount "
						+ "from trade,trade_detail,menu "
						+ "where trade.number=trade_detail.number and trade_detail.goods=menu.name "
						+ "and trade.number not in(select number from refund) "
						+ "and date_format(date, '%Y-%m-%d') between '"+String.format("%d-%02d-%02d",symd[0],symd[1],symd[2])
						+ "' and '"+String.format("%d-%02d-%02d",eymd[0],eymd[1],eymd[2])+"' "
						+ "and (";
				String t = "";
				for (int i = 0; i < section.length ; i++ )
					t += "section='"+section[i]+"' ";
				if (section.length > 1)
					t = t.replace("' s", "' or s");
				strSql += t;
				strSql += ")and (";
				t = "";
				for (int i = 0; i < menu.length ; i++ )
					t += "goods='"+menu[i]+"' ";
				if (menu.length > 1)
					t = t.replace("' g", "' or g");
				strSql += t;
				strSql += ") group by section,goods order by payment desc;";
			}
			else
			{
				return;
			}
			ResultSet result = db.queryDB(strSql);
			while (result.next())
			{
				Vector t = new Vector();
				if (rbChk == 2) //메뉴구분&메뉴
				{
					t.add(result.getString("section")+"");
					t.add(result.getString("goods")+"");
				}
				if (rbChk == 1) //메뉴
				{
					t.add("-");
					t.add(result.getString("goods")+"");
				}
				if (rbChk == 0) //메뉴구분
				{
					t.add(result.getString("section")+"");
					t.add("-");
				}
				t.add(String.format("%,d",result.getInt("payment")));
				t.add(result.getString("amount")+"");
				model.addRow(t);
			}
			db.closeDB();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}	
	}
	public void itemStateChanged(ItemEvent ie) //날짜를 변경하면 날짜를 저장하는 이벤트
	{	
		if (ie.getStateChange() == 1)  //item이벤트 1번만 실행
		{
			for (int i = 0; i < jcb_start.length ; i++ )
			{
				if (ie.getSource() == jcb_start[i])
					symd[i] = (int)jcb_start[i].getSelectedItem();
				if (ie.getSource() == jcb_end[i])
					eymd[i] = (int)jcb_end[i].getSelectedItem();
			}
		}
		rbChk = -1;
		if ( jchk_section[0].isSelected() ) //체크버튼의 체크 종류별로 값 변경, 메뉴구분
			rbChk = 0; 
		if ( jchk_section[1].isSelected() )  //메뉴
			rbChk = 1; 
		if ( jchk_section[0].isSelected() && jchk_section[1].isSelected() )  //메뉴구분 & 메뉴
			rbChk = 2;
	}
	public void valueChanged(ListSelectionEvent lse) //메뉴를 선택하면 선택목록을 저장하는 이벤트
	{
		if (!lse.getValueIsAdjusting()) return; //ListSelectionEvent가 2번 실행되지 않도록 함
		if (lse.getSource() == jlst_menu)
		{
			menu = jlst_menu.getSelectedValues();
		}
		if (lse.getSource() == jlst_section)
		{
			section = jlst_section.getSelectedValues();
		}
	}
	DefaultTableModel getModel(){ return model; }
}
class UserInquery extends JPanel implements ActionListener, ItemListener //회원별 검색 탭화면
{
	JComboBox jcb_start[], jcb_end[];
	JButton jbtn_search;
	JTable jtab_list;
	DefaultTableModel model;
	String[] header = {"회원", "매출액"};
	JTextField jtf_id;
	Date d = new Date();
	int symd[], eymd[];
	String searchID;
	UserInquery()
	{
		searchID = "";
		symd = new int[3];
		eymd = new int[3];
		symd[0] = d.getYear()+1900;
		symd[1] = d.getMonth()+1;
		symd[2] = d.getDate();
		eymd[0] = symd[0];
		eymd[1] = symd[1];
		eymd[2] = symd[2];
		{ 
			model = new DefaultTableModel(header,0)
			{
				public boolean isCellEditable(int row, int col)
				{
					return false;
				}
			};

			jcb_start = new JComboBox[3];
			jcb_end = new JComboBox[3];
			Vector t_year = new Vector();
			Vector t_month = new Vector();
			Vector t_day = new Vector();
			for (int i = d.getYear()+1900; i > (d.getYear()+1900-30) ; i--)
				t_year.add(i);
			for (int i = 1; i <= (d.getMonth()+1) ; i++)
				t_month.add(i);
			for (int i = 1; i <= 31 ; i++)
				t_day.add(i);
			jcb_start[0] = new JComboBox(t_year);
			jcb_start[1] = new JComboBox(t_month);
			jcb_start[2] = new JComboBox(t_day);
			jcb_end[0] = new JComboBox(t_year);
			jcb_end[1] = new JComboBox(t_month);
			jcb_end[2] = new JComboBox(t_day);
			jbtn_search = new JButton("검색");
			jtab_list = new JTable(model);
			JScrollPane sc = new JScrollPane(jtab_list);

			JPanel top = new JPanel();
			JPanel center = new JPanel();
			JPanel bottom = new JPanel();//
			top.add(new JLabel("시작일자 :"));
			for (int i = 0; i < jcb_start.length ; i++ )
			{
				top.add(jcb_start[i]);
				jcb_start[i].setSelectedItem(symd[i]);
				jcb_start[i].addItemListener(this);
				jcb_start[i].addActionListener(this);
			}
			top.add(new JLabel("종료일자 :"));
			for (int i = 0; i < jcb_end.length ; i++ )
			{
				top.add(jcb_end[i]);
				jcb_end[i].setSelectedItem(eymd[i]);
				jcb_end[i].addItemListener(this);
				jcb_end[i].addActionListener(this);
			}
			top.add(new JLabel("회원ID :"));

			jtf_id = new JTextField(8);
			top.add(jtf_id);
			top.add(jbtn_search);
			center.add(sc);
			
			add(top);
			add(center);

			jbtn_search.addActionListener(this);
			jtf_id.addActionListener(this);
			search();
		}
	}
	public void actionPerformed(ActionEvent ae) //버튼, 텍스트필드 이벤트 처리
	{
		model.setRowCount(0);
		searchID = jtf_id.getText();
		search();
	}
	void search() //검색결과 출력
	{
		try
		{
			UseDB db = new UseDB();
			String strSql = "select id, sum(payment) as payment "
							+"from trade "
							+"where number not in(select number from refund) "
							+"and id like '%"+searchID+"%' "
							+"and date_format(date, '%Y-%m-%d') between '"+String.format("%d-%02d-%02d",symd[0],symd[1],symd[2])
							+"' and '"+String.format("%d-%02d-%02d",eymd[0],eymd[1],eymd[2])+"' "
							+"group by id  order by payment desc;";
			
			ResultSet result = db.queryDB(strSql);
			while (result.next())
			{
				Vector t = new Vector();
				t.add(result.getString("id")+"");
				t.add(String.format("%,d",result.getInt("payment")));
				model.addRow(t);
			}
			db.closeDB();
		}
		catch(ClassNotFoundException e) 
		{
			System.err.println("드라이버 로드에 실패했습니다."); 
		}
		catch (SQLException e)
		{
			System.out.println("SQLException : " + e.getMessage()); 
		}	
	}
	public void itemStateChanged(ItemEvent ie) //날짜를 변경하면 날짜를 저장하는 이벤트
	{	
		if (ie.getStateChange() == 1)  //item이벤트 1번만 실행
		{
			for (int i = 0; i < jcb_start.length ; i++ )
			{
				if (ie.getSource() == jcb_start[i])
					symd[i] = (int)jcb_start[i].getSelectedItem();
				if (ie.getSource() == jcb_end[i])
					eymd[i] = (int)jcb_end[i].getSelectedItem();
			}
		}
	}
	DefaultTableModel getModel(){ return model; } //출력을 위해 모델을 리턴하는 메소드
}
/* --------------------------- 매출 조회 끝 ---------------------------------------- */