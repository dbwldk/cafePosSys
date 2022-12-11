package cafe.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cafe.dao.cafeDAO;
import cafe.vo.cafeVO;

public class cafePosView extends JPanel implements Runnable{
	//전역
	ArrayList<cafeVO> cafeVOList;
	
	JPanel[] pans; //for menu tab
	GridBagConstraints c; //for gridbag
	
	ArrayList<String> btnsHeaders; //menu header(type)
	ArrayList<String> btnNames; //menu name
	
	JTabbedPane tab;
	
	JPanel info;
	JLabel lblMenu;
	JLabel lblTotal;
	
	JLabel lblCal = new JLabel("", JLabel.RIGHT);
	Calendar calendar;
	
	JButton btnStop;
	JButton btnPay;
	
	//
	public cafePosView() {
		//
		GridBagLayout gb = new GridBagLayout();
		setLayout(gb);
		
		c = new GridBagConstraints();
		
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		c.fill = GridBagConstraints.BOTH;
		//전체 레이아웃을 그리드백으로 설정
		
		//
		lblCal.setForeground(Color.BLUE);
		lblCal.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		//시계 라벨
		
		//
		tab = new JTabbedPane(JTabbedPane.TOP);
		btnsHeaders = new ArrayList<>();
		btnNames = new ArrayList<>();
		//메뉴 선택 탭
		
		//
		btnStop = new JButton("운영 일시중지");
		btnPay = new JButton("결제");
		//버튼
		
		//
		info = new JPanel(new GridLayout(4, 2));
		lblMenu = new JLabel("안녕");
		lblTotal = new JLabel("테스트야");
		//
		
		info.setBackground(Color.yellow);
		info.add(lblMenu);
		info.add(lblTotal);
		//정보 패널
		
		//그리드백 배치
		lay(lblCal, 9, 0, 10, 1);
		lay(tab, 1, 1, 7, 7);
		lay(btnStop, 1, 9, 3, 2);
		lay(btnPay, 4, 9, 3, 2);
		lay(info, 9, 2, 6, 9);
		
		//쓰레드
		Thread t = new Thread(this);
		t.start();
	}
	
	//그리드백 레이아웃 배치 함수
	public void lay(Component obj, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		add(obj, c);
	}
	
	
	//시계
	@Override
	public void run() {
		while (true) {
			calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DATE);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			int second = calendar.get(Calendar.SECOND);
			
			lblCal.setText((month + 1) + "월" + day + "일 " + hour + ":" + minute + ":" + second);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//버튼 넘기기: stop
	public JButton getBtnStop() {
		return btnStop;
	}
	
	//버튼 넘기기: pay
		public JButton getBtnPay() {
			return btnPay;
		}
	
	//volist 설정하기
	public void setCafeVOList(ArrayList<cafeVO> cafeVOList) {
		this.cafeVOList = cafeVOList;
	}
	
	//메뉴 타입 설정하기
	public ArrayList<String> setMenuHeader() {
		cafeVO vo = null;
		
		for(int i = 0; i < cafeVOList.size(); i++) {
			vo = cafeVOList.get(i);
			
			btnsHeaders.add(vo.getTypeName());
			
			if(i == cafeVOList.size() - 1) {
				//System.out.println(btnsHeaders.toString());
			}
		}
		
		return btnsHeaders;
	}
	
	//메뉴 버튼 설정하기
	public void setMenuBtns() {
			cafeVO vo = null;
			btnNames.clear();
			
			for(int i = 0; i < cafeVOList.size(); i++) {
				vo = cafeVOList.get(i);
				
				btnNames.add(vo.getMenuName());
			}
		}
	
	//메뉴 타입별로 메뉴 버튼 만들기
	public void setMenu(int i) {
		pans = new JPanel[btnsHeaders.size()];
		
		pans[i] = new JPanel(new GridLayout(0, 4));
		
		JButton[] btns = new JButton[btnNames.size()];
			
		for (int j = 0; j < btnNames.size(); j++) {
			btns[j] = new JButton(btnNames.get(j));
				
			pans[i].add(btns[j]);
		}
		
		tab.add(btnsHeaders.get(i), pans[i]);
	}

}
