package warmGame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btn,btn2,btn3;
    JLabel paneltitle;
    MainFrame mframe;
    String str;
    
    public LoadPanel(MainFrame m,String s){
        mframe = m;
        str = s;
        this.setName("lpanel");
        this.setLayout(null);
        this.setSize(800, 800);
        paneltitle = new JLabel("これは"+getClass().getCanonicalName()+"クラスのパネルです");
        paneltitle.setBounds(0, 5, 400, 40);
        this.add(paneltitle);
        
        btn = new JButton("GamePanelに移動");
        btn.setBounds(325, 700, 150, 40);
        btn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mframe.PanelNames[2]);
            }
        });
        this.add(btn);
        
        
        this.setBackground(Color.getHSBColor(65, 0.7f, 0.9f));
    }
    
    public void pc(String str){
        mframe.PanelChange((JPanel)this, str);
    }
}
