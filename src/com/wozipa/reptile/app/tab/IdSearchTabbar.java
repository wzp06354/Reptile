package com.wozipa.reptile.app.tab;

import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import com.wozipa.reptile.data.db.DBConnection;
import com.wozipa.reptile.data.db.IdDBData;

public class IdSearchTabbar extends CTabItem{
	
	private static final Log LOG=LogFactory.getLog(IdSearchTabbar.class);
	
	private static final String[] HEADERS={"加密ID","真实ID","结果位置"};
	
	private CTabFolder parent;
	private int style;
	private Composite composite;
	
	private Text input;
	private Text output;
	private Table table=null;
	private Runtime runtime;
	
	public IdSearchTabbar(CTabFolder parent, int style) {
		super(parent, style);
		this.parent=parent;
		this.style=style;
		this.setText("ID功能");
		this.setShowClose(true);
		this.composite=new Composite(parent,style);
		this.setControl(composite);
		//
		composite.setLayout(new GridLayout(3, false));
		composite.setBackground(new Color(null, new RGB(255, 255, 255)));
		//
		runtime=Runtime.getRuntime();
	}
	
	public void createContent()
	{
		createInputArea();
		createResultArea();
		
	}
	
	public void createInputArea()
	{
		Label label=new Label(composite, SWT.NONE);
		label.setText("输入ID");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		label.setBackground(new Color(null, new RGB(255, 255, 255)));
		//
		input=new Text(composite,SWT.BORDER);
		input.setLayoutData(new GridData(SWT.FILL,SWT.CENTER, true, false, 1, 1));
		//
		Button button=new Button(composite, SWT.BUTTON1);
		button.setText("进行获取");
		button.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,1,1));
		
		button.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				String id=input.getText();
				if(id==null || id.isEmpty())
				{
					MessageBox box=new MessageBox(parent.getShell());
					box.setMessage("请输入商品的ID值");
					box.open();
					return;
				}
//				FileConnection connectin=(FileConnection) ConnManager.getInstance().getConnection(IdFileData.class);
//				if(connectin.existedId(id))
//				{
//					output.setText(connectin.getGoodId(id));
//				}
				System.out.println(id);
				table.removeAll();
				DBConnection connection=new DBConnection();
				List<IdDBData> datas=connection.search(id);
				System.out.println(datas.size());
				for(IdDBData data:datas)
				{
					TableItem item=new TableItem(table, style);
					item.setText(new String[]{data.getEnId(),data.getOgId(),data.getResult()});
				}
				connection.close();
			}
		});
	}
	
	public void createResultArea()
	{
//		Label label=new Label(composite, SWT.NONE);
//		label.setText("商品ID");
//		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
//		label.setBackground(new Color(null, new RGB(255, 255, 255)));
//		//
//		output=new Text(composite,SWT.BORDER);
//		output.setLayoutData(new GridData(SWT.FILL,SWT.CENTER, true, false, 2, 1));
		table=new Table(composite, SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true,true,3,1));
		table.setHeaderVisible(true);
		for(String header:HEADERS)
		{
			TableColumn tableColumn=new TableColumn(table, style);
			tableColumn.setText(header);
			tableColumn.setToolTipText(header);
			tableColumn.pack();
		}
		table.pack();
		table.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent arg0) {
				// TODO Auto-generated method stub
				 TableColumn[] columns = table.getColumns();
                 int clientWidth = table.getBounds().width;
                for(int i=0;i<columns.length;i++){
                    columns[i].setWidth((clientWidth)/columns.length);
                }
                //
			}
		});
		//
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				Table table=(Table) e.getSource();
				TableItem item=table.getSelection()[0];
				String resultPath=item.getText(2);
				System.out.println(item.getText(2));
				try {
					runtime.exec("explorer "+resultPath);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void createCopyArea()
	{
		Label label=new Label(composite, SWT.NONE);
		label.setText("输入ID");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		label.setBackground(new Color(null, new RGB(255, 255, 255)));
		//
		input=new Text(composite,SWT.BORDER);
		input.setLayoutData(new GridData(SWT.FILL,SWT.CENTER, true, false, 1, 1));
		//
		Button button=new Button(composite, SWT.BUTTON1);
		button.setText("进行获取");
		button.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false,1,1));
		
		button.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				String id=input.getText();
				if(id==null || id.isEmpty())
				{
					MessageBox box=new MessageBox(parent.getShell());
					box.setMessage("请输入商品的ID值");
					box.open();
					return;
				}
//				FileConnection connectin=(FileConnection) ConnManager.getInstance().getConnection(IdFileData.class);
//				if(connectin.existedId(id))
//				{
//					output.setText(connectin.getGoodId(id));
//				}
				table.removeAll();
				DBConnection connection=new DBConnection();
				List<IdDBData> datas=connection.search(id);
				for(IdDBData data:datas)
				{
					TableItem item=new TableItem(table, style);
					item.setText(new String[]{data.getEnId(),data.getOgId(),data.getResult()});
				}
				connection.close();
			}
		});
	}
}
