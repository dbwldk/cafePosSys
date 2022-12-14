package cafe.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cafe.vo.cafeVO;

public class cafeSellView extends JPanel {
	DefaultTableModel model;
	ArrayList<cafeVO> cafeVOList;
	
	JTable table;
	JPanel panS;
	
	String[] header = {"주문번호", "주문시각", "메뉴"};
	JLabel[] lbls = new JLabel[header.length];
	
	public cafeSellView() {
		setLayout(new BorderLayout());
	}
	
	public void initView() {
		model = new DefaultTableModel(header, cafeVOList.size()) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(model);
		
		//table 칼럼 너비 조정
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		
		JScrollPane scroll = new JScrollPane(table);
		
		putSearchResult();
		
		add("Center", scroll);
	}
	
	//default table model에 도서 정보 설정
	public void putSearchResult() {
		//결과행 개수만큼 나오게
		model.setRowCount(cafeVOList.size());
		//
		cafeVO vo = null;
		
		for (int i = 0; i < cafeVOList.size(); i++) {
			vo = cafeVOList.get(i);
			
			//i: 행번호 , 0 - 2:열번호
			model.setValueAt(vo.getSellId(), i, 0);
			model.setValueAt(vo.getSellTime(), i, 1);
			model.setValueAt(vo.getMenuId(), i, 2);
		}
	}
	
	//
	public void setCafeVOList(ArrayList<cafeVO> cafeVOList) {
		this.cafeVOList = cafeVOList;
	}
	
	//
	public JTable getTable() {
		return table;
	}
}
