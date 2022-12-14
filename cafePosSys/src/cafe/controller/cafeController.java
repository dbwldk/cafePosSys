package cafe.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import cafe.dao.cafeDAO;
import cafe.view.cafeIngView;
import cafe.view.cafeMenuView;
import cafe.view.cafePosView;
import cafe.view.cafeSellView;
import cafe.vo.cafeVO;

public class cafeController extends JFrame {
	//전역
	cafePosView posPan;
	cafeMenuView menuPan;
	cafeIngView ingPan;
	cafeSellView sellPan;
	
	cafeDAO dao;
	cafeVO vo;
	ArrayList<cafeVO> cafeVOList;
	
	JComboBox<String> combo;
	JTable table;
	
	JButton btnStop;
	int st_cnt = 0;
	JButton btnRst;
	JButton btnPay;
	
	ArrayList<JButton> menuBtns;
	ArrayList<Integer> menuCnts;
	ArrayList<Integer> whatMenu;
	
	
	JPanel resultPan;
	
	int preCnt = 0;
	
	JLabel lblMenuClick; 
	JLabel lblTotal;
	
	int total = 0;
	
	
	public cafeController() {
		JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
		
		dao = new cafeDAO();
		vo = null;
		
		//posPan
		posPan = new cafePosView();
		tab.add("Pos", posPan);
		
		cafeVOList = dao.select_def(3); //menu_type 테이블을 모두 받아옴
		int typeSize = cafeVOList.size();
		
		posPan.setCafeVOList(cafeVOList);
		
		ArrayList<String> btnsHeaders = posPan.setMenuHeader();
		System.out.println(btnsHeaders);
		
		cafeVOList = dao.select_def(2);
		
		
		for(int a = 0; a < typeSize; a++) {
			cafeVOList = dao.select_where(3, 5, btnsHeaders.get(a));
			vo = cafeVOList.get(0); //타입 이름이 같은 항목은 해당 테이블에서 하나밖에 없으므로
			int ind = vo.getMenuType(); //type_id 값
			
			cafeVOList = dao.select_where(2, 4, ind+""); //menu 테이블에서 위에서 받은 type_id를 참조하는 모든 메뉴를 받아옴
			
			posPan.setCafeVOList(cafeVOList);
			
			posPan.setMenuBtns();
			posPan.setMenu(a);
		}
		
		menuBtns = posPan.getMenuBtns();
		menuCnts = new ArrayList<>();
		whatMenu = new ArrayList<>();
		
		btnStop = posPan.getBtnStop();
		btnRst = posPan.getBtnRst();
		btnPay = posPan.getBtnPay();
		
		resultPan = posPan.getResultPan();
		
		for(int i = 0; i < menuBtns.size(); i++) {
			menuBtns.get(i).addActionListener(btnL); //모든 메뉴 버튼에 리스너 설정
			menuCnts.add(0); //메뉴버튼의 size와 동일하게 만들어짐
		}
		
		btnStop.addActionListener(btnL);
		btnRst.addActionListener(btnL);
		btnPay.addActionListener(btnL); //운영 일시중지, 리셋, 결제 버튼에 리스너 설정
		
		/*
		
		//menuPan
		menuPan = new cafeMenuView();
		tab.add("메뉴", menuPan);
		
		
		//ingPan
		ingPan = new cafeIngView();
		tab.add("재료", ingPan);
		*/
		
		//sellPan
		sellPan = new cafeSellView();
		tab.add("주문 내역", sellPan);
		
		cafeVOList = dao.select_def(6);
		sellPan.setCafeVOList(cafeVOList);
		sellPan.initView();
		
		table = sellPan.getTable();
		
		//창 설정
		add(tab);
		setTitle("가게관리");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(600, 300, 800, 600);
		setVisible(true);
		
	}
	
	//actionlistener
	ActionListener btnL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnStop) {
				st_cnt++;
				
				if(st_cnt % 2 == 1) {
					//홀수 번 누를 경우
					//운영 일시중지 = 버튼 비활성화
					for(int i = 0; i < menuBtns.size(); i++) {
						menuBtns.get(i).setEnabled(false);
					}
					btnRst.setEnabled(false);
					btnPay.setEnabled(false);
				} else {
					//짝수 번 누를 경우
					//운영 재개 = 버튼 활성화
					for(int i = 0; i < menuBtns.size(); i++) {
						menuBtns.get(i).setEnabled(true);
					}
					btnRst.setEnabled(true);
					btnPay.setEnabled(true);
				}
				
			} else if(e.getSource() == btnRst) {
				total = 0;
				menuCnts.clear();
				whatMenu.clear();
				
				for(int i = 0; i < menuBtns.size(); i++) {
					menuCnts.add(0); //메뉴버튼의 size와 동일하게 만들어짐
				}
				
				resultPan.removeAll(); //결과 패널 초기화
				
			} else if(e.getSource() == btnPay) {
				if(whatMenu != null) {
					String datetime = posPan.getTime();
					
					String[] res1 = datetime.split(" "); //공백 기준으로 날짜와 시간을 분리
					String date = res1[0];
					String time = res1[1];
					
					String[] res2 = date.split("-");
					date = res2[0] + res2[1] + res2[2]; //날짜 yyyyddmm 으로
					
					String[] res3 = time.split(":");
					time = res3[0] + res3[1] + res3[2]; //시간 hhmmss 으로
					
					vo = null;
					
					for(int i = 0; i < whatMenu.size(); i++) {
						int j = whatMenu.get(i);
						
						String menu = menuBtns.get(j).getActionCommand();
						int menuCnt = menuCnts.get(j);
						
						cafeVOList = dao.select_where(2, 7, menu); //클릭된 메뉴의 정보를 받기 위함
						vo = cafeVOList.get(0);
						
						String menuId = vo.getMenuId();
						
						int price = vo.getPrice();
						int total = price * menuCnt;
						
						String sellId = date + "_" + time + "_" + menuId;
						//sellId, sellTime, menuId, menuCnt, total
						
						vo = new cafeVO();
						vo.setSellId(sellId);
						vo.setSellTime(datetime);
						vo.setMenuId(menuId);
						vo.setSellCnt(menuCnt);
						vo.setTotal(total);
						
						dao.insert(vo, 6);
						
						vo = null;
				}
				
				}
				
				
			} else {
				
				resultPan.removeAll(); //결과 패널 초기화
				
				String btnName = e.getActionCommand(); //클릭된 버튼 이름 = menu_name 받아오기
				
				for(int i = 0; i < menuBtns.size(); i++) {
					if(menuBtns.get(i).getActionCommand().equals(btnName)) {
						menuCnts.set(i, menuCnts.get(i) + 1);
						//위에서 menuBtns와 menuCnts의 크기를 동일하게 맞춤, 
						//menuBtns(i)(메뉴) -> menuCnts(i)(가격)
					}
				}
				
				ArrayList<Integer> nowMenu = new ArrayList<>();
				
				for(int i = 0; i < menuCnts.size(); i++) {
					if(menuCnts.get(i) != 0) {
						nowMenu.add(i);
					}
				}
				//menuCnts를 모두 0으로 초기화했으므로
				//cnt의 값이 변경된, 즉 클릭이 된 적이 있는 메뉴
				
				JLabel menulbl;
				total = 0;
				
				for(int i = 0; i < nowMenu.size(); i++) {
					int j = nowMenu.get(i);
					
					String menu = menuBtns.get(j).getActionCommand();
					int menuCnt = menuCnts.get(j);
					
					menulbl = new JLabel(menu + " * " + menuCnt);
					resultPan.add(menulbl);
					//클릭된 메뉴 모두 lbl로 출력
					
					
					cafeVOList = dao.select_where(2, 7, menu); //클릭된 메뉴의 정보를 받기 위함
					vo = cafeVOList.get(0);
					
					int price = vo.getPrice();
					
					total += price * menuCnt;
					//총 가격 계산하기
				} 
				
				resultPan.add(new JLabel(" ")); //가격과 메뉴 사이에 공간 주기
				
				lblTotal = new JLabel("총액: " + total);
				lblTotal.setForeground(Color.blue);
				resultPan.add(lblTotal);
				
				//마지막에 지금까지 클릭된 모든 메뉴항목 넘기기
				whatMenu = nowMenu;
			}
		}
	};
	
	//main
	public static void main(String[] args) {
		new cafeController();
	}

}
